package com.malinskiy.superrecyclerview.core;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.viewholder.DifferentSituationViewHolder;

/**
 * Created by xu on 2016/7/4.
 * 对RecyclerView不同情况的适配器
 */
public interface RecyclerDifferentSituationAdapter {

    /**
     * 获取没有网络是显示的ViewHolder
     * @return
     */
    DifferentSituationViewHolder getNoNetwork(Context context, ViewGroup parent, Bundle data);

    /**
     * 获取空数据显示的ViewHolder
     * @return
     */
    DifferentSituationViewHolder getEmpty(Context context, ViewGroup parent, Bundle data,int type);

    /**
     * 获取数据错误显示的ViewHolder
     * @return
     */
    DifferentSituationViewHolder getError(Context context, ViewGroup parent, Bundle data);

    /**
     * 获取正在加载显示的ViewHolder
     * @return
     */
    DifferentSituationViewHolder getLoading(Context context, ViewGroup parent, Bundle data);

}
