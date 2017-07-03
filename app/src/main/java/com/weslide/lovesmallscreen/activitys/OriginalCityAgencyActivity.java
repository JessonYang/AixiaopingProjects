package com.weslide.lovesmallscreen.activitys;

import android.os.Bundle;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.fragments.OriginalCityAgencyFragment;

/**
 * Created by YY on 2017/3/21.
 */
public class OriginalCityAgencyActivity extends com.weslide.lovesmallscreen.core.BaseActivity {

    private OriginalCityAgencyFragment originalCityAgencyFragment = new OriginalCityAgencyFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_partner);
        getSupportFragmentManager().beginTransaction().replace(R.id.original_city_partner_replace, originalCityAgencyFragment).commit();
    }
}
