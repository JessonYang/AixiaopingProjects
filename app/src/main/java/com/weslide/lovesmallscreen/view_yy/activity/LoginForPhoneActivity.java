package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.view_yy.fragment.LoginForPhoneFragment;

/**
 * Created by YY on 2017/6/6.
 */
public class LoginForPhoneActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_for_phone);
        getSupportFragmentManager().beginTransaction().replace(R.id.replace_login_for_phone_ll,new LoginForPhoneFragment()).commit();
    }
}
