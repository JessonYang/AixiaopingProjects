package com.weslide.lovesmallscreen;


import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.weslide.lovesmallscreen.dao.serialize.ClientConfigSerialize;
import com.weslide.lovesmallscreen.dao.sp.LocalConfigSP;
import com.weslide.lovesmallscreen.dao.sp.UserInfoSP;
import com.weslide.lovesmallscreen.models.Location;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.models.Zone;
import com.weslide.lovesmallscreen.models.config.ClientConfig;
import com.weslide.lovesmallscreen.models.config.LocalConfig;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.DeviceUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.StringUtils;

import java.util.List;

/**
 * Created by xu on 2016/5/25.
 * App保存在内存中的主要参数
 */
public class ContextParameter {

    private static long timeExtra;

    public static long getTimeExtra() {
        return timeExtra;
    }

    public static void setTimeExtra(long timeExtra) {
        ContextParameter.timeExtra = timeExtra;
    }

    /**
     * 当前登录的用户信息
     */
    private static UserInfo userInfo;
    /**
     * 当前的定位地址
     */
    private static Location currentLocation;

    /**
     * 当前定位的城市标示
     */
    private static Zone currentZone;

    public static List<Zone> getHotCityList() {
        return hotCityList;
    }

    public static void setHotCityList(List<Zone> hotCityList) {
        ContextParameter.hotCityList = hotCityList;
    }

    /**
     * 热门城市

     */
    private static List<Zone> hotCityList;

    /**
     * 客户端运行配置
     */
    private static ClientConfig clientConfig;

    /**
     * 本地数据配置
     */
    private static LocalConfig localConfig;

    /**
     * 当前App版本
     */
    private static String appVersion;
    /**
     * 屏幕密度
     */
    private static String dip;
    /**
     * 个推生成的cids
     */
    private static String channelId;
    /**
     * 当前设备机器码
     */
    private static String machineCode;

    private static boolean athanasiaService = true;

    static {
        setUserInfo(UserInfoSP.getUserInfo());
        setLocalConfig(LocalConfigSP.getLocalConfig());

        setCurrentZone(new Zone());  //暂时使用默认值

        setAppVersion(AppUtils.getVersionName(ArchitectureAppliation.getAppliation()));
//        setAppVersion(AppUtils.getVersionCode(ArchitectureAppliation.getAppliation())+"");
//        Log.d("雨落无痕丶", "static versionName: " + AppUtils.getVersionName(ArchitectureAppliation.getAppliation()));
        setDip(DeviceUtils.getDip(ArchitectureAppliation.getAppliation()) + "");
        setMachineCode(DeviceUtils.getMachineCode(ArchitectureAppliation.getAppliation()));
    }

