package com.weslide.lovesmallscreen.fragments.mall;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.FreeMall;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.ClassifiGoodsAdapter;
import com.weslide.lovesmallscreen.views.adapters.FreeGoodsAdapter;
import com.weslide.lovesmallscreen.views.adapters.Preferential99Adapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/13.
 * 免单商品列表界面
 */
public class FreeGoodsFragment extends BaseFragment {

    View mView;
    @BindView(R.id.list)
    SuperRecyclerView list;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    FreeGoodsAdapter mAdapter;
    DataList<RecyclerViewModel> mDataList = new DataList<>();

    GetGoodsListBean mGoodsListReqeust = new GetGoodsListBean();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_free_goods, container, false);

        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(v -> getActivity().finish());

        //设置请求为数据为会员免单商城
        mGoodsListReqeust.setMallTyle(Constants.MALL_FREE_SINGLE);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果是商品占用一格，如果不是占用一行
                return mAdapter.isGoods(position) ? 1 : layoutManager.getSpanCount();
            }
        });
        list.setLayoutManager(layoutManager);

        //添加分割
        list.addItemDecoration(new RecyclerView.ItemDecoration() {

            int goodsIndex = 0;

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                int position = parent.getChildLayoutPosition(view);
                int itemType = mAdapter.getItemViewType(position);

                if (itemType == FreeGoodsAdapter.TYPE_GOODS_ITEM) {
                    int space = DensityUtils.dp2px(getActivity(), 4);

                    outRect.top = space;
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

        mAdapter = new FreeGoodsAdapter(getActivity(), mDataList);


        list.setAdapter(mAdapter);
        list.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> loadGoodsData(), 2);
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

    /**
     * 加载数据
     */
    public void load(){
        mDataList.getDataList().clear();
        loadFreeMall();
    }

    /**
     * 加载免单商城的数据
     */
    public void loadFreeMall() {
        Request request = new Request();
        RXUtils.request(getActivity(), request, "freeMall", new RecyclerViewSubscriber<Response<FreeMall>>(mAdapter, mDataList){

            @Override
            public void onSuccess(Response<FreeMall> freeMallResponse) {
                handlerFreeMall(freeMallResponse);
                mAdapter.notifyDataSetChanged();
                loadGoodsData();
            }
        });
    }

    /**
     * 加载更多数据
     */
    public void loadGoodsData() {
        Request<GetGoodsListBean> request = new Request<>();
        mGoodsListReqeust.setPageIndex(mGoodsListReqeust.getPageIndex() + 1);
        mGoodsListReqeust.setMallTyle(Constants.MALL_FREE_SINGLE);
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
    public void handlerFreeMall(Response<FreeMall> response) {
        RecyclerViewModel recyclerViewModel = new RecyclerViewModel();
        recyclerViewModel.setData(response.getData());
        recyclerViewModel.setItemType(FreeGoodsAdapter.TYPE_BECOM_VIP_BANNERS);

        mDataList.getDataList().add(recyclerViewModel);

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
            recyclerViewModel.setItemType(FreeGoodsAdapter.TYPE_GOODS_ITEM);

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
}
