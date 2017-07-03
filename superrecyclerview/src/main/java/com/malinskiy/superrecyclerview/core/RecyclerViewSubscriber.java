package com.malinskiy.superrecyclerview.core;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.L;

/**
 * Created by xu on 2016/7/4.
 * RecyclerView使用的Subscriber
 */
public abstract class RecyclerViewSubscriber<T extends Response<?>> extends SupportSubscriber<T> {

    SuperRecyclerViewAdapter mAdapter;
    DataList mList;

    public RecyclerViewSubscriber(SuperRecyclerViewAdapter adapter, DataList list){
        mAdapter = adapter;
        mList = list;
    }


    @Override
    public void onError(Throwable e) {
        super.onError(e);
        mList.setRecyclerViewStatus(SuperRecyclerView.RECYCLER_VIEW_STATUS_ERROR);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoNetwork() {
        mList.setRecyclerViewStatus(SuperRecyclerView.RECYCLER_VIEW_STATUS_NO_NETWORK);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseError(Response response) {
        mList.setRecyclerViewStatus(SuperRecyclerView.RECYCLER_VIEW_STATUS_ERROR);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        //如果是第一页的情况下清空，考虑到布局类型分布很多种， 不能将所有清空，但是固定数据开始加载时pageIndex是0,
        //可以根据这个来判断             还有一种更好的办法，清空指定类型的项，没遇到什么问题暂时先这样用着
        if (mList.getPageIndex() == 0) {
            mList.setRecyclerViewStatus(SuperRecyclerView.RECYCLER_VIEW_STATUS_LOADING);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        L.i("rx运行结束");
    }

    @Override
    public void onNext(T t) {
        mList.setRecyclerViewStatus(SuperRecyclerView.RECYCLER_VIEW_STATUS_SUCCESS);
        onSuccess(t);
    }

    public abstract void onSuccess(T t);
}
