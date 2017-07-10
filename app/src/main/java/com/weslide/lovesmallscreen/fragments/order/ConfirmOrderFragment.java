package com.weslide.lovesmallscreen.fragments.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.MyPay;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.GoodsActivity;
import com.weslide.lovesmallscreen.activitys.order.ConfirmOrderActivity;
import com.weslide.lovesmallscreen.activitys.order.OrderActivity;
import com.weslide.lovesmallscreen.activitys.user.MyAddressActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.managers.pay.PayListener;
import com.weslide.lovesmallscreen.managers.pay.PayMessage;
import com.weslide.lovesmallscreen.managers.pay.PayModel;
import com.weslide.lovesmallscreen.models.Address;
import com.weslide.lovesmallscreen.models.Order;
import com.weslide.lovesmallscreen.models.OrderList;
import com.weslide.lovesmallscreen.models.bean.ConfirmOrderBean;
import com.weslide.lovesmallscreen.models.bean.PayOrderBean;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateOrderListMessage;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateShoppingCarMessage;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ReflectionUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;
import com.weslide.lovesmallscreen.views.order.OrderView;
import com.weslide.lovesmallscreen.views.order.PayView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/29.
 * 确认订单界面
 */
public class ConfirmOrderFragment extends BaseFragment implements PayListener {

    /**
     * 用于生成临时订单的Bean
     */
    public static final String KEY_ORDER_LIST= "KEY_ORDER_LIST";

    OrderList mOrderList;
    Address mAddress;
    String mPayMode;

