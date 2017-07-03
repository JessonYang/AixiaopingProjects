package com.weslide.lovesmallscreen.presenter_yy;

import android.content.Context;

import com.weslide.lovesmallscreen.model_yy.IOrderMsgFgListenner;
import com.weslide.lovesmallscreen.model_yy.ISystemMsgData;
import com.weslide.lovesmallscreen.model_yy.ISystemMsgDataImpl;
import com.weslide.lovesmallscreen.models.OrderMsgDtOb;
import com.weslide.lovesmallscreen.view_yy.viewinterface.IShowSystemMsgFg;

import java.util.List;

/**
 * Created by YY on 2017/5/5.
 */
public class SystemMsgFgPresenter {
    private IShowSystemMsgFg iShowSystemMsgFg;
    private ISystemMsgData iSystemMsgData;

    public SystemMsgFgPresenter(IShowSystemMsgFg iShowSystemMsgFg) {
        this.iShowSystemMsgFg = iShowSystemMsgFg;
        this.iSystemMsgData = new ISystemMsgDataImpl();
    }

    public void initSystemMsgFgView() {
        iShowSystemMsgFg.initView();
        iShowSystemMsgFg.initData();
    }

    public void upDateMsgList(Context context, String typeId){
        iSystemMsgData.getSystemMsgList(context, typeId, new IOrderMsgFgListenner() {
            @Override
            public void onSuccess(List<OrderMsgDtOb> list) {
                iShowSystemMsgFg.showView(list);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }
}
