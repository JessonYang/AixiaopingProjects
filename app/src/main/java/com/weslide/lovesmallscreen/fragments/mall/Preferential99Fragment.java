package com.weslide.lovesmallscreen.fragments.mall;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.GoodsSearchActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.Preferential99;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.Preferential99Adapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/19.
 * 99特惠fragment
 */
public class Preferential99Fragment extends BaseFragment {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.list)
    SuperRecyclerView list;
    Preferential99Adapter mAdapter;
    DataList<RecyclerViewModel> mDataList = new DataList<>();
    GetGoodsListBean mGoodsListReqeust = new GetGoodsListBean();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_preferential_99, container, false);
        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果是商品占用一格，如果不是占用一行
                return mAdapter.isGoods(position) ? 1 : layoutManager.getSpanCount();
            }
        });
        list.setLayoutManager(layoutManager);
        mAdapter = new Preferential99Adapter(getActivity(), mDataList);

        list.setAdapter(mAdapter);

        //添加分割
        list.addItemDecoration(new RecyclerView.ItemDecoration() {

            int goodsIndex = 0;

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                int position = parent.getChildLayoutPosition(view);
                int itemType = mAdapter.getItemViewType(position);

                if (itemType == Preferential99Adapter.VIEW_TYPE_FILTER_TYPE) {
                    int space = DensityUtils.dp2px(getActivity(), 4);
                    outRect.top = space;
                    outRect.bottom = space;
                } else if (itemType == Preferential99Adapter.VIEW_TYPE_GOODS_ITEM) {
                    int space = DensityUtils.dp2px(getActivity(), 4);

                    outRect.bottom = space;
                    //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
                    if (goodsIndex % 2 == 0) {
                        outRect.left = space;
                        outRect.right = space / 2;
                    } else {
                        outRect.right = space;
                        outRect.left = space / 2;
                    }

                    goodsIndex++;
                }


            }
        });
        list.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> loadGoodsList(), 2);
        //数据请求失败后的数据重新载入
        list.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });

        load();

        return mView;
    }


    private void load() {
        mDataList.getDataList().clear();
        loadPreferential99();
    }

    private void loadPreferential99() {
        Request request = new Request();
        RXUtils.request(getActivity(), request, "preferential99", new RecyclerViewSubscriber<Response<Preferential99>>(mAdapter, mDataList) {

            @Override
            public void onSuccess(Response<Preferential99> preferential99Response) {
                handlerPreferential99(preferential99Response);
                mAdapter.notifyDataSetChanged();
                loadGoodsList();
            }
        });
    }

    private void loadGoodsList() {
        Request<GetGoodsListBean> request = new Request<>();
        mGoodsListReqeust.setPageIndex(mGoodsListReqeust.getPageIndex() + 1);
        mGoodsListReqeust.setMallTyle(Constants.MALL_PREFERENTIAL_99);
        request.setData(mGoodsListReqeust);

        RXUtils.request(getActivity(), request, "getGoodsList", new RecyclerViewSubscriber<Response<GoodsList>>(mAdapter, mDataList) {

            @Override
            public void onSuccess(Response<GoodsList> goodsListResponse) {
                handlerGoodsItem(goodsListResponse);
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    /**
     * 处理免单商城数据
     *
     * @param response
     * @return
     */
    public void handlerPreferential99(Response<Preferential99> response) {


        RecyclerViewModel VIEW_TYPE_BANNER = new RecyclerViewModel();
        VIEW_TYPE_BANNER.setData(response.getData());
        VIEW_TYPE_BANNER.setItemType(Preferential99Adapter.VIEW_TYPE_BANNER);

        RecyclerViewModel VIEW_TYPE_FREE_GOODS = new RecyclerViewModel();
        VIEW_TYPE_FREE_GOODS.setData(response.getData());
        VIEW_TYPE_FREE_GOODS.setItemType(Preferential99Adapter.VIEW_TYPE_FREE_GOODS);

        RecyclerViewModel VIEW_TYPE_BECOME_VIP = new RecyclerViewModel();
        VIEW_TYPE_BECOME_VIP.setData(response.getData());
        VIEW_TYPE_BECOME_VIP.setItemType(Preferential99Adapter.VIEW_TYPE_BECOME_VIP);

//        RecyclerViewModel VIEW_TYPE_FILTER_TYPE = new RecyclerViewModel();
//        VIEW_TYPE_FILTER_TYPE.setData(response.getData());
//        VIEW_TYPE_FILTER_TYPE.setItemType(Preferential99Adapter.VIEW_TYPE_FILTER_TYPE);

        mDataList.getDataList().add(VIEW_TYPE_BANNER);
        mDataList.getDataList().add(VIEW_TYPE_FREE_GOODS);
        mDataList.getDataList().add(VIEW_TYPE_BECOME_VIP);
//        mDataList.getDataList().add(VIEW_TYPE_FILTER_TYPE);

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
            recyclerViewModel.setItemType(Preferential99Adapter.VIEW_TYPE_GOODS_ITEM);

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

    @OnClick(R.id.btn_search)
    public void onClick() {

        Bundle bundle = new Bundle();
        bundle.putString(GoodsSearchActivity.KEY_MALL_TYPE, Constants.MALL_PREFERENTIAL_99);
        AppUtils.toActivity(getActivity(), GoodsSearchActivity.class, bundle);

    }
}
