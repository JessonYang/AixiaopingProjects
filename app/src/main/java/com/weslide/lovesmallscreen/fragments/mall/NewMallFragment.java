package com.weslide.lovesmallscreen.fragments.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kaelaela.verticalviewpager.VerticalViewPager;

/**
 * Created by Administrator on 2016/10/22.
 */
public class NewMallFragment extends BaseFragment {
    View mView;
    @BindView(R.id.home_view_pager)
    VerticalViewPager homeViewPager;
    List<BaseFragment> mFragments = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_home_mall_all, container, false);

        ButterKnife.bind(this, mView);
        mFragments.add(new SecondMallFragment());
        mFragments.add(new MallFragment());

        homeViewPager.setAdapter(new HomeMallFragmentPageAdapter(getActivity().getSupportFragmentManager()));
        return mView;
    }

    class HomeMallFragmentPageAdapter extends FragmentPagerAdapter {

        public HomeMallFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }


        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
