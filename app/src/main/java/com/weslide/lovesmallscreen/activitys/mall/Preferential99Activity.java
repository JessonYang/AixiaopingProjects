package com.weslide.lovesmallscreen.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.mall.Preferential99Fragment;

/**
 * Created by xu on 2016/7/19.
 * 99特惠界面
 */
public class Preferential99Activity extends BaseActivity {

    Preferential99Fragment fragment = new Preferential99Fragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preferential_99);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
