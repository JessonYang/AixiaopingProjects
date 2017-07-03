package com.weslide.lovesmallscreen.view_yy.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by YY on 2017/6/6.
 */
public class AXPTextView_Line extends TextView {

    private Context mContext;

    public AXPTextView_Line(Context context) {
        this(context, null);
    }

    public AXPTextView_Line(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AXPTextView_Line(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#4672e9"));
        paint.setStrokeWidth(1);
        canvas.drawLine(0, getMeasuredHeight()-1, getMeasuredWidth(), getMeasuredHeight()-1, paint);
    }
}
