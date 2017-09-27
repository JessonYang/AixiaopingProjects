package com.weslide.lovesmallscreen.view_yy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.UploadSubscriber;
import com.weslide.lovesmallscreen.models.bean.UploadFileBean;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by Dong on 2017/2/13.
 */
public class UploadLicenseActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_license)
    ImageView ivLicense;
    MultiImageSelector selector = MultiImageSelector.create(UploadLicenseActivity.this);
    private ArrayList<String> mSelectPath;
    Intent intent;
    UploadFileBean uploadFileBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_license);
        ButterKnife.bind(this);
        intent = getIntent();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadFileBean != null) {
                    setResult(2, intent);
                    UploadLicenseActivity.this.finish();
                } else {
                    UploadLicenseActivity.this.finish();
                }
            }
        });
    }

    @OnClick({R.id.tv_guide, R.id.iv_license, R.id.btn_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_guide:
                break;
            case R.id.iv_license:
                break;
            case R.id.btn_upload:
                selector.single();
                selector.start(UploadLicenseActivity.this, 2);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == -1) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                inputHeadImg();
            }
        }
    }

    private void inputHeadImg() {

        List<UploadFileBean> uploadFileBeen = new ArrayList<>();
        if (mSelectPath != null && mSelectPath.size() > 0) {

            for (int i = 0; i < mSelectPath.size(); i++) {
                UploadFileBean bean = new UploadFileBean();
                bean.setFile(new File(mSelectPath.get(i)));
                bean.setUserId(ContextParameter.getUserInfo().getUserId());
                bean.setFileUse(Constants.UPLOAD_FEED_BACK);

                uploadFileBeen.add(bean);
            }
        }
        RXUtils.uploadImages(UploadLicenseActivity.this, uploadFileBeen, new UploadSubscriber() {

            LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(UploadLicenseActivity.this);
                loadingDialog.show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }

            @Override
            public void onNext(List<Response<UploadFileBean>> responses) {
                //    postHeadImg(responses.get(0).getData().getOppositeUrl(),responses.get(0).getData().getAbsoluteUrl());
                Glide.with(UploadLicenseActivity.this).load(responses.get(0).getData().getAbsoluteUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivLicense);
                uploadFileBean = responses.get(0).getData();
                intent.putExtra("path", uploadFileBean);
                new AlertDialog.Builder(UploadLicenseActivity.this).setTitle("提示")
                        .setMessage("上传成功!")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (uploadFileBean != null) {
                                    setResult(2, intent);
                                    UploadLicenseActivity.this.finish();
                                }
                            }
                        }).create().show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (uploadFileBean != null) {
            setResult(2, intent);
            UploadLicenseActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}
