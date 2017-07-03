package com.weslide.lovesmallscreen.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.OriginalCityAgencyAllOrderFragment;

/**
 * Created by YY on 2017/3/23.
 */
public class OriginalCityAgencyAllOrderActivity extends BaseActivity {


    private OriginalCityAgencyAllOrderFragment originalCityAgencyAllOrderFragment = new OriginalCityAgencyAllOrderFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_agency_all_order);
        getSupportFragmentManager().beginTransaction().replace(R.id.city_agency_all_order_replace,originalCityAgencyAllOrderFragment).commit();
    }
}
