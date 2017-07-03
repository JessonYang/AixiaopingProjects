package com.weslide.lovesmallscreen.activitys.mall;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.utils.T;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/13.
 * 周边商家
 */
public class AmbitusSellerActivity extends BaseActivity {

    public static final String KEY_SELLER_LIST = "KEY_SELLER_LIST";
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    /**
     * MapView 是地图主控件
     */
    @BindView(R.id.bmapView)
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private UiSettings mUiSettings;
    private BDLocation currentLocation;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();

    boolean isFirstLoc = true; // 是否首次定位

    ArrayList<Seller> sellerArrayList = new ArrayList<>();
    List<Overlay> overlayList = new ArrayList<>();
    BitmapDescriptor hongdianBitmap = BitmapDescriptorFactory
            .fromResource(R.drawable.hongdian);

    // 将google地图、soso地图、aliyun地图、mapabc地图和amap地图// 所用坐标转换成百度坐标
    CoordinateConverter converter = new CoordinateConverter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBaiduMap = mMapView.getMap();
        mUiSettings = mBaiduMap.getUiSettings();

        MapStatus ms = new MapStatus.Builder().build();
        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
        mBaiduMap.animateMapStatus(u, 1000);


        converter.from(CoordinateConverter.CoordType.COMMON);
        //手势
//        mUiSettings.setZoomGesturesEnabled(true);



        loadBundle();


        for (Seller seller : sellerArrayList) {
            BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_weizi);
            Overlay overlay = new Overlay();
            overlay.bitmapDescriptor = descriptor;
            overlay.seller = seller;

            LatLng latLng = new LatLng(Double.parseDouble(seller.getLat()), Double.parseDouble(seller.getLng()));

            overlay.latLng = latLng;

            overlayList.add(overlay);
        }

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, false, hongdianBitmap));

        initOverlay();

        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (int i = 0; i < overlayList.size(); i++) {
                    if (overlayList.get(i).marker == marker) {

                        showNav(overlayList.get(i));

                    }
                }

                return false;
            }
        });

    }

    public void showNav(Overlay overlay) {
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {

                fragment.dismiss();

                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

                switch (getSelectedIndex()) {
                    case 0:
                        startBaidumapLine(gcj022bd(latLng), gcj022bd(overlay.latLng));
                        break;
                    case 1:
                        startAMapLine(latLng, overlay.latLng);
                        break;
                }

                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder) builder).items(new String[]{"百度地图", "高德地图"}, 0)
                .title("请选择导航地图")
                .positiveAction("确定")
                .negativeAction("取消");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }


    /**
     * 启动百度地图路线规划
     *
     * @param start
     * @param end
     */
    public void startBaidumapLine(LatLng start, LatLng end) {

        // 构建 导航参数
//        NaviParaOption para = new NaviParaOption()
//                .startPoint(start).endPoint(end)
//                .startName("起点").endName("终点");
//
//        try {
//            BaiduMapNavigation.openBaiduMapNavi(para, this);
//        } catch (BaiduMapAppNotSupportNaviException e) {
//            e.printStackTrace();
//            showDialog();
//        }

        try {
            Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:" + start.latitude + "," + start.longitude + "|" +
                    "name:我家&destination=latlng:" + end.latitude + "," + end.longitude +
                    "|name:终点&mode=driving&region=&src=yourCompanyName|yourAppName#Intent;scheme=b" +
                    "dapp;package=com.baidu.BaiduMap;end");
            startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            showDialog();
        }

    }

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(AmbitusSellerActivity.this);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

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
                    android.net.Uri.parse("androidamap://route?sourceApplication=softname&slat="
                            + start.latitude + "&slon=" + start.longitude + "&sname=起点&dlat="
                            + end.latitude + "&dlon=" + end.longitude + "&dname=终点&dev=0&m=0&t=1"));
            intent.setPackage("com.autonavi.minimap");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            T.showShort(this, "未安装高德地图");
        }


    }

    public void loadBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sellerArrayList = (ArrayList<Seller>) bundle.getSerializable(KEY_SELLER_LIST);
        }
    }

    public void initOverlay() {
        for (int i = 0; i < overlayList.size(); i++) {
            MarkerOptions option = new MarkerOptions().position(overlayList.get(i).latLng).icon(overlayList.get(i).bitmapDescriptor)
                    .zIndex(9).draggable(true);
            option.animateType(MarkerAnimateType.grow);
            overlayList.get(i).marker = (Marker) mBaiduMap.addOverlay(option);
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }

            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
         //   currentLatLng = gcj022bd(currentLatLng);

            currentLocation = location;

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(currentLatLng.latitude)
                    .longitude(currentLatLng.longitude).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
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
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        super.onDestroy();
        // 回收 bitmap 资源
        for (int i = 0; i < overlayList.size(); i++) {
            overlayList.get(i).bitmapDescriptor.recycle();
        }
        hongdianBitmap.recycle();
    }

    class Overlay {
        public Seller seller;
        public BitmapDescriptor bitmapDescriptor;
        public LatLng latLng;
        public Marker marker;
    }

}
