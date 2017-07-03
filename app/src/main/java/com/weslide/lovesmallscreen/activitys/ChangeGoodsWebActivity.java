package com.weslide.lovesmallscreen.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.baidu.mapapi.model.LatLng;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.QRActivity;
import com.weslide.lovesmallscreen.core.UploadSubscriber;
import com.weslide.lovesmallscreen.models.ImageUp;
import com.weslide.lovesmallscreen.models.bean.UploadFileBean;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.MapUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.views.AXPWebView;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by Dong on 2017/1/13.
 */
public class ChangeGoodsWebActivity extends BaseActivity implements SensorEventListener {

    Toolbar mToolbar;

    AXPWebView mAxpWebView;
    WebView mWebView;
    SpinKitView mSpinKitView;
    RelativeLayout rl;
    public static final String KEY_LOAD_URL = "load_url";
    public static final String KEY_TITLE = "title";
    String str;
    String slng, slat, elng, elat;
    private long lastTime = 0;

    /**
     * 页面正在加载的URL
     */
    String loadUrl = "";
    /**
     * 网页标题
     */
    String title;
    MultiImageSelector selector = MultiImageSelector.create(ChangeGoodsWebActivity.this);
    private ArrayList<String> mSelectPath;
    private SoundPool soundPool;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x121) {
                //触发点击事件，获得相对与绝对路径
                selector.single();
                // selector.count(9);
                selector.start(ChangeGoodsWebActivity.this, 2);
            } else if (msg.what == 0x122) {
                ChangeGoodsWebActivity.this.finish();
            } else if (msg.what == 0x123) {
                //导航
                MapUtils.showNav(ChangeGoodsWebActivity.this, new LatLng(Double.parseDouble(elat), Double.parseDouble(elng)));
            }
        }
    };
    private SensorManager mSensorManager;
    private Sensor defaultSensor;
    private Vibrator vibrator;
    private int playId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(net.aixiaoping.library.R.layout.activity_web);

        initView();
        initData();
        initListener();
        mWebView.addJavascriptInterface(new InJavaScriptGetBody(),
                "android");
        mWebView.loadUrl(loadUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (defaultSensor != null) {
            mSensorManager.unregisterListener(this);
        }
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
        mToolbar.setVisibility(View.GONE);
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
        }
        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        vibrator = ((Vibrator) getSystemService(VIBRATOR_SERVICE));
        defaultSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        initSoundPool();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            mWebView.loadUrl("javascript:del()");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        /*long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastTime < 300) {
            return;
        }
        lastTime = currentTimeMillis;*/
        float[] values = sensorEvent.values;
        //分别获取手机在X、Y、Z轴上的加速度大小
        float valueX = Math.abs(values[0]);
        float valueY = Math.abs(values[1]);
        float valueZ = Math.abs(values[2]);
        //根据加速度计算摇动频率的算法
        double accelerameter = Math.sqrt(Math.pow(valueX, 2) + Math.pow(valueY, 2) + Math.pow(valueZ, 2));
        /*if (valueX > 19 || valueY > 19 || valueZ > 19) {
            if (vibrator != null) {
                vibrator.vibrate(300);
                mWebView.loadUrl("javascript:count()");
            }
        }*/
        if (accelerameter > 20) {
            if (vibrator != null) {
//                vibrator.vibrate(200);
                soundPool.play(playId,1,1,0,0,1);
                mWebView.loadUrl("javascript:count()");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * 供网页js调用的方法
     */
    class InJavaScriptGetBody {

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void backIndex() {

            handler.sendEmptyMessage(0x122);

        }

        @JavascriptInterface
        public void getMap(String json) {
            L.e("==========" + json);
            //获得经纬度
            try {
                JSONObject object = new JSONObject(json);
/*                slat = dataobj.getString("slat");
                slng = dataobj.getString("slng");*/
                elat = object.getString("elat");
                elng = object.getString("elng");
                L.e("==========" + slat);
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x123);
        }

        @JavascriptInterface
        public void uploadMess() {

            handler.sendEmptyMessage(0x121);

        }

        @JavascriptInterface
        public void scanCode() {
            new IntentIntegrator(ChangeGoodsWebActivity.this).setOrientationLocked(false).setCaptureActivity(QRActivity.class).initiateScan();
//            startActivityForResult(new Intent(ChangeGoodsWebActivity.this, CaptureActivity.class),0);
        }

        @JavascriptInterface
        public void jspCall(String phoneNum) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
            startActivity(intent);
        }

        @JavascriptInterface
        public void shake_open() {
            if (defaultSensor != null) {
                mSensorManager.registerListener(ChangeGoodsWebActivity.this, defaultSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }

        @JavascriptInterface
        public void shakeEnded() {
            if (defaultSensor != null) {
                mSensorManager.unregisterListener(ChangeGoodsWebActivity.this);
            }
        }
    }

    private void updateImg() {
        List<UploadFileBean> uploadFileBeen = new ArrayList<>();
        if (mSelectPath != null && mSelectPath.size() > 0) {

            for (int i = 0; i < mSelectPath.size(); i++) {
                UploadFileBean bean = new UploadFileBean();
                bean.setFile(new File(mSelectPath.get(i)));
                bean.setUserId(ContextParameter.getUserInfo().getUserId());
                bean.setFileUse(Constants.UPLOAD_FEED_BACK);
                uploadFileBeen.add(bean);
            }
        }
        RXUtils.uploadImages(ChangeGoodsWebActivity.this, uploadFileBeen, new UploadSubscriber() {

            LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(ChangeGoodsWebActivity.this);
                loadingDialog.show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }

            @Override
            public void onNext(List<Response<UploadFileBean>> responses) {
                //提交给网页
                Gson gson = new Gson();
                List<ImageUp> list = new ArrayList<>();
                for (int i = 0; i < responses.size(); i++) {
                    ImageUp imageUp = new ImageUp();
                    imageUp.setImage(responses.get(i).getData().getOppositeUrl());
                    list.add(imageUp);
                }
                str = gson.toJson(list);
                //  mWebView.loadUrl("javascript"+str);
                mWebView.loadUrl("javascript:getUplodInfo(" + str + ")");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == -1) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                updateImg();
                return;
            }
        }/*else if (requestCode == 0){
            mWebView.loadUrl("javascript:pingUid("+ContextParameter.getUserInfo().getUserId()+")");
            Toast.makeText(ChangeGoodsWebActivity.this, data.getExtras().getString("result"), Toast.LENGTH_SHORT).show();
        }*/
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            String result = scanResult.getContents();
            if (result != null && result.contains("ExchangeSign")) {
                mWebView.loadUrl("javascript:pingUid(" + ContextParameter.getUserInfo().getUserId() + ")");
            }
        }
    }

    private void initSoundPool() {
        soundPool = null;
        if (Build.VERSION.SDK_INT > 20) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //设置最大并发流数量
            builder.setMaxStreams(3);
            AudioAttributes.Builder builder1 = new AudioAttributes.Builder();
            builder1.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(builder1.build());
            soundPool = builder.build();
        } else {
            soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }
        //读取一个音频文件
        playId = soundPool.load(this, R.raw.yy, 1);
    }
}
