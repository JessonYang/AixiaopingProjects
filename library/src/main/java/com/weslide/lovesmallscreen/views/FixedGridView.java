package com.weslide.lovesmallscreen.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by xu on 2016/6/12.
 * 固定高度的GridView
 * View之间无法进行复用
 */
public class FixedGridView extends GridView {
    public FixedGridView(Context context) {
        super(context);
    }

    public FixedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO 自动生成的方法存根
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
