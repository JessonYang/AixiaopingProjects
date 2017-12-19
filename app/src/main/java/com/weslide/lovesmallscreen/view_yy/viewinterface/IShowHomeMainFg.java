package com.weslide.lovesmallscreen.view_yy.viewinterface;

import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.network.DataList;

/**
 * Created by YY on 2017/11/27.
 */
public interface IShowHomeMainFg extends IShowBaseFg {
    void showView(DataList<RecyclerViewModel> dataList);

    void refreshView(DataList<RecyclerViewModel> list);
}
