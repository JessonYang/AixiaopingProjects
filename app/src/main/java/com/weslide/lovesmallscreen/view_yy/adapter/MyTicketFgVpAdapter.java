package com.weslide.lovesmallscreen.view_yy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.weslide.lovesmallscreen.view_yy.fragment.MyTicketVpBaseFragment;

import java.util.List;

/**
 * Created by YY on 2017/6/7.
 */
public class MyTicketFgVpAdapter extends FragmentPagerAdapter {

    private List<MyTicketVpBaseFragment> list;
    private String[] titles;

    public MyTicketFgVpAdapter(FragmentManager fm, List<MyTicketVpBaseFragment> list, String[] titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
