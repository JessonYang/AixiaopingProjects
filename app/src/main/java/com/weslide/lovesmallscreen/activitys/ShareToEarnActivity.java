package com.weslide.lovesmallscreen.activitys;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.ShareToEarnFragment;

/**
 * Created by YY on 2017/4/17.
 */
public class ShareToEarnActivity extends BaseActivity{

    ShareToEarnFragment shareToEarnFragment = new ShareToEarnFragment();
    public static String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_to_earn);
        if (Build.VERSION.SDK_INT>19){
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        }
        url = getIntent().getStringExtra("invatationCodeUrl");
        getSupportFragmentManager().beginTransaction().replace(R.id.share_to_earn_fl,shareToEarnFragment).commit();
    }
}
