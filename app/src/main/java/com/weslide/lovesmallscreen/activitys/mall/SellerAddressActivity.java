package com.weslide.lovesmallscreen.activitys.mall;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.managers.LocationManager;
import com.weslide.lovesmallscreen.models.Location;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xu on 2016/7/13.
 * 选择商家地址
 */
public class SellerAddressActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    /**
     * MapView 是地图主控件
     */
    @BindView(R.id.bmapView)
    MapView mMapView;
    @BindView(R.id.tv_address)
    EditText tvAddress;
    private BaiduMap mBaiduMap;
    private UiSettings mUiSettings;
    private Location currentLocation;

    Marker hongdianMarker;

    boolean isFirstLoc = true; // 是否首次定位

    // 将google地图、soso地图、aliyun地图、mapabc地图和amap地图// 所用坐标转换成百度坐标
    CoordinateConverter converter = new CoordinateConverter();

    BitmapDescriptor hongdianBitmap = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_weizi);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_address);
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBaiduMap = mMapView.getMap();
        mUiSettings = mBaiduMap.getUiSettings();

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);


        converter.from(CoordinateConverter.CoordType.COMMON);
        //手势
        mUiSettings.setZoomGesturesEnabled(true);

        // 开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);
//        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, false, hongdianBitmap));

        loadLocation();


    }

    private void loadLocation(){
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        Observable.just(null).observeOn(Schedulers.computation())
                .map(new Func1<Object, Location>() {
                    @Override
                    public Location call(Object o) {
                        Location location = LocationManager.syncGetLocation();
                        currentLocation = location;
                        return location;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Location>() {
                    @Override
                    public void onCompleted() {
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onNext(Location location) {

                        LatLng ll = new LatLng(Double.parseDouble(location.getLatitude()),
                                Double.parseDouble(location.getLongitude()));
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(ll).zoom(18.0f);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                        MarkerOptions option = new MarkerOptions().position(ll).icon(hongdianBitmap)
                                .zIndex(9).draggable(true);
                        option.animateType(MarkerOptions.MarkerAnimateType.grow);

                        hongdianMarker = (Marker) mBaiduMap.addOverlay(option);

                        tvAddress.setText(location.getAddress());
                    }
                });
    }



    public LatLng gcj022bd(LatLng old) {
        // sourceLatLng待转换坐标
        converter.coord(old);
        return converter.convert();
    }

    /**
     * 启动本地高德地图路线规划
     *
     * @param start
     * @param end
     */
    public void startAMapLine(LatLng start, LatLng end) {

        try {
            Intent intent = new Intent("android.intent.action.VIEW",
                    Uri.parse("androidamap://route?sourceApplication=softname&slat="
                            + start.latitude + "&slon=" + start.longitude + "&sname=起点&dlat="
                            + end.latitude + "&dlon=" + end.longitude + "&dname=终点&dev=0&m=0&t=1"));
            intent.setPackage("com.autonavi.minimap");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            T.showShort(this, "未安装高德地图");
        }


    }

    @OnClick(R.id.btn_post)
    public void onClick() {

        if (StringUtils.isEmpty(tvAddress.getText().toString())) {
            T.showShort(this, "请填写商家地址");
            return;
        }
        Map<String, String> values = new HashMap<String, String>();
        values.put("sellerId", ContextParameter.getUserInfo().getSellerId());
        values.put("sellerAddress", tvAddress.getText().toString());
        values.put("lat", currentLocation.getLatitude() + "");
        values.put("lng", currentLocation.getLongitude() + "");

        LoadingDialog loadingDialog = new LoadingDialog(this);

        RXUtils.request(this, new Request().setData(values), "postSellerAddress", new SupportSubscriber<Response>() {

            @Override
            public void onStart() {
                super.onStart();
                loadingDialog.show();
            }

            @Override
            public void onStop() {
                super.onStop();
                loadingDialog.dismiss();
            }

            @Override
            public void onNext(Response response) {
                T.showShort(SellerAddressActivity.this, "已更新到当前位置");
            }
        });

    }


    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();

        hongdianBitmap.recycle();

        super.onDestroy();
    }

    class Overlay {
        public Seller seller;
        public BitmapDescriptor bitmapDescriptor;
        public LatLng latLng;
        public Marker marker;
    }

}
