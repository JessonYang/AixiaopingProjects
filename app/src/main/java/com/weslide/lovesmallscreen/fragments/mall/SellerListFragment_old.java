package com.weslide.lovesmallscreen.fragments.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.SellerListActivity_old;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.SellerList;
import com.weslide.lovesmallscreen.models.bean.GetSellerListBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.SellerListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/23.
 * 商家列表
 */
public class SellerListFragment_old extends BaseFragment {

    View mView;
    @BindView(R.id.list)
    SuperRecyclerView list;
    SellerListAdapter mAdapter;
    SellerList mSellerList = new SellerList();
    GetSellerListBean getSellerListBean = new GetSellerListBean();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_seller_list_old, container, false);

        ButterKnife.bind(this, mView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);

        mAdapter = new SellerListAdapter(getActivity(), mSellerList);

        list.setAdapter(mAdapter);

        //刷新
        list.setRefreshListener(() -> {
            getSellerListBean.setPageIndex(0);
            loadData();
        });
        //加载更多
        list.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> loadData(), 2);
        //数据请求失败后的数据重新载入
        list.setDifferentSituationOptionListener(view -> {
            getSellerListBean.setPageIndex(0);
            loadData();
        });

        loadData();
        return mView;
    }

    public void loadData(){
        Request request = new Request();
        getSellerListBean.setPageIndex(getSellerListBean.getPageIndex() + 1);
        getSellerListBean.setType("LIST");//列表类型
        request.setData(getSellerListBean);
        RXUtils.request(getActivity(), request, "getSellerList", new RecyclerViewSubscriber<Response<SellerList>>(mAdapter, mSellerList) {
            @Override
            public void onSuccess(Response<SellerList> sellerListResponse) {
                SellerListActivity_old.sellerList = (ArrayList) sellerListResponse.getData().getDataList();
                mAdapter.addDataListNotifyDataSetChanged(sellerListResponse.getData());
            }
        });
    }
}
