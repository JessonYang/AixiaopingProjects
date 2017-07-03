package com.weslide.lovesmallscreen.activitys.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.sellerinfo.ChargebacKauditFragment;

/**
 * Created by Dong on 2016/7/16.
 * 退单申请
 */
public class ChargebacKauditActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,new ChargebacKauditFragment()).commit();
    }
}
