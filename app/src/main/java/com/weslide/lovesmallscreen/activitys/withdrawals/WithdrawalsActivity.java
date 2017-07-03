package com.weslide.lovesmallscreen.activitys.withdrawals;

import android.content.Intent;
import android.os.Bundle;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.withdrawals.WithdrawalsFragment;
import com.weslide.lovesmallscreen.models.Withdrawals;

/**
 * Created by Dong on 2016/12/30.
 * 提现界面
 */
public class WithdrawalsActivity extends BaseActivity {
    WithdrawalsFragment fragment = new WithdrawalsFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
        Intent intent = getIntent();
        Bundle bundle1 = intent.getExtras();
        Withdrawals withdrawals = (Withdrawals) bundle1.getSerializable("withdrawals");
        Bundle bundle = new Bundle();
        bundle.putSerializable("withdrawals",withdrawals);
        fragment.setArguments(bundle);
    }
}
