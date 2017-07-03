package com.weslide.lovesmallscreen.model_yy;

import android.content.Context;

import com.weslide.lovesmallscreen.model_yy.javabean.TicketGoodsDtModel;

/**
 * Created by YY on 2017/6/1.
 */
public interface ITicketGoodsDtFgData {
    void getTicketGoodDtData(Context context, String ticketId, IFgBaseObjListenner<TicketGoodsDtModel> listenner);
}
