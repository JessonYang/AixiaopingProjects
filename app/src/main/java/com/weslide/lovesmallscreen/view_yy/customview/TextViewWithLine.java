package com.weslide.lovesmallscreen.view_yy.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.weslide.lovesmallscreen.utils.DensityUtils;

/**
 * Created by YY on 2017/6/2.
 */
public class TextViewWithLine extends TextView {

    private Context mContext;

    public TextViewWithLine(Context context) {
        this(context,null);
    }

    public TextViewWithLine(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextViewWithLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(DensityUtils.dp2px(mContext, 1));
        canvas.drawLine(0, getMeasuredHeight()/2,getMeasuredWidth(), getMeasuredHeight()/2+DensityUtils.dp2px(mContext, 2), paint);
    }
}
