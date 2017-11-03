package net.aixiaoping.unlock.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.core.APeriod;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.dao.sp.UserInfoSP;
import com.weslide.lovesmallscreen.models.AcquireScore;
import com.weslide.lovesmallscreen.models.AdvertImg;
import com.weslide.lovesmallscreen.models.RedPaper;
import com.weslide.lovesmallscreen.models.bean.UpdateScoreBean;
import com.weslide.lovesmallscreen.models.eventbus_message.DownloadImageMessage;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;

import net.aixiaoping.unlock.R;
import net.aixiaoping.unlock.views.adpaters.UnlockViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import me.kaelaela.verticalviewpager.VerticalViewPager;


/**
 * Created by xu on 2016/5/16.
 * 锁屏使用的view
 */
public class UnlockView extends FrameLayout implements View.OnClickListener, APeriod {

    /**
     * 自动滑动的间隔时间
     */
    public static final int INTERVAL_TIME = 3000;

    /**
     * 解锁操作
     */
    public static final String OPTION_UNLOCK = "OPTION_UNLOCK";
    /**
     * 品牌官网
     */
    public static final String OPTION_WEBSITE = "OPTION_WEBSITE";
    /**
     * 进入商城
     */
    public static final String OPTION_MALL = "OPTION_MALL";

    /**
     * 解锁增加的积分类型
     */
    public static final String ACQUIRE_SCORE_TYPE_UNLOCK = Constants.SCORE_TYPE_UNLOCK;
    /**
     * 滑屏增加的积分类型
     */
    public static final String ACQUIRE_SCORE_TYPE_GLIDE_SCREEN = Constants.SCORE_TYPE_GLIDE_ADVERT;
    /**
     * 跳转品牌官网
     */
    public static final String ACQUIRE_SCORE_TYPE_WEBSITE = Constants.SCORE_TYPE_WEBSITE;

    public static RedPaper mRedPaper;

    VerticalViewPager mViewPager;
    SliderRelativeLayout mSliderRelativeLayout;
    UnlockClockView mUnlockClockView;
    TextView mAcquiteScoreView;
    UnlockScoreIcon mWebsiteIcon;
    UnlockScoreIcon mMallIcon;
    Animation mAcquiteScoreAnimation;
    FrameLayout mRedPaperView;
    private View.OnClickListener listener;

    /**
     * 滑屏广告切换事件
     */
    PageChangeListener mPageChangeListener;
    /**
     * 锁屏界面的各种操作事件
     */
    OnOptionListener mOptionListener;
    List<AdvertImg> mAdverts;
    public static Context mContext;
    private ImageView gold_iv,chai_iv;

    public UnlockView(Context context) {
        super(context);

        mContext = context;

        initView();
    }

