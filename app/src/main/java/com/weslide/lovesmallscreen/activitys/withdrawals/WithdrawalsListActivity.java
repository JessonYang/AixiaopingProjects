package com.weslide.lovesmallscreen.activitys.withdrawals;

import android.os.Bundle;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.withdrawals.WithdrawalsListFragment;

/**
 * Created by Dong on 2016/12/30.
 * 提现支付与未支付列表
 */
public class WithdrawalsListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,new WithdrawalsListFragment()).commit();
    }
}
