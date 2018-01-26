package com.weslide.lovesmallscreen.exchange.presenter;

import android.content.Context;

import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.exchange.basemodel.iinterface.IExchangeFgDataListenner;
import com.weslide.lovesmallscreen.exchange.basemodel.iinterface.IShowExchangeFg;
import com.weslide.lovesmallscreen.exchange.basemodel.model.datamodel.IExchangeFgData;
import com.weslide.lovesmallscreen.exchange.basemodel.model.datamodel.IExchangeFgDataImpl;
import com.weslide.lovesmallscreen.models.GoodsType;
import com.weslide.lovesmallscreen.network.DataList;

import java.util.List;

/**
 * Created by YY on 2018/1/22.
 */
public class ExchangeHomePresenter {
    private IShowExchangeFg iShowExchangeFg;
    private IExchangeFgData iExchangeFgData;

    public ExchangeHomePresenter(IShowExchangeFg iShowExchangeFg) {
        this.iShowExchangeFg = iShowExchangeFg;
        iExchangeFgData = new IExchangeFgDataImpl();
    }

    public void showFgView(Context context,String typeId,String search){
        iShowExchangeFg.initView();
        iShowExchangeFg.showLoading();
        iShowExchangeFg.initData();
        iExchangeFgData.askNetData(context, typeId, search, new IExchangeFgDataListenner() {
            @Override
            public void onSuccess(DataList<RecyclerViewModel> dataList, List<GoodsType> typeList) {
                iShowExchangeFg.showView(dataList,typeList);
                iShowExchangeFg.dismissLoading();
            }

            @Override
            public void onErr(String errMsg) {
                iShowExchangeFg.dismissLoading();
            }
        });
    }

    public void refreshView(String typeId,String search){
        iExchangeFgData.refreshData(typeId, search, new IExchangeFgDataListenner() {
            @Override
            public void onSuccess(DataList<RecyclerViewModel> dataList, List<GoodsType> typeList) {
                iShowExchangeFg.showView(dataList,null);
            }

            @Override
            public void onErr(String errMsg) {

            }
        });
    }

    public void loadMore(){
        iExchangeFgData.loadMoreData(new IExchangeFgDataListenner() {
            @Override
            public void onSuccess(DataList<RecyclerViewModel> dataList, List<GoodsType> typeList) {
                iShowExchangeFg.showView(dataList,null);
            }

            @Override
            public void onErr(String errMsg) {

            }
        });
    }
}
