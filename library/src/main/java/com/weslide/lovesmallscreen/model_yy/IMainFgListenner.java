package com.weslide.lovesmallscreen.model_yy;


import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.network.DataList;

/**
 * Created by YY on 2017/5/5.
 */
public interface IMainFgListenner {
    void onSuccess(DataList<RecyclerViewModel> dataList);

    void onError(String errMsg);
}
