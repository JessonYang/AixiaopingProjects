package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.view_yy.fragment.PartnerListFragment;

/**
 * Created by YY on 2017/6/7.
 */
public class PartnerListActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_list);
        getSupportFragmentManager().beginTransaction().replace(R.id.replace_partner_list,new PartnerListFragment()).commit();
    }
}
