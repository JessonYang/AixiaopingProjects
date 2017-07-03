package com.weslide.lovesmallscreen.views.dialogs;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.weslide.lovesmallscreen.models.GoodsType;

import net.aixiaoping.library.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xu on 2016/6/23.
 * 级联类型选择视图
 */
public class SecondaryClassificationDialog extends android.app.Dialog {

    List<GoodsType> mTypes = new ArrayList<>();
    List<GoodsType> mTowTypes = new ArrayList<>();
    ListView lvOneLevel;
    BaseAdapter oneLevelAdapter;
    ListView lvTowLevel;
    BaseAdapter towLevelAdapter;
    int top;

    OnClassificationSelectListener mOnClassificationSelectListener;

    public SecondaryClassificationDialog(Context context, List<GoodsType> types, int top) {
        super(context, R.style.secondaryClassificationDialog);
        mTypes = types;
        this.top = top;
        mTowTypes.addAll(getSelectGoodsTypeItems());
    }

    public SecondaryClassificationDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SecondaryClassificationDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setOnClassificationSelectListener(OnClassificationSelectListener onClassificationSelectListener) {
        mOnClassificationSelectListener = onClassificationSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_secondary_classification);
        lvOneLevel = (ListView) findViewById(R.id.lv_one_level);
        lvTowLevel = (ListView) findViewById(R.id.lv_tow_level);

        oneLevelAdapter = new OneLevelAdapter(getContext(), mTypes);
        towLevelAdapter = new TowLevelAdapter(getContext(), mTowTypes);

        lvOneLevel.setAdapter(oneLevelAdapter);
        lvTowLevel.setAdapter(towLevelAdapter);

        lvOneLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTowTypes.clear();
                resetGoodsTypeSelect();
                mTypes.get(position).setSelect(true);
                mTowTypes.addAll(mTypes.get(position).getTypeItems());

                oneLevelAdapter.notifyDataSetChanged();
                towLevelAdapter.notifyDataSetChanged();
            }
        });

        lvTowLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetGoodsTypeItemSelect();
                mTowTypes.get(position).setSelect(true);
                if (mOnClassificationSelectListener != null)
                    mOnClassificationSelectListener.select(mTowTypes.get(position));
                dismiss();
            }
        });

    }

    @Override
    public void show() {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        getWindow().setGravity(Gravity.LEFT | Gravity.TOP);
        layoutParams.x = 0;
        layoutParams.y = top;
        getWindow().setAttributes(layoutParams);
        super.show();
    }

    public List<GoodsType> getSelectGoodsTypeItems(){
        for (GoodsType goodsType : mTypes) {
            if(goodsType.getSelect()){
                return goodsType.getTypeItems();
            }
        }

        return mTypes.get(0).getTypeItems();
    }

    public void resetGoodsTypeSelect() {
        for (GoodsType goodsType : mTypes) {
            goodsType.setSelect(false);

        }
    }

    public void resetGoodsTypeItemSelect(){
        for (GoodsType goodsType : mTypes) {
            if (goodsType.getTypeItems() != null) {
                for (GoodsType item : goodsType.getTypeItems()) {
                    item.setSelect(false);
                }
            }

        }
    }

    public GoodsType getParentGoodsType(GoodsType type) {
        for (GoodsType goodsType : mTypes) {
            if (goodsType.getTypeItems() != null) {
                for (GoodsType item : goodsType.getTypeItems()) {
                    if (item.getTypeId().equals(type.getTypeId())) {
                        return goodsType;
                    }
                }
            }

        }
        return null;
    }

    /**
     * 分类项选中事件
     */
    public interface OnClassificationSelectListener {
        void select(GoodsType type);
    }

    class OneLevelAdapter extends BaseAdapter {

        Context mContext;
        List<GoodsType> mTypes;

        public OneLevelAdapter(Context context, List<GoodsType> types) {
            mContext = context;
            mTypes = types;
        }

        @Override
        public int getCount() {
            return mTypes.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.view_secondary_classification_one_item, parent, false);
            }

            TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
            ImageView tvToClassification = (ImageView) convertView.findViewById(R.id.iv_to_classification);

            //判断该分类是否还有子分类
            if (mTypes.get(position).getTypeItems() == null && mTypes.get(position).getTypeItems().size() == 0) {
                tvToClassification.setVisibility(View.GONE);
            } else {

                tvToClassification.setVisibility(View.VISIBLE);
            }

            if (mTypes.get(position).getSelect()) {
                convertView.findViewById(R.id.layout_classification_tow).setBackgroundColor(mContext.getResources().getColor(R.color.line));
            } else {
                convertView.findViewById(R.id.layout_classification_tow).setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }

            tvName.setText(mTypes.get(position).getTypeName());

            return convertView;
        }
    }


    class TowLevelAdapter extends BaseAdapter {

        Context mContext;
        List<GoodsType> mTypes;

        public TowLevelAdapter(Context context, List<GoodsType> types) {
            mContext = context;
            mTypes = types;
        }

        @Override
        public int getCount() {
            return mTypes.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.view_secondary_classification_tow_item, parent, false);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
            tvName.setText(mTypes.get(position).getTypeName());

            if (mTypes.get(position).getSelect()) {
                convertView.findViewById(R.id.layout_classification_tow).setBackgroundColor(mContext.getResources().getColor(R.color.line));
                convertView.findViewById(R.id.iv_select).setVisibility(View.VISIBLE);
            } else {
                convertView.findViewById(R.id.layout_classification_tow).setBackgroundColor(mContext.getResources().getColor(R.color.white));
                convertView.findViewById(R.id.iv_select).setVisibility(View.GONE);
            }

            return convertView;
        }
    }
}
