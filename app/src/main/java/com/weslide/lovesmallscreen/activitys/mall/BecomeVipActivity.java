package com.weslide.lovesmallscreen.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.mall.BecomeVipFragment;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.utils.UserUtils;

/**
 * Created by xu on 2016/7/14.
 * 成为会员界面
 */
public class BecomeVipActivity extends BaseActivity {

    BecomeVipFragment mFragment = new BecomeVipFragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_become_vip);

        if(!ContextParameter.isLogin()){
            //未登录的情况下
            UserUtils.login(this);
            finish();
            return;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();
    }
}
