package com.weslide.lovesmallscreen.views.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.aixiaoping.library.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by xu on 2016/7/15.
 * 图文加图片上传
 */
public class TextUploadImageView extends FrameLayout {
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.gv_photo)
    GridView gvPhoto;
    private ArrayList<String> mSelectPath;
    MyAdapter adapter;
    MultiImageSelector selector;

    /**
     * 一个界面同时存在多个图片选择器时，避免冲突
     */
    int requestCode;

    public TextUploadImageView(Context context) {
        super(context);
        initView();
    }

    public TextUploadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TextUploadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContentHint(String hint) {
        etContent.setHint(hint);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_text_upload_image, this, true);
        ButterKnife.bind(this);
        requestCode = (int) (Math.random() * 10000);
        selector = MultiImageSelector.create(getContext());
        selector.multi();
        selector.count(4);
        adapter = new MyAdapter();
    }

    /**
     * fragment 或 activity中的onActivityResult必须调用该方法才能生效
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode) {
            if (resultCode == -1) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);

                gvPhoto.setAdapter(adapter);
            }
        }
    }

    /**
     * 得到填写的内容
     *
     * @return
     */
    public String getContent() {
        return etContent.getText().toString();
    }

    /**
     * 得到选中的图片路径
     *
     * @return
     */
    public ArrayList<String> getSelectPath() {
        if (mSelectPath == null) {
            mSelectPath = new ArrayList<>();
        }
        return mSelectPath;
    }

    @OnClick(R.id.iv_add_photo)
    public void onClick() {
        selector.start((Activity) getContext(), requestCode);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mSelectPath.size();
        }

        @Override
        public Object getItem(int position) {
            return mSelectPath.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    com.weslide.lovesmallscreen.R.layout.item_comment_image, parent, false);
            ImageView iv = (ImageView) convertView.findViewById(com.weslide.lovesmallscreen.R.id.iv_photo);
            Glide.with(getContext()).load(mSelectPath.get(position)).into(iv);
            return convertView;
        }
    }
}
