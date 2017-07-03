package com.weslide.lovesmallscreen.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import com.baidu.mapapi.utils.CoordinateConverter;
import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.managers.LocationManager;
import com.weslide.lovesmallscreen.map.SensorHelper;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地图使用Demo
 */
public class MapDemoActivity extends AppCompatActivity {

    @BindView(R.id.mv_baidumap)
    MapView mMapView;
    BaiduMap mBaidumap;
    @BindView(R.id.tv_locatiion)
    TextView mLocationView;

    LocationClient mLocationClient;
    MapLocationListenner mLocationListenner;
    BDLocation mLocation;
    int lastSensorValue = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_mao);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mBaidumap = mMapView.getMap();

        // 开启定位图层
        mBaidumap.setMyLocationEnabled(true);
        //设置定位图层的显示
        mBaidumap.setMyLocationConfigeration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null));

        //开始定位当前位置
        mLocationClient = new LocationClient(ArchitectureAppliation.getAppliation());
        mLocationListenner = new MapLocationListenner();
        LocationClientOption option = LocationManager.DEFAULT_OPTION;
        option.setScanSpan(1000);
        option.setCoorType("gcj02");
        mLocationClient.registerLocationListener(mLocationListenner);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

        sensorHandler.sendEmptyMessageDelayed(1, 500);

    }

    /**
     * 启动百度地图路线规划
     *
     * @param start
     * @param end
     */
    public void startBaidumapLine(LatLng start, LatLng end) {
        try {
            //移动APP调起Android百度地图方式举例
            CoordinateConverter converter = new CoordinateConverter();
            converter.from(CoordinateConverter.CoordType.COMMON);
            converter.coord(start);
            start = converter.convert();  //将gcj02坐标转换为百度地图bd09ll坐标

            converter.coord(end);
            end = converter.convert();    //将gcj02坐标转换为百度地图bd09ll坐标

            Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:" + start.latitude + "," + start.longitude + "|" +
                    "name:我家&destination=latlng:" + end.latitude + "," + end.longitude +
                    "|name:终点&mode=driving&region=&src=yourCompanyName|yourAppName#Intent;scheme=b" +
                    "dapp;package=com.baidu.BaiduMap;end");
            startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * 启动本地高德地图路线规划
     *
     * @param start
     * @param end
     */
    public void startAMapLine(LatLng start, LatLng end) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://route?sourceApplication=softname&slat="
                        + start.latitude + "&slon=" + start.longitude + "&sname=起点&dlat="
                        + end.latitude + "&dlon=" + end.longitude + "&dname=终点&dev=0&m=0&t=1"));
        intent.setPackage("com.autonavi.minimap");
        startActivity(intent);

    }

    @OnClick({R.id.tv_start_amap, R.id.tv_start_baidumap})
    public void navClick(View view) {
        LatLng start = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        LatLng end = new LatLng(23.131379,113.267591);
        switch (view.getId()){
            case R.id.tv_start_amap:
                startAMapLine(start, end);
                break;
            case R.id.tv_start_baidumap:
                startBaidumapLine(start, end);
                break;
        }

    }

    boolean isFirstLoc = true;

    /**
     * 定位SDK监听函数
     */
    private class MapLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            mLocation = location;

            mLocationView.setText("纬度：" + location.getLatitude() + ", 经度：" + location.getLongitude());
            // 将google地图、soso地图、aliyun地图、mapabc地图和amap地图// 所用坐标转换成百度坐标
            LatLng sourceLatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            CoordinateConverter converter = new CoordinateConverter();
            converter.from(CoordinateConverter.CoordType.COMMON);
            // sourceLatLng待转换坐标
            converter.coord(sourceLatLng);
            LatLng desLatLng = converter.convert();

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(SensorHelper.getInstance().getAccuracy())
                    .latitude(desLatLng.latitude)
                    .longitude(desLatLng.longitude).build();
            mBaidumap.setMyLocationData(locData);

            //判断是否是第一次定位，如果是，便将地图界面转到当前位置
            if (isFirstLoc) {
                isFirstLoc = false;
                MapStatus.Builder builder = new MapStatus.Builder();
                //设置缩放
                builder.target(desLatLng).zoom(20);
                MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(builder.build());

                mBaidumap.animateMapStatus(u);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mLocationClient.unRegisterLocationListener(mLocationListenner);
        mLocationClient.stop();
        mBaidumap.clear();
        mMapView.onDestroy();
    }

    // 每隔200毫秒更新一次指南针
    Handler sensorHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (mLocation == null) {
                sensorHandler.sendEmptyMessageDelayed(1, 200);
                return false;
            }

            int sensorValue = SensorHelper.getInstance().getAccuracy();
            if (lastSensorValue == -1) {
                mLocation.setDirection(sensorValue);
                mLocationListenner.onReceiveLocation(mLocation);
                lastSensorValue = sensorValue;
            } else {
                if ((lastSensorValue - sensorValue) >= 5 || (lastSensorValue - sensorValue) <= -5) {
                    mLocation.setDirection(sensorValue);
                    mLocationListenner.onReceiveLocation(mLocation);
                    lastSensorValue = sensorValue;
                }
            }

            sensorHandler.removeMessages(1);
            sensorHandler.sendEmptyMessageDelayed(1, 200);
            return false;
        }
    });
}
