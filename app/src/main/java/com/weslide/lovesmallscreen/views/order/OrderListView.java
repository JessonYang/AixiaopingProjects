package com.weslide.lovesmallscreen.views.order;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.malinskiy.superrecyclerview.decoration.SpaceItemDecoration;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.activitys.order.ApplyBackOrderActivity;
import com.weslide.lovesmallscreen.activitys.order.EvaluateActivity;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.LoadingSubscriber;
import com.weslide.lovesmallscreen.models.Order;
import com.weslide.lovesmallscreen.models.OrderItem;
import com.weslide.lovesmallscreen.models.OrderList;
import com.weslide.lovesmallscreen.models.bean.CancelOrderBean;
import com.weslide.lovesmallscreen.models.bean.CreateTempOrderListBean;
import com.weslide.lovesmallscreen.models.bean.GetOrderListBean;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateOrderListMessage;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.CustomDialogUtil;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.NetworkUtils;
import com.weslide.lovesmallscreen.utils.OrderUtils;
import com.weslide.lovesmallscreen.utils.QRCodeUtil;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.adapters.OrderListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/19.
 * 订单列表
 * 原来使用的是Fragment
 * 但是因为fragment本身嵌套缺陷的问题，只能继续使用View
 */
public class OrderListView extends FrameLayout {


    String mStatus = Constants.ORDER_STATUS_WAIT_PAY;  //默认为待付款
    GetOrderListBean mGetOrderListBean;

    @BindView(R.id.list)
    SuperRecyclerView list;
    @BindView(R.id.no_data_ll)
    LinearLayout no_data_ll;
    @BindView(R.id.empty_reload_btn)
    Button btn_empty_reload;
    OrderListAdapter mAdapter;
    OrderList mOrderList = new OrderList();

    public OrderListView(Context context, String status) {
        super(context);
        mStatus = status;
        initView();
    }

