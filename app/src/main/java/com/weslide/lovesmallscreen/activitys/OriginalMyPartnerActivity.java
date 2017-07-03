package com.weslide.lovesmallscreen.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.OriginalMyPartnerFragment_new;

/**
 * Created by YY on 2017/3/23.
 */
public class OriginalMyPartnerActivity extends BaseActivity {

    private OriginalMyPartnerFragment_new originalMyPartnerFragment_new = new OriginalMyPartnerFragment_new();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_my_partner);
        getSupportFragmentManager().beginTransaction().replace(R.id.original_my_partner_fg_replace, originalMyPartnerFragment_new).commit();
    }
}
