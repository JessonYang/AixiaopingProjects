package com.weslide.lovesmallscreen.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Live;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/10/26.
 */
public class LiveDetails extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_live_name)
    TextView tvLiveName;
    @BindView(R.id.wb_live)
    WebView wbLive;
    @BindView(R.id.iv_live_img)
    ImageView ivLiveImg;
    DataList<Live> lives = new DataList<>();
    String liveId;
    @BindView(R.id.tv_intrduce)
    TextView tvIntrduce;
    String sellerId;
    String URL;
    @BindView(R.id.btn_seller)
    Button btnSeller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            liveId = bundle.getString("liveId");
        }
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveDetails.this.finish();
            }
        });
        Request<Live> requst = new Request<>();
        Live live = new Live();
        live.setLiveid(liveId);
        requst.setData(live);
        RXUtils.request(LiveDetails.this, requst, "getLiveById", new SupportSubscriber<Response<DataList<Live>>>() {

            @Override
            public void onNext(Response<DataList<Live>> liveDataList) {
                setData(liveDataList.getData().getDataList().get(0));
            }
        });

    }

    private void setData(Live live) {
        sellerId = live.getSellerId();
        if(StringUtils.isBlank(sellerId)){
            btnSeller.setVisibility(View.GONE);
        }else{
            btnSeller.setVisibility(View.VISIBLE);
        }
        tvName.setText(live.getName());
        tvLiveName.setText(live.getLivename());
        WebSettings ws = wbLive.getSettings();
        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        // ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);// 排版适应屏幕

        ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。

        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setGeolocationEnabled(true);// 启用地理定位
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
        ws.setDomStorageEnabled(true);
        ws.setSupportMultipleWindows(true);// 新加
        wbLive.getSettings().setJavaScriptEnabled(true);
        wbLive.getSettings().setBlockNetworkImage(false);
        wbLive.setVisibility(View.VISIBLE);
        wbLive.loadUrl(live.getUri());
        URL = live.getUri();
        tvIntrduce.setText(live.getViewname());
        Glide.with(LiveDetails.this).load(live.getSellerimgae()).into(ivLiveImg);
        Glide.with(LiveDetails.this).load(live.getLogo()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(ivLogo) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(LiveDetails.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                ivLogo.setImageDrawable(circularBitmapDrawable);

            }
        });
    }

    @OnClick({R.id.btn_seller, R.id.iv_become})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_seller:
                AppUtils.toSeller(LiveDetails.this, sellerId);
                break;
            case R.id.iv_become:
                Intent intent = new Intent(this, LiveMaxActivity.class);
                intent.putExtra("live", URL);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wbLive.destroy();
    }
}
