package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.ScoreExchangeAdapter;
import com.weslide.lovesmallscreen.views.adapters.SellerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Dong on 2016/6/26.
 */
public class ScoreExchangeFragment extends BaseFragment {
    View mView;
    @BindView(R.id.list)
    SuperRecyclerView list;
    ScoreExchangeAdapter mAdapter;
    int pageIndex = 0;
    DataList<RecyclerViewModel> mScoreData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_score_exchange, container, false);

        ButterKnife.bind(this, mView);
        initRecyclerView();
        mAdapter = new ScoreExchangeAdapter(getActivity(), mScoreData);
        list.setAdapter(mAdapter);
        return mView;
    }

    /**
     * 管理recycler
     */
    private void initRecyclerView() {

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果是商品占用一格，如果不是占用一行
                return mAdapter.isGoods(position) ? 1 : layoutManager.getSpanCount();
            }
        });
        list.setLayoutManager(layoutManager);
        list.setLoadingMore(true);
        //设置加载更多事件
        list.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                L.e("正在加载更多");
                pageIndex += 1;
                //    loadSellerGoodsData(pageIndex);
            }
        }, 2);
    }

    /**
     * 获取网络数据
     */
    private void initSellerData() {

        Request<Seller> sellerRequest = new Request<Seller>();
        // sellerRequest.setData();

        RXUtils.request(getActivity(), sellerRequest, "getSeller", new SupportSubscriber() {


            @Override
            public void onNext(Object o) {
                Response<Seller> response = (Response<Seller>) o;

                mScoreData = handlerSellerData(response);
                mAdapter = new ScoreExchangeAdapter(getActivity(), mScoreData);
                mAdapter.setShowBottemLoadingLayout(true);
                list.setAdapter(mAdapter);

                loadScoreGoodsData(pageIndex);
            }
        });
    }

    /**
     * 分页加载商家的商品
     */
    private void loadScoreGoodsData(int pageIndex) {

        Observable.just(null).observeOn(Schedulers.computation())
                .map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        GetGoodsListBean goodsReqeust = new GetGoodsListBean();
                        goodsReqeust.setPageIndex(pageIndex);
                        //   goodsReqeust.setSellerId(mSellerId);
                        Request<GetGoodsListBean> request = new Request<>();
                        request.setData(goodsReqeust);

                        RXUtils.request(getActivity(), request, "getGoodsList", new SupportSubscriber() {

                            @Override
                            public void onNoNetwork() {
                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                L.e("error", "error", e);
                            }

                            @Override
                            public void onNext(Object o) {
                                Response<GoodsList> response = (Response<GoodsList>) o;
                                DataList<RecyclerViewModel> dataList = handlerSellerGoodsListData(response);
                                addSellerData(mScoreData, dataList);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
    }
    /**
     * 添加商家详情数据
     * @param oldData
     * @param newData
     */
    public void addSellerData(DataList<RecyclerViewModel> oldData ,DataList<RecyclerViewModel> newData){
        setResponseData(oldData, newData);
        oldData.getDataList().addAll(newData.getDataList());
    }
    /**
     * 设置通用的响应数据
     * @param oldData
     * @param newData
     */
    private void setResponseData(DataList oldData ,DataList newData){
        oldData.setPageIndex(newData.getPageIndex());
        oldData.setPageItemCount(newData.getPageItemCount());
        oldData.setPageSize(newData.getPageSize());
    }
    /**
     * 将请求数据转换为RecyclerView能识别的数据集合
     * @param response
     * @return
     */
    public DataList<RecyclerViewModel> handlerSellerGoodsListData(Response<GoodsList> response){

        DataList<RecyclerViewModel> responseRecyclerViewModel = new DataList<>();
        List<RecyclerViewModel> recyclerViewModels = new ArrayList<>();

        setResponseData(responseRecyclerViewModel, response.getData());

        for (Goods goods : response.getData().getDataList()){
            RecyclerViewModel sellerRecyclerViewModel = new RecyclerViewModel();
            sellerRecyclerViewModel.setItemType(SellerAdapter.TYPE_SELLER_GOODS);
            sellerRecyclerViewModel.setData(goods);
            recyclerViewModels.add(sellerRecyclerViewModel);
        }

        responseRecyclerViewModel.setDataList(recyclerViewModels);

        return responseRecyclerViewModel;
    }
    /**
     * 将请求数据转换为RecyclerView能识别的数据集合
     * @param response
     * @return
     */
    public DataList<RecyclerViewModel> handlerSellerData(Response<Seller> response){
        DataList<RecyclerViewModel> responseRecyclerViewModel = new DataList<>();

        List<RecyclerViewModel> recyclerViewModels = new ArrayList<>();
        responseRecyclerViewModel.setDataList(recyclerViewModels );

        //头部轮播
        RecyclerViewModel imagesRecyclerViewModel = new RecyclerViewModel();
        imagesRecyclerViewModel.setItemType(SellerAdapter.TYPE_SELLER_IMAGES);
        List<ImageText> imageTexts = new ArrayList<>();
        if(response.getData().getVideos() != null) imageTexts.addAll(response.getData().getVideos());
        if(response.getData().getImages() != null)imageTexts.addAll(response.getData().getImages());
        imagesRecyclerViewModel.setData(imageTexts);
        recyclerViewModels.add(imagesRecyclerViewModel);

        //商家信息
        RecyclerViewModel baseInfoRecyclerViewModel = new RecyclerViewModel();
        baseInfoRecyclerViewModel.setItemType(SellerAdapter.TYPE_SELLER_INFO);
        baseInfoRecyclerViewModel.setData(response.getData());
        recyclerViewModels.add(baseInfoRecyclerViewModel);

        //积分商品列表
        RecyclerViewModel socreGoodsRecyclerViewModel = new RecyclerViewModel();
        socreGoodsRecyclerViewModel.setItemType(SellerAdapter.TYPE_SELLER_SCORE_GOODS);
        socreGoodsRecyclerViewModel.setData(response.getData().getRecommendScoreGoodsList());
        recyclerViewModels.add(socreGoodsRecyclerViewModel);

        //横幅广告
        RecyclerViewModel bannerRecyclerViewModel = new RecyclerViewModel();
        bannerRecyclerViewModel.setItemType(SellerAdapter.TYPE_SELLER_BANNER);
        bannerRecyclerViewModel.setData(response.getData().getBanner());
        recyclerViewModels.add(bannerRecyclerViewModel);

        return responseRecyclerViewModel;
    }
}