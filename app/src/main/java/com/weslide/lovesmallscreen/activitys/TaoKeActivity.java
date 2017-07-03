package com.weslide.lovesmallscreen.activitys;

import android.content.pm.PackageInfo;
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

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.github.ybq.android.spinkit.SpinKitView;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.views.AXPWebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dong on 2017/1/13.
 */
public class TaoKeActivity extends BaseActivity {

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

    /**
     * 页面正在加载的URL
     */
    String loadUrl = "";
    /**
     * 网页标题
     */
    String title;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x121) {
                mToolbar.setVisibility(View.VISIBLE);
            } else if (msg.what == 0x122) {
                mToolbar.setVisibility(View.GONE);
            } else if (msg.what == 0x123) {
                ShareUtils.share(TaoKeActivity.this, titiles,
                        image,
                        targetUrl,
                        content);
            } else if (msg.what == 0x124) {
              /*  Intent intent = new Intent(TaoKeActivity.this,WebActivity.class);
                intent.putExtra("load_url",URL);
                startActivity(intent);*/
                toTaobao(URL);
            }
        }
    };
    private String search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(net.aixiaoping.library.R.layout.activity_web);

        initView();
        initData();
        initListener();
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.addJavascriptInterface(new InJavaScriptGetBody(),
                "android");
        if (search != null && search.length() > 0) {
            String parmars = "search=" + search + "&state=app" + "&userId = " + ContextParameter.getUserInfo().getUserId();
            mWebView.postUrl(loadUrl, parmars.getBytes());
        } else {
            mWebView.loadUrl(loadUrl + "&userId = " + ContextParameter.getUserInfo().getUserId());
        }
        Log.d("雨落无痕丶", "onCreate: " + loadUrl);
    }

    private void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                if (getIntent().getExtras() == null || StringUtils.isEmpty(getIntent().getExtras().getString(KEY_TITLE, null))) {
                    mToolbar.setTitle(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mSpinKitView.setVisibility(View.VISIBLE);

            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
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

    private void initView() {
        rl = (RelativeLayout) findViewById(net.aixiaoping.library.R.id.rl);
        mToolbar = (Toolbar) findViewById(net.aixiaoping.library.R.id.tool_bar);
        //   mToolbar.setVisibility(View.GONE);
        //setSupportActionBar(mToolbar);
        mAxpWebView = (AXPWebView) findViewById(net.aixiaoping.library.R.id.axpwv_webview);
        mSpinKitView = (SpinKitView) findViewById(net.aixiaoping.library.R.id.spin_kit);
        mWebView = mAxpWebView.getWebView();
    }

    private void initData() {
        if (getIntent().getExtras() != null) {
            loadUrl = getIntent().getExtras().getString(KEY_LOAD_URL);
            title = getIntent().getExtras().getString(KEY_TITLE, "网页");
            search = getIntent().getExtras().getString("search", "");
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        mWebView = null;
        AlibcTradeSDK.destory();
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
            if (s.equals("back")) {
                handler.sendEmptyMessage(0x121);

            } else if (s.equals("noback")) {
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
        public void openUrl(String url) {
            URL = url;
            handler.sendEmptyMessage(0x124);
        }

        @JavascriptInterface
        public void LINK_TB(String url) {
            URL = url;
            handler.sendEmptyMessage(0x124);
        }

        @JavascriptInterface
        public void backIndex() {
            finish();
        }
    }

    public void getMsg() {
        Log.d("雨落无痕丶", "getMsg:yy ");
    }

    private void toTaobao(String url) {
        Log.d("雨落无痕丶", "toTaobao: tt");
        //提供给三方传递配置参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        AlibcBasePage alibcBasePage = new AlibcPage(url);
        //设置页面打开方式
        AlibcShowParams showParams = null;
        if (isPkgInstalled("com.taobao.taobao") == false) {
            showParams = new AlibcShowParams(OpenType.Auto, false);
        } else {
            showParams = new AlibcShowParams(OpenType.Native, false);
        }
        //使用百川sdk提供默认的Activity打开detail
        AlibcTrade.show(TaoKeActivity.this, alibcBasePage, showParams, null, exParams,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                    }
                });
    }

    private boolean isPkgInstalled(String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = TaoKeActivity.this.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
