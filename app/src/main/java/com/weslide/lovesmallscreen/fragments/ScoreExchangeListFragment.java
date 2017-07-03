package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.ScoreExchangeActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.Concentration;
import com.weslide.lovesmallscreen.models.Fans;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.bean.FansBean;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.FreeGoodsAdapter;
import com.weslide.lovesmallscreen.views.adapters.HomeMallAdapter;
import com.weslide.lovesmallscreen.views.adapters.ScoreExchangeAdapter;
import com.weslide.lovesmallscreen.views.adapters.ScoreExchangeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/18.
 * 积分兑换商城列表
 */
public class ScoreExchangeListFragment extends BaseFragment {

    View mView;
    @BindView(R.id.lv_score_exchange)
    SuperRecyclerView lvScoreExchange;
    private int mTag;
    ScoreExchangeActivity mActivity;
    Concentration concentration;
    DataList<RecyclerViewModel> mDataList = new DataList<>();
    GetGoodsListBean mGoodsListReqeust = new GetGoodsListBean();
    public ScoreExchangeListFragment() {

    }



    public static final ScoreExchangeListFragment newInstance(int tag) {
        ScoreExchangeListFragment fragment = new ScoreExchangeListFragment();
        fragment.mTag = tag;
        return fragment;
    }

    public static final ScoreExchangeListFragment newInstance(int tag, Concentration concentration) {
        ScoreExchangeListFragment fragment = new ScoreExchangeListFragment();
        fragment.mTag = tag;
        fragment.concentration = concentration;
        return fragment;
    }

    ScoreExchangeListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_score_exchange_list, container, false);
        mActivity  = (ScoreExchangeActivity) getContext();
        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        mDataList.setDataList(new ArrayList<>());
        mGoodsListReqeust.setMallTyle(Constants.MALL_SOCRE);
        mGoodsListReqeust.setTag(new int[]{mTag});
        mAdapter = new ScoreExchangeListAdapter(getActivity(), mDataList);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (mAdapter.isGoods(position) == 1) {
                    return 4;
                } else if (mAdapter.isGoods(position) == ScoreExchangeListAdapter.TYPE_SELECTION) {
                    return 1;
                } else if(mAdapter.isGoods(position) == ScoreExchangeListAdapter.TYPE_GOODS_ITEM){
                    return 2;
                }
                return layoutManager.getSpanCount();
            }
        });
        lvScoreExchange.setLayoutManager(layoutManager);
        lvScoreExchange.setAdapter(mAdapter);
        load();
        lvScoreExchange.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getScoreExchangeGood(mGoodsListReqeust), 2);

        lvScoreExchange.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }


        });
        lvScoreExchange.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
    }

    /**
     * 加载数据
     */
    public void load() {
        mDataList.getDataList().clear();
        mGoodsListReqeust.setPageIndex(0);
        getScoreExchangeGood(mGoodsListReqeust);
        if (concentration != null) {
            headerBanner(concentration.getConcentrationBanners());
            selectionData(concentration.getConcentrationImageTexts());
        }
    }

    /**
     * 加载更多数据
     */
    private void getScoreExchangeGood(GetGoodsListBean bean) {
        Request<GetGoodsListBean> request = new Request<>();
        bean.setPageIndex(bean.getPageIndex()+1);
        request.setData(bean);

        RXUtils.request(getActivity(), request, "getGoodsList", new RecyclerViewSubscriber<Response<GoodsList>>(mAdapter, mDataList) {

            @Override
            public void onSuccess(Response<GoodsList> goodsListResponse) {
                handlerGoodsItem(goodsListResponse);

                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 处理商品列表
     *
     * @param response
     * @return
     */
    public void handlerGoodsItem(Response<GoodsList> response) {
        setResponseData(mDataList, response.getData());

        for (Goods goods : response.getData().getDataList()) {
            RecyclerViewModel recyclerViewModel = new RecyclerViewModel();
            recyclerViewModel.setData(goods);
            recyclerViewModel.setItemType(ScoreExchangeListAdapter.TYPE_GOODS_ITEM);

            mDataList.getDataList().add(recyclerViewModel);
        }

    }

    /**
     * 设置通用的响应数据
     *
     * @param oldData
     * @param newData
     */
    private void setResponseData(DataList oldData, DataList newData) {
        oldData.setPageIndex(newData.getPageIndex());
        oldData.setPageItemCount(newData.getPageItemCount());
        oldData.setPageSize(newData.getPageSize());
    }


    public void headerBanner(List<ImageText> data) {
        /*DataList<RecyclerViewModel> responseRecyclerViewModel = new DataList<>();

        List<RecyclerViewModel> recyclerViewModels = new ArrayList<>();

        for (ImageText imageText : data) {
            RecyclerViewModel bannerRecyclerViewModel = new RecyclerViewModel();
            bannerRecyclerViewModel.setItemType(type);
            bannerRecyclerViewModel.setData(imageText);
            recyclerViewModels.add(bannerRecyclerViewModel);
        }

        responseRecyclerViewModel.setDataList(recyclerViewModels);*/
        RecyclerViewModel recyclerViewModel = new RecyclerViewModel();
        recyclerViewModel.setData(data);
        recyclerViewModel.setItemType(ScoreExchangeListAdapter.HEAD_BANNER);

        mDataList.getDataList().add(recyclerViewModel);

    }

    public void selectionData(List<ImageText> data) {
        for (ImageText imageText : data) {
            RecyclerViewModel recyclerViewModel = new RecyclerViewModel();
            recyclerViewModel.setData(imageText);
            recyclerViewModel.setItemType(ScoreExchangeListAdapter.TYPE_SELECTION);

            mDataList.getDataList().add(recyclerViewModel);
        }
    }

}