    public UnlockView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        initView();
    }

    public UnlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 用于自动轮播
     */
    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (mViewPager.getAdapter() == null) {
                L.e("锁屏尚未加载完毕");
                return false;
            }

            mPageChangeListener.setOption(0);
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            mHandler.sendEmptyMessageDelayed(1, INTERVAL_TIME);
            return false;
        }
    });

    public void setOnOptionListener(OnOptionListener listener) {
        mOptionListener = listener;
    }

    private void initView() {

        LayoutInflater.from(mContext).inflate(R.layout.view_unlock, this, true);

        mViewPager = (VerticalViewPager) findViewById(R.id.view_pager);
        mSliderRelativeLayout = (SliderRelativeLayout) findViewById(R.id.srl_unlock_layout);
        mAcquiteScoreView = (TextView) findViewById(R.id.tv_unlock_acquire_score);
        mWebsiteIcon = (UnlockScoreIcon) findViewById(R.id.usi_unlock_website);
        mMallIcon = (UnlockScoreIcon) findViewById(R.id.usi_unlock_mall);
        mRedPaperView = (FrameLayout) findViewById(R.id.layout_redpaper);
        gold_iv = ((ImageView) findViewById(R.id.loading_gold));
        chai_iv = ((ImageView) findViewById(R.id.chai_iv));
        mUnlockClockView = (UnlockClockView) findViewById(R.id.unlock_clock_view);

        initAnim();

        mWebsiteIcon.setScore(randownScore() + "");
        mMallIcon.setScore(randownScore() + "");

        mWebsiteIcon.setOnClickListener(this);
        mMallIcon.setOnClickListener(this);

        //设置滑屏控件操作事件
        mSliderRelativeLayout.setOnSliderOptionListener(new SliderRelativeLayout.OnSliderOptionListener() {
            @Override
            public void unlock() {
                acquireScore(null, ACQUIRE_SCORE_TYPE_UNLOCK, Integer.parseInt(mMallIcon.getScore()));
                UnlockView.this.unlock(OPTION_UNLOCK);
            }
        });

        //监听手势
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPageChangeListener.setOption(1); //设置手势操作
                        mHandler.removeMessages(1);  //禁用自动滑动
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessageDelayed(1, INTERVAL_TIME);
                        break;
                }
                return false;
            }
        });

        /**
         * 跳转红包
         */
        mRedPaperView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  RedPaper redPaper = (RedPaper) view.getTag();
                String url = HTTP.URL_OPEN_RED_PAPER + HTTP.formatJSONData(new Request().setData(redPaper));
                unlock(OPTION_UNLOCK);
                URIResolve.resolve(getContext(), url);*/
                unlock(OPTION_UNLOCK);
                RedPaper redPaper = (RedPaper) view.getTag();
                openAdminRedPaper(redPaper.getSettingId());
                /*Intent intent = new Intent();
                intent.setClassName(getContext(), "com.weslide.lovesmallscreen.activitys.sellerinfo.RedPaperDetailsActivity");
                intent.putExtra("settingId",redPaper.getSettingId());
                mContext.startActivity(intent);
                mRedPaperView.setVisibility(View.GONE);*/
            }
        });
    }

    private void openAdminRedPaper(final int settingId) {
        Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0, 360, 120, 120, 0f, Rotate3dAnimation.ROTATE_Y_AXIS, true);
        rotate3dAnimation.setRepeatCount(Animation.INFINITE);
        rotate3dAnimation.setDuration(1200);
        chai_iv.setVisibility(GONE);
        gold_iv.setVisibility(VISIBLE);
        gold_iv.startAnimation(rotate3dAnimation);
        Request<RedPaper> request = new Request<>();
        RedPaper redPaper = new RedPaper();
        redPaper.setSettingId(settingId);
        redPaper.setType("6");
        request.setData(redPaper);
        RXUtils.request(mContext, request, "openAdminRedPaper", new SupportSubscriber<Response<RedPaper>>() {

            @Override
            public void onNext(Response<RedPaper> redPaperResponse) {
                mRedPaper = redPaperResponse.getData();
                Intent intent = new Intent();
                intent.setClassName(getContext(), "com.weslide.lovesmallscreen.activitys.sellerinfo.RedPaperDetailsActivity");
                intent.putExtra("status", redPaperResponse.getStatus());
                mContext.startActivity(intent);
                mRedPaperView.setVisibility(View.GONE);
                gold_iv.setVisibility(GONE);
                chai_iv.setVisibility(VISIBLE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onResponseError(Response response) {
                if (response.getStatus() == -3) {//红包金额被领完了
                    Intent intent = new Intent();
                    intent.setClassName(getContext(), "com.weslide.lovesmallscreen.activitys.sellerinfo.RedPaperDetailsActivity");
                    intent.putExtra("status", response.getStatus());
                    mContext.startActivity(intent);
                    mRedPaperView.setVisibility(View.GONE);
                    gold_iv.setVisibility(GONE);
                }
            }
        });
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        EventBus.getDefault().register(this);

        mSliderRelativeLayout.resetViewState();
        mRedPaperView.setVisibility(View.GONE);
        mRedPaperView.setTag(null);
        mUnlockClockView.onStart();
        if (ArchitectureAppliation.getDaoSession().getAdvertImgDao() != null) {
            mAdverts = ArchitectureAppliation.getDaoSession().getAdvertImgDao().loadAll();
        }
        //需要过滤掉没有下载完成的图片
        if (mAdverts != null) {
            for (int i = 0; i < mAdverts.size(); i++) {
                if (StringUtils.isBlank(mAdverts.get(i).getImageFile())) {
                    mAdverts.remove(i);
                    i--;
                }
            }
        }

        mViewPager.setAdapter(new UnlockViewPagerAdapter(mContext, mAdverts));
        mViewPager.addOnPageChangeListener(mPageChangeListener = new PageChangeListener(mContext, this, mAdverts));
        mViewPager.setPageTransformer(false, new VerticalTransformer());
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);

        start();

        ((BaseActivity) mContext).addPeriod(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mViewPager.removeAllViews();
        mOptionListener = null;

        EventBus.getDefault().unregister(this);

        ((BaseActivity) mContext).removePeriod(this);

        onDestroy();
    }

    @Subscribe
    public void onEvent(DownloadImageMessage message) {
        L.e("test", "图片下载完成回调");

        List<AdvertImg> adverts = ArchitectureAppliation.getDaoSession().getAdvertImgDao().loadAll();

        //需要过滤掉没有下载完成的图片
        if (adverts != null) {
            for (int i = 0; i < adverts.size(); i++) {
                if (StringUtils.isBlank(adverts.get(i).getImageFile())) {
                    adverts.remove(i);
                    i--;
                }
            }
        }

        mAdverts.clear();
        mAdverts.addAll(adverts);
        mViewPager.getAdapter().notifyDataSetChanged();

    }

    /**
     * 获得积分
     *
     * @param advertId 积分获得的广告id
     * @param type     获得的积分类型
     * @param score    获得的积分
     */
    public static void acquireScore(String advertId, String type, int score) {
        AcquireScore acquireScore = new AcquireScore();
        acquireScore.setAcquireTime(new Date().getTime() + "");
        acquireScore.setType(type);
        acquireScore.setScore(score + "");
        acquireScore.setAdvertId(advertId);

        //每次滑屏的时候直接提交积分
        if (ContextParameter.isLogin()) {
            List<AcquireScore> acquireScoreList = new ArrayList<>();
            acquireScoreList.add(acquireScore);
            UpdateScoreBean updateScoreReqeust = new UpdateScoreBean();
            updateScoreReqeust.setAcquireScores(acquireScoreList);
            Request<UpdateScoreBean> reqeust = new Request<UpdateScoreBean>();
            reqeust.setData(updateScoreReqeust);
            RXUtils.request(mContext, reqeust, "updateScore", new SupportSubscriber<Response<UpdateScoreBean>>() {
                @Override
                public void onNext(Response<UpdateScoreBean> updateScoreBeanResponse) {
                    ContextParameter.getUserInfo().setScore(updateScoreBeanResponse.getData().getTotalScore());
                    UserInfoSP.setUserInfo(ContextParameter.getUserInfo());
                    Log.d("雨落无痕丶", "score: " + ContextParameter.getUserInfo().getScore());
                }
            });
        }

        //将滑屏所得积分添加至数据库(解锁之后再一起提交积分)
//        ArchitectureAppliation.getDaoSession().getAcquireScoreDao().insert(acquireScore);
    }

    /**
     * 生成随机数
     */
    public static int randownScore() {
        int max = ContextParameter.getClientConfig().getMaxscore();
        int min = ContextParameter.getClientConfig().getMinscore();
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public void stop() {
        mHandler.removeMessages(1);
    }

    public void start() {
        mHandler.sendEmptyMessageDelayed(1, INTERVAL_TIME);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        L.e("33333333onStart");
        start();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {
        L.e("33333333onStop");
        stop();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onRestart() {

    }

    public void onDestroy() {
        mHandler.removeMessages(1);  //禁用自动滑动
        mUnlockClockView.onDestroy();
        Glide.get(mContext).clearMemory();
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        mAcquiteScoreAnimation = AnimationUtils.loadAnimation(mContext,
                R.anim.add_score_flipping_up);

        mAcquiteScoreAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束后隐藏
                mAcquiteScoreView.setVisibility(View.GONE);

            }
        });
    }

    /**
     * 解锁
     */
    public void unlock(String option) {
        AdvertImg advertImg = mAdverts == null || mAdverts.size() == 0 ? null :
                mAdverts.get(mViewPager.getCurrentItem() % mAdverts.size());
        mOptionListener.option(option, advertImg);
    }

    @Override
    public void onClick(View v) {
        L.e("点击");
        if (v.getId() == R.id.usi_unlock_mall) {
            acquireScore(null, ACQUIRE_SCORE_TYPE_UNLOCK, Integer.parseInt(mMallIcon.getScore()));
            UnlockView.this.unlock(OPTION_MALL);
        } else if (v.getId() == R.id.usi_unlock_website) {
            acquireScore(null, ACQUIRE_SCORE_TYPE_WEBSITE, Integer.parseInt(mWebsiteIcon.getScore()));
            UnlockView.this.unlock(OPTION_WEBSITE);
        }
    }

    /**
     * 界面各种操作的事件
     */
    public interface OnOptionListener {
        void option(String option, AdvertImg advertImg);
    }

    /**
     * 触发红包
     */
    public void detonateRedPaper() {

        //判断用户是否登录
        if (!ContextParameter.isLogin()) {
            L.i("用户未登录，不能触发红包");
            return;
        }

        int probability = ContextParameter.getClientConfig().getRedPaperProbability();
        int random = (int) (Math.random() * 100);

        L.e("probability->" + probability + ", random->" + random);

        //几率判断
        if (random < probability) {

            RXUtils.request(getContext(), new Request(), "checkRedPaper", new SupportSubscriber<Response<RedPaper>>() {

                @Override
                public void onResponseError(Response response) {
                    super.onResponseError(response);
                    L.i("未有红包返回");
                }

                @Override
                public void onNext(Response<RedPaper> redPaperResponse) {
                    if (redPaperResponse.getData().getSettingId() > 0) {
                        mRedPaperView.setVisibility(VISIBLE);
                        gold_iv.setVisibility(GONE);
                        chai_iv.setVisibility(VISIBLE);
                        mRedPaperView.setTag(redPaperResponse.getData());
                        animationShow(mRedPaperView, 1200).start();

                    }
                }
            });
        }
    }

    private AnimatorSet animationShow(final View target, final long duration) {
        ViewGroup parent = (ViewGroup) target.getParent();
        int distance = parent.getHeight() - target.getTop();
        AnimatorSet anim = new AnimatorSet();
        anim.playTogether(ObjectAnimator.ofFloat(target, "alpha", 0, 1, 1),
                ObjectAnimator.ofFloat(target, "scaleX", 0.1f, 0.475f, 1),
                ObjectAnimator.ofFloat(target, "scaleY", 0.1f, 0.475f, 1),
                ObjectAnimator.ofFloat(target, "translationY", distance, -60, 0)
        );
        anim.setTarget(target);
        anim.setDuration(duration);
        anim.setStartDelay(0);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        return anim;
    }

}

