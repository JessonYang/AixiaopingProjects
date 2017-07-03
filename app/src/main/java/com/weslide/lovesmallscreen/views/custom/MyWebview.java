package com.weslide.lovesmallscreen.views.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by YY on 2017/5/17.
 */
public class MyWebview extends WebView {

    private float oldY;
    private int t;
    public MyWebview(Context context) {
        this(context,null);
    }

    public MyWebview(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceY = event.getY() - oldY;
                if (distanceY > 0 && t == 0){
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.t = t;
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
