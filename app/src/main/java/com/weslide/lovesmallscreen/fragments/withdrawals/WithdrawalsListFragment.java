package com.weslide.lovesmallscreen.fragments.withdrawals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.user.BindingContactsActivity;
import com.weslide.lovesmallscreen.activitys.user.RetrieveActivity;
import com.weslide.lovesmallscreen.activitys.withdrawals.AuthenticationActivity;
import com.weslide.lovesmallscreen.activitys.withdrawals.WithdrawalsActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/30.
 */
public class WithdrawalsListFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.stl_score_exchange_tab)
    SlidingTabLayout mTab;
    @BindView(R.id.vp_main_mall_search_shop_list)
    ViewPager vp;
    List<BaseFragment> mFragments = new ArrayList<>();
    ApplyListFragment fragment2 = new ApplyListFragment();
    ApplyForPayListFragment fragment1 = new ApplyForPayListFragment();
    List<String> mTitles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_withdra_list, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init(){
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mTitles.add("已确认");
        mTitles.add("未确认");
        vp.setAdapter(new ApplyFragmentPageAdapter(getActivity().getSupportFragmentManager()));
        mTab.setViewPager(vp);
    }

    class ApplyFragmentPageAdapter extends FragmentPagerAdapter {

        public ApplyFragmentPageAdapter(FragmentManager fm) {
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

    @OnClick(R.id.btn_withdraw)
    public void onClick() {
        L.e("手机号码"+ContextParameter.getUserInfo().getPhone());
        if(!StringUtils.isBlank(ContextParameter.getUserInfo().getPhone())) {
            AppUtils.toActivity(getActivity(), AuthenticationActivity.class);
        }else {
            AppUtils.toActivity(getActivity(), RetrieveActivity.class);
        }
    }
}
