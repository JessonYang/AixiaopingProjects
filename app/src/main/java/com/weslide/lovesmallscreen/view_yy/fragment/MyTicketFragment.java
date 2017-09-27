package com.weslide.lovesmallscreen.view_yy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.view_yy.adapter.MyTicketFgVpAdapter;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/6/6.
 */
public class MyTicketFragment extends BaseFragment {
    private View mFgView;
    private TabLayout tab;
    private ViewPager vp;
    private String[] titles = new String[]{"未使用", "已使用", "已过期"};
    private List<MyTicketVpBaseFragment> mFragmentList;
    private CustomToolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFgView = inflater.inflate(R.layout.fragment_my_ticket, container, false);
        initView();
        initData();
        return mFgView;
    }

    private void initData() {
        addFragmentList();
        vp.setAdapter(new MyTicketFgVpAdapter(getActivity().getSupportFragmentManager(), mFragmentList, titles));
        vp.setOffscreenPageLimit(2);
        tab.setupWithViewPager(vp);
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                getActivity().finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
    }

    private void addFragmentList() {
        mFragmentList = new ArrayList<>();
        int type;
        for (int i = 0; i < titles.length; i++) {
            if (i != titles.length - 1) {
                type = i;
            } else type = -1;
            Bundle bundle = new Bundle();
            bundle.putString("ticketType", String.valueOf(type));
            mFragmentList.add(MyTicketVpBaseFragment.getInstance(bundle));
        }
    }

    private void initView() {
        tab = ((TabLayout) mFgView.findViewById(R.id.my_ticket_tab));
        vp = ((ViewPager) mFgView.findViewById(R.id.my_ticket_vp));
        toolbar = ((CustomToolbar) mFgView.findViewById(R.id.my_ticket_toolbar));
    }
}
