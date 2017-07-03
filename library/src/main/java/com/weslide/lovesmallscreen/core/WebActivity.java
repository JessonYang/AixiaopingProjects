package com.weslide.lovesmallscreen.core;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.views.AXPWebView;

import net.aixiaoping.library.R;

/**
 * Created by xu on 2016/6/2.
 * 浏览器
 */
public class WebActivity extends BaseActivity {

    Toolbar mToolbar;

    AXPWebView mAxpWebView;
    WebView mWebView;
    SpinKitView mSpinKitView;
    RelativeLayout rl;
    public static final String KEY_LOAD_URL = "load_url";
    public static final String KEY_TITLE = "title";

    /** 页面正在加载的URL */
    String loadUrl = "";
    /** 网页标题 */
    String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        initView();
        initData();
        initListener();
        mWebView.addJavascriptInterface(new InJavaScriptGetBody(),
                "android");
        mWebView.loadUrl(loadUrl);
    }

    private void initListener(){
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                if(getIntent().getExtras() == null || StringUtils.isEmpty(getIntent().getExtras().getString(KEY_TITLE, null))){
                    mToolbar.setTitle(title);
                }

            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mSpinKitView.setVisibility(View.VISIBLE);

            }
        });

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSpinKitView.setVisibility(View.GONE);
                if(url.contains("app/trade/paysuc")==true){
                    mWebView.loadUrl("http://www.518wtk.com/index.php/Index/pay.html?link="+url);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
    }

    private void initView(){
        rl = (RelativeLayout) findViewById(R.id.rl);
        rl.setVisibility(View.GONE);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mAxpWebView = (AXPWebView) findViewById(R.id.axpwv_webview);
        mSpinKitView = (SpinKitView) findViewById(R.id.spin_kit);
        mWebView = mAxpWebView.getWebView();
    }

    private void initData(){
        if(getIntent().getExtras() != null){
            loadUrl = getIntent().getExtras().getString(KEY_LOAD_URL);
            title = getIntent().getExtras().getString(KEY_TITLE, "网页");
        }
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    /**
     * 供网页js调用的方法
     */
    class InJavaScriptGetBody {

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void back() {
           handler.sendEmptyMessage(0x121);
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x121) {
                WebActivity.this.finish();
            }
        }
    };
}
