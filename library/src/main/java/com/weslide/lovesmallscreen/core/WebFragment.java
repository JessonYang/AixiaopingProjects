package com.weslide.lovesmallscreen.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.views.AXPWebView;

/**
 * Created by xu on 2016/6/15.
 * 商品界面
 */
public class WebFragment extends BaseFragment {

    public AXPWebView mWebView;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mWebView = new AXPWebView(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(layoutParams);
        mWebView.getWebView().loadUrl(url);
        L.e("加载完成");

        return mWebView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mWebView.getWebView().destroy();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
