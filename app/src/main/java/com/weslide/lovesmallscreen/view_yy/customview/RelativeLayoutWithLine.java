package com.weslide.lovesmallscreen.view_yy.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.weslide.lovesmallscreen.utils.DensityUtils;

/**
 * Created by YY on 2017/6/3.
 */
public class RelativeLayoutWithLine extends RelativeLayout {

    private Context mContext;

    public RelativeLayoutWithLine(Context context) {
        this(context, null);
    }

    public RelativeLayoutWithLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RelativeLayoutWithLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(DensityUtils.dp2px(mContext, 1));
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(getMeasuredWidth(), getMeasuredHeight());
        path.close();
        canvas.drawPath(path, paint);
//        canvas.drawLine(0, 0,getMeasuredWidth(), getMeasuredHeight(), paint);
    }
}
