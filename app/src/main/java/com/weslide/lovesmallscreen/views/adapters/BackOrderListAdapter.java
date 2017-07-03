package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.order.BackOrderDetailActivity;
import com.weslide.lovesmallscreen.models.BackOrder;
import com.weslide.lovesmallscreen.models.BackOrderList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.views.adapters.viewholder.BaseViewHolder;
import com.weslide.lovesmallscreen.views.order.GoodsItemView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/15.
 * 退单列表适配器
 */
public class BackOrderListAdapter extends SuperRecyclerViewAdapter<BackOrder, BackOrderItemViewHolder> {
    public BackOrderListAdapter(Context context, BackOrderList dataList) {
        super(context, dataList);
    }

    @Override
    public BackOrderItemViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        return new BackOrderItemViewHolder(mContext, parent);
    }

    @Override
    public void onSuperBindViewHolder(BackOrderItemViewHolder holder, int position) {
        holder.bindView(mList.get(position));
    }
}

class BackOrderItemViewHolder extends BaseViewHolder<BackOrder> {

    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.back_order_num)
    TextView tvOrderNum;
    @BindView(R.id.layout_order_goods)
    LinearLayout layoutOrderGoods;
    @BindView(R.id.tv_reality_money)
    TextView tvRealityMoney;

    public BackOrderItemViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.back_order_list_item);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(BackOrder order) {
        tvSellerName.setText(order.getSeller().getSellerName());
        tvOrderStatus.setText(order.getBackOrderStatus().getName());
        tvOrderNum.setText("订单编号:" + order.getOrderNumber());
        tvRealityMoney.setText("￥" + order.getRealityMoney());

        layoutOrderGoods.removeAllViews();
        if (order.getBackOrderItem() != null) {
            GoodsItemView itemView = new GoodsItemView(getContext());
            itemView.bindView(order.getBackOrderItem());
            layoutOrderGoods.addView(itemView);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(BackOrderDetailActivity.KEY_BACK_ORDER_ITEM_ID, order.getBackOrderItemId());
                AppUtils.toActivity(getContext(), BackOrderDetailActivity.class, bundle);
            }
        });

    }

}
