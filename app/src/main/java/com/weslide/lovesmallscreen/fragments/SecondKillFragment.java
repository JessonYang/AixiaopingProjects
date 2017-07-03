package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.SecondKill;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.DateUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.DetachedFromWindowCountDownView;
import com.weslide.lovesmallscreen.views.adapters.SecondKillAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/21.
 */
public class SecondKillFragment extends BaseFragment {
    View mView;
    GetGoodsListBean mGoodsListReqeust = new GetGoodsListBean();
    DataList<Goods> mDataList = new DataList<>();
    SecondKillAdapter mAdapter;
    @BindView(R.id.lv_scoond_kill)
    SuperRecyclerView lvScoondKill;
    private String mSecondKillId;
    public boolean startSecondKill;
    public SecondKillFragment(){

    }



    public static final SecondKillFragment newInstance(String secondKillId) {
        SecondKillFragment fragment = new SecondKillFragment();
        fragment.mSecondKillId = secondKillId;
        return fragment;
    }
    public void setStartSecondKill(boolean startSecondKill){
        this.startSecondKill = startSecondKill;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_secondkill, container, false);


        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        mAdapter = new SecondKillAdapter(getActivity(), mDataList,SecondKillFragment.this);
        mGoodsListReqeust.setMallTyle(Constants.MALL_SECOND_KILL);
        reLoadData();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        lvScoondKill.setLayoutManager(manager);

        lvScoondKill.setAdapter(mAdapter);

        lvScoondKill.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getGoodList(), 2);

        lvScoondKill.setRefreshListener(() -> reLoadData());
        lvScoondKill.setDifferentSituationOptionListener(v -> reLoadData());

    }

    public void reLoadData(){
        mGoodsListReqeust.setPageIndex(0);
        getGoodList();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void getGoodList() {
        Request<GetGoodsListBean> request = new Request<>();
        mGoodsListReqeust.setPageIndex(mGoodsListReqeust.getPageIndex() + 1);
        mGoodsListReqeust.setSecondKillId(mSecondKillId);
        request.setData(mGoodsListReqeust);

        RXUtils.request(getActivity(), request, "getGoodsList", new RecyclerViewSubscriber<Response<DataList<Goods>>>(mAdapter, mDataList) {

            @Override
            public void onSuccess(Response<DataList<Goods>> goodsResponse) {

             mAdapter.addDataListNotifyDataSetChanged(goodsResponse.getData());
            }
        });
    }
}
