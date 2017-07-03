package com.weslide.lovesmallscreen.fragments.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.Home;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateMallMessage;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.views.adapters.SecondMallAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/10/18.
 */
public class SecondMallFragment extends BaseFragment {

    View mView;
    @BindView(R.id.list)
    SuperRecyclerView list;
    SecondMallAdapter adapter;
    DataList<RecyclerViewModel> mHomeDataList;
    GetGoodsListBean request = new GetGoodsListBean();
    @BindView(R.id.fl_no_type)
    RelativeLayout flNoType;
    String typeId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_second_mall, container, false);

        ButterKnife.bind(this, mView);
        EventBus.getDefault().register(this);
        initView();
        return mView;
    }

    public void initView() {
        mHomeDataList = new DataList<>();
        mHomeDataList.setDataList(new ArrayList<>());
        adapter = new SecondMallAdapter(getActivity(), mHomeDataList, SecondMallFragment.this);
        getHomeData();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (adapter.getItemViewType(position) == 3) {
                    return 1;
                }
                return layoutManager.getSpanCount();
            }
        });
        list.setLayoutManager(layoutManager);

        list.setAdapter(adapter);

        list.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getGoodList(request), 3);

        list.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHomeData();
            }
        });

    }

    private void getHomeData() {
        RXUtils.request(getActivity(), new Request(), "getHome", new SupportSubscriber<Response<Home>>() {

            @Override
            public void onNext(Response<Home> homeResponse) {

                if (homeResponse.getData().getGoodsClassifys() != null && homeResponse.getData().getGoodsClassifys().size() > 0) {
                    list.setVisibility(View.VISIBLE);
                    flNoType.setVisibility(View.GONE);
                    setData(homeResponse.getData().getGoodsClassifys());
                    request.setTypeId(homeResponse.getData().getGoodsClassifys().get(0).getTypeId());
                    typeId = homeResponse.getData().getGoodsClassifys().get(0).getTypeId();
                    request.setMallTyle(Constants.MALL_STAND_ALONE);
                    regetGoodsList(request);
                    adapter.notifyDataSetChanged();
                } else {
                    mHomeDataList.getDataList().clear();
                    list.setVisibility(View.GONE);
                    flNoType.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void setData(List<ImageText> data) {
        mHomeDataList.getDataList().clear();
        RecyclerViewModel recyclerViewModel = new RecyclerViewModel();
        recyclerViewModel.setItemType(SecondMallAdapter.TYPE_GOODS_CLASSIFYS);
        recyclerViewModel.setData(data);
        mHomeDataList.getDataList().add(recyclerViewModel);
    }

    public void regetGoodsList(GetGoodsListBean mGoodsListReqeust) {
        Request<GetGoodsListBean> requests = new Request<>();
        mGoodsListReqeust.setPageIndex(1);
        if(StringUtils.isBlank(mGoodsListReqeust.getTypeId())){
            mGoodsListReqeust.setTypeId(typeId);
        }
        requests.setData(mGoodsListReqeust);
        request.setTypeId(mGoodsListReqeust.getTypeId());
        request.setPageIndex(mGoodsListReqeust.getPageIndex());
        request.setValue(mGoodsListReqeust.getValue());
        request.setSalesVolume(mGoodsListReqeust.getSalesVolume());
        RXUtils.request(getActivity(), requests, "getGoodsList", new SupportSubscriber<Response<GoodsList>>() {

            @Override
            public void onNext(Response<GoodsList> goodsListResponse) {
                //  handlerGoodsType(goodsListResponse.getData());

                clearOlderGoods();
                clearOlderType();

                handlerGoodsType(goodsListResponse.getData());
                handlerGooderListData(goodsListResponse.getData());

                adapter.notifyDataSetChanged();
            }
        });
    }

    public void getGoodList(GetGoodsListBean mGoodsListReqeust) {
        Request<GetGoodsListBean> request = new Request<>();
        mGoodsListReqeust.setPageIndex(mGoodsListReqeust.getPageIndex() + 1);
        request.setData(mGoodsListReqeust);

        RXUtils.request(getActivity(), request, "getGoodsList", new SupportSubscriber<Response<GoodsList>>() {

            @Override
            public void onNext(Response<GoodsList> goodsListResponse) {
                //  handlerGoodsType(goodsListResponse.getData());

                handlerGooderListNewData(goodsListResponse.getData());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void handlerGoodsType(GoodsList goodList) {
        //clearOlderType();
        RecyclerViewModel sellerRecyclerViewModel = new RecyclerViewModel();
        sellerRecyclerViewModel.setItemType(SecondMallAdapter.TYPE_GOODS_TYPE);
        sellerRecyclerViewModel.setData(goodList.getTypes());
        mHomeDataList.getDataList().add(sellerRecyclerViewModel);

    }

    private void clearOlderType() {
        for (int i = 0; i < mHomeDataList.getDataList().size(); i++) {
            if (mHomeDataList.getDataList().get(i).getItemType() == SecondMallAdapter.TYPE_GOODS_TYPE) {
                mHomeDataList.getDataList().remove(i);
                i--;
            }
        }
    }

    public void handlerGooderListData(GoodsList goodList) {

        setResponseData(mHomeDataList, goodList);
        for (Goods goods : goodList.getDataList()) {
            RecyclerViewModel sellerRecyclerViewModel = new RecyclerViewModel();
            sellerRecyclerViewModel.setItemType(SecondMallAdapter.TYPE_GOODS_LIST);
            sellerRecyclerViewModel.setData(goods);
            mHomeDataList.getDataList().add(sellerRecyclerViewModel);
        }
    }

    public void handlerGooderListNewData(GoodsList goodList) {

        for (Goods goods : goodList.getDataList()) {
            RecyclerViewModel sellerRecyclerViewModel = new RecyclerViewModel();
            sellerRecyclerViewModel.setItemType(SecondMallAdapter.TYPE_GOODS_LIST);
            sellerRecyclerViewModel.setData(goods);
            mHomeDataList.getDataList().add(sellerRecyclerViewModel);
        }
    }

    private void clearOlderGoods() {
        for (int i = 0; i < mHomeDataList.getDataList().size(); i++) {
            if (mHomeDataList.getDataList().get(i).getItemType() == SecondMallAdapter.TYPE_GOODS_LIST) {
                mHomeDataList.getDataList().remove(i);
                i--;
            }
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

    /**
     * eventBus事件
     *
     * @param event
     */
    @Subscribe
    public void onEvent(UpdateMallMessage event) {

        L.e("切换城市，改变分类数据");

        getHomeData();
    }

    public void onShow(List<ImageText> data,ImageView selct,int postion){
        for(int i = 0 ;i < data.size();i++){
            data.get(i).setSelect(false);
            selct.setVisibility(View.GONE);
            if(i == postion){
                data.get(postion).setSelect(true);
            }
        }
        if (data.get(postion).isSelect() == true){
            selct.setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
