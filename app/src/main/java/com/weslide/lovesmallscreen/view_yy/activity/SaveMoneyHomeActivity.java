package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.view_yy.fragment.SaveMoneyHomeFragment;
import com.weslide.lovesmallscreen.view_yy.fragment.SaveMoneyHomeFragment2;

/**
 * Created by YY on 2017/6/13.
 */
public class SaveMoneyHomeActivity extends BaseActivity {
//    public static SaveMoneyHomeActivity saveMoneyHomeActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_money_home);
        String toolbarType = getIntent().getExtras().getString("toolbarType");
//        saveMoneyHomeActivity = this;
        if (!toolbarType.equals("省钱购物") && !toolbarType.equals("九块九")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.replace_save_money_home,new SaveMoneyHomeFragment2()).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.replace_save_money_home,new SaveMoneyHomeFragment()).commit();
        }
    }
}
