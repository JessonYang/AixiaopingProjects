package com.weslide.lovesmallscreen.activitys;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.webkit.WebView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

/**
 * Created by YY on 2017/4/1.
 */
public class OriginalCityAgencyHelpActivity extends BaseActivity {
    private CustomToolbar toolBar;
    private WebView webView;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_city_agency_help);
        initView();
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("抱歉!该功能暂未开放")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
        /*url = getIntent().getExtras().getString("url");
        WebSettings ws = webView.getSettings();
        //允许javascript执行
        ws.setJavaScriptEnabled(true);
        //加载一个服务端网页
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //开始加载网页时回调
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //网页加载结束时回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            //获取网页标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

        });*/
    }

    /*@Override
    public void onBackPressed() {
        //如果网页可以后退，则网页后退
        if (webView.canGoBack()) {
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }*/

    private void initView() {
        toolBar = ((CustomToolbar) findViewById(R.id.help_toolbar));
//        webView = ((WebView) findViewById(R.id.city_agency_help_web));
        toolBar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
    }
}
