package com.weslide.lovesmallscreen.activitys.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.user.NameEditFragment;

/**
 * Created by Dong on 2016/7/1.
 * 添加修改名字
 */
public class NameEditActivity extends BaseActivity{

    NameEditFragment fragment = new NameEditFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
    }
}
