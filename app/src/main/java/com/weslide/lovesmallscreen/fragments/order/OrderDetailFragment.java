package com.weslide.lovesmallscreen.fragments.order;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Order;
import com.weslide.lovesmallscreen.models.OrderItem;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateOrderListMessage;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.views.order.OrderAddress;
import com.weslide.lovesmallscreen.views.order.OrderListView;
import com.weslide.lovesmallscreen.views.order.OrderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/18.
 * 订单详情界面
 */
public class OrderDetailFragment extends BaseFragment {

    /**
     * 状态
     */
    public static final String KEY_STATUS = "KEY_STATUS";
    /**
     * 订单id
     */
    public static final String KEY_ORDER_ID = "KEY_ORDER_ID";

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.oa_address)
    OrderAddress oaAddress;
    @BindView(R.id.layout_orders)
    LinearLayout layoutOrders;
    @BindView(R.id.layout_wait_of_goods)
    LinearLayout layoutWaitOfGoods;
    @BindView(R.id.layout_receipt_of_comment)
    LinearLayout layoutReceiptOfComment;
    @BindView(R.id.layout_receipt_of_pay)
    LinearLayout layoutReceiptOfPay;
    @BindView(R.id.layout_receipt_of_send_out_goods)
    LinearLayout layoutReceiptOfSendOutGoods;
    @BindView(R.id.layout_receipt_exchange)
    LinearLayout layoutReceiptExchange;
    @BindView(R.id.layout_order_option)
    LinearLayout layoutOrderOption;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.progress)
    FrameLayout progress;

    Context mContext;
    Order mOrder;
    String mStatus;
    String mOrderId;
    @BindView(R.id.tv_express_tactics)
    TextView tvExpressTactics;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_order_detail, container, false);
        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        loadBundle();
        loadData();

        return mView;
    }

    private void loadBundle() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            mStatus = bundle.getString(KEY_STATUS);
            mOrderId = bundle.getString(KEY_ORDER_ID);
        }
    }

    private void loadData() {

        Request request = new Request();
        Map<String, String> value = new HashMap<>();
        value.put("orderId", mOrderId);
        request.setData(value);
        RXUtils.request(getActivity(), request, "getOrder", new SupportSubscriber<Response<Order>>() {

            @Override
            public void onStart() {
                progress.setVisibility(View.VISIBLE);
                container.setVisibility(View.GONE);
            }

            @Override
            public void onCompleted() {
                progress.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onCompleted();
            }

            @Override
            public void onNext(Response<Order> orderResponse) {
                bindView(orderResponse.getData());
            }
        });

    }

    public void bindView(Order order) {

        mOrder = order;


        layoutOrders.removeAllViews();
        OrderView orderView = new OrderView(getActivity());
        boolean canSelect = false;
        if (mStatus.equals(Constants.ORDER_STATUS_WAIT_OF_GOODS) ||
                mStatus.equals(Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS) ||
                mStatus.equals(Constants.ORDER_STATUS_WAIT_COMMENT) ||
                mStatus.equals(Constants.ORDER_STATUS_WAIT_EXCHANGE)) {
            //显示多选框
            canSelect = true;
        }
        orderView.bindView(mOrder, canSelect);
        layoutOrders.addView(orderView);
        tvExpressTactics.setText(mOrder.getExpressTactics());
        tvOrderCode.setText("订单号："+mOrder.getOrderNumber());
        if (StringUtils.isEmpty(mOrder.getUsername())) {
            oaAddress.setVisibility(View.GONE);
        } else {
            oaAddress.bindView(mOrder, false);
        }


        switch (mStatus) {
            case Constants.ORDER_STATUS_WAIT_PAY: //待支付
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutReceiptOfPay.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS: //待发货
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutReceiptOfSendOutGoods.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_STATUS_WAIT_EXCHANGE: //待兑换
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutReceiptExchange.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_STATUS_WAIT_OF_GOODS: //待收货
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutWaitOfGoods.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_STATUS_WAIT_COMMENT: //待评价
                layoutOrderOption.setVisibility(View.VISIBLE);
                layoutReceiptOfComment.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Subscribe
    public void onEvent(UpdateOrderListMessage message) {

        if (mStatus.equals(message.getStatus())) {
            getActivity().finish();
        }
    }

    @OnClick({R.id.btn_logistics, R.id.btn_back, R.id.btn_confirm_receipt, R.id.btn_comment, R.id.btn_contact_seller, R.id.btn_cancel_order, R.id.btn_to_pay, R.id.btn_back_send_out_goods, R.id.btn_contact_seller_send_out_goods, R.id.btn_back_exchange, R.id.btn_output_qr, R.id.btn_contact_seller_exchange})
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }
}
