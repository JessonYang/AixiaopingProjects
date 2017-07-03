package com.weslide.lovesmallscreen.view_yy.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.views.AXPWebView;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

/**
 * Created by YY on 2017/6/27.
 */
public class ApplayPartnerBaseFragment extends Fragment {
    private View fgView;
    private AXPWebView axpWeb;
    private WebView mWebView;
    private  String loadUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_applay_partner_base, container, false);
        loadUrl = getArguments().getString("url");
        initView();
        initData();
        return fgView;
    }

    private void initData() {
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            private LoadingDialog loadingDialog;

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(getActivity());
                }
                loadingDialog.show();
            }
        });
        mWebView.loadUrl(loadUrl);
    }

    private void initView() {
        axpWeb = ((AXPWebView) fgView.findViewById(R.id.axp_web));
        mWebView = axpWeb.getWebView();
    }
}