    /**
     * 判断用户是否已经登录
     *
     * @return
     */
    public static boolean isLogin() {
        if (StringUtils.isBlank(getUserInfo().getUserId())) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 计算当前定位与某点的位置并带上单位
     *
     * @param lat
     * @param lng
     * @return
     */
    public static String getDistanceForCurrentLocationAddUnit(String lat, String lng) {
        int location = getDistanceForCurrentLocation(lat, lng);
        L.e("test", location + "距离");
        String result = null;
        if (location < 1000) {
            result = location + "m";
        } else {
            double value = location / 1000;
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
            result = df.format(value);
            result = result + "km";
        }

        return result;
    }

    /**
     * 计算当前定位与某点的位置并带上单位
     *
     * @param seller
     * @return
     */
    public static String getDistanceForCurrentLocationAddUnit(Seller seller) {
        double location = getDistanceForCurrentLocation(seller.getLat(), seller.getLng());
        String result = null;
        if (location < 1000) {
            result = location + "m";
        } else {
            double value = location / 1000;
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
            result = df.format(value);
            result = result + "km";
        }

        return result;
    }

    /**
     * 计算当前定位与某点的位置
     *
     * @param lat
     * @param lng
     * @return
     */
    public static int getDistanceForCurrentLocation(String lat, String lng) {
        if (StringUtils.isEmpty(lat) || StringUtils.isEmpty(lng)) {
            return 0;
        }
        return getDistance(getCurrentLocation().getLatitude(), getCurrentLocation().getLongitude(), lat, lng);
    }

    /**
     * 计算两点间的位置 单位米
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static int getDistance(double lat1, double lng1, double lat2, double lng2) {

        LatLng start = new LatLng(lat1, lng1);
        LatLng end = new LatLng(lat2, lng2);

        return (int) DistanceUtil.getDistance(start, end);

//        double radLat1 = (lat1 * Math.PI / 180.0);
//        double radLat2 = (lat2 * Math.PI / 180.0);
//        double a = radLat1 - radLat2;
//        double b = (lng1 - lng2) * Math.PI / 180.0;
//        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
//                + Math.cos(radLat1) * Math.cos(radLat2)
//                * Math.pow(Math.sin(b / 2), 2)));
//        s = s * getEarthRadius();
//        return (int) s;
    }


    /**
     * 计算两点之间的位置
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static int getDistance(String lat1, String lng1, String lat2, String lng2) {
        return getDistance(Double.parseDouble(lat1), Double.parseDouble(lng1), Double.parseDouble(lat2), Double.parseDouble(lng2));
    }

    /**
     * 当前定位的城市标示
     */
    public static Zone getCurrentZone() {
        return currentZone;
    }

    public static void setCurrentZone(Zone currentZone) {
        ContextParameter.currentZone = currentZone;
    }


    /**
     * 客户端运行配置
     */
    public static ClientConfig getClientConfig() {

        if (clientConfig == null) {
            clientConfig = ClientConfigSerialize.getClientConfig(ArchitectureAppliation.getAppliation());
        }

        return clientConfig;
    }

    public static void setClientConfig(ClientConfig clientConfig) {
        ContextParameter.clientConfig = clientConfig;
        if (clientConfig != null) {
            ClientConfigSerialize.setClientConfig(ArchitectureAppliation.getAppliation(), clientConfig);
        }

    }

    /**
     * 本地数据配置
     */
    public static LocalConfig getLocalConfig() {
        if (localConfig == null) {
            localConfig = LocalConfigSP.getLocalConfig();
        }
        return localConfig;
    }

    public static void setLocalConfig(LocalConfig localConfig) {
        LocalConfigSP.setLocalConfig(localConfig);
        ContextParameter.localConfig = localConfig;
    }


    /**
     * 当前登录的用户信息
     */
    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        ContextParameter.userInfo = userInfo;
    }

    /**
     * 当前的定位地址
     */
    public static Location getCurrentLocation() {

        if (currentLocation == null) {
            currentLocation = getDefaultLocation();
        }

        return currentLocation;
    }

    public static void setCurrentLocation(Location currentLocation) {
        ContextParameter.currentLocation = currentLocation;
    }

    /**
     * 当前App版本
     */
    public static String getAppVersion() {
        return appVersion;
    }

    public static void setAppVersion(String appVersion) {
        ContextParameter.appVersion = appVersion;
    }

    /**
     * 屏幕密度
     */
    public static String getDip() {
        return dip;
    }

    public static void setDip(String dip) {
        ContextParameter.dip = dip;
    }

    /**
     * 个推生成的cids
     */
    public static String getChannelId() {
        return channelId;
    }

    public static void setChannelId(String channelId) {
        ContextParameter.channelId = channelId;
    }

    /**
     * 当前设备机器码
     */
    public static String getMachineCode() {
        return machineCode;
    }

    public static void setMachineCode(String machineCode) {
        ContextParameter.machineCode = machineCode;
    }


    public static Location getDefaultLocation() {
//        renderReverse&&renderReverse({"status":0,"result":{"location":{"lng":113.3718050377,"lat":23.12809498275},
//            "formatted_address":"广东省广州市天河区黄埔大道中60号","business":"员村,天河公园,棠下",
//                    "addressComponent":{"adcode":"440106","city":"广州市","country":"中国","direction":"附近",
//                    "distance":"2","district":"天河区","province":"广东省","street":"黄埔大道中","street_number":"60号",
//                    "country_code":0},"poiRegions":[],"sematic_description":"天河区政府东南467米","cityCode":257}})
        Location location = new Location();

        location.setAddress("广东省广州市天河区黄埔大道中60号");
        location.setCity("广州市");
        location.setCityCode("257");
        location.setDistrict("天河区");
        location.setLatitude("23.12809498275");
        location.setLongitude("113.371805037");
        location.setStreet("黄埔大道中");
        location.setStreetNumber("60号");
        location.setCountry("中国");
        location.setCountryCode("0");

        return location;
    }

    /**
     * 决定不死服务是否会被杀死
     */
    public static boolean isAthanasiaService() {
        return athanasiaService;
    }

    public static void setAthanasiaService(boolean athanasiaService) {
        ContextParameter.athanasiaService = athanasiaService;
    }
}
