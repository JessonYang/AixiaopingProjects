package com.weslide.lovesmallscreen.view_yy.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.weslide.lovesmallscreen.R;

/**
 * Created by YY on 2017/11/1.
 */
public class RoundImgView extends ImageView {
    private int mBorderRadio;
    private int mImgType;
    private int ROUND_CIRCLE = 0;//圆形
    private int ROUND_RECT = 1;//圆角矩形
    private int mViewWidth, mRadios;
    private Drawable mDrawable;
    private Matrix mMatrix;
    private Paint mPaint;
    private BitmapShader mBitmapShader;
    private RectF mRect;

    public RoundImgView(Context context) {
        this(context, null);
    }

    public RoundImgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mMatrix = new Matrix();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImgView);
        mBorderRadio = typedArray.getDimensionPixelSize(R.styleable.RoundImgView_roundRadios, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
        mImgType = typedArray.getInt(R.styleable.RoundImgView_type, ROUND_CIRCLE);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mImgType == ROUND_CIRCLE) {
            mViewWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
            mRadios = mViewWidth / 2;
            setMeasuredDimension(mViewWidth, mViewWidth);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mImgType == ROUND_RECT) {
            mRect = new RectF(0,0,getWidth(),getHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        setUpShader();
        if (mImgType == ROUND_CIRCLE) {
            canvas.drawCircle(mRadios,mRadios,mRadios,mPaint);
        } else if (mImgType == ROUND_RECT) {
            canvas.drawRoundRect(mRect,mBorderRadio,mBorderRadio,mPaint);
        }
    }

    private void setUpShader() {
        mDrawable = getDrawable();
        if (mDrawable == null) {
            return;
        }
        Bitmap btm = drawableToBitmap(mDrawable);
        mBitmapShader = new BitmapShader(btm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1f;
        if (mImgType == ROUND_CIRCLE) {
            int min = Math.min(btm.getWidth(), btm.getHeight());
            scale = mViewWidth / min * 1f;
        } else if (mImgType == ROUND_RECT) {
            scale = Math.max(getWidth() * 1.0f / btm.getWidth(), getHeight()
                    * 1.0f / btm.getHeight());
        }
        mMatrix.setScale(scale,scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mBitmapShader);
    }

    private Bitmap drawableToBitmap(Drawable mDrawable) {
        if (mDrawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) mDrawable;
            return bd.getBitmap();
        }
        int intrinsicWidth = mDrawable.getIntrinsicWidth();
        int intrinsicHeight = mDrawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mDrawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        mDrawable.draw(canvas);
        return bitmap;
    }
}
