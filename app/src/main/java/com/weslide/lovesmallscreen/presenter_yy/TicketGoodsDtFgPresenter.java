package com.weslide.lovesmallscreen.presenter_yy;

import android.content.Context;

import com.weslide.lovesmallscreen.model_yy.IFgBaseObjListenner;
import com.weslide.lovesmallscreen.model_yy.ITicketGoodsDtFgData;
import com.weslide.lovesmallscreen.model_yy.ITicketGoodsDtFgDataImpl;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketGoodsDtModel;
import com.weslide.lovesmallscreen.view_yy.viewinterface.IShowTicketGoodsDtFg;

/**
 * Created by YY on 2017/6/2.
 */
public class TicketGoodsDtFgPresenter {
    private IShowTicketGoodsDtFg iShowPromotionProductDtFg;
    private ITicketGoodsDtFgData iTicketGoodsDtFgData;

    public TicketGoodsDtFgPresenter(IShowTicketGoodsDtFg iShowPromotionProductDtFg) {
        this.iShowPromotionProductDtFg = iShowPromotionProductDtFg;
        iTicketGoodsDtFgData = new ITicketGoodsDtFgDataImpl();
    }

    public void showTicketGoodDtFgView(Context context,String ticketId){
        iShowPromotionProductDtFg.initView();
        iShowPromotionProductDtFg.initData();
        iShowPromotionProductDtFg.showLoadingView();
        iTicketGoodsDtFgData.getTicketGoodDtData(context, ticketId, new IFgBaseObjListenner<TicketGoodsDtModel>() {
            @Override
            public void onSuccess(TicketGoodsDtModel obj) {
                iShowPromotionProductDtFg.showViewData(obj);
                iShowPromotionProductDtFg.dismissLoadingView();
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }
}
