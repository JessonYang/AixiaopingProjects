package com.weslide.lovesmallscreen.view_yy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.Button;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.MyPay;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.managers.pay.PayListener;
import com.weslide.lovesmallscreen.managers.pay.PayModel;
import com.weslide.lovesmallscreen.models.OrderList;
import com.weslide.lovesmallscreen.models.bean.ConfirmOrderBean;
import com.weslide.lovesmallscreen.models.bean.CreateTempOrderListBean;
import com.weslide.lovesmallscreen.models.bean.PayOrderBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ReflectionUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;
import com.weslide.lovesmallscreen.views.order.PayView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/6/18.
 */
public class ApplayPartnerPayActivity extends BaseActivity implements View.OnClickListener, PayListener {

    private ImageView back_iv;
    private PayView pay_view;
    private Button pay_btn;
    private String mPayMode;
    private static OrderList orderList;
    private TextView pay_num_tv;
    private String orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applay_partner_pay);
        initView();
        initData();
    }

    private void initData() {
        back_iv.setOnClickListener(this);
        pay_btn.setOnClickListener(this);
        pay_view.setOnPayChangeListener(new PayView.OnPayChangeListener() {
            @Override
            public void payChange(String pay) {
                mPayMode = pay;
            }
        });
    }

    private void initView() {
        back_iv = ((ImageView) findViewById(R.id.back_iv));
        pay_view = ((PayView) findViewById(R.id.pay_view));
        pay_btn = ((Button) findViewById(R.id.pay_btn));
        pay_num_tv = ((TextView) findViewById(R.id.pay_num_tv));
        CreateTempOrderListBean bean = new CreateTempOrderListBean();
        bean.setType("2");
        bean.setGoodsId("nnm349");
        bean.setNumber(1);
        createTempList(this, bean);
    }

    /**
     * 提交订单
     */
    private void confirmOrder() {
        Request request = new Request();

        List<String> orderIds = new ArrayList<>();
        orderId = orderList.getDataList().get(0).getOrderId();
        orderIds.add(orderId);
        Log.d("雨落无痕丶", "confirmOrder: "+ orderId);
        ConfirmOrderBean bean = new ConfirmOrderBean();
        bean.setOrderIds(orderIds);
//        bean.setName(mAddress.getName());
//        bean.setPhone(mAddress.getPhone());
//        bean.setAddress(mAddress.getAddress());

        request.setData(bean);

        RXUtils.request(this, request, "confirmOrder", new SupportSubscriber<Response>() {

            LoadingDialog dialog;

            @Override
            public void onStart() {
                dialog = new LoadingDialog(ApplayPartnerPayActivity.this);
                dialog.show();
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(ApplayPartnerPayActivity.this, response.getMessage());
            }

            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

            @Override
            public void onNext(Response response) {
                pay();
            }
        });
    }

    /**
     * 支付
     */
    private void pay() {
        Request request = new Request();

        List<String> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        PayOrderBean bean = new PayOrderBean();
        bean.setOrderIds(orderIds);
        bean.setPayType(mPayMode);
        request.setData(bean);

        RXUtils.request(ApplayPartnerPayActivity.this, request, "payOrders", new SupportSubscriber<Response<PayModel>>() {

            LoadingDialog dialog;

            @Override
            public void onStart() {
                dialog = new LoadingDialog(ApplayPartnerPayActivity.this);
                dialog.show();
            }

            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(ApplayPartnerPayActivity.this, response.getMessage());
            }

            @Override
            public void onNext(Response<PayModel> payOrderBean) {

                if (Constants.PAY_ALIPAY.equals(mPayMode)) {
                    MyPay.payToAlipay(ApplayPartnerPayActivity.this, payOrderBean.getData().getSign(), ApplayPartnerPayActivity.this);
                } else if (Constants.PAY_WALLET.equals(mPayMode)) {
                    onSuccess();
                } else if (Constants.PAY_WEIXIN.equals(mPayMode)) {
                    MyPay.payToWeiXin(ApplayPartnerPayActivity.this, payOrderBean.getData(), ApplayPartnerPayActivity.this);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.pay_btn:
                if (StringUtils.isEmpty(mPayMode)) {
                    T.showShort(this, "请选择支付方式~");
                    return;
                }

                confirmOrder();
                break;
        }
    }

    @Override
    public void onSuccess() {
        L.i("支付成功");
        Toast.makeText(this, "支付成功!", Toast.LENGTH_SHORT).show();
        AppUtils.toActivity(this, ApplayPartnerPaySuccessActivity.class);
        //关闭不需要的界面
        getSupportApplication().removeActivitys(ApplayPartnerPayActivity.class);
    }

    @Override
    public void onDefeated() {
        T.showShort(this, "支付失败了~，再试试吧。");
    }

    @Override
    public void onCancel() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
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

    /**
     * 向服务器发送生成临时当单请求
     */
    public void createTempList(Activity context, CreateTempOrderListBean createTempOrderListBean) {
        Request<CreateTempOrderListBean> request = new Request<>();
        request.setData(createTempOrderListBean);
        RXUtils.request(context, request, "createTempOrderList", new SupportSubscriber() {
            LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(context);
                loadingDialog.show();
            }

            @Override
            public void onStop() {
                super.onStop();
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                T.showShort(context, "出错了，请重试");
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(context, response.getMessage());
            }

            @Override
            public void onNext(Object o) {
                Response<OrderList> response = (Response<OrderList>) o;
                orderList = response.getData();
                pay_num_tv.setText(orderList.getTotalMoney() + "");
            }
        });
    }
}
