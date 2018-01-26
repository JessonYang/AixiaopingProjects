package com.weslide.lovesmallscreen.exchange.basemodel.iinterface;

import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.GoodsType;
import com.weslide.lovesmallscreen.network.DataList;

import java.util.List;

/**
 * Created by YY on 2018/1/20.
 */
public interface IShowExchangeFg extends BaseInterface{
    void showView(DataList<RecyclerViewModel> dataList, List<GoodsType> typeList);
}
