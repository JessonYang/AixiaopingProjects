package com.weslide.lovesmallscreen.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.weslide.lovesmallscreen.utils.L;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by xu on 2016/6/23.
 * 解决这个倒计时控件在view被移除后不继续执行的问题
 */
public class DetachedFromWindowCountDownView extends CountdownView {
    public DetachedFromWindowCountDownView(Context context) {
        super(context);
    }

    public DetachedFromWindowCountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetachedFromWindowCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        if(((Activity)getContext()).isFinishing()) {
            L.e("倒计时结束了");
            stop();
        }
    }
}
