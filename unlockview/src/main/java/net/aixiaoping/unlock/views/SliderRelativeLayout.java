package net.aixiaoping.unlock.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.weslide.lovesmallscreen.utils.L;

import net.aixiaoping.unlock.R;

public class SliderRelativeLayout extends RelativeLayout {
    private static String TAG = "SliderRelativeLayout";

    private ImageView tv_slider_icon = null; // 初始控件，用来判断是否为拖动？
    private ImageView getup_arrow1 = null;
    private ImageView getup_arrow2 = null;
    private ImageView getup_finish_ico1 = null;
    private ImageView getup_finish_ico2 = null;

    private Bitmap dragBitmap = null; // 拖拽图片
    private Context mContext = null; // 初始化图片拖拽时的Bitmap对象

    public int mLastMoveX = 2000; // 当前bitmap应该绘制的地方 ， 初始值为足够大，可以认为看不见

    private Animation alpha_in;

    private Animation alpha_out;

    OnSliderOptionListener mOnSliderOptionListener;

    private int LEFT = 413;
    private int RIGHT = 577;
    // 箭头动画
    private Animation anim1;
    private Animation anim2;
    // 回退动画时间间隔值
    private static int BACK_DURATION = 20; // 20ms
    // 水平方向前进速率
    private static float VE_HORIZONTAL = 0.7f; // 0.1dip/ms

    public void setOnSliderOptionListener(OnSliderOptionListener listener){
        mOnSliderOptionListener = listener;
    }


    public SliderRelativeLayout(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    public SliderRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mContext = context;

        initView();
    }

