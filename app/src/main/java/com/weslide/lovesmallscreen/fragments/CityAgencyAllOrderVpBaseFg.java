package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.bean.Orders;
import com.weslide.lovesmallscreen.models.bean.OrdersOb;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.CityAgencyOrderAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/3/23.
 */
public class CityAgencyAllOrderVpBaseFg extends BaseFragment {

    private String time_num;
    private View vpFg;
    private int page = 1;
    private static int DEFAULT_PAGE = 0;
    private static int REFRESH_PAGE = 1;
    private PullToRefreshListView lv;
    private List<OrdersOb> list = new ArrayList<>();
    private CityAgencyOrderAdapter mAdapter;
    private String userId;
    private ImageView all_order_wudingdan_iv;
    public String orderType;

    public static CityAgencyAllOrderVpBaseFg getInstance(Bundle bundle) {
        CityAgencyAllOrderVpBaseFg cityAgencyAllOrderVpBaseFg = new CityAgencyAllOrderVpBaseFg();
        cityAgencyAllOrderVpBaseFg.setArguments(bundle);
        return cityAgencyAllOrderVpBaseFg;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        time_num = getArguments().getString("time_num");
        orderType = getArguments().getString("orderType");
        vpFg = inflater.inflate(R.layout.fragment_city_agency_all_order_vp, container, false);
        initView();
        mAdapter = new CityAgencyOrderAdapter(getActivity(), list);
        lv.setAdapter(mAdapter);
        /*lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            }
        });*/
        userId = ContextParameter.getUserInfo().getUserId();
        getLvData(userId, String.valueOf(page), time_num, "", DEFAULT_PAGE);
        lv.setMode(PullToRefreshBase.Mode.PULL_UP_TO_REFRESH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                lv.onRefreshComplete();
//                Log.d("雨落无痕丶", "onPullDownToRefresh: yyyyyyyyyyyy");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getLvData(userId, String.valueOf(page), String.valueOf(time_num), "", REFRESH_PAGE);
//                Log.d("雨落无痕丶", "onPullUpToRefresh: page:"+page);
            }
        });
        return vpFg;
    }

    private void initView() {
        lv = ((PullToRefreshListView) vpFg.findViewById(R.id.original_city_agency_all_order_lv));
        all_order_wudingdan_iv = ((ImageView) vpFg.findViewById(R.id.all_order_wudingdan_iv));
    }

    private void getLvData(String userId, String page, String timeNum, String search, int type) {
        Request<Orders> ordersRequest = new Request<>();
        Orders orders = new Orders();
        orders.setTime_num(timeNum);
        orders.setOrderType(orderType);
        orders.setPage(page);
        orders.setSeach(search);
        ordersRequest.setData(orders);
        RXUtils.request(getActivity(), ordersRequest, "getPartnerOrder", new SupportSubscriber<Response<Orders>>() {
            @Override
            public void onNext(Response<Orders> ordersResponse) {
                if (type == 0) {
                    list.clear();
                    list.addAll(ordersResponse.getData().getOrdersObs());
                    mAdapter.notifyDataSetChanged();
//                    adapter = new CityAgencyOrderAdapter(OriginalCityAgencyFragment.this.getActivity(), list);
//                    lv.setAdapter(adapter);
//                    Log.d("雨落无痕丶", "onNext: oooooooooooo" + list.size());
                } else if (type == 1) {
                    if (list != null) {
                        list.addAll(ordersResponse.getData().getOrdersObs());
                        mAdapter.notifyDataSetChanged();
                        lv.onRefreshComplete();
                    }
                }
                if (list.size() == 0 || list == null) {
                    lv.setVisibility(View.GONE);
                    all_order_wudingdan_iv.setVisibility(View.VISIBLE);
                } else {
                    lv.setVisibility(View.VISIBLE);
                    all_order_wudingdan_iv.setVisibility(View.GONE);
                }
                EventBus.getDefault().post("order");
            }
        });
    }

    public void upDateOrder(String search) {
        getLvData(userId, String.valueOf(page), time_num, search, DEFAULT_PAGE);
    }

}
