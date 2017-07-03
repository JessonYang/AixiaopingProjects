package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.view_yy.fragment.TicketGoodsDtFragment;

/**
 * Created by YY on 2017/6/1.
 */
public class TicketGoodsDtActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_product_dt);
        getSupportFragmentManager().beginTransaction().replace(R.id.replace_promotion_product_dt,new TicketGoodsDtFragment()).commit();
    }
}
