package com.weslide.lovesmallscreen.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.SystemMsgFragment;


/**
 * Created by YY on 2017/5/5.
 */
public class SystemMsgActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_msg);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_replace,new SystemMsgFragment()).commit();
    }
}
