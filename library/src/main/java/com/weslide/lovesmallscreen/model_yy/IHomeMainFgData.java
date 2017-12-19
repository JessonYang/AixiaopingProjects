package com.weslide.lovesmallscreen.model_yy;

import android.content.Context;

/**
 * Created by YY on 2017/11/27.
 */
public interface IHomeMainFgData {
    void getHomeDataList(Context context, IMainFgListenner iMainFgListenner);

    void refreshHomeData(IMainFgListenner listenner);

    void loadMoreData(IMainFgListenner listenner);
}
