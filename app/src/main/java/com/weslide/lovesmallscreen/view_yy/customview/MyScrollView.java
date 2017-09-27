package com.weslide.lovesmallscreen.view_yy.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by YY on 2017/6/10.
 */
public class MyScrollView extends ScrollView {
    private OnScrollListener onScrollListener;
    private OnTouchDownListener onTouchDownListener;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置滚动接口
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollListener != null){
            onScrollListener.onScroll(this,t);
        }
    }

    public void setOnTouchDownListener(OnTouchDownListener onTouchDownListener) {
        this.onTouchDownListener = onTouchDownListener;
    }


    /**
     *
     * 滚动的回调接口
     *
     * @author xiaanming
     *
     */
    public interface OnScrollListener{
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         * @param scrollY
         *              、
         */
        public void onScroll(ScrollView scrollView,int scrollY);
    }

    public interface OnTouchDownListener {
        public void onTouchDown();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (onTouchDownListener != null) {
                    if (getChildAt(0).getMeasuredHeight() <= (getScrollY() + getHeight())) {
                        onTouchDownListener.onTouchDown();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
