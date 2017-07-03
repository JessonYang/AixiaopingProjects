package com.weslide.lovesmallscreen.fragments.mall;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.widget.VerticalSlide;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.CityDistrictActivity;
import com.weslide.lovesmallscreen.activitys.mall.GoodsSearchActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Home;
import com.weslide.lovesmallscreen.models.Live;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.SellerList;
import com.weslide.lovesmallscreen.models.TaoKe;
import com.weslide.lovesmallscreen.models.bean.GetSellerListBean;
import com.weslide.lovesmallscreen.models.config.ShareContent;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateMallMessage;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.network.StatusCode;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.NetworkUtils;
import com.weslide.lovesmallscreen.utils.QRCodeUtil;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.SerializableUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.views.adapters.HomeMallAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xu on 2016/6/3.
 * 商城界面
 */
public class MallFragment extends BaseFragment {

    /**
     * 首页数据序列化存储文件名 Response<List<RecyclerViewModel>>
     */
    public static final String DATA_SERIALIZE_FILE_NAME = "MALL_HOME";

    @BindView(R.id.list)
    public SuperRecyclerView mRecyclerView;
    HomeMallAdapter mAdapter;
    DataList<RecyclerViewModel> mHomeDataList;

    GetSellerListBean getSellerListReqeust = new GetSellerListBean();
    RecyclerView.OnScrollListener mTitleScrollListener;
    View mView;
    int size = 0;
    GridLayoutManager layoutManager;

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mall, container, false);
        ButterKnife.bind(this, mView);

        EventBus.getDefault().register(this);

        //读取序列化数据
        Object object = SerializableUtils.getObjectByCacheFile(getActivity(), DATA_SERIALIZE_FILE_NAME);
        if (object == null) {
            mHomeDataList = new DataList<>();
            mHomeDataList.setDataList(new ArrayList<>());
        } else {
            mHomeDataList = (DataList<RecyclerViewModel>) object;
        }
        mAdapter = new HomeMallAdapter(getActivity(), mHomeDataList, mRecyclerView);
        layoutManager = new GridLayoutManager(getActivity(), 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (mAdapter.getItemViewType(position) == 12) {
                    if(size == 1){
                        return 4;
                    }
                    return 2;
                }else if(mAdapter.getItemViewType(position) == 14){
                    return 1;
                }
                return layoutManager.getSpanCount();
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //加载更多
        mRecyclerView.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> {
            L.e("加载下一页");
            getSellerListReqeust.setPageIndex(getSellerListReqeust.getPageIndex() + 1);
            loadSellerList(getSellerListReqeust);
        }, 3);

        //滑动状态改变
        mRecyclerView.setOnScrollListener(mTitleScrollListener);


        L.e(AppUtils.getSign(getActivity(), getActivity().getPackageName()));

        load();
        return mView;
    }
    public void goTop(){
        layoutManager.scrollToPosition(0);
    }

    public void setTitleOnScrollListener(RecyclerView.OnScrollListener listener){
        mTitleScrollListener = listener;
    }

    private void load() {
        loadHomeData();

    }
    /**
     * eventBus事件
     *
     * @param event
     */
    @Subscribe
    public void onEvent(UpdateMallMessage event) {
        load();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }

    /**
     * 加载首页数据
     */
    public void loadHomeData() {
        Observable.just(null)
                .filter(new Func1<Object, Boolean>() {  //判断网络
                    @Override
                    public Boolean call(Object o) {
                        return NetworkUtils.isConnected(getActivity());
                    }
                })
                .observeOn(Schedulers.computation())
                .map(new Func1<Object, Response<Home>>() {   //请求首页网络数据
                    @Override
                    public Response<Home> call(Object o) {

                        try {
                            Request request = new Request();
                            return HTTP.getAPI().getHome(HTTP.formatJSONData(request)).execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                })
                .filter(new Func1<Response<Home>, Boolean>() {  //验证数据
                    @Override
                    public Boolean call(Response<Home> homeResponse) {
                        return StatusCode.validateSuccess(getActivity(), homeResponse);
                    }
                })
                .map(new Func1<Response<Home>, DataList<RecyclerViewModel>>() {  //数据转换显示

                    @Override
                    public DataList<RecyclerViewModel> call(Response<Home> homeResponse) {
                        return handlerHomeData(homeResponse);
                    }
                })
                .map(new Func1<DataList<RecyclerViewModel>, DataList<RecyclerViewModel>>() {  //序列化存储
                    @Override
                    public DataList<RecyclerViewModel> call(DataList<RecyclerViewModel> listResponse) {
                        SerializableUtils.serializableToCacheFile(getActivity(), listResponse, DATA_SERIALIZE_FILE_NAME);
                        return listResponse;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DataList<RecyclerViewModel>>() {  //数据更新
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("error", "error", e);
                    }

                    @Override
                    public void onNext(DataList<RecyclerViewModel> homeList) {
                        setHomeData(mHomeDataList, homeList);
                        getSellerListReqeust.setPageIndex(0);
                        mAdapter.notifyDataSetChanged();

                        getSellerListReqeust.setPageIndex(1);
                        loadSellerList(getSellerListReqeust);

                    }
                });
    }

    public void loadSellerList(final GetSellerListBean getSellerListRequest) {

        Request<GetSellerListBean> request = new Request<>();
        getSellerListRequest.setType("HOME");//首页的商家
        request.setData(getSellerListRequest);
        RXUtils.request(getActivity(), request, "getSellerList", new SupportSubscriber() {

            @Override
            public void onNext(Object o) {
                Response<SellerList> dataListResponse = (Response<SellerList>) o;
                handlerSellerListData(dataListResponse.getData());
                if (getSellerListRequest.getPageIndex() == 1) {
                    //如果没有商品的情况下
                    if (dataListResponse.getData().getDataList() == null || dataListResponse.getData().getDataList().size() == 0) {
                        removeSellerTitleRecyclerViewModel();
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 设置首页数据
     *
     * @param oldData
     * @param newData
     */
    public void setHomeData(DataList<RecyclerViewModel> oldData, DataList<RecyclerViewModel> newData) {
        setResponseData(oldData, newData);
        oldData.setPageSize(0);
        oldData.getDataList().clear();
        oldData.getDataList().addAll(newData.getDataList());
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

    /**
     * 将商家列表返回数据处理为RecyclerView能识别并分类的实体
     *
     * @param sellerList
     * @return
     */
    public void handlerSellerListData(SellerList sellerList) {

        List<RecyclerViewModel> recyclerViewModels = new ArrayList<>();

        setResponseData(mHomeDataList, sellerList);

        for (Seller seller : sellerList.getDataList()) {
            RecyclerViewModel sellerRecyclerViewModel = new RecyclerViewModel();
            sellerRecyclerViewModel.setItemType(HomeMallAdapter.TYPE_SELLER);
            sellerRecyclerViewModel.setData(seller);
            recyclerViewModels.add(sellerRecyclerViewModel);
        }

        mHomeDataList.getDataList().addAll(recyclerViewModels);

    }

    /**
     * 将首页返回数据处理为RecyclerView能识别并分类的实体
     *
     * @param home
     * @return
     */
    public DataList<RecyclerViewModel> handlerHomeData(Response<Home> home) {
        DataList<RecyclerViewModel> responseRecyclerViewModel = new DataList<>();

        List<RecyclerViewModel> recyclerViewModels = new ArrayList<>();

        //头部轮播
        if (home.getData().getHeadBanners() != null && home.getData().getHeadBanners().size() != 0) {
            RecyclerViewModel bannerRecyclerViewModel = new RecyclerViewModel();
            bannerRecyclerViewModel.setItemType(HomeMallAdapter.TYPE_HEAD_BANNERS);
            bannerRecyclerViewModel.setData(home.getData().getHeadBanners());
            recyclerViewModels.add(bannerRecyclerViewModel);
        }
         if(home.getData().getTaokeTitle() != null){
             RecyclerViewModel taokeTitle = new RecyclerViewModel();
             taokeTitle.setItemType(HomeMallAdapter.TYPE_TAOKE_TITLE);
             taokeTitle.setData(home.getData().getTaokeTitle());
             recyclerViewModels.add(taokeTitle);
         }

        //商品分类
        /*if (home.getData().getGoodsClassifys() != null && home.getData().getGoodsClassifys().size() != 0) {
            RecyclerViewModel goodsClassifysRecyclerViewModel = new RecyclerViewModel();
            goodsClassifysRecyclerViewModel.setItemType(HomeMallAdapter.TYPE_GOODS_CLASSIFYS);
            goodsClassifysRecyclerViewModel.setData(home.getData().getGoodsClassifys());
            recyclerViewModels.add(goodsClassifysRecyclerViewModel);
        }
*/

        //图文操作
        RecyclerViewModel optionRecyclerViewModel = new RecyclerViewModel();
        optionRecyclerViewModel.setItemType(HomeMallAdapter.TYPE_OPTION);
        optionRecyclerViewModel.setData(home.getData());
        recyclerViewModels.add(optionRecyclerViewModel);

        if(home.getData().getTaokeType() !=null && home.getData().getTaokeType().size() > 0){
            int size;
            if(home.getData().getTaokeType().size() > 4){
                size = 4;
            }else{
                size = home.getData().getTaokeType().size();
            }
            for(int i = 0; i < size; i++ ){
                RecyclerViewModel taokeType  = new RecyclerViewModel();
                taokeType.setItemType(HomeMallAdapter.TYPE_TAOKE_TYPE);
                taokeType.setData(home.getData().getTaokeType().get(i));
                recyclerViewModels.add(taokeType);
            }
        }

        //精选商家
//        if (home.getData().getConcentrationSellers() != null && home.getData().getConcentrationSellers().size() != 0) {
//            RecyclerViewModel concentrationSellersRecyclerViewModel = new RecyclerViewModel();
//            concentrationSellersRecyclerViewModel.setItemType(HomeMallAdapter.TYPE_CONCENTRATION_SELLERS);
//            concentrationSellersRecyclerViewModel.setData(home.getData().getConcentrationSellers());
//            recyclerViewModels.add(concentrationSellersRecyclerViewModel);
//        }


   /*     //爱划算
        if (home.getData().getCostEfficients() != null && home.getData().getCostEfficients().size() != 0) {
            RecyclerViewModel costEfficientsRecyclerViewModel = new RecyclerViewModel();
            costEfficientsRecyclerViewModel.setItemType(HomeMallAdapter.TYPE_OPTION_BANNER);
            costEfficientsRecyclerViewModel.setData(home.getData().getCostEfficients());
            recyclerViewModels.add(costEfficientsRecyclerViewModel);
        }


        //特卖汇
        if (home.getData().getSaleExchanges() != null && home.getData().getSaleExchanges().size() != 0) {
            RecyclerViewModel saleExchangeRecyclerViewModel = new RecyclerViewModel();
            saleExchangeRecyclerViewModel.setItemType(HomeMallAdapter.TYPE_OPTION_BANNER);
            saleExchangeRecyclerViewModel.setData(home.getData().getSaleExchanges());
            recyclerViewModels.add(saleExchangeRecyclerViewModel);
        }*/

        //秒杀
        if (home.getData().getSecondKills() != null && home.getData().getSecondKills().size() != 0) {
            RecyclerViewModel secondKillRecyclerViewModel = new RecyclerViewModel();
            secondKillRecyclerViewModel.setItemType(HomeMallAdapter.TYPE_SECOND_KILL);
            secondKillRecyclerViewModel.setData(home.getData().getSecondKills());
            recyclerViewModels.add(secondKillRecyclerViewModel);
        }
        if(home.getData().getLive() != null && home.getData().getLive().size() != 0) {
            //直播标题
            RecyclerViewModel liveTitleRecyclerViewModel = new RecyclerViewModel();
            liveTitleRecyclerViewModel.setItemType(HomeMallAdapter.TYPE_LIVE_TITLE);
            recyclerViewModels.add(liveTitleRecyclerViewModel);
        }

        //直播列表
        if (home.getData().getLive() != null && home.getData().getLive().size() != 0) {
            size = home.getData().getLive().size();
            for (Live live : home.getData().getLive()) {
                RecyclerViewModel liveRecyclerViewModel = new RecyclerViewModel();
                liveRecyclerViewModel.setItemType(HomeMallAdapter.TYPE_LIVE_LIST);
                liveRecyclerViewModel.setData(live);
                recyclerViewModels.add(liveRecyclerViewModel);
            }
        }

        //积分标签分类
        if (home.getData().getScoreGoodsClassifys() != null && home.getData().getScoreGoodsClassifys().size() != 0) {
            RecyclerViewModel scoreGoodsClassifysRecyclerViewModel = new RecyclerViewModel();
            scoreGoodsClassifysRecyclerViewModel.setItemType(HomeMallAdapter.TYPE_SCORE_GOODS_CLASSIFYS);
            scoreGoodsClassifysRecyclerViewModel.setData(home.getData().getScoreGoodsClassifys());
            recyclerViewModels.add(scoreGoodsClassifysRecyclerViewModel);
        }


        //周边店铺标题
        RecyclerViewModel sellerTitleRecyclerViewModel = new RecyclerViewModel();
        sellerTitleRecyclerViewModel.setItemType(HomeMallAdapter.TYPE_SELLER_TITLE);
        recyclerViewModels.add(sellerTitleRecyclerViewModel);

        responseRecyclerViewModel.setDataList(recyclerViewModels);
        return responseRecyclerViewModel;
    }

    public void removeSellerTitleRecyclerViewModel() {
        for (int i = 0; i < mAdapter.getData().getDataList().size(); i++) {
            if (mAdapter.getData().getDataList().get(i).getItemType() == HomeMallAdapter.TYPE_SELLER_TITLE) {
                mAdapter.getData().getDataList().remove(i);
                return;
            }
        }
    }

    public void removeLiveTitleRecyclerViewModel(){
        for (int i = 0; i < mAdapter.getData().getDataList().size(); i++) {
            if (mAdapter.getData().getDataList().get(i).getItemType() == HomeMallAdapter.TYPE_LIVE_TITLE) {
                mAdapter.getData().getDataList().remove(i);
                return;
            }
        }
    }

}