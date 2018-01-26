package com.weslide.lovesmallscreen.exchange.basemodel.model.datamodel;

import android.content.Context;

import com.weslide.lovesmallscreen.exchange.basemodel.iinterface.IExchangeFgDataListenner;

/**
 * Created by YY on 2018/1/20.
 */
public interface IExchangeFgData {
    void askNetData(Context context,String typeId,String search, IExchangeFgDataListenner listener);

    void refreshData(String typeId,String search,IExchangeFgDataListenner listener);

    void loadMoreData(IExchangeFgDataListenner listener);
}
