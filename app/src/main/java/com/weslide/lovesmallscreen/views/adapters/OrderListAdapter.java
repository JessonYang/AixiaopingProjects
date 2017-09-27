package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.order.OrderDetailActivity;
import com.weslide.lovesmallscreen.models.Order;
import com.weslide.lovesmallscreen.models.OrderItem;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.views.order.GoodsItemView;
import com.weslide.lovesmallscreen.views.order.OrderListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/1.
 * 订单列表适配器
 */
public class OrderListAdapter extends SuperRecyclerViewAdapter<Order, OrderListAdapterViewHolder> {

    String mStatus;

    public OrderListAdapter(Context context, DataList<Order> dataList, String status) {
        super(context, dataList);
        mStatus = status;
    }

    @Override
    public OrderListAdapterViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderListAdapterViewHolder(mContext, parent, mStatus);
    }

    @Override
    public void onSuperBindViewHolder(OrderListAdapterViewHolder holder, int position) {
        holder.bindView(mList.get(position));
    }
}

class OrderListAdapterViewHolder extends RecyclerView.ViewHolder {

    Context mContext;
    Order mOrder;
    String mStatus;

    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.layout_order_goods)
    LinearLayout layoutOrderGoods;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_reality_money)
    TextView tvRealityMoney;
    @BindView(R.id.layout_wait_of_goods)
    LinearLayout layoutWaitOfGoods;
    @BindView(R.id.layout_receipt_of_comment)
    LinearLayout layoutReceiptOfComment;
    @BindView(R.id.layout_receipt_of_pay)
    LinearLayout layoutReceiptOfPay;
    @BindView(R.id.layout_receipt_of_send_out_goods)
    LinearLayout layoutReceiptOfSendOutGoods;
    @BindView(R.id.layout_order_option)
    LinearLayout layoutOrderOption;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.layout_receipt_exchange)
    LinearLayout layoutReceiptExchange;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_back_send_out_goods)
    Button btnBackSendOutGoods;
    @BindView(R.id.btn_back_exchange)
    Button btnBackExchange;

    public OrderListAdapterViewHolder(Context context, ViewGroup parent, String status) {
        super(LayoutInflater.from(context).inflate(R.layout.order_view_order_list_item, parent, false));

        mContext = context;
        mStatus = status;

        ButterKnife.bind(this, itemView);
    }

    public void bindView(Order order) {

        mOrder = order;

        tvSellerName.setText(mOrder.getSeller().getSellerName());
        tvOrderDate.setText("订单时间：" + mOrder.getOrderDate());
        tvOrderStatus.setText(mOrder.getStatus().getName());
        tvRealityMoney.setText("￥" + mOrder.getRealityMoney());

        layoutOrderGoods.removeAllViews();

        boolean canSelect = false;

        if (mOrder.getOrderItems() != null) {
            for (OrderItem orderItem : mOrder.getOrderItems()) {

                if(orderItem.getGoods().getMallType().equals(Constants.MALL_TYPE_FREE_SINGLE)){
                    //免单商城不允许退单
                    orderItem.setBack(false);
                }

                GoodsItemView itemView = new GoodsItemView(mContext);
                itemView.bindView(orderItem);
                if (mStatus.equals(Constants.ORDER_STATUS_WAIT_OF_GOODS) ||
                        mStatus.equals(Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS) ||
                        mStatus.equals(Constants.ORDER_STATUS_WAIT_EXCHANGE)) {

                    if (orderItem.isBack()) {
                        //显示多选框
                        itemView.showCheckBox();
                        canSelect = true;

                    }

                } else if (mStatus.equals(Constants.ORDER_STATUS_WAIT_COMMENT)) {
                    //显示多选框
                    itemView.showCheckBox();
                }

                layoutOrderGoods.addView(itemView);
            }
        }

        if(!canSelect){  //不能退单的情况下将退单按钮隐藏
            btnBack.setVisibility(View.GONE);
            btnBackExchange.setVisibility(View.GONE);
            btnBackSendOutGoods.setVisibility(View.GONE);
        }


        switch (mStatus) {
            case Constants.ORDER_STATUS_WAIT_PAY: //待支付
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutReceiptOfPay.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS: //待发货
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutReceiptOfSendOutGoods.setVisibility(View.VISIBLE);
                if (Integer.parseInt(mOrder.getOrderItems().get(0).getScore()) > 0) {
                    btnBackSendOutGoods.setVisibility(View.GONE);
                }
                break;
            case Constants.ORDER_STATUS_WAIT_EXCHANGE: //待兑换
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutReceiptExchange.setVisibility(View.VISIBLE);
                if (Integer.parseInt(mOrder.getOrderItems().get(0).getScore()) > 0) {
                    btnBackExchange.setVisibility(View.GONE);
                }
                break;
            case Constants.ORDER_STATUS_WAIT_OF_GOODS: //待收货
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutWaitOfGoods.setVisibility(View.VISIBLE);
                if (Integer.parseInt(mOrder.getOrderItems().get(0).getScore()) > 0) {
                    btnBack.setVisibility(View.GONE);
                }
                break;
            case Constants.ORDER_STATUS_WAIT_COMMENT: //待评价
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutReceiptOfComment.setVisibility(View.VISIBLE);
                break;
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(OrderDetailActivity.KEY_ORDER_ID, mOrder.getOrderId());
                bundle.putString(OrderDetailActivity.KEY_STATUS, mStatus);
                AppUtils.toActivity(mContext, OrderDetailActivity.class, bundle);
            }
        });

    }

    @OnClick({R.id.btn_logistics, R.id.btn_back, R.id.btn_confirm_receipt, R.id.btn_comment,
            R.id.btn_contact_seller, R.id.btn_cancel_order, R.id.btn_to_pay, R.id.btn_back_send_out_goods,
            R.id.btn_output_qr, R.id.btn_contact_seller_send_out_goods, R.id.btn_back_exchange, R.id.btn_contact_seller_exchange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logistics://查看物流
                OrderListView.toLogistics(mContext, mOrder);
                break;
            case R.id.btn_back_send_out_goods://退单
            case R.id.btn_back:
            case R.id.btn_back_exchange:
                OrderListView.back(mContext, OrderListView.getSelectOrderItem(mOrder), mStatus);
                break;
            case R.id.btn_confirm_receipt://确定收货
                OrderListView.confirmReceipt(mContext, mOrder, mStatus);
                break;
            case R.id.btn_comment://评论晒单
                ArrayList<OrderItem> orderItems = OrderListView.getSelectOrderItem(mOrder);
                ArrayList<Order> orders = new ArrayList<>();

                for (OrderItem orderItem : orderItems) {
                    Order order = new Order();
                    order.setOrderId(mOrder.getOrderId());
                    Seller seller = new Seller();
                    seller.setSellerName(mOrder.getSeller().getSellerName());
                    order.setSeller(seller);

                    ArrayList<OrderItem> _list = new ArrayList<>();
                    _list.add(orderItem);
                    order.setOrderItems(_list);

                    orders.add(order);
                }

                OrderListView.comment(mContext, orders, mStatus);
                break;
            case R.id.btn_contact_seller_send_out_goods://联系商家
            case R.id.btn_contact_seller:
            case R.id.btn_contact_seller_exchange:
                OrderListView.contactSeller(mContext, mOrder);
                break;
            case R.id.btn_cancel_order://取消订单
                OrderListView.cancelOrder(mContext, mOrder, mStatus);
                break;
            case R.id.btn_to_pay://去付款
                OrderListView.toPay(mContext, mOrder, mStatus);
                break;
            case R.id.btn_output_qr://出示二维码
                OrderListView.outputQR(mContext, mOrder);
                break;
        }
    }


}