    public OrderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OrderListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.fragment_order_list, this, true);

        ButterKnife.bind(this);
        mGetOrderListBean = new GetOrderListBean();

        if (!NetworkUtils.isConnected(getContext())) {
            list.setVisibility(GONE);
            no_data_ll.setVisibility(VISIBLE);
        }else {
            list.setVisibility(VISIBLE);
            no_data_ll.setVisibility(GONE);
        }
        btn_empty_reload.setEnabled(true);
        btn_empty_reload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtils.isConnected(getContext())) {
                    reLoadData();
                    list.setVisibility(VISIBLE);
                    no_data_ll.setVisibility(GONE);
                }
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        //添加项的间隔
        list.addItemDecoration(new SpaceItemDecoration(DensityUtils.dp2px(getContext(), 8)));
        mAdapter = new OrderListAdapter(getContext(), mOrderList, mStatus);
        list.setAdapter(mAdapter);

        //加载更多
        list.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                loadData();
            }
        });

        //下拉刷新
        list.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reLoadData();
            }
        });

        //重新加载
        list.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoadData();
            }
        });
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    public void reLoadData(){
        mGetOrderListBean.setPageIndex(0);
        loadData();
    }

    public void loadData() {

        if(!ContextParameter.isLogin()){
            return;
        }

        Request<GetOrderListBean> request = new Request();
        mGetOrderListBean.setPageIndex(mGetOrderListBean.getPageIndex() + 1);
        mGetOrderListBean.setStatus(mStatus);

        request.setData(mGetOrderListBean);
        RXUtils.request(getContext(), request, "getOrderList", new RecyclerViewSubscriber<Response<OrderList>>(mAdapter, mOrderList) {

            @Override
            public void onSuccess(Response<OrderList> orderListResponse) {
                mAdapter.addDataListNotifyDataSetChanged(orderListResponse.getData());
            }
        });
    }

    @Subscribe
    public void onEvent(UpdateOrderListMessage message) {

        if (mStatus.equals(message.getStatus())) {
            mGetOrderListBean.setPageIndex(0);
            loadData();
        }
    }


    public static void toLogistics(Context context, Order order) {
        AppUtils.toExpress(context, order.getExpressName(), order.getExpressNumber());
    }

    /**
     * 商品退单
     *
     * @param context
     * @param orderItems
     * @param status
     */
    public static void back(Context context, ArrayList<OrderItem> orderItems, String status) {
        if (orderItems == null || orderItems.size() == 0) {
//            T.showShort(context, "还未选中");
            CustomDialogUtil.showNoticDialog(context,"还未选中任何商品!");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(ApplyBackOrderActivity.KEY_ORDER_ITEM_LIST, orderItems);
        bundle.putString(EvaluateActivity.KEY_STATUS, status);

        AppUtils.toActivity(context, ApplyBackOrderActivity.class, bundle);
    }


    /**
     * 确定收货
     *
     * @param context
     * @param order   操作的订单
     * @param status  该订单的状态， 这个状态和Order里的订单状态有所不同，就比如，待收货状态在前端，就包括待收货和待兑换
     */
    public static void confirmReceipt(Context context, Order order, String status) {

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        com.rey.material.app.Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                super.onPositiveActionClicked(fragment);
                Request request = new Request();
                Map<String, String> map = new HashMap<>();
                map.put("orderId", order.getOrderId());
                request.setData(map);

                RXUtils.request(context, request, "confirmReceipt", new LoadingSubscriber<Response>(context) {
                    @Override
                    public void onNext(Response o) {
                        //发送列表数据更新消息
                        UpdateOrderListMessage message = new UpdateOrderListMessage();
                        message.setStatus(status);
                        EventBus.getDefault().post(message);
                    }
                });
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder) builder).message("您确定收货吗？")
                .title("订单提示")
                .positiveAction("确定").negativeAction("再等等");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(((BaseActivity) context).getSupportFragmentManager(), null);
    }

    /**
     * 评价晒单
     *
     * @param context
     * @param orders
     * @param status
     */
    public static void comment(Context context, ArrayList<Order> orders, String status) {

        if (orders == null || orders.size() == 0) {
            T.showShort(context, "还未选中");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(EvaluateActivity.KEY_LIST_ORDER, orders);
        bundle.putString(EvaluateActivity.KEY_STATUS, status);

        AppUtils.toActivity(context, EvaluateActivity.class, bundle);

    }

    /**
     * 联系商家
     *
     * @param context
     * @param order
     */
    public static void contactSeller(Context context, Order order) {
        if (StringUtils.isEmpty(order.getSeller().getSellerPhone())) {
            T.showShort(context, "该商家还没有填写手机号码");
            return;
        }
        AppUtils.toCallPhone(context, order.getSeller().getSellerPhone());
    }

    /**
     * 取消订单
     *
     * @param context
     * @param order
     * @param status
     */
    public static void cancelOrder(Context context, Order order, String status) {
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        com.rey.material.app.Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                super.onPositiveActionClicked(fragment);
                CancelOrderBean bean = new CancelOrderBean();
                bean.setOrderId(order.getOrderId());

                Request request = new Request();
                request.setData(bean);

                RXUtils.request(context, request, "cancelOrder", new LoadingSubscriber<Response>(context) {
                    @Override
                    public void onNext(Response response) {
                        //发送列表数据更新消息
                        UpdateOrderListMessage message = new UpdateOrderListMessage();
                        message.setStatus(status);
                        EventBus.getDefault().post(message);
                    }
                });
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder) builder).message("您确定取消该订单吗？")
                .title("订单提示")
                .positiveAction("确定").negativeAction("再等等");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(((BaseActivity) context).getSupportFragmentManager(), null);
    }

    /**
     * 再次付款
     *
     * @param context
     * @param order
     * @param status
     */
    public static void toPay(Context context, Order order, String status) {
        CreateTempOrderListBean bean = new CreateTempOrderListBean();
        bean.setType("3");
        bean.setOrderId(order.getOrderId());

        OrderUtils.createTempOrderList((Activity) context, bean);

    }

    /**
     * 出示二维码
     *
     * @param context
     * @param order
     */
    public static void outputQR(Context context, Order order) {
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        com.rey.material.app.Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {

            @Override
            protected com.rey.material.app.Dialog onBuild(Context context, int styleId) {
                return super.onBuild(context, styleId);
            }

            @Override
            protected void onBuildDone(com.rey.material.app.Dialog dialog) {
                android.widget.ImageView ivQR = (android.widget.ImageView) dialog.findViewById(R.id.iv_qr);
                TextView tvExchangeCode = (TextView) dialog.findViewById(R.id.tv_exchange_code);

                tvExchangeCode.setText(order.getExchangeCode());
                ivQR.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                        Bitmap bitmap = QRCodeUtil.createQRImage(
                                URIResolve.createExchangeURI(order.getOrderId(), order.getExchangeCode()), ivQR.getMeasuredWidth(), ivQR.getMeasuredHeight());
                        ivQR.setImageBitmap(bitmap);
                    }
                });

                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
//                EditText et_pass = (EditText)fragment.getDialog().findViewById(R.id.custom_et_password);
//                Toast.makeText(, "Connected. pass=" + et_pass.getText().toString(), Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.title("出示二维码").contentView(R.layout.dialog_show_goods_exchange_qr);

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(((BaseActivity) context).getSupportFragmentManager(), null);
    }

    public static ArrayList<OrderItem> getSelectOrderItem(Order order) {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.isSelect()) {
                orderItems.add(orderItem);
            }

        }
        return orderItems;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        EventBus.getDefault().unregister(this);
    }

}
