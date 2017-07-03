package net.aixiaoping.unlock.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.aixiaoping.unlock.R;

/**
 * Created by xu on 2016/5/24.
 * 一个图标上方带有获得多少积分并带有动画效果的View
 */
public class UnlockScoreIcon extends FrameLayout {

    Context mContext;
    TextView tvScoreNumber;
    ImageView ivOptionIcon;
    Animation mScoreAnimation;

    private String score;

    //当前显示的图标
    Drawable mIcon;
    //选中后显示的图标
    Drawable mSelectIcon;

    public UnlockScoreIcon(Context context) {
        super(context);

        mContext = context;
        initView();
    }

    public UnlockScoreIcon(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UnlockScoreIcon, 0, 0);

        mIcon = typedArray.getDrawable(R.styleable.UnlockScoreIcon_unselect_icon);
        mSelectIcon = typedArray.getDrawable(R.styleable.UnlockScoreIcon_select_icon);
        typedArray.recycle();

        mContext = context;


        initView();
    }

    public UnlockScoreIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        initView();
    }



    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.view_unlock_score_icon, this, true);

        ivOptionIcon = (ImageView) findViewById(R.id.iv_unlcok_icon);
        tvScoreNumber = (TextView) findViewById(R.id.tv_unlock_score_number);

        /** 解锁加积分动画 */
        mScoreAnimation = AnimationUtils.loadAnimation(
                this.getContext(), R.anim.unlockview_addrightscore_anim);

        ivOptionIcon.setBackground(mIcon);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //暂时去掉动画
//                tvScoreNumber.startAnimation(mScoreAnimation);

                ivOptionIcon.setBackground(mSelectIcon);
                break;

            case MotionEvent.ACTION_UP:
//                tvScoreNumber.clearAnimation();

                ivOptionIcon.setBackground(mIcon);
                break;
        }

        return super.onTouchEvent(event);
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
        tvScoreNumber.setText(score+"积分");
    }
}
