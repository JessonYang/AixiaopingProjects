package com.weslide.lovesmallscreen.core;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xu on 2016/5/11.
 * 一个基础的Recycler适配器
 */
public abstract class BaseRecyclerAdapter<T, L extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<L> {
    protected List<T> mInfoList = null;
    protected Activity mActivity;

    public BaseRecyclerAdapter(Activity activity, List<T> infoList) {
        mActivity = activity;
        mInfoList = infoList;
    }


    @Override
    public L onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(LayoutInflater.from(mActivity), parent, viewType);
    }

    protected abstract L onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    protected abstract void onBindData(L holder, T data);

    public abstract View getEmptyView();

    public abstract View getNoNetworkView();

    public abstract View getLoadingView();

    public abstract View getLoadSuccessView();




    @Override
    public void onBindViewHolder(L holder, int position) {
        T data = mInfoList.get(position);
        onBindData(holder, data);
    }


    @Override
    public int getItemCount() {
        return mInfoList.size();
    }
}
