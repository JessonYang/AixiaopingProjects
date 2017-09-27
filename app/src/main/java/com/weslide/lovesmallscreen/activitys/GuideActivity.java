package com.weslide.lovesmallscreen.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro2;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.fragments.GlideFragment;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.SerializableUtils;


/**
 * Created by xu on 2016/5/5.
 * 引导页
 */
public class GuideActivity extends AppIntro2 {


    @Override
    public void init(@Nullable Bundle savedInstanceState) {


        addSlide(GlideFragment.newInstance(R.mipmap.loading1));
        addSlide(GlideFragment.newInstance(R.mipmap.loading2));
        addSlide(GlideFragment.newInstance(R.mipmap.loading3));
        addSlide(GlideFragment.newInstance(R.mipmap.loading4, true));
//        setZoomAnimation();
//        setSlideOverAnimation();

        setProgressIndicator();
       // setProgressButtonEnabled(false);
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        UserInfo axp_userinfo = (UserInfo) SerializableUtils.getObjectByCacheFile(this, "AXP_USERINFO");
        if (axp_userinfo != null && axp_userinfo.getUserId() != null) {
            ContextParameter.setUserInfo(axp_userinfo);
            AppUtils.toActivity(this,UnlockActivity.class);
        }else {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isShow",true);
            AppUtils.toActivity(this, LoginOptionActivity.class,bundle);
        }
        finish();
    }

    @Override
    public void onSlideChanged() {

    }
}
