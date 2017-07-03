package com.weslide.lovesmallscreen.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.fragments.mall.SellerFragment;


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
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JCVideoPlayer.releaseAllVideos();
    }
}
