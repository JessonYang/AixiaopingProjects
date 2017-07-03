package com.weslide.lovesmallscreen.model_yy;



import java.util.List;

/**
 * Created by YY on 2017/5/5.
 */
public interface IFgBaseListListenner<V> {
    void onSuccess(List<V> list);

    void onError(String errMsg);
}
