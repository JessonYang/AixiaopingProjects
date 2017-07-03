package com.weslide.lovesmallscreen.fragments.sellerinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.malinskiy.superrecyclerview.decoration.SpaceItemDecoration;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.BackOrderList;
import com.weslide.lovesmallscreen.models.bean.GetBackOrderListBean;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateSellerBackOrderMessage;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.SellerHandlerBackOrderListAdapter;
import com.weslide.lovesmallscreen.views.adapters.SellerOrderListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/25.
 * 商家处理用户退单列表
 */
public class SellerHandlerBackOrderListFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.list)
    SuperRecyclerView list;
    SellerHandlerBackOrderListAdapter mAdapter;
    BackOrderList mBackOrderList = new BackOrderList();
    GetBackOrderListBean getBackOrderListBean = new GetBackOrderListBean();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        mView = inflater.inflate(R.layout.fragment_seller_handler_back_order_list, container, false);

        ButterKnife.bind(this, mView);

        getBackOrderListBean.setSellerId(ContextParameter.getUserInfo().getSellerId());

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);
        //添加项的间隔
        list.addItemDecoration(new SpaceItemDecoration(DensityUtils.dp2px(getActivity(), 8)));
        mAdapter = new SellerHandlerBackOrderListAdapter(getActivity(), mBackOrderList);
        list.setAdapter(mAdapter);

        //加载更多
        list.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                loadData();
            }
        });

        //下拉刷新
        list.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBackOrderListBean.setPageIndex(0);
                loadData();
            }
        });

        //重新加载
        list.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBackOrderListBean.setPageIndex(0);
                loadData();
            }
        });

        loadData();

        return mView;
    }


    @Subscribe
    public void onEvent(UpdateSellerBackOrderMessage message){
        getBackOrderListBean.setPageIndex(0);
        loadData();
    }



    private void loadData() {
        getBackOrderListBean.setPageIndex(getBackOrderListBean.getPageIndex() + 1);
        RXUtils.request(getActivity(), new Request().setData(getBackOrderListBean), "getSellerBackOrderList", new RecyclerViewSubscriber<Response<BackOrderList>>(mAdapter, mBackOrderList) {

            @Override
            public void onSuccess(Response<BackOrderList> backOrderListResponse) {
                mAdapter.addDataListNotifyDataSetChanged(backOrderListResponse.getData());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }
}
