package com.weslide.lovesmallscreen.managers;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.models.Location;

import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.NetworkUtils;

import java.util.Date;

/**
 * Created by xu on 2016/4/26.
 * 实现应用中的定位功能
 */
public class LocationManager {

    private static final String TAG = "LocationManager";
    static boolean success = false; //同步定位是否已经完成
    static Location location = null; //同步定位后的返回数据
    public static LocationClientOption DEFAULT_OPTION;
    private static LocationClient mClient;
    private static LocationService service ;
    //两次定位间的最小间隔
    public static long INTERVAL_DATE = 2000;
    //上一次定位的时间，用于定位间隔，百度定位一秒以内多次定位无效
    private static long lastDate;

    static {
        DEFAULT_OPTION = new LocationClientOption();
        DEFAULT_OPTION.setLocationMode(
                LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
     /*   DEFAULT_OPTION.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        DEFAULT_OPTION.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        DEFAULT_OPTION.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        DEFAULT_OPTION.setOpenGps(true);//可选，默认false,设置是否使用gps
        DEFAULT_OPTION.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        DEFAULT_OPTION.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        DEFAULT_OPTION.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        DEFAULT_OPTION.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        DEFAULT_OPTION.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        DEFAULT_OPTION.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        DEFAULT_OPTION.setIsNeedAltitude(false);*/
        DEFAULT_OPTION.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        DEFAULT_OPTION.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        DEFAULT_OPTION.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        DEFAULT_OPTION.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
        DEFAULT_OPTION.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
        DEFAULT_OPTION.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        DEFAULT_OPTION.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        DEFAULT_OPTION.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        DEFAULT_OPTION.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        DEFAULT_OPTION.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        DEFAULT_OPTION.setOpenGps(true);
        DEFAULT_OPTION.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
    }

    /**
     * 初始化定位
     * @param context
     */
    public static void initialise(Context context){
        if(mClient != null){
            if(mClient.isStarted()){
                mClient.stop();
            }
        }
        mClient = new LocationClient(context);
        service = new LocationService(context);
    }

    private static boolean checkClient(){
        if(mClient == null){
            return  false;
        }

        return  true;
    }

    /**
     * 同步定位， 将会堵塞当前线程，请使用子线程执行
     *
     * 注意：因为使用的是静态的设计，所以不支持同时多次定位,会造成数据混乱
     * @return
     */
    public synchronized static Location syncGetLocation() {
        long runtime = 0;
        long currentDate = new Date().getTime();
        if(lastDate != 0 && (currentDate - lastDate) <= INTERVAL_DATE){
            return null;
        }

        lastDate = currentDate;

        if(!checkClient()) L.e("请将先调用LocationManager，initialise方法");

        success = false;

        //考虑到离线定位的问题，离线定位将返回上一次的位置
        if(!NetworkUtils.isConnected(ArchitectureAppliation.getAppliation())){
            //查询最后一条数据
            location = ArchitectureAppliation.getDaoSession().getLocationDao().loadLastRow();
            return location;
        }
        BaiduMapLocationListener listener = new BaiduMapLocationListener() {
            @Override
            public void onLocation(Location loc) {
                success = true;
                location = loc;
            }
        };
        mClient.registerLocationListener( listener );    //注册监听函数
        mClient.setLocOption(DEFAULT_OPTION);
        mClient.start();
     /*   service.start();
        service.registerListener(new LocationManager.BaiduMapLocationListener() {
            @Override
            public void onLocation(Location loc) {
                success = true;
                location = loc;
            }
        });*/
        while (!success) {
            try {
                Thread.sleep(200);
                runtime += 200;

                if(runtime >= 10000){  //10秒还未有返回，直接结束
                    mClient.unRegisterLocationListener(listener);
                    mClient.stop();
                    return null;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return  location;
    }

    /**
     * 开始定位
     * @param listener  定位完成的回调事件
     */
//    public static void startLocation(BaiduMapLocationListener listener ){
//        startLocation(listener, DEFAULT_OPTION);
//    }

    /**
     * 开始定位
     * @param listener  定位完成的回调事件
     * @param option 定位配置
     *
     *  因为未开网络的情况下进到应用，再次打开网络，就无法定位的问题，此方法暂时屏蔽
     *  syncGetLocation 代替
     */
//    public static void startLocation(BaiduMapLocationListener listener ,LocationClientOption option){
//
//        if(!checkClient()) L.e("请将先调用LocationManager，initialise方法");
//
//        //声明LocationClient类
//        listener.bindLocationClientOption(option);
//        mClient.registerLocationListener( listener );    //注册监听函数
//        mClient.setLocOption(option);
//        mClient.start();
//
//    }


    /**
     * 使用该定位回调事件的好处在于定位用于有数据返回，就算失败的情况下，还会降定位数据进行缓存
     */
    public abstract static class BaiduMapLocationListener implements BDLocationListener {
        LocationClientOption mOption;


        public void bindLocationClientOption(LocationClientOption option){
            mOption = option;
        }

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                //离线定位成功，离线定位结果也是有效的
                Log.e(TAG, "离线定位成功，离线定位结果也是有效的");
            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                //服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因
                Log.e(TAG, "服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                //网络不同导致定位失败，请检查网络是否通畅
                Log.e(TAG, "网络不同导致定位失败，请检查网络是否通畅");
            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                //无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机
                Log.e(TAG, "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }

            Location location = null;

            if(bdLocation == null || bdLocation.getLatitude() == 0 || bdLocation.getLongitude() == 0
                     || bdLocation.getCity() == null){
                Log.d("雨落无痕丶", "onReceiveLocation: 定位失败");
                //查询最后一条数据
//                 location = ArchitectureAppliation.getDaoSession().getLocationDao().loadLastRow();
            } else {
                location = new Location();
                location.setLatitude(bdLocation.getLatitude() + "");
                location.setProvince(bdLocation.getProvince());
                location.setLongitude(bdLocation.getLongitude() + "");
                location.setTime(bdLocation.getTime());
                location.setCountry(bdLocation.getCountry());
                location.setCountryCode(bdLocation.getCountryCode());
                location.setAddress(bdLocation.getAddrStr());
                location.setCity(bdLocation.getCity());
                location.setCityCode(bdLocation.getCityCode());
                location.setCoordinateType(bdLocation.getCoorType());
                location.setStreet(bdLocation.getStreet());
                location.setStreetNumber(bdLocation.getStreetNumber());
                location.setDistrict(bdLocation.getDistrict());

                ArchitectureAppliation.getDaoSession().getLocationDao().insert(location);
            }

            onLocation(location);

            //判断是否结束定位
            if(mOption != null  && mClient != null){
                if(mOption.getScanSpan() == 0){
                    mClient.stop();
                    mClient.unRegisterLocationListener(this);
                }
            } else {
                mClient.stop();
                mClient.unRegisterLocationListener(this);
            }

        }

        /**
         * 定位回调
         * @param location
         */
        public abstract void onLocation(Location location);
    }

}
