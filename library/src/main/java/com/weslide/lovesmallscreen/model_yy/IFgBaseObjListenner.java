package com.weslide.lovesmallscreen.model_yy;



/**
 * Created by YY on 2017/5/5.
 */
public interface IFgBaseObjListenner<V> {
    void onSuccess(V obj);

    void onError(String errMsg);
}
