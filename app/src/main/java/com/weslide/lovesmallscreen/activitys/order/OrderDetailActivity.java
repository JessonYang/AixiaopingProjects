package com.weslide.lovesmallscreen.activitys.order;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.order.OrderDetailFragment;

/**
 * Created by xu on 2016/7/18.
 * 订单详情界面
 */
public class OrderDetailActivity extends BaseActivity {

    /**
     * 状态
     */
    public static final String KEY_STATUS = "KEY_STATUS";
    /**
     * 订单id
     */
    public static final String KEY_ORDER_ID = "KEY_ORDER_ID";


    OrderDetailFragment fragment = new OrderDetailFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_detail);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
