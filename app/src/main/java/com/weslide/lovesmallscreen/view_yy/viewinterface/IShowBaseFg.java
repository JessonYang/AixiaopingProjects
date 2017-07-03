package com.weslide.lovesmallscreen.view_yy.viewinterface;

/**
 * Created by YY on 2017/5/5.
 */
public interface IShowBaseFg {
    //初始化view
    void initView();

    //准备工作，初始化数据
    void initData();

    //加载数据之前显示的LoadingView
    void showLoadingView();

    void dismissLoadingView();

    //请求网络数据失败
    void showErrView();
}
