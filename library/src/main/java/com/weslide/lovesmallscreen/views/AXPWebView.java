package com.weslide.lovesmallscreen.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.weslide.lovesmallscreen.utils.L;

import net.aixiaoping.library.R;

/**
 * Created by xu on 2016/6/2.
 * 浏览器
 */
public class AXPWebView extends FrameLayout {

    View mView;
    Context mContext;

    WebView mWebView;
    RelativeLayout mWebViewLayout;
    LinearLayout mErrorLayout;
    Button mFreshen;

    public AXPWebView(Context context) {
        super(context);
        init(context);
    }

    public AXPWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AXPWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context){
        initView(context);
        initWebViewConfig();
    }

    private void initView(Context context){

        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.view_web, this, true);

        mWebView = (WebView) findViewById(R.id.wv_webview);
        mWebViewLayout = (RelativeLayout) findViewById(R.id.layout_page_webview);
        mErrorLayout = (LinearLayout) findViewById(R.id.layout_page_error);
        mFreshen = (Button) findViewById(R.id.btn_refresh);
    }

    /**
     * 初始化WebView的属性
     */
    private void initWebViewConfig() {

        mWebView.setWebViewClient(new WebSiteWebViewClient());
        mWebView.setDownloadListener(new WebSiteDownLoadListener());

        mWebView.getSettings().setJavaScriptEnabled(true);

        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //设置默认加载的可视范围是大视野范围
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        //隐藏缩放按钮
        mWebView.getSettings().setDisplayZoomControls(false);

        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示

        //适应屏幕
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);

        mWebView.getSettings().setAppCacheMaxSize(1024*1024*10);//设置缓冲大小，10M
        String appCacheDir = mContext.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        mWebView.getSettings().setAppCachePath(appCacheDir);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setLoadsImagesAutomatically(false);
        //图片最后加载，皮面空白时间过长
        if(Build.VERSION.SDK_INT >= 19) {
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            mWebView.getSettings().setLoadsImagesAutomatically(false);
        }

        showWebView();
    }

    public WebView getWebView(){
        return mWebView;
    }

    /** 显示错误页面 */
    public void showError(){
        mWebViewLayout.setVisibility(GONE);
        mErrorLayout.setVisibility(VISIBLE);
    }

    /** 显示WEBview */
    public void showWebView(){
        mWebViewLayout.setVisibility(VISIBLE);
        mErrorLayout.setVisibility(GONE);
    }



    /**
     * WebViewClient主要帮助WebView处理各种通知、请求事件的，比如：
     *	onLoadResource
     *	onPageStart
     *	onPageFinish
     *	onReceiveError
     *	onReceivedHttpAuthRequest
     * @author xu
     *
     */
    class WebSiteWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            L.e("test", "跳转：" + url);
            //这里什么都不写可以避免重定向的问题
            return false;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            L.e("onReceivedError");
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            L.e("onReceivedHttpError");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            L.e("test", "跳转：onPageStarted-" + url + ", title=" + view.getTitle());

            //mLlProgress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            L.e("test", "跳转：onPageFinished-" + url+ ", title=" + view.getTitle());
            if(mWebView == null){
                //finish 后这个方法任然可能会在执行一次, 所以就有可能会抛出空指针
                return;
            }

            if(mWebView.getSettings() != null && !mWebView.getSettings().getLoadsImagesAutomatically()) {
                mWebView.getSettings().setLoadsImagesAutomatically(true);
            }
        }
    }

    /**
     * WebView的文件下载事件
     * @author xu
     *
     */
    private class WebSiteDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {

            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);

            if(mWebView.canGoBack()){
                mWebView.goBack();
            } else {
                if(mContext instanceof Activity){
                    ((Activity) mContext).finish();
                }
            }
        }

    }

}
