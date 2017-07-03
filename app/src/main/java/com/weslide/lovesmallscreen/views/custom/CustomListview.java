package com.weslide.lovesmallscreen.views.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by YY on 2017/3/23.
 */

public class CustomListview extends ListView{


    public CustomListview(Context context) {
        this(context,null);
    }

    public CustomListview(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}