package com.weslide.lovesmallscreen.activitys.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.user.MyStoreFragment;

/**
 * Created by Dong on 2016/6/30.
 * 商家中心
 */
public class MyStoreActivity extends BaseActivity {

    MyStoreFragment fragment = new MyStoreFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_change,fragment).commit();
    }
        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("QRActivity", "Cancelled scan");
            } else {
                Log.d("QRActivity", "Scanned : " +result.getContents());
                URIResolve.resolve(this, result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
