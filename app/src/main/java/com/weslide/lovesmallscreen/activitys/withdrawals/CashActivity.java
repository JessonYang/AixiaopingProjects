package com.weslide.lovesmallscreen.activitys.withdrawals;

import android.os.Bundle;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.withdrawals.NewCashFragment;

/**
 * Created by Dong on 2016/12/30.
 * 现金收益
 */
public class CashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,new NewCashFragment()).commit();
    }
}
