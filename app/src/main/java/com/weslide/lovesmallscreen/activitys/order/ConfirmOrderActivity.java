package com.weslide.lovesmallscreen.activitys.order;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.order.ConfirmOrderFragment;

/**
 * Created by xu on 2016/6/30.
 * 确认订单界面
 */
public class ConfirmOrderActivity extends BaseActivity {

    /**
     * 用于生成临时订单的Bean
     */
    public static final String KEY_ORDER_LIST = "KEY_ORDER_LIST";

    ConfirmOrderFragment mFragment = new ConfirmOrderFragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirm_order);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();
    }
}
