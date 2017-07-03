package com.weslide.lovesmallscreen.model_yy;

import android.content.Context;

/**
 * Created by YY on 2017/5/5.
 */
public interface IOrderMsgFgData {
    void getOrderMsgList(Context context, String typeId, IOrderMsgFgListenner listenner);
}
