package com.weslide.lovesmallscreen.views;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.weslide.lovesmallscreen.core.APeriod;
import com.weslide.lovesmallscreen.core.BaseActivity;

/**
 * Created by xu on 2016/8/13.
 * 可绑定Activity生命周期的FrameLayout
 * 务必将Activity继承BaseActivity
 */
public class AFrameLayout extends FrameLayout implements APeriod {
    public AFrameLayout(Context context) {
        super(context);
        init();
    }

    public AFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        //绑定Activity生命周期
        BaseActivity baseActivity = (BaseActivity) getContext();
        baseActivity.addPeriod(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onDestroy() {

    }
}
