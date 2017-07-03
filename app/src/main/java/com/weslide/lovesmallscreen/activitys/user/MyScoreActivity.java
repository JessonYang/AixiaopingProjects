package com.weslide.lovesmallscreen.activitys.user;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.user.MyIntegralSaveFragment;

/**
 * Created by Dong on 2016/6/29.
 * 积分列表
 */
public class MyScoreActivity extends BaseActivity {

    MyIntegralSaveFragment fragment = new MyIntegralSaveFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
       getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
    }
}
