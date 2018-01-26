package com.weslide.lovesmallscreen.activitys.user;

import android.os.Bundle;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.user.ScoreRechargeSuccessFragment;


public class ScoreRechargeSuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_recharge_success);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ScoreRechargeSuccessFragment()).commit();
    }
}
