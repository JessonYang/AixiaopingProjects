package com.weslide.lovesmallscreen.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.mall.SpecialLocalProductFragment;

/**
 * Created by xu on 2016/7/19.
 * 特产速递
 */
public class SpecialLocalProductActivity extends BaseActivity {

    SpecialLocalProductFragment fragment = new SpecialLocalProductFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_special_local_product);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
