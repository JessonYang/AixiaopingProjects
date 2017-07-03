package com.weslide.lovesmallscreen.activitys.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.order.ApplyBackOrderFragment;

/**
 * Created by xu on 2016/7/15.
 * 申请退单
 */
public class ApplyBackOrderActivity extends BaseActivity {

    /**
     * 订单所属的订单状态,用于刷新列表
     */
    public static final String KEY_STATUS = "KEY_STATUS";


    ApplyBackOrderFragment fragment = new ApplyBackOrderFragment();
    public static final String KEY_ORDER_ITEM_LIST = "KEY_ORDER_ITEM_LIST";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_apply_back_order);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
