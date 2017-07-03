package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.views.adapters.viewholder.ListSellerViewHolder;

/**
 * Created by xu on 2016/7/23.
 * 商家列表适配器
 */
public class SellerListAdapter extends SuperRecyclerViewAdapter<Seller, ListSellerViewHolder> {
    public SellerListAdapter(Context context, DataList<Seller> dataList) {
        super(context, dataList);
    }

    @Override
    public ListSellerViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListSellerViewHolder(mContext, parent);
    }

    @Override
    public void onSuperBindViewHolder(ListSellerViewHolder holder, int position) {
        holder.bindView(mList.get(position));
    }
}
