package com.weslide.lovesmallscreen.views.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/3/13.
 */
public class Line_TextView extends TextView {

    private Paint paint;

    public Line_TextView(Context context) {
        this(context,null);
    }

    public Line_TextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Line_TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#999999"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, 0, this.getWidth()-1 , 0, paint);
        canvas.drawLine(0, 0, 0, this.getHeight()-1, paint);
        canvas.drawLine(this.getWidth()-1 , 0, this.getWidth()-1 , this.getHeight() , paint);
        canvas.drawLine(0, this.getHeight()-1 , this.getWidth() , this.getHeight()-1 , paint);
        super.onDraw(canvas);
    }
}
