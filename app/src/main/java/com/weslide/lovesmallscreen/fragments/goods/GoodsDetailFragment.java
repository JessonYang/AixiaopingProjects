package com.weslide.lovesmallscreen.fragments.goods;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.bean.NewGoodDetailModel;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.ScreenUtils;
import com.weslide.lovesmallscreen.views.custom.GoodDtScrollView;
import com.weslide.lovesmallscreen.views.custom.MyWebview;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/7.
 * 商品详情
 */
public class GoodsDetailFragment extends BaseFragment {

    View mView;
//    @BindView(R.id.wv_web)
//    AXPWebView wvWeb;

    @BindView(R.id.wv_web)
    MyWebview wb;
    @BindView(R.id.good_detail_containner)
    LinearLayout goodContainner;
    @BindView(R.id.good_dt_scorll)
    GoodDtScrollView goodDtScroll;
    private boolean isload = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_goods_detail, container, false);

        ButterKnife.bind(this, mView);
        return mView;
    }

    public void setUrl(String url) {
        goodDtScroll.setVisibility(View.GONE);
        wb.setVisibility(View.VISIBLE);
        if(!isload){
            isload = true;

            new Handler().postAtTime(new Runnable() {
                @Override
                public void run() {
//                    wvWeb.getWebView().getSettings().setUseWideViewPort(true);
//                    wvWeb.getWebView().getSettings().setLoadWithOverviewMode(true);
                    /*int screenDensity = getResources().getDisplayMetrics().densityDpi ;
                    WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM ;
                    switch (screenDensity){
                        case DisplayMetrics.DENSITY_LOW :
                            zoomDensity = WebSettings.ZoomDensity.CLOSE;
                            break;
                        case DisplayMetrics.DENSITY_MEDIUM:
                            zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                            break;
                        case DisplayMetrics.DENSITY_HIGH:
                            zoomDensity = WebSettings.ZoomDensity.FAR;
                            break ;
                    }
                    wvWeb.getWebView().getSettings().setDefaultZoom(zoomDensity);*/
//                    wvWeb.getWebView().loadUrl(url);

                    wb.getSettings().setJavaScriptEnabled(true);

                    // 设置可以支持缩放
                    wb.getSettings().setSupportZoom(true);
                    // 设置出现缩放工具
                    wb.getSettings().setBuiltInZoomControls(true);
                    //设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
                    wb.getSettings().setUseWideViewPort(true);
                    //设置默认加载的可视范围是大视野范围
                    wb.getSettings().setLoadWithOverviewMode(true);
                    wb.getSettings().setDomStorageEnabled(true);

                    //隐藏缩放按钮
                    wb.getSettings().setDisplayZoomControls(false);

                    wb.setHorizontalScrollBarEnabled(false);//水平不显示
                    wb.setVerticalScrollBarEnabled(false); //垂直不显示

                    //适应屏幕
                    wb.getSettings().setUseWideViewPort(true);
                    wb.getSettings().setLoadWithOverviewMode(true);
                    wb.setWebViewClient(new MyWebviewClient());
                    wb.loadUrl(url);
//                    Log.d("雨落无痕丶", "run:gd "+url);
                }
            },800);
        }
    }

    private class MyWebviewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //  html加载完成之后，调用js的方法
            imgReset();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        private void imgReset() {
            wb.loadUrl("javascript:(function(){"
                    + "var objs = document.getElementsByTagName('img'); "
                    + "for(var i=0;i<objs.length;i++)  " + "{"
                    + "var img = objs[i];   "
                    + "    img.style.width = '100%';   "
                    + "    img.style.height = 'auto';   "
                    + "}" + "})()");
        }
    }

    public void setDetailGood(List<NewGoodDetailModel> list){
        wb.setVisibility(View.GONE);
        goodDtScroll.setVisibility(View.VISIBLE);
        goodContainner.removeAllViews();
        if (list != null && list.size() > 0) {
            for (NewGoodDetailModel model : list) {
                String detailType = model.getDetailType();
                if (detailType.equals("text")) {
                    TextView textView = new TextView(getActivity());
                    textView.setTextColor(Color.parseColor("#000000"));
                    textView.setTextSize(DensityUtils.dp2px(getActivity(),5));
                    textView.setText(model.getText());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.topMargin = DensityUtils.dp2px(getActivity(),12);
                    goodContainner.addView(textView,params);
                } else if (detailType.equals("picture")) {
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    String picture = model.getPicture();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (picture != null && picture.length() > 0) {
                                Bitmap bitmap = returnBitMap(picture);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int bHeight = bitmap.getHeight();
                                        int bWidth = bitmap.getWidth();
                                        int screenWidth = ScreenUtils.getScreenWidth(getActivity());
                                        int height = screenWidth * bHeight / bWidth;
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        params.height = height;
                                        params.topMargin = DensityUtils.dp2px(getActivity(),12);
                                        imageView.setLayoutParams(params);
                                        Glide.with(getActivity()).load(picture).into(imageView);
                                        goodContainner.addView(imageView,params);
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        }
    }

    public Bitmap returnBitMap(final String url) {

        Bitmap bitmap = null;
        URL imageurl = null;
        try {
            imageurl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = null;
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
