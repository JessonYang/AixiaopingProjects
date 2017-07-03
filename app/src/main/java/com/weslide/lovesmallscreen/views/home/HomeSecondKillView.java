package com.weslide.lovesmallscreen.views.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.weslide.lovesmallscreen.activitys.SecondKillActivity;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.fragments.mall.HomeSecondKillGoodsFragment;
import com.weslide.lovesmallscreen.models.SecondKill;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.DateUtils;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.ScreenUtils;
import com.weslide.lovesmallscreen.views.custom.SuperViewPager;

import net.aixiaoping.library.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by xu on 2016/6/4.
 * 首页限时秒杀模块
 */
public class HomeSecondKillView extends FrameLayout {

    /** 开始秒杀 */
    boolean startSecondKill;

    List<HomeSecondKillGoodsFragment> mFragments = new ArrayList<>();
    List<String> mTitles = new ArrayList<>();
    @BindView(R.id.stl_second_kill_tab)
    SlidingTabLayout stlSecondKillTab;
    @BindView(R.id.cv_second_kill)
    CountdownView cvSecondKill;
    @BindView(R.id.layout_more)
    RelativeLayout layoutMore;
    @BindView(R.id.vp_pager)
    SuperViewPager vpPager;
    @BindView(R.id.tv_countdown_title)
    TextView tvCountdownTitle;

    int currentPosition = 0;
    List<SecondKill> secondKills;

    public HomeSecondKillView(Context context) {
        super(context);
        initView();
    }

    public HomeSecondKillView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HomeSecondKillView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.home_view_second_kill, this, true);
        ButterKnife.bind(this);

        ViewGroup.LayoutParams layoutParams = vpPager.getLayoutParams();

        int height = ScreenUtils.getScreenWidth(getContext()) / 2;
        height += DensityUtils.dp2px(getContext(), 92);
        layoutParams.height = height;

        vpPager.setLayoutParams(layoutParams);

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (secondKills == null) {
            return;
        }

        startCountdown();
    }

    @Override
    protected void onDetachedFromWindow() {
        cvSecondKill.stop();
    }

    /**
     * 设置值
     *
     * @param secondKills
     */
    public void bindData(List<SecondKill> secondKills) {

        mFragments.clear();
        mTitles.clear();

        this.secondKills = secondKills;


        for (int i = 0; i < (secondKills.size() >= 3 ? 3 : secondKills.size()); i++) {
            mFragments.add(HomeSecondKillGoodsFragment.newInstance(secondKills.get(i)));
            mTitles.add(secondKills.get(i).getSecondKillName());
        }

        vpPager.setAdapter(new SecondKillFragmentPageAdapter(((BaseActivity) getContext()).getSupportFragmentManager()));
        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                startCountdown();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        stlSecondKillTab.setViewPager(vpPager);

        stlSecondKillTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                currentPosition = position;
                startCountdown();

            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        startCountdown();

    }

    static long dayTime = 1000 * 60 * 60 * 24;

    public void startCountdown() {


        //计算时间
        SecondKill secondKill = secondKills.get(currentPosition);
        String startDate = DateUtils.date2Str(new Date(), DateUtils.FORMAT_YMD) + " " + secondKill.getStartDate();
        String endDate = DateUtils.date2Str(new Date(), DateUtils.FORMAT_YMD) + " " + secondKill.getEndDate();

        long endDateMillis = DateUtils.getMillis(DateUtils.str2Date(endDate));
        long startDateMillis = DateUtils.getMillis(DateUtils.str2Date(startDate));
        long currentDateMillis = new Date().getTime();

        long interval = 0;

        if (currentDateMillis >= startDateMillis && currentDateMillis <= endDateMillis) {
            tvCountdownTitle.setText(getResources().getString(R.string.home_second_kill_with_end));
            interval = endDateMillis - currentDateMillis;
            startSecondKill = true;
        } else {
            tvCountdownTitle.setText(getResources().getString(R.string.home_second_kill_with_start));

            if((startDateMillis - currentDateMillis) < 0){
                startDateMillis += dayTime;
            }

            interval = startDateMillis - currentDateMillis;
            startSecondKill = false;
        }

        cvSecondKill.start(interval);

        mFragments.get(currentPosition).setStartSecondKill(startSecondKill);
    }

    @OnClick(R.id.tv_more)
    public void onClick() {

        AppUtils.toActivity(getContext(), SecondKillActivity.class);

    }

//    private long convertTime(SecondKill secondKill){
//
//    }

    class SecondKillFragmentPageAdapter extends FragmentPagerAdapter {

        public SecondKillFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getCount() {
            return mTitles.size();
        }
    }

}

