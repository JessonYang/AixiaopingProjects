package com.weslide.lovesmallscreen.presenter_yy;

import android.content.Context;

import com.weslide.lovesmallscreen.model_yy.IOrderMsgFgData;
import com.weslide.lovesmallscreen.model_yy.IOrderMsgFgDataImpl;
import com.weslide.lovesmallscreen.model_yy.IOrderMsgFgListenner;
import com.weslide.lovesmallscreen.models.OrderMsgDtOb;
import com.weslide.lovesmallscreen.view_yy.viewinterface.IShowOrderMsgFg;

import java.util.List;

/**
 * Created by YY on 2017/5/5.
 */
public class OrderMsgFgPresenter {
    private IShowOrderMsgFg iShowOrderMsgFg;
    private IOrderMsgFgData iOrderMsgFgData;

    public OrderMsgFgPresenter(IShowOrderMsgFg iShowOrderMsgFg) {
        this.iShowOrderMsgFg = iShowOrderMsgFg;
        this.iOrderMsgFgData = new IOrderMsgFgDataImpl();
    }

    public void showOrderMsgFgView(Context context,int typeId){
        iShowOrderMsgFg.initView();
        iShowOrderMsgFg.initData();
        iOrderMsgFgData.getOrderMsgList(context, typeId+"", new IOrderMsgFgListenner() {
            @Override
            public void onSuccess(List<OrderMsgDtOb> list) {
                iShowOrderMsgFg.showView(list);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    public void upDateMsgList(Context context,int typeId){
        iOrderMsgFgData.getOrderMsgList(context, typeId+"", new IOrderMsgFgListenner() {
            @Override
            public void onSuccess(List<OrderMsgDtOb> list) {
                iShowOrderMsgFg.showView(list);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }
}
