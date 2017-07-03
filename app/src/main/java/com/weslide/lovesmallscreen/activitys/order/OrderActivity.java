package com.weslide.lovesmallscreen.activitys.order;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.order.OrderFragment;

/**
 * Created by xu on 2016/7/1.
 * 订单列表
 */
public class OrderActivity extends BaseActivity {

    /**
     * 用于传递订单状态
     */
    public static final String KEY_ORDER_STATUS = "KEY_ORDER_STATUS";

    OrderFragment mFragment = new OrderFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);

        mFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();

    }
}
