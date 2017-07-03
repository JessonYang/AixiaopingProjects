package com.weslide.lovesmallscreen.views.custom;

/**
 * Created by YY on 2017/3/27.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class MyListview extends ListView {
    int mLastMotionY;
    boolean bottomFlag;

    public MyListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (bottomFlag) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        int y = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                if (deltaY < 0) {
                    View child = getChildAt(0);
                    if (child != null) {
                        if (getLastVisiblePosition() == (getChildCount() - 1) && child.getBottom() == (getChildCount() - 1)) {
                            bottomFlag = true;
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }

                        int bottom = child.getBottom();
                        int padding = getPaddingTop();
                        if (getLastVisiblePosition() == (getChildCount() - 1)
                                && Math.abs(bottom - padding) >= 20) {
                            bottomFlag = true;
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setBottomFlag(boolean flag) {
        bottomFlag = flag;
    }
}