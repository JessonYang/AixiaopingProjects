package com.weslide.lovesmallscreen.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.fragments.SecondKillFragment;
import com.weslide.lovesmallscreen.fragments.mall.HomeSecondKillGoodsFragment;
import com.weslide.lovesmallscreen.models.SecondKill;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.DateUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.DetachedFromWindowCountDownView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/23.
 * 秒杀
 */
public class SecondKillActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.stl_score_exchange_tab)
    SlidingTabLayout stlScoreExchangeTab;
    @BindView(R.id.vp_second_kill)
    ViewPager vpSecondKill;
    List<SecondKillFragment> mFragments = new ArrayList<>();
    List<String> mTitles = new ArrayList<>();
    @BindView(R.id.tv_countdown_title)
    TextView tvCountdownTitle;
    @BindView(R.id.cv_second_kill)
    DetachedFromWindowCountDownView cvSecondKill;
    boolean startSecondKill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_kill);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondKillActivity.this.finish();
            }
        });
        getSecondKillMall();
    }

    /**
     * 获取秒杀商城secondKillId以及开始结束的时间
     */
    private void getSecondKillMall() {
        Request request = new Request();
        RXUtils.request(this, request, "secondKillMall", new SupportSubscriber<Response<DataList<SecondKill>>>() {

            @Override
            public void onNext(Response<DataList<SecondKill>> response) {
                addFragment(response.getData().getDataList());
            }
        });
    }

    private void addFragment(List<SecondKill> data) {
        for (int i = 0; i < data.size(); i++) {
            mFragments.add(SecondKillFragment.newInstance(data.get(i).getSecondKillId()));
            mTitles.add(data.get(i).getSecondKillName());
        }
        vpSecondKill.setAdapter(new SccondKillFragmentPageAdapter(this.getSupportFragmentManager()));
        vpSecondKill.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position){
                startCountdown(data,position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        stlScoreExchangeTab.setViewPager(vpSecondKill);
        startCountdown(data,0);
        stlScoreExchangeTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                startCountdown(data,position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    class SccondKillFragmentPageAdapter extends FragmentPagerAdapter {

        public SccondKillFragmentPageAdapter(FragmentManager fm) {
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

    static long dayTime = 1000 * 60 * 60 * 24;
    public void startCountdown(List<SecondKill> secondKills, int postion) {
        //计算时间
        SecondKill secondKill = secondKills.get(postion);
        String startDate = DateUtils.date2Str(new Date(), DateUtils.FORMAT_YMD) + " " + secondKill.getStartDate();
        String endDate = DateUtils.date2Str(new Date(), DateUtils.FORMAT_YMD) + " " + secondKill.getEndDate();

        long endDateMillis = DateUtils.getMillis(DateUtils.str2Date(endDate));
        long startDateMillis = DateUtils.getMillis(DateUtils.str2Date(startDate));
        long currentDateMillis = new Date().getTime();
        long interval = 0;
        if (currentDateMillis >= startDateMillis && currentDateMillis <= endDateMillis) {
            tvCountdownTitle.setText(getResources().getString(net.aixiaoping.library.R.string.home_second_kill_with_end));
            interval = endDateMillis - currentDateMillis;
            startSecondKill = true;
        } else {
            tvCountdownTitle.setText(getResources().getString(net.aixiaoping.library.R.string.home_second_kill_with_start));

            if((startDateMillis - currentDateMillis) < 0){
                startDateMillis += dayTime;
            }

            interval = startDateMillis - currentDateMillis;
            startSecondKill = false;
        }
        if(interval ==0){
            mFragments.get(postion).reLoadData();
        }
        cvSecondKill.start(interval);
        mFragments.get(postion).setStartSecondKill(startSecondKill);
        mFragments.get(postion).reLoadData();
    }

}
