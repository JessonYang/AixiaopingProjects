package com.weslide.lovesmallscreen.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.utils.DensityUtils;

/**
 * Created by YY on 2017/3/21.
 */
public class CustomToolbar extends RelativeLayout {

    private ImageView leftIv;
    private ImageView rightIv;
    private Drawable leftDrawable;
    private Drawable rightDrawable;
    private TextView title;

    public CustomToolbar(Context context) {
        this(context,null);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar);
        leftDrawable = typedArray.getDrawable(R.styleable.CustomToolbar_leftIv);
        rightDrawable = typedArray.getDrawable(R.styleable.CustomToolbar_rightIv);
        int titleColor = typedArray.getColor(R.styleable.CustomToolbar_titleColor, Color.BLACK);
        String titleName = typedArray.getString(R.styleable.CustomToolbar_titleName);
        float titleSize = typedArray.getDimensionPixelSize(R.styleable.CustomToolbar_titleSize, 12);
        typedArray.recycle();

        if (leftDrawable != null) {
            leftIv = new ImageView(context);
            leftIv.setImageDrawable(leftDrawable);
            leftIv.setPadding(20,20,20,20);
            LayoutParams leftIvLp = new LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics()));
            leftIvLp.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,12,getResources().getDisplayMetrics());
            leftIvLp.addRule(ALIGN_PARENT_LEFT|CENTER_VERTICAL,TRUE);
            addView(leftIv,leftIvLp);
        }

        if (rightDrawable != null) {
            rightIv = new ImageView(context);
            rightIv.setImageDrawable(rightDrawable);
            LayoutParams rightIvLp = new LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
            rightIvLp.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,12,getResources().getDisplayMetrics());
            rightIvLp.addRule(ALIGN_PARENT_RIGHT,TRUE);
            rightIvLp.addRule(CENTER_VERTICAL,TRUE);
            addView(rightIv,rightIvLp);
            rightIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onImgClick != null){
                        onImgClick.onRightImgClick();
                    }
                }
            });
        }

        if (titleName != null) {
            title = new TextView(context);
            title.setTextSize(DensityUtils.px2sp(context,titleSize));
            title.setTextColor(titleColor);
            title.setText(titleName);
            LayoutParams titleLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            titleLp.addRule(CENTER_IN_PARENT,TRUE);
            addView(title,titleLp);
        }

        leftIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onImgClick != null){
                    onImgClick.onLeftImgClick();
                }
            }
        });


    }

    public interface OnImgClick {
        public void onLeftImgClick();

        public void onRightImgClick();
    }

    private OnImgClick onImgClick;

    public void setOnImgClick(OnImgClick onImgClick) {
        this.onImgClick = onImgClick;
    }

    public void setTextViewTitle(String text){
        removeView(title);
        TextView t = new TextView(getContext());
        t.setTextSize(20);
        t.setTextColor(Color.parseColor("#333333"));
        t.setText(text);
        LayoutParams titleLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLp.addRule(CENTER_IN_PARENT,TRUE);
        addView(t,titleLp);
    }
}
