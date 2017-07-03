package com.weslide.lovesmallscreen.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tencent.bugly.crashreport.CrashReport;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.GuideActivity;
import com.weslide.lovesmallscreen.core.QRActivity;
import com.weslide.lovesmallscreen.managers.LocationManager;
import com.weslide.lovesmallscreen.models.Location;

import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textview1)
    TextView textView;
    @BindView(R.id.imageview1)
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Scan Barcode");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        textView.setText("张三");

        Glide.with(this)
                .load("http://img161.poco.cn/mypoco/myphoto/20100428/09/5331008020100428090852067.jpg")
                .bitmapTransform(new CropCircleTransformation(this))
                .into(imageView1);
    }

    @OnClick({R.id.btn_test_alipay, R.id.btn_test_dialog, R.id.btn_test_guide, R.id.btn_test_log, R.id.btn_test_network_dao_recycler,
            R.id.btn_test_push, R.id.btn_test_qr, R.id.btn_test_share, R.id.btn_test_weixinpay, R.id.btn_test_location, R.id.btn_test_map,
            R.id.btn_test_widget
    })
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_test_alipay:

                break;
            case R.id.btn_test_dialog:



                break;
            case R.id.btn_test_guide:
                AppUtils.toActivity(this, GuideActivity.class);
                break;
            case R.id.btn_test_log:
                CrashReport.testJavaCrash();
                break;
            case R.id.btn_test_network_dao_recycler:
                AppUtils.toActivity(this, DemoActivity.class);
                break;
            case R.id.btn_test_push:

                break;
            case R.id.btn_test_qr:
                new IntentIntegrator(MainActivity.this).setCaptureActivity(QRActivity.class).initiateScan();
                break;
            case R.id.btn_test_share:
//                ShareDialog dialog1 = ShareDialog.newInstance("分享测试",
//                        "http://pic40.nipic.com/20140404/6608733_213718309103_2.jpg",
//                        "https://www.baidu.com",
//                        "这是一个分享测试");
//                dialog1.show(getSupportFragmentManager(), "test");
                break;
            case R.id.btn_test_weixinpay:
                break;

            case R.id.btn_test_location:
                Observable.just(null)
                        .observeOn(Schedulers.computation())
                        .map(new Func1<Object, Location>() {  //开始定位
                            @Override
                            public Location call(Object aVoid) {
                                Location location = LocationManager.syncGetLocation();
                                return location;
                            }
                        })
                        .filter((Location location) -> location != null)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Location>() {    //输出定位数据
                            @Override
                            public void call(Location location) {
                                L.e(location.getAddress());
                                T.showShort(MainActivity.this, location.getAddress());
                            }
                        });
                break;
            case R.id.btn_test_map:
                AppUtils.toActivity(this, MapDemoActivity.class);
                break;

            case R.id.btn_test_widget:
                AppUtils.toActivity(this, WidgetActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
