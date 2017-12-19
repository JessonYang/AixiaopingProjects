package com.weslide.lovesmallscreen.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.HomeActivity;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.mall.SellerFragment;
import com.weslide.lovesmallscreen.utils.AppUtils;


/**
 * Created by xu on 2016/6/13.
 * 商家详情界面
 */
public class SellerActivity extends BaseActivity {
    /** 界面显示的商家id */
    public static final String SELLER_ID = "seller_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seller);

        SellerFragment fragment = new SellerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();


    }

    @Override
    public void onBackPressed() {
        //预防全屏时，用户点击返回后直接退出了activity
//        if (JCVideoPlayer.backPress()) {
//            return;
//        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("back_type") != null && bundle.getString("back_type").length() > 0 && Constants.SELLER_ACTIVITY_HOME_BACK.equals(bundle.getString("back_type"))) {
                AppUtils.toActivity(this, HomeActivity.class);
                finish();
            }else {
                super.onBackPressed();
            }
        }else super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JCVideoPlayer.releaseAllVideos();
    }
}
