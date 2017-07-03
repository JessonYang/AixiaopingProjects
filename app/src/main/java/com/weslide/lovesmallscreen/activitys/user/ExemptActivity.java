package com.weslide.lovesmallscreen.activitys.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.user.ExemptFragment;

/**
 * Created by Dong on 2016/7/7.
 * 免单券列表
 */
public class ExemptActivity extends BaseActivity {
    ExemptFragment fragment = new ExemptFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
    }
}
