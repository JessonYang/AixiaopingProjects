package com.weslide.lovesmallscreen.activitys.withdrawals;

import android.os.Bundle;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.withdrawals.AuthenticationFragment;

/**
 * Created by Dong on 2016/12/30.
 * 身份验证，通过可以提现，如果没有手机号码，跳转至绑定手机号码界面
 */
public class AuthenticationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,new AuthenticationFragment()).commit();
    }
}
