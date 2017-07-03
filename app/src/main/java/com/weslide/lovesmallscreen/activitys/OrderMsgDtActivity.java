package com.weslide.lovesmallscreen.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.OrderMsgDtFragment;


/**
 * Created by YY on 2017/5/9.
 */
public class OrderMsgDtActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_msg_dt);
        getSupportFragmentManager().beginTransaction().replace(R.id.order_msg_dt_replace,new OrderMsgDtFragment()).commit();
    }
}