    public SliderRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_slide_lock, this, true);

        // 该控件主要判断是否处于滑动点击区域。滑动时 处于INVISIBLE(不可见)状态，滑动时处于VISIBLE(可见)状态
        tv_slider_icon = (ImageView) findViewById(R.id.slider_icon);

        getup_finish_ico1 = (ImageView) findViewById(R.id.getup_finish_ico1);
        getup_finish_ico2 = (ImageView) findViewById(R.id.getup_finish_ico2);

        // 解锁箭头，设置动画
        getup_arrow1 = (ImageView) findViewById(R.id.getup_arrow1);
        getup_arrow2 = (ImageView) findViewById(R.id.getup_arrow2);

        anim1 = new TranslateAnimation(0.2f, 70.0f, 0.0f, 0.0f);
        anim1.setRepeatCount(Animation.INFINITE);
        anim1.setDuration(1000);

        anim2 = new TranslateAnimation(0.0f, -70.0f, 0.0f, 0.0f);
        anim2.setRepeatCount(Animation.INFINITE);
        anim2.setDuration(1000);

        //开始执行动画
        getup_arrow1.startAnimation(anim1);
        getup_arrow2.startAnimation(anim2);

        dragBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.unlock_3);

        alpha_in = AnimationUtils.loadAnimation(this.getContext(), R.anim.alpha_in);
        alpha_out = AnimationUtils.loadAnimation(this.getContext(), R.anim.alpha_out);
    }


    /**
     * 按下
     */
    private void keydown() {
        L.e("按下");

    }

    /**
     * 解锁成功
     */
    private void unlocksuccess() {
        mOnSliderOptionListener.unlock();
        L.e("解锁");
    }

    /**
     * 释放
     */
    private void keyup() {
        L.e("释放");

    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.i(TAG, "onTouchEvent" + " X is " + x + " Y is " + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 处理Action_Down事件： 判断是否点击了滑动区域
                return handleActionDownEvenet(event);
            case MotionEvent.ACTION_MOVE:
                mLastMoveX = x; // 保存了X轴方向
                handleActionMoveEvent(event, mLastMoveX);
                invalidate(); // 重新绘制
                return true;
            case MotionEvent.ACTION_UP:
                // 处理Action_Up事件： 判断是否解锁成功，成功则结束我们的Activity ；否则 ，缓慢回退该图片。
                handleActionUpEvent(event);
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


    }

    // 绘制拖动时的图片
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 图片更随手势移动
        invalidateDragImg(canvas);
    }

    /**
     * 图片跟随手势移动
     *
     * @param canvas
     */
    @SuppressLint("NewApi")
    private void invalidateDragImg(Canvas canvas) {
        // Log.e(TAG, "handleActionUpEvenet : invalidateDragImg" );
        // 以合适的坐标值绘制该图片
        int drawXCor = mLastMoveX - dragBitmap.getWidth();
        int drawYCor = getup_finish_ico1.getTop();
        int slider_src_x = (int) tv_slider_icon.getX();
        int slider_src_width_half = tv_slider_icon.getWidth() / 2;
        slider_src_x = slider_src_x + slider_src_width_half - dragBitmap.getWidth() / 2;
        if (mLastMoveX >= getup_finish_ico1.getX() && mLastMoveX <= getup_finish_ico1.getX()) {
            canvas.drawARGB(00, 00, 00, 00);
        } else if (mLastMoveX > LEFT || mLastMoveX < RIGHT) {

            canvas.drawBitmap(dragBitmap, drawXCor < 0 ? 5 : drawXCor, drawYCor, null);
        } else {
            canvas.drawBitmap(dragBitmap, drawXCor < 0 ? 5 : drawXCor, drawYCor, null);
        }
    }


    /**
     * 图片随手势移动
     * @param canvas
     *//*
    private void invalidateDragImg1(Canvas canvas) {
		int drawXCor = mLastMoveX - dragBitmap.getWidth();
		int drawYCor = getup_finish_ico1.getTop();
		canvas.drawBitmap(dragBitmap, drawXCor < 0 ? 5 : drawXCor,drawYCor,null);
	}*/


    /**
     * 手势落下是，是否点中了图片，即是否需要开始移动
     *
     * @param event
     * @return
     */
    public boolean handleActionDownEvenet(MotionEvent event) {

        Rect rect = new Rect();
        tv_slider_icon.getHitRect(rect);
        //获取左右坐标
        RIGHT = tv_slider_icon.getRight();
        LEFT = tv_slider_icon.getLeft();
        boolean isHit = rect.contains((int) event.getX(), (int) event.getY());
        if (isHit) { // 开始拖拽 ，隐藏该图片
            keydown();
            getup_finish_ico1.setVisibility(View.VISIBLE);
            getup_finish_ico1.startAnimation(alpha_in);
            getup_finish_ico2.setVisibility(View.VISIBLE);
            getup_finish_ico2.startAnimation(alpha_in);
            mLastMoveX = (int) event.getX();
            tv_slider_icon.setVisibility(View.INVISIBLE);
            getup_arrow1.setImageResource(R.mipmap.unlock_arrow_1_clicked);
            getup_arrow2.setImageResource(R.mipmap.unlock_arrow_2_clicked);
            return isHit;
        }
        return false;
    }

    public void hideSlider() {
        tv_slider_icon.setVisibility(View.INVISIBLE);
    }

    /**
     * 手指左右滑动解锁上方屏幕，是否滑动，即是否需要开始移动
     *
     * @param event
     * @return
     */
    public boolean handleActionDownEvenet_unlock(MotionEvent event) {
        //获取Unlockview的x坐标值
        boolean isHit_unlockview = false;
        int x = (int) event.getX();
        if (x != 0) {
            isHit_unlockview = true;
        }
        //获取左右坐标
        RIGHT = tv_slider_icon.getRight();
        LEFT = tv_slider_icon.getLeft();
        //if(isHit_unlockview){
        keydown();
        getup_finish_ico1.setVisibility(View.VISIBLE);
        getup_finish_ico1.startAnimation(alpha_in);
        getup_finish_ico2.setVisibility(View.VISIBLE);
        getup_finish_ico2.startAnimation(alpha_in);
        mLastMoveX = x;
        tv_slider_icon.setVisibility(View.INVISIBLE);
        getup_arrow1.setImageResource(R.mipmap.unlock_arrow_1_clicked);
        getup_arrow2.setImageResource(R.mipmap.unlock_arrow_2_clicked);
        //return isHit_unlockview;
        //}
        return false;
    }

    @SuppressLint("NewApi")
    public boolean handleActionMoveEvent(MotionEvent event, int mLastMoveX) {
        //Log.d(TAG, "lastMoveX:"+mLastMoveX);
        //判断滑动图片是在左边还是右边
        if (mLastMoveX < LEFT) {
            getup_finish_ico1.setVisibility(View.GONE);
            getup_arrow1.setImageResource(R.color.transparent);

            getup_finish_ico2.setVisibility(View.VISIBLE);
            getup_arrow2.setImageResource(R.mipmap.unlock_arrow_2_clicked);
        } else if (mLastMoveX > RIGHT) {
            getup_finish_ico2.setVisibility(View.GONE);
            getup_arrow2.setImageResource(R.color.transparent);

            getup_finish_ico1.setVisibility(View.VISIBLE);
            getup_arrow1.setImageResource(R.mipmap.unlock_arrow_1_clicked);
        } else {
            getup_finish_ico1.setVisibility(View.VISIBLE);
            getup_arrow1.setImageResource(R.mipmap.unlock_arrow_1_clicked);
            getup_finish_ico2.setVisibility(View.VISIBLE);
            getup_arrow2.setImageResource(R.mipmap.unlock_arrow_2_clicked);
        }
        // 滑到一半解屏
        float endPosition1 = (getup_finish_ico1.getX() - (tv_slider_icon.getX() + tv_slider_icon.getWidth())) / 2 + tv_slider_icon.getX() + tv_slider_icon.getWidth();// 右边锁图的位置
        float endPosition2 = (getup_finish_ico2.getX() - (tv_slider_icon.getX() + tv_slider_icon.getWidth())) / 2 + tv_slider_icon.getX() + tv_slider_icon.getWidth();// 左边锁图的位置
        if (event.getX() > endPosition1) {
            Log.i(TAG, "endPosition >>> " + endPosition1 + ", this.Width >>> " + this.getWidth() + ", endPictureWidth >>> " + getup_finish_ico1.getWidth() + ", event.getX >>> " + event.getX()
                    + ", dragBitmap.width >>> " + dragBitmap.getWidth());
            getup_finish_ico1.setBackgroundResource(R.mipmap.unlock_7);
            //getup_finish_ico1.setVisibility(View.INVISIBLE);
        } else if (event.getX() < endPosition2) {
            //getup_finish_ico2.setVisibility(View.INVISIBLE);
            getup_finish_ico2.setBackgroundResource(R.mipmap.unlock_7);
        } else {
            Log.i(TAG, "endPosition >>> " + endPosition1 + ", this.Width >>> " + this.getWidth() + ", endPictureWidth >>> " + getup_finish_ico1.getWidth() + ", event.getX >>> " + event.getX()
                    + ", dragBitmap.width >>> " + dragBitmap.getWidth());
            getup_finish_ico1.setBackgroundResource(R.mipmap.unlock_8);
            getup_finish_ico2.setBackgroundResource(R.mipmap.unlock_8);
        }
        return true;
    }


    /**
     * 判断松开手指时，是否达到尾部（滑到一半解屏）即可以开锁了 , 是，则开锁，否则，通过一定的算法使其回退。
     *
     * @param event
     */
    @SuppressLint("NewApi")
    public void handleActionUpEvent(MotionEvent event) {
        int x = (int) event.getX();
        Log.e(TAG, "handleActionUpEvent : x -->" + x + "   getRight() " + getRight());
        Log.e(TAG, "handleActionUpEvent : x -->" + x + "   getleft() " + getLeft());
        // 滑到一半解屏。
        boolean isSucess_right = x - ((getup_finish_ico1.getX() - (tv_slider_icon.getX() + tv_slider_icon.getWidth())) / 2 + tv_slider_icon.getX() + tv_slider_icon.getWidth()) > 0;
        boolean isSucess_left = x - ((getup_finish_ico2.getX() - (tv_slider_icon.getX() + tv_slider_icon.getWidth())) / 2 + tv_slider_icon.getX() + tv_slider_icon.getWidth()) < 0;
        getup_finish_ico1.setVisibility(View.GONE);
        getup_finish_ico1.startAnimation(alpha_out);
        getup_finish_ico2.setVisibility(View.GONE);
        getup_finish_ico2.startAnimation(alpha_out);
        if (isSucess_left) {
            changViewState(false);
            getup_finish_ico1.clearAnimation();
            //resetViewState();
            //getup_finish_ico1.clearAnimation();
            //virbate(); // 震动一下
            // 结束我们的主Activity界面
            unlocksuccess();
        } else if (isSucess_right) {
            changViewState(false);
            getup_finish_ico2.clearAnimation();
            //resetViewState();
            //getup_finish_ico2.clearAnimation();
            //virbate(); // 震动一下
            // 结束我们的主Activity界面
            unlocksuccess();
        } else {// 没有成功解锁，以一定的算法使其回退
            // 每隔20ms , 速率为0.6dip/ms , 使当前的图片往后回退一段距离，直到到达最左端
            mLastMoveX = x; // 记录手势松开时，当前的坐标位置。
            int distance_right = x - tv_slider_icon.getRight();
            int distance_left = 0;
            if (tv_slider_icon.getLeft() >= 0) {
                distance_left = tv_slider_icon.getLeft() - x;
            } else {
                distance_left = Math.abs(tv_slider_icon.getLeft() - x);
            }
            // 只有移动了足够距离才回退
            Log.e(TAG, "handleActionUpEvent : mLastMoveX -->" + mLastMoveX + " distance -->" + distance_left);
            if (distance_right >= 0) {
                mHandler.postDelayed(BackDragImgTask, BACK_DURATION);
                getup_finish_ico1.setBackgroundResource(R.mipmap.unlock_8);
            } else if (distance_left >= 0) {
                mHandler.postDelayed(BackDragImgTask, BACK_DURATION);
                getup_finish_ico2.setBackgroundResource(R.mipmap.unlock_8);
            } else { // 复原初始场景
                resetViewState();
            }
        }
    }


    /**
     * 重置初始的状态，显示tv_slider_icon图像，使bitmap不可见
     */
    public void resetViewState() {
        mLastMoveX = 2000;
        tv_slider_icon.setVisibility(View.VISIBLE);
        getup_finish_ico1.setBackgroundResource(R.mipmap.unlock_8);
        getup_finish_ico2.setBackgroundResource(R.mipmap.unlock_8);
        /**解锁后不显示两个锁的图片*/
        getup_finish_ico1.setVisibility(View.INVISIBLE);
        getup_finish_ico2.setVisibility(View.INVISIBLE);

        getup_arrow1.setImageResource(R.mipmap.translucency);
        getup_arrow2.setImageResource(R.mipmap.translucency);
        keyup();
        invalidate(); // 重绘最后一次
    }


    /**
     * 改变初始的状态，不显示tv_slider_icon图像，使bitmap不可见
     */
    public void changViewState(boolean isVisibility) {
        mLastMoveX = 2000;
        if (isVisibility) {
            tv_slider_icon.setVisibility(View.VISIBLE);
            getup_finish_ico1.setVisibility(View.INVISIBLE);
            getup_finish_ico2.setVisibility(View.INVISIBLE);
        } else
            tv_slider_icon.setVisibility(View.INVISIBLE);
        getup_arrow1.setImageResource(R.mipmap.translucency);
        getup_arrow2.setImageResource(R.mipmap.translucency);
        keyup();
        invalidate(); // 重绘最后一次
    }


    /**
     * 通过延时控制当前绘制bitmap的位置坐标
     */
    private Runnable BackDragImgTask = new Runnable() {

        public void run() {
            if (mLastMoveX > RIGHT) {
                mLastMoveX = mLastMoveX - (int) (BACK_DURATION * VE_HORIZONTAL);
                invalidate();// 重绘
                // 是否需要下一次动画 ？ 到达了初始位置，不在需要绘制
                boolean shouldEnd = Math.abs(mLastMoveX - tv_slider_icon.getRight()) <= 8;
                if (!shouldEnd)
                    mHandler.postDelayed(BackDragImgTask, BACK_DURATION);
                else { // 复原初始场景
                    resetViewState();
                }
            } else if (mLastMoveX < LEFT) {
                mLastMoveX = mLastMoveX + (int) (BACK_DURATION * VE_HORIZONTAL);
                // 是否需要下一次动画 ？ 到达了初始位置，不在需要绘制
                boolean shouldEnd = Math.abs(tv_slider_icon.getLeft() - mLastMoveX) <= 8;
                if (!shouldEnd)
                    mHandler.postDelayed(BackDragImgTask, BACK_DURATION);
                else { // 复原初始场景
                    resetViewState();
                }
            }
            Log.e(TAG, "BackDragImgTask ############# mLastMoveX " + mLastMoveX);
        }
    };


    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {

            Log.i(TAG, "handleMessage :  #### ");

        }
    };


    /**
     * 震动一下下咯
     */
    private void virbate() {
        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(20);
        Thread.currentThread();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /** 用于监听解锁操作 */
    public interface OnSliderOptionListener{
        /** 解锁成功 */
        public void unlock();
    }

}
