package com.weslide.lovesmallscreen.model_yy;

import android.content.Context;

import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.OrderMsgModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;


/**
 * Created by YY on 2017/5/5.
 */
public class ISystemMsgDataImpl implements ISystemMsgData {
    @Override
    public void getSystemMsgList(Context context, String typeId, final IOrderMsgFgListenner listenner) {
        Request<OrderMsgModel> request = new Request<>();
        OrderMsgModel orderMsgModel = new OrderMsgModel();
        orderMsgModel.setUserId(ContextParameter.getUserInfo().getUserId());
        orderMsgModel.setTypeId(typeId);
        request.setData(orderMsgModel);
        RXUtils.request(context, request, "getMsgList", new SupportSubscriber<Response<OrderMsgModel>>() {
            @Override
            public void onNext(Response<OrderMsgModel> orderMsgModelResponse) {
                listenner.onSuccess(orderMsgModelResponse.getData().getMsgList());
            }

        });
    }
}
