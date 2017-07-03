package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.fragments.sellerinfo.SellerOrderListFragment;
import com.weslide.lovesmallscreen.models.Order;
import com.weslide.lovesmallscreen.models.OrderItem;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.order.GoodsItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/14.
 * 商家订单适配器
 */
public class SellerOrderListAdapter extends SuperRecyclerViewAdapter<Order, SellerOrderListAdapterViewHolder> {

    SellerOrderListFragment mOrderListFragment;
    String mStatus;

    public SellerOrderListAdapter(Context context, SellerOrderListFragment fragment, DataList<Order> dataList, String status) {
        super(context, dataList);
        mOrderListFragment = fragment;
        mStatus =status;
    }

    @Override
    public SellerOrderListAdapterViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        return new SellerOrderListAdapterViewHolder(mContext, mOrderListFragment, parent, mStatus);
    }

    @Override
    public void onSuperBindViewHolder(SellerOrderListAdapterViewHolder holder, int position) {
        holder.bindView(mList.get(position));
    }
}

class SellerOrderListAdapterViewHolder extends RecyclerView.ViewHolder {

    Context mContext;
    SellerOrderListFragment mOrderListFragment;
    Order mOrder;
    String mStatus;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_order_order_number)
    TextView tvOrderOrderNumber;
    @BindView(R.id.layout_order_goods)
    LinearLayout layoutOrderGoods;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_reality_money)
    TextView tvRealityMoney;
    @BindView(R.id.layout_wait_confirm)
    LinearLayout layoutWaitConfirm;
    @BindView(R.id.layout_receipt_of_send_out_goods)
    LinearLayout layoutReceiptOfSendOutGoods;
    @BindView(R.id.layout_wait_exchange)
    LinearLayout layoutWaitExchange;
    @BindView(R.id.layout_wait_of_goods)
    LinearLayout layoutWaitOfGoods;
    @BindView(R.id.tv_user_comment_content)
    TextView tvUserCommentContent;
    @BindView(R.id.layout_comment)
    LinearLayout layoutComment;
    @BindView(R.id.layout_order_option)
    LinearLayout layoutOrderOption;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;


    public SellerOrderListAdapterViewHolder(Context context, SellerOrderListFragment fragment, ViewGroup parent, String status) {
        super(LayoutInflater.from(context).inflate(R.layout.seller_order_view_order_list_item, parent, false));

        mContext = context;
        mOrderListFragment = fragment;
        mStatus = status;
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Order order) {
        mOrder = order;

        tvOrderDate.setText(order.getOrderDate());
        tvOrderOrderNumber.setText("订单编号：" + order.getOrderNumber());
        tvOrderStatus.setText(order.getStatus().getName());
        tvRealityMoney.setText( order.getRealityMoney());
        tvUserName.setText(order.getUser().getName());

        layoutOrderGoods.removeAllViews();
        if (mOrder.getOrderItems() != null) {
            for (OrderItem orderItem : mOrder.getOrderItems()) {
                GoodsItemView itemView = new GoodsItemView(mContext);
                itemView.bindView(orderItem);
                layoutOrderGoods.addView(itemView);
            }
        }


        switch (order.getStatus().getStatusId()) {
            case Constants.ORDER_STATUS_COMMENT: //已评价
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutComment.setVisibility(View.VISIBLE);
                if (order.getOrderItems() != null && order.getOrderItems().size() != 0) {
                    tvUserCommentContent.setText(order.getOrderItems().get(0).getUserComment().getCommentContent());
                }

                break;
            case Constants.ORDER_STATUS_END: //已完成
                break;
            case Constants.ORDER_STATUS_WAIT_CONFIRM: //待确认
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutWaitConfirm.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS: //待发货
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutReceiptOfSendOutGoods.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_STATUS_WAIT_EXCHANGE: //待兑换
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutWaitExchange.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_STATUS_WAIT_OF_GOODS: //待收货
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutWaitOfGoods.setVisibility(View.VISIBLE);
                break;
        }

    }

    @OnClick({R.id.btn_confirm, R.id.btn_contact_user_wait_confirm, R.id.btn_send_out_goods, R.id.btn_contact_user_send_out_goods,
            R.id.btn_exchange, R.id.btn_contact_user_wait_exchange, R.id.btn_logistics, R.id.btn_contact_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:

                SellerOrderListFragment.sellerConfirmOrder(mContext, mOrder, mStatus);

                break;
            case R.id.btn_contact_user_wait_confirm:
            case R.id.btn_contact_user:
            case R.id.btn_contact_user_send_out_goods:
            case R.id.btn_contact_user_wait_exchange:
                SellerOrderListFragment.contactUser(mContext, mOrder);
                break;
            case R.id.btn_send_out_goods:
                SellerOrderListFragment.sendOutGoods(mContext, mOrder);
                break;

            case R.id.btn_exchange:
                SellerOrderListFragment.exchange(mContext, mOrder);
                break;

            case R.id.btn_logistics:
                SellerOrderListFragment.toLogistics(mContext, mOrder);

                break;

        }
    }
}