    View mView;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.layout_orders)
    LinearLayout layoutOrders;
    @BindView(R.id.pv_pay)
    PayView pvPay;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.progress)
    FrameLayout progress;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    private Float totalMoney;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_confirm_order, container, false);
        EventBus.getDefault().register(this);

        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        pvPay.setOnPayChangeListener(new PayView.OnPayChangeListener() {
            @Override
            public void payChange(String pay) {
                mPayMode = pay;
            }
        });

        loadBundle();

        bindView();

        progress.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);

        return mView;
    }

    private void loadBundle() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            mOrderList = (OrderList) bundle.getSerializable(KEY_ORDER_LIST);
        }
    }

    private void bindView() {

        if(mOrderList.getTotalMoney() == 0){
            mPayMode = Constants.PAY_WALLET;
            pvPay.setVisibility(View.GONE);
        }

        totalMoney = mOrderList.getTotalMoney();
        tvTotalMoney.setText("合计：￥" + totalMoney);
        for (Order order : mOrderList.getDataList()) {
            OrderView orderView = new OrderView(getActivity());
            orderView.bindView(order);
            layoutOrders.addView(orderView);
        }
    }


    @Subscribe
    public void onEvent(PayMessage payMessage) {

        switch (payMessage.getResult()) {
            case 0:
                onSuccess();
                break;
            case -1:
                onDefeated();
                break;
            case -2:
                onCancel();
                break;
        }

    }


    /**
     * 获取用户默认地址
     */
    private void getDefaultAddress() {
        Request request = new Request();
        RXUtils.request(getActivity(), request, "getDefaultAddress", new SupportSubscriber() {
            @Override
            public void onNext(Object o) {
                Response<Address> response = (Response<Address>) o;

                mAddress = response.getData();

                updateAddress();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getDefaultAddress();
    }

    /**
     * 提交订单
     */
    private void confirmOrder() {
        Request request = new Request();

        List<String> orderIds = new ArrayList<>();
        for (Order order : mOrderList.getDataList()) {
            orderIds.add(order.getOrderId());
        }

        ConfirmOrderBean bean = new ConfirmOrderBean();
        bean.setOrderIds(orderIds);
        bean.setName(mAddress.getName());
        bean.setPhone(mAddress.getPhone());
        bean.setAddress(mAddress.getAddress());

        request.setData(bean);

        RXUtils.request(getActivity(), request, "confirmOrder", new SupportSubscriber<Response>() {

            LoadingDialog dialog;

            @Override
            public void onStart() {
                dialog = new LoadingDialog(getActivity());
                dialog.show();
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);

                T.showShort(getActivity(), response.getMessage());
            }

            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

            @Override
            public void onNext(Response response) {

                //发送购物车改变事件
                EventBus.getDefault().post(new UpdateShoppingCarMessage());

                pay();
            }
        });

    }

    private void updateAddress() {
        tvAddress.setText(mAddress.getAddress());
        tvPhone.setText(mAddress.getPhone());
        tvUsername.setText(mAddress.getName());
    }


    /**
     * 支付
     */
    private void pay() {
        Request request = new Request();

        List<String> orderIds = new ArrayList<>();
        for (Order order : mOrderList.getDataList()) {
            orderIds.add(order.getOrderId());
        }

        PayOrderBean bean = new PayOrderBean();
        bean.setOrderIds(orderIds);
        bean.setPayType(mPayMode);
        request.setData(bean);

        RXUtils.request(getActivity(), request, "payOrders", new SupportSubscriber<Response<PayModel>>() {

            LoadingDialog dialog;

            @Override
            public void onStart() {
                dialog = new LoadingDialog(getActivity());
                dialog.show();
            }

            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getActivity(), response.getMessage());
            }

            @Override
            public void onNext(Response<PayModel> payOrderBean) {

                if (Constants.PAY_ALIPAY.equals(mPayMode)) {
                    MyPay.payToAlipay(getActivity(), payOrderBean.getData().getSign(), ConfirmOrderFragment.this);
                } else if (Constants.PAY_WALLET.equals(mPayMode)) {
                    onSuccess();
                } else if (Constants.PAY_WEIXIN.equals(mPayMode)) {
                    MyPay.payToWeiXin(getActivity(), payOrderBean.getData(), ConfirmOrderFragment.this);
                }
            }
        });
    }


    @OnClick({R.id.layout_address, R.id.btn_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_address:
                AppUtils.toActivity(getActivity(), MyAddressActivity.class);
                break;
            case R.id.btn_pay:
                if (StringUtils.isEmpty(mPayMode)) {
                    T.showShort(getActivity(), "请选择支付方式~");
                    return;
                }

                if (mAddress == null || StringUtils.isEmpty(mAddress.getPhone()) ||
                        StringUtils.isEmpty(mAddress.getAddress()) ||
                        StringUtils.isEmpty(mAddress.getName())) {
                    T.showShort(getActivity(), "还是先完善地址吧~");
                    return;
                }

                confirmOrder();


                break;
        }
    }

    @Override
    public void onSuccess() {
        L.i("支付成功");
        ContextParameter.getUserInfo().setAvailableMoney(Integer.parseInt(ContextParameter.getUserInfo().getAvailableMoney())-totalMoney+"");
        Toast.makeText(getActivity(), "支付成功!", Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        X:for (int i = 0; i < mOrderList.getDataList().size(); i++) {
            int type = mOrderList.getDataList().get(i).getType();

            switch (type) {

                case 1:
                    bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_EXCHANGE);
                    break X;
                default:
                    bundle.putString(OrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS);
                    break;

            }
        }

        getSupportApplication().removeActivitys(OrderActivity.class);

        AppUtils.toActivity(getActivity(), OrderActivity.class, bundle);

        //关闭不需要的界面
        getSupportApplication().removeActivitys(ConfirmOrderActivity.class, GoodsActivity.class);
    }

    @Override
    public void onDefeated() {
        T.showShort(getActivity(), "支付失败了~，再试试吧。");
        //发送列表数据更新消息
        UpdateOrderListMessage message = new UpdateOrderListMessage();
        message.setStatus(Constants.ORDER_STATUS_WAIT_PAY);
        EventBus.getDefault().post(message);
    }

    @Override
    public void onCancel() {
        //发送列表数据更新消息
        UpdateOrderListMessage message = new UpdateOrderListMessage();
        message.setStatus(Constants.ORDER_STATUS_WAIT_PAY);
        EventBus.getDefault().post(message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            //内存泄露风险
            L.e("正加载处理订单内存泄露");
            Class tenCla = Class.forName("com.tencent.a.a.a.a.g");
            Field iField = ReflectionUtils.getDeclaredField(tenCla, "i");
            iField.setAccessible(true);
            iField.set(null,null);

            //处理支付宝内存泄露
            Class aliCla = Class.forName("com.alipay.sdk.packet.d");
            Field tField = ReflectionUtils.getDeclaredField(aliCla, "t");
            tField.setAccessible(true);
            tField.set(null, null);


            L.e("提交订单内存泄露已处理");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
