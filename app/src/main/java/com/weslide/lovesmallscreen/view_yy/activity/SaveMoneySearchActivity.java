package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.view_yy.fragment.SaveMoneySearchFragment;

/**
 * Created by YY on 2017/6/13.
 */
public class SaveMoneySearchActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_money_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.replace_save_money_home,new SaveMoneySearchFragment()).commit();
    }
}
