package com.weslide.lovesmallscreen.exchange.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by YY on 2018/1/23.
 */
public class CustomColum extends RelativeLayout {
    private String mTitle;
    private float mTitleSize;
    private Drawable rightDrawable;
    private int mTitleColor;

    public CustomColum(Context context) {
        this(context,null);
    }

    public CustomColum(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomColum(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attributeSet) {
        /*TypedArray typedArray = context.obtainStyledAttributes(R.styleable.CustomColum);
        mTitle = typedArray.getString(R.styleable.CustomColum_title);
        mTitleSize = typedArray.getDimensionPixelSize(R.styleable.CustomColum_titleSize, DensityUtils.dp2px(context,16));
        mTitleColor = typedArray.getColor(R.styleable.CustomColum_titleColor, Color.parseColor("#333333"));
        rightDrawable = typedArray.getDrawable(R.styleable.CustomColum_rightDrawable);
        typedArray.recycle();

        if (mTitle != null) {
            TextView title = new TextView(context);
            title.setText(mTitle);
            title.setTextSize(mTitleSize);
            title.setTextColor(mTitleColor);
        }*/
    }
}
