package com.weslide.lovesmallscreen.core;

import com.weslide.lovesmallscreen.models.Live;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.L;

import rx.Subscriber;

/**
 * Created by xu on 2016/6/13.
 */
public abstract class SupportSubscriber<T> extends Subscriber<T> {


    /**
     * 执行顺序
     *
     * 全局
     * onError
     * onResponseError
     * onNoNetwork
     *
     *
     * 顺序
     * onStart
     * handlerResponse
     * handlerResponseMainThread
     * onNext
     * onCompleted
     */


    /**
     * 没有网络时调用，仅限RXUtils调用
     */
    public void onNoNetwork(){
        L.i("rx运行无网络");
    }

    @Override
    public void onError(Throwable e) {
        L.e("error", "error", e);
        onStop();
    }

    /**
     * 整个生命周期运行结束
     * onCompleted 在出现异常onError时不会执行
     */
    public void onStop(){

    }

    @Override
    public void onCompleted() {
        L.i("rx运行结束");
        onStop();
    }

    /**
     * 请求执行完成后处理，处于子线程内
     * 请求返回值出现错误不会继续执行
     * @param t
     */
    public void handlerResponse(T t){}

    /**
     * 请求执行完成后处理，主线程内
     * 请求返回值出现错误不会继续执行
     * @param t
     */
    public void handlerResponseMainThread(T t){

    }

    /**
     * 请求错误
     * @param response
     */
    public void onResponseError(Response response){L.i("请求错误");};
}