/**
 * 界面切换事件
 */
class PageChangeListener implements ViewPager.OnPageChangeListener {

    Context mContext;
    UnlockView mUnlockView;
    List<AdvertImg> mAdverts;

    /**
     * 用于判断是否使用手滑动的  0：自动滑动 1：手操作滑动
     */
    private int option;

    public PageChangeListener(Context context, UnlockView unlockView, List<AdvertImg> adverts) {
        mContext = context;
        mUnlockView = unlockView;
        mAdverts = adverts;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        position = position % mAdverts.size();
        AdvertImg advertImg = mAdverts.get(position);

        if (option == 0) { //自动滑动
            L.i("自动滑动");
        } else {  //手动滑动
            L.i("手动滑动");

            //触发红包
            mUnlockView.detonateRedPaper();

            //判断该图片是否已经获得积分
            if (advertImg.getAcquireSocre().equals("false")) {
                int score = acquiteGlideScreenSocre(position);
                advertImg.setAcquireSocre("true"); //设置已经获得积分，下次不在回去
                advertImg.setAcquireSocreNumber(score + ""); //设置获得的积分
                ArchitectureAppliation.getDaoSession().getAdvertImgDao().update(advertImg);
            }

        }
        option = 0;
    }

    private int acquiteGlideScreenSocre(int position) {

        int score = UnlockView.randownScore();

        //开始加分动画
        mUnlockView.mAcquiteScoreView.setVisibility(View.VISIBLE);
        mUnlockView.mAcquiteScoreView.startAnimation(mUnlockView.mAcquiteScoreAnimation);
        mUnlockView.mAcquiteScoreView.setText(score + "");

        UnlockView.acquireScore(mAdverts.get(position).getAdvertId(), UnlockView.ACQUIRE_SCORE_TYPE_GLIDE_SCREEN, score);

        return score;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
}

/**
 * 垂直ViewPager动画
 */
class VerticalTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        page.setTranslationX(page.getWidth() * -position);
        float yPosition = position * page.getHeight();
        page.setTranslationY(yPosition);
    }
}

