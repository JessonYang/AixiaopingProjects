package com.weslide.lovesmallscreen.view_yy.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by YY on 2017/11/23.
 * 自定义recyclerview分割线(只针对线性布局)
 */
public class RecyclerViewDivider extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    //默认分割线大小
    private int mDividerHeight = 2;
    private Drawable mDivider;
    private int mOrientation;
    //默认listview的分割线颜色
    private int[] ATTRS = new int[]{android.R.attr.listDivider};

    //默认分割线大小2PX，颜色灰色
    public RecyclerViewDivider(Context context,int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            mOrientation = LinearLayoutManager.VERTICAL;
        }else {
            mOrientation = orientation;
        }
        TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    /**
     *
     * @param context
     * @param orientation
     * @param drawableId 自定义分割线图片
     */
    public RecyclerViewDivider(Context context,int orientation,int drawableId){
        this(context,orientation);
        mDivider = ContextCompat.getDrawable(context,drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     *
     * @param context
     * @param orientation
     * @param dividerHeight 分割线高度
     * @param color 分割线颜色
     */
    public RecyclerViewDivider(Context context,int orientation,int dividerHeight,int color){
        this(context,orientation);
        mDividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
            if (mPaint != null) {
                c.drawRect(left,top,right,bottom,mPaint);
            }
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = layoutParams.bottomMargin + child.getBottom();
            int bottom = top + mDividerHeight;
            if (mDivider != null){
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
            if (mPaint != null) {
                c.drawRect(left,top,right,bottom,mPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0,0,0,mDividerHeight);
        } else if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            outRect.set(0,0,mDividerHeight,0);
        }
    }
}
