package com.weslide.lovesmallscreen.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.utils.L;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/14.
 * 商家中心订单列表
 */
public class SellerOrderFragment extends BaseFragment {

    /**
     * 用于传递订单状态
     */
    public static final String KEY_ORDER_STATUS = "KEY_ORDER_STATUS";

    /**
     * 默认显示
     */
    private String showOrderStatus = Constants.ORDER_STATUS_WAIT_CONFIRM;

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tl_tab)
    SegmentTabLayout tlTab;
    @BindView(R.id.vp_pager)
    ViewPager vpPager;

    String[] mTitles = new String[]{"待确认", "待发货", "待兑换", "待收货", "已评价", "已完成"};
    ArrayList<SellerOrderListFragment> mSellerOrderView = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_seller_order, container, false);

        ButterKnife.bind(this, mView);

        toolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        loadBundle();

        init();

        return mView;
    }

    private void loadBundle() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            showOrderStatus = bundle.getString(KEY_ORDER_STATUS);
        }
    }

    private void init() {
        /** 待确认 */
        SellerOrderListFragment waitPay = new SellerOrderListFragment(getActivity(), Constants.ORDER_STATUS_WAIT_CONFIRM);
        /** 待发货 */
        SellerOrderListFragment waiSendOutGoods = new SellerOrderListFragment(getActivity(), Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS);
        /** 待兑换 */
        SellerOrderListFragment waitOfGoods = new SellerOrderListFragment(getActivity(), Constants.ORDER_STATUS_WAIT_EXCHANGE);
        /** 待收货 */
        SellerOrderListFragment waitComment = new SellerOrderListFragment(getActivity(), Constants.ORDER_STATUS_WAIT_OF_GOODS);
        /** 已评价 */
        SellerOrderListFragment comment = new SellerOrderListFragment(getActivity(), Constants.ORDER_STATUS_COMMENT);
        /** 已完成 */
        SellerOrderListFragment end = new SellerOrderListFragment(getActivity(), Constants.ORDER_STATUS_END);

        mSellerOrderView.add(waitPay);
        mSellerOrderView.add(waiSendOutGoods);
        mSellerOrderView.add(waitOfGoods);
        mSellerOrderView.add(waitComment);
        mSellerOrderView.add(comment);
        mSellerOrderView.add(end);

        tlTab.setTabData(mTitles);

        vpPager.setOffscreenPageLimit(0);
        vpPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mSellerOrderView.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mSellerOrderView.get(position));
                return mSellerOrderView.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mSellerOrderView.get(position));
            }

        });
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tlTab.setCurrentTab(position);
                L.e("加载数据：" + position);
                mSellerOrderView.get(position).reloadDtaa();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tlTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        switch (showOrderStatus) {
            case Constants.ORDER_STATUS_WAIT_CONFIRM:
                tlTab.setCurrentTab(0);
                vpPager.setCurrentItem(0);
                mSellerOrderView.get(0).reloadDtaa();
                break;
            case Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS:
                tlTab.setCurrentTab(1);
                vpPager.setCurrentItem(1);
                break;
            case Constants.ORDER_STATUS_WAIT_EXCHANGE:
                tlTab.setCurrentTab(2);
                vpPager.setCurrentItem(2);
                break;
            case Constants.ORDER_STATUS_WAIT_OF_GOODS:
                tlTab.setCurrentTab(3);
                vpPager.setCurrentItem(3);
                break;
            case Constants.ORDER_STATUS_COMMENT:
                tlTab.setCurrentTab(4);
                vpPager.setCurrentItem(4);
                break;
            case Constants.ORDER_STATUS_END:
                tlTab.setCurrentTab(5);
                vpPager.setCurrentItem(5);
                break;
        }
    }
}
