package com.weslide.lovesmallscreen.view_yy.customview;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by YY on 2017/6/16.
 */
public class ObservableScrollView extends NestedScrollView {

    /**
     * 回调接口监听事件
     */
    private OnObservableScrollViewListener mOnObservableScrollViewListener;
    private int downX;
    private int downY;
    private int mTouchSlop;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 添加回调接口，便于把滑动事件的数据向外抛
     */
    public interface OnObservableScrollViewListener {
        void onObservableScrollViewListener(int l, int t, int oldl, int oldt);
    }

    /**
     * 注册回调接口监听事件
     *
     * @param onObservableScrollViewListener
     */
    public void setOnObservableScrollViewListener(OnObservableScrollViewListener onObservableScrollViewListener) {
        this.mOnObservableScrollViewListener = onObservableScrollViewListener;
    }

    /**
     * @param l Current horizontal scroll origin. 当前滑动的x轴距离
     * @param t Current vertical scroll origin. 当前滑动的y轴距离
     * @param oldl Previous horizontal scroll origin. 上一次滑动的x轴距离
     * @param oldt Previous vertical scroll origin. 上一次滑动的y轴距离
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnObservableScrollViewListener != null) {
            //将监听到的数据向外抛
            mOnObservableScrollViewListener.onObservableScrollViewListener(l, t, oldl, oldt);
        }
    }

    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                // 判断是否滑动，若滑动就拦截事件
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
                break;
            default:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }*/
}
