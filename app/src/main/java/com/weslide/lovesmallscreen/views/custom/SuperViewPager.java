package com.weslide.lovesmallscreen.views.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Dong on 2016/9/13.
 */
public class SuperViewPager extends ViewPager{
    private float mDownx,mDownY;

    public SuperViewPager(Context context) {
        super(context);
    }

    public SuperViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownx = event.getX();
                mDownY = event.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(event.getX()-mDownx) > Math.abs(event.getY()-mDownY)){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;

        }
        return super.dispatchHoverEvent(event);
    }
}
