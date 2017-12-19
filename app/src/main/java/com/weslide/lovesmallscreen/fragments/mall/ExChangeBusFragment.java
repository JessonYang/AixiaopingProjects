package com.weslide.lovesmallscreen.fragments.mall;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/10/26.
 * 换货会
 */
public class ExChangeBusFragment extends BaseFragment {

    View mView;
    @BindView(R.id.wb)
    WebView wb;
    @BindView(R.id.tv)
    TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_exchange_bus, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    @SuppressLint("JavascriptInterface")
    private void init() {


        wb.setWebViewClient(new WebViewClient());
        //支持javascript
        wb.getSettings().setJavaScriptEnabled(true);
// 设置可以支持缩放
        wb.getSettings().setSupportZoom(true);
// 设置出现缩放工具
        wb.getSettings().setBuiltInZoomControls(false);
//扩大比例的缩放
        wb.getSettings().setUseWideViewPort(true);
//自适应屏幕
        wb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub

                view.loadUrl(url);
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                return true;
            }

            //页面开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //加载的ProgressDialog 显示
                tv.setVisibility(View.VISIBLE);
            }

            //加载结束 （其实页面404等等错误的情况也算加载完成）
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //对话框消失 显示页面
                tv.setVisibility(View.GONE);
            }
        });

        String URL = HTTP.URL_EXCHENGE_GOODS + "appVersion=" + ContextParameter.getAppVersion() +
                "&channelId=" + ContextParameter.getChannelId() + "&dip=" +
                ContextParameter.getDip() + "&lat=" + ContextParameter.getCurrentLocation().getLatitude() +
                "&lng" + ContextParameter.getCurrentLocation().getLongitude() + "&os=ANDROID" + "&userId=" + ContextParameter.getUserInfo().getUserId()
                + "&zoneId=" + ContextParameter.getCurrentZone().getZoneId();
        L.e("换货会链接", URL);
        wb.addJavascriptInterface(new InJavaScriptGetBody(), "back");
        wb.loadUrl(URL);

    }

    /**
     * 供网页js调用的方法
     */
    class InJavaScriptGetBody {

        @JavascriptInterface
        public void back() {
            getActivity().onBackPressed();
        }
    }

}
