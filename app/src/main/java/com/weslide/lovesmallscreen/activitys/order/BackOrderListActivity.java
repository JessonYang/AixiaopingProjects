package com.weslide.lovesmallscreen.activitys.order;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.order.BackOrderListFragment;

/**
 * Created by xu on 2016/7/15.
 * 退单列表
 */
public class BackOrderListActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_back_order_list);

        BackOrderListFragment fragment = new BackOrderListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
