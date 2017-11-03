package com.weslide.lovesmallscreen.view_yy.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.weslide.lovesmallscreen.R;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class MyRadiobutton extends RadioButton {


    private float width;
    private float height;

    public MyRadiobutton(Context context) {
        this(context, null);
    }

    public MyRadiobutton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRadiobutton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRadiobutton);
        width = typedArray.getDimensionPixelSize(R.styleable.MyRadiobutton_drawableTopWidth, 36);
        height = typedArray.getDimensionPixelSize(R.styleable.MyRadiobutton_drawableTopHeight, 36);
        Drawable drawableTop = typedArray.getDrawable(R.styleable.MyRadiobutton_MydrawableTop);
        typedArray.recycle();
        setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null, null);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        if (top != null) {
            top.setBounds(0, 0, (int) width, (int) height);
        }
        setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        boolean checked = isChecked();
        if (checked) {
            setTextColor(Color.parseColor("#ff2d47"));
        } else setTextColor(Color.parseColor("#555555"));
        super.onDraw(canvas);
    }
}
