package com.weslide.lovesmallscreen.view_yy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.managers.LocationManager;
import com.weslide.lovesmallscreen.models.Location;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Dong on 2017/3/1.
 */
public class InputAddressActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    /**
     * MapView 是地图主控件
     */
    @BindView(R.id.bmapView)
    MapView mMapView;
    @BindView(R.id.tv_address)
    EditText tvAddress;
    @BindView(R.id.tv_address_details)
    EditText tvAddressDetails;
    private BaiduMap mBaiduMap;
    private UiSettings mUiSettings;
    private Location currentLocation;
    Location myLocation;

    Marker hongdianMarker;

    boolean isFirstLoc = true; // 是否首次定位

    // 将google地图、soso地图、aliyun地图、mapabc地图和amap地图// 所用坐标转换成百度坐标
    CoordinateConverter converter = new CoordinateConverter();

    BitmapDescriptor hongdianBitmap = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_weizi);
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_address);
        ButterKnife.bind(this);
        intent = getIntent();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvAddress.getText().toString() != null && tvAddressDetails.getText().toString() != null) {
                    intent.putExtra("address", tvAddress.getText().toString()+tvAddressDetails.getText().toString());
                }else {
                    Toast.makeText(InputAddressActivity.this, "请完善地址信息!", Toast.LENGTH_SHORT).show();
                }
                setResult(4, intent);
                finish();
            }
        });

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                tvAddress.setText(mapPoi.getName());
                return false;
            }
        });
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

    private void loadLocation() {
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

                        MarkerOptions option = new MarkerOptions().position(ll).icon(hongdianBitmap).zIndex(9).draggable(true);
                        option.animateType(MarkerOptions.MarkerAnimateType.grow);

                        hongdianMarker = (Marker) mBaiduMap.addOverlay(option);

                        tvAddress.setText(location.getProvince() + location.getCity() + location.getDistrict());

                        tvAddressDetails.setText(location.getStreet());

                        intent.putExtra("address", location.getAddress());

                    }
                });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (tvAddress.getText().toString() != null && tvAddressDetails.getText().toString() != null) {
            intent.putExtra("address", tvAddress.getText().toString()+tvAddressDetails.getText().toString());
        }else {
            Toast.makeText(InputAddressActivity.this, "请完善地址信息!", Toast.LENGTH_SHORT).show();
        }
        setResult(4, intent);
        return super.onKeyDown(keyCode, event);
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

    @OnClick({R.id.rl_choise_address, R.id.btn_post})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_choise_address:
                Intent intent1 = new Intent(this, InputChioseAddressActivity.class);//选择地址
                startActivityForResult(intent1, 1);

                break;
            case R.id.btn_post:
                if (tvAddress.getText().toString() != null && tvAddressDetails.getText().toString() != null) {
                    intent.putExtra("address", tvAddress.getText().toString()+tvAddressDetails.getText().toString());
                }else {
                    Toast.makeText(InputAddressActivity.this, "请完善地址信息!", Toast.LENGTH_SHORT).show();
                }
                setResult(4, intent);
                InputAddressActivity.this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == 1) {
                myLocation = (Location) data.getSerializableExtra("Location");
                intent.putExtra("address", myLocation.getProvince() + myLocation.getCity() + myLocation.getDistrict() + myLocation.getStreet());
                tvAddress.setText(myLocation.getProvince() + myLocation.getCity() + myLocation.getDistrict());
                tvAddressDetails.setText(myLocation.getStreet());
            }
        }
    }
}
