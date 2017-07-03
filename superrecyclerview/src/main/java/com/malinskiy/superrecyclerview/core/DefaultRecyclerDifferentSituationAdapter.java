package com.malinskiy.superrecyclerview.core;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.viewholder.DifferentSituationViewHolder;
import com.malinskiy.superrecyclerview.viewholder.EmptyViewHolder;
import com.malinskiy.superrecyclerview.viewholder.ErrorViewHolder;
import com.malinskiy.superrecyclerview.viewholder.LoadingViewHolder;
import com.malinskiy.superrecyclerview.viewholder.NoNetworkViewHolder;

/**
 * Created by xu on 2016/7/4.
 * 默认的情况适配器
 */
public class DefaultRecyclerDifferentSituationAdapter implements RecyclerDifferentSituationAdapter {

    @Override
    public DifferentSituationViewHolder getNoNetwork(Context context, ViewGroup parent, Bundle data) {
        return new NoNetworkViewHolder(context, parent);
    }

    @Override
    public DifferentSituationViewHolder getEmpty(Context context, ViewGroup parent, Bundle data,int type) {
        return new EmptyViewHolder(context, parent,type);
    }

    @Override
    public DifferentSituationViewHolder getError(Context context, ViewGroup parent, Bundle data) {
        return new ErrorViewHolder(context, parent);
    }

    @Override
    public DifferentSituationViewHolder getLoading(Context context, ViewGroup parent, Bundle data) {
        return new LoadingViewHolder(context, parent);
    }
}
