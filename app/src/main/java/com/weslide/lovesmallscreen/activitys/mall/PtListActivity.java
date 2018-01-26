package com.weslide.lovesmallscreen.activitys.mall;

import android.os.Bundle;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.mall.PtListFragment;

public class PtListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pt_list);
        getSupportFragmentManager().beginTransaction().replace(R.id.pt_list_fl,new PtListFragment()).commit();
    }
}
