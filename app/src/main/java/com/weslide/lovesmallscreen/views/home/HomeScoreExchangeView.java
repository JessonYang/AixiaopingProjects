package com.weslide.lovesmallscreen.views.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.activitys.ScoreExchangeActivity;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.fragments.goods.GoodsFragment;
import com.weslide.lovesmallscreen.fragments.mall.HomeScoreExchangeGoodsFragment;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.HomeScoreExchangeGoodsAdapter;
import com.weslide.lovesmallscreen.views.custom.SuperViewPager;

import net.aixiaoping.library.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/4.
 * 首页积分兑换模块
 */
public class HomeScoreExchangeView extends FrameLayout {

    @BindView(R.id.stl_score_exchange_tab)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.vp_score_pager)
    ViewPager mPager;

    List<BaseFragment> mFragments = new ArrayList<>();
    List<String> mTitles = new ArrayList<>();

    public HomeScoreExchangeView(Context context) {
        super(context);
        initView();
    }

    public HomeScoreExchangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HomeScoreExchangeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.home_view_score_exchange, this, true);

        ButterKnife.bind(this);

    }

    /**
     * 设置值
     *
     * @param data
     */
    public void bindData(List<ImageText> data) {

        mFragments.clear();
        mTitles.clear();

        for (ImageText imageText : data) {
            mFragments.add(HomeScoreExchangeGoodsFragment.newInstance(Integer.parseInt(imageText.getTypeId())));
            mTitles.add(imageText.getName());
        }

        mPager.setAdapter(new ScoreExchangeFragmentPageAdapter(((BaseActivity) getContext()).getSupportFragmentManager()));
        mTabLayout.setViewPager(mPager);

    }

    @OnClick(R.id.tv_more)
    public void onClick() {

        AppUtils.toActivity(getContext(), ScoreExchangeActivity.class);

    }

    class ScoreExchangeFragmentPageAdapter extends FragmentPagerAdapter {

        public ScoreExchangeFragmentPageAdapter(FragmentManager fm) {
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

