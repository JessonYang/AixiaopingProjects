package com.weslide.lovesmallscreen.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.fragments.GlideFragment;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;


/**
 * Created by xu on 2016/5/5.
 * 引导页
 */
public class GuideActivity extends AppIntro2 {


    @Override
    public void init(@Nullable Bundle savedInstanceState) {


        addSlide(GlideFragment.newInstance(R.mipmap.guide1));
        addSlide(GlideFragment.newInstance(R.mipmap.guide2));
        addSlide(GlideFragment.newInstance(R.mipmap.guide3));
        addSlide(GlideFragment.newInstance(R.mipmap.guide4, true));
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
        Bundle bundle = new Bundle();
        bundle.putBoolean("isShow",true);
        AppUtils.toActivity(this, LoginOptionActivity.class,bundle);
        finish();
    }

    @Override
    public void onSlideChanged() {

    }
}
