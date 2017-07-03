package com.weslide.lovesmallscreen.model_yy;

import android.content.Context;

import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketGoodsDtModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;

/**
 * Created by YY on 2017/6/2.
 */
public class ITicketGoodsDtFgDataImpl implements ITicketGoodsDtFgData {
    @Override
    public void getTicketGoodDtData(Context context, String ticketId, final IFgBaseObjListenner<TicketGoodsDtModel> listenner) {
        Request<TicketGoodsDtModel> request = new Request<>();
        TicketGoodsDtModel model = new TicketGoodsDtModel();
        model.setTicketId(ticketId);
        request.setData(model);
        RXUtils.request(context,request,"getTicketGoodsDetail", new SupportSubscriber<Response<TicketGoodsDtModel>>() {
            @Override
            public void onNext(Response<TicketGoodsDtModel> ticketGoodsDtModelResponse) {
                listenner.onSuccess(ticketGoodsDtModelResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                listenner.onError(e.getMessage());
            }

            @Override
            public void onResponseError(Response response) {
                listenner.onError(response.getMessage());
            }
        });
    }
}
