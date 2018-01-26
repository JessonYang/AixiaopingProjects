package com.weslide.lovesmallscreen.utils;

import android.app.Activity;
import android.content.Intent;

import com.weslide.lovesmallscreen.activitys.order.ConfirmOrderActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.OrderList;
import com.weslide.lovesmallscreen.models.bean.CreateTempOrderListBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

/**
 * Created by xu on 2016/8/17.
 * 订单工具类
 */
public class OrderUtils {

    /**
     * 向服务器发送生成临时当单请求
     */
    public static void createTempOrderList(Activity context, CreateTempOrderListBean createTempOrderListBean,int goodType) {
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
                OrderList orderList = response.getData();

                Intent intent = new Intent(context, ConfirmOrderActivity.class);
                intent.putExtra(ConfirmOrderActivity.KEY_ORDER_LIST, orderList);
                intent.putExtra("science", response.getData().getScience());
                intent.putExtra("goodType", goodType);
                intent.putExtra("teamOrderId", orderList.getDataList().get(0).getOrderId());
                context.startActivity(intent);

            }
        });
    }

    /**
     * 向服务器发送生成临时当单请求(重载方法)
     */
    public static void createTempOrderList(Activity context, CreateTempOrderListBean createTempOrderListBean) {
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
                OrderList orderList = response.getData();

                Intent intent = new Intent(context, ConfirmOrderActivity.class);
                intent.putExtra(ConfirmOrderActivity.KEY_ORDER_LIST, orderList);
                intent.putExtra("science", response.getData().getScience());
                context.startActivity(intent);

            }
        });
    }

}
