package com.weslide.lovesmallscreen.core;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.models.config.ShareContent;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.AXPWebView;

import net.aixiaoping.library.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xu on 2016/6/2.
 * 浏览器
 */
public class SaveMoneyWebActivity extends BaseActivity {

    Toolbar mToolbar;

    AXPWebView mAxpWebView;
    WebView mWebView;
    SpinKitView mSpinKitView;
    RelativeLayout rl;
    String titiles = null;
    String content = null;
    String image = null;
    String targetUrl = null;
    public static final String KEY_LOAD_URL = "load_url";
    public static final String KEY_TITLE = "title";
    private String URL;

    /** 页面正在加载的URL */
    String loadUrl = "";
    /** 网页标题 */
    String title;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x121) {
                mToolbar.setVisibility(View.VISIBLE);
            }else if(msg.what == 0x122){
                mToolbar.setVisibility(View.GONE);
            }else if(msg.what == 0x123){
                ShareUtils.share(SaveMoneyWebActivity.this, titiles,
                        image,
                        targetUrl,
                        content);
            }else if (msg.what == 0x124){
                Intent intent = new Intent(SaveMoneyWebActivity.this,WebActivity.class);
                intent.putExtra("load_url",URL);
                startActivity(intent);
            }
        }
    };
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
                rl.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
        });
    }

    private void initView(){
        rl = (RelativeLayout) findViewById(R.id.rl);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
     //   mToolbar.setVisibility(View.GONE);
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
        public void showNav(String s) {
            if(s.equals("back")){
                handler.sendEmptyMessage(0x121);

            }else if(s.equals("noback")){
                handler.sendEmptyMessage(0x122);
            }
        }
        @JavascriptInterface
        public void showDailog(String json) {

            try {
                JSONObject object = new JSONObject(json);
                titiles = object.getString("title");
                content = object.getString("content");
                image = object.getString("image");
                targetUrl = object.getString("targetUrl");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            handler.sendEmptyMessage(0x123);
        }
        @JavascriptInterface
        public void openUrl(String url){
            URL = url;
            handler.sendEmptyMessage(0x124);
        }
    }

}
