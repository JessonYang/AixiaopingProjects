package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.rey.material.widget.Button;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YY on 2017/6/18.
 */
public class ApplayPartnerPaySuccessActivity extends BaseActivity {

    @BindView(R.id.pay_success_btn)
    Button pay_success_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applay_partner_success);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.pay_success_btn})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pay_success_btn:
            case R.id.back_iv:
                finish();
                if (ApplayPartnerActivity.applayPartnerActivity != null) {
                    ApplayPartnerActivity.applayPartnerActivity.finish();
                }
                break;
        }
    }
}
