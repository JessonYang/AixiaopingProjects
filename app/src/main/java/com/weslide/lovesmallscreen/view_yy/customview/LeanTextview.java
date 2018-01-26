package com.weslide.lovesmallscreen.view_yy.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by YY on 2018/1/3.
 */
public class LeanTextview extends TextView {
    public LeanTextview(Context context) {
        super(context);
    }

    public LeanTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //倾斜度45,上下左右居中
        canvas.rotate(45.5f, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        super.onDraw(canvas);
    }

}
