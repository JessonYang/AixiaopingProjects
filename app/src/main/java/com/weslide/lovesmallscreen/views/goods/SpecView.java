package com.weslide.lovesmallscreen.views.goods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Spec;
import com.weslide.lovesmallscreen.models.SpecItem;
import com.weslide.lovesmallscreen.models.SpecNote;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.views.widget.SpecTagGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/18.
 * 规格列表显示View
 */
public class SpecView extends LinearLayout {

    //服务器返回的规格信息
    List<Spec> mSpecList;
    //服务器返回的有关规格的库存、价格等信息
    List<SpecNote> mSpecNoteList;
    private OnSpecItemViewSelectListener mOnSpecItemViewSelectListener;

    //规格的展示信息
    List<SpecItemView> mSpecItemViewList = new ArrayList<>();
    //多个规格的数据列表
    List<List<SpecTagGroup.Tag>> mSpecTagList;

    public SpecView(Context context) {
        super(context);

        initView();
    }

    public SpecView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public SpecView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        setOrientation(LinearLayout.VERTICAL);
    }

    public void show(List<Spec> specList, List<SpecNote> specNoteList) {
        mSpecList = specList;
        mSpecNoteList = specNoteList;

        loadView();

        mSpecTagList = new ArrayList<>();
        for (Spec spec: mSpecList){
            List<SpecTagGroup.Tag> tags = new ArrayList<>();
            for (SpecItem item: spec.getSpecItems() ) {
                SpecTagGroup.Tag tag = new SpecTagGroup.Tag();
                tag.tagName = item.getSpecItemName();
                tag.tagId = item.getSpecItemId();
                tag.specNoteList = specNoteList;
                tag.specList = mSpecList;
                tag.tags = tags;
                tags.add(tag);

            }
            mSpecTagList.add(tags);
        }
        handlerCannotSelectStockNumber();
        changeTagView();

    }

    private void loadView() {
        for (Spec spec: mSpecList) {
            SpecItemView itemView = new SpecItemView(getContext(), spec);
            mSpecItemViewList.add(itemView);
            addView(itemView);
        }
    }

    private void changeTagView(){
        for (int i = 0;i < mSpecItemViewList.size();i++){
            mSpecItemViewList.get(i).setNote(mSpecTagList.get(i));
        }
    }


    /**
     * 处理默认可选中的规格
     */
    private void handlerCannotSelectStockNumber(){
        for (List<SpecTagGroup.Tag> tags: mSpecTagList) {
            for (SpecTagGroup.Tag tag : tags){

                for (SpecNote specNote : mSpecNoteList){
                    for (String key : specNote.getKey()){
                        if(tag.tagId.equals(key) && specNote.getStockNumber() > 0){
                            tag.status = SpecTagGroup.STATUS_UN_SELECT;
                        }
                    }
                }

            }
        }
    }

    /**
     * 处理当前选中项对应的库存可选项
     * @param keys
     */
    public void handlerStockNumber(String[] keys){

        long start = System.currentTimeMillis();

        for (List<SpecTagGroup.Tag> tags: mSpecTagList) {
            for (SpecTagGroup.Tag tag : tags) {
                tag.status = tag.verifyInventory(keys);
            }
        }

        long end = System.currentTimeMillis();

        L.e("处理耗时：" + (end - start));

    }

    public String[] getSelect(){
        List<String> _value = new ArrayList<>();
        for (List<SpecTagGroup.Tag> tags: mSpecTagList) {
            for (SpecTagGroup.Tag tag : tags){
                if(tag.status == SpecTagGroup.STATUS_SELECT){
                    _value.add(tag.tagId);
                }

            }
        }

        return _value.toArray(new String[_value.size()]);
    }

    public OnSpecItemViewSelectListener getOnSpecItemViewSelectListener() {
        return mOnSpecItemViewSelectListener;
    }

    public void setOnSpecItemViewSelectListener(OnSpecItemViewSelectListener mOnSpecItemViewSelectListener) {
        this.mOnSpecItemViewSelectListener = mOnSpecItemViewSelectListener;
    }


    /**
     * 规格
     */
    class SpecItemView extends FrameLayout {

        @BindView(R.id.tv_spec_name)
        TextView tvSpecName;
        @BindView(R.id.stg_spec)
        SpecTagGroup stgSpec;

        Spec mSpec;

        public SpecItemView(Context context, Spec spec) {
            super(context);
            mSpec = spec;
            initView();
        }

        public SpecItemView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initView();
        }

        public SpecItemView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initView();
        }

        private void initView() {
            LayoutInflater.from(getContext()).inflate(R.layout.goods_view_spec_item, this, true);

            ButterKnife.bind(this);

            tvSpecName.setText(mSpec.getName());
            stgSpec.setOnTagClickListener(new SpecItemViewOnTagClickListener());

        }

        public void setNote(List<SpecTagGroup.Tag> tags){
            stgSpec.setSpecTag(tags);

        }



    }


    /**
     * 标签选中事件
     */
    public interface OnSpecItemViewSelectListener {
        /**
         * 选中
         * @param specNote
         */
        void select(SpecNote specNote, String[] selectKeys);
    }

    /**
     * 根据库存改变各个规格的选中可用情况
     */
    class SpecItemViewOnTagClickListener implements SpecTagGroup.OnTagClickListener {

        @Override
        public void onTagClick(SpecTagGroup.Tag tag) {

            String[] selectKeys = getSelect();
            handlerStockNumber(selectKeys);
            changeTagView();

            mOnSpecItemViewSelectListener.select(SpecTagGroup.Tag.getSpecNote(mSpecNoteList, selectKeys), selectKeys);



        }
    }
}
