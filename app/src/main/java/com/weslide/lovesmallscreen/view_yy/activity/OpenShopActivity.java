package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2017/2/13.
 */
public class OpenShopActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_shop);
        ButterKnife.bind(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenShopActivity.this.finish();
            }
        });
    }

    @OnClick({R.id.tv_guide, R.id.btn_means})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_guide://开店指引

                break;
            case R.id.btn_means:
                AppUtils.toActivity(OpenShopActivity.this,InputInformationActivity.class);
                this.finish();
                break;
        }
    }
}
