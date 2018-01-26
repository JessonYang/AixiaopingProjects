package com.weslide.lovesmallscreen.network;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

/**
 * Created by xu on 2016/5/24.
 * 请求时的基本参数
 */
public class Request<T> extends BaseModel {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat formatMD5 = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

    public Request() {
        userId = ContextParameter.getUserInfo().getUserId();
        if (userId == null) {
            userId = "";
        }
        appVersion = ContextParameter.getAppVersion();
        dip = ContextParameter.getDip();
        channelId = ContextParameter.getChannelId();
//        channelId = "-1";
        if (channelId == null) {
            channelId = "axp-2017-personl-zhanghongkun";
        }
        zoneId = ContextParameter.getCurrentZone().getZoneId();
        lng = ContextParameter.getCurrentLocation().getLongitude();
        lat = ContextParameter.getCurrentLocation().getLatitude();
        machineCode = ContextParameter.getMachineCode();
        if (zoneId == null) {
            zoneId = "";
        }
        String axpabc = md5("axp"+userId);
        String s = md5(axpabc + formatMD5.format(System.currentTimeMillis() + ContextParameter.getTimeExtra()));
        axp =s;
    }

    /**
     * 用户ID
     */
    @SerializedName("userId")
    private String userId;

    @SerializedName("times")
    private String times = format.format(System.currentTimeMillis());

    @SerializedName("app")
    private String app = "USERS";

    @SerializedName("os")
    /** 系统 */
    private String os = "ANDROID";
    @SerializedName("appVersion")
    /** APP版本 */
    private String appVersion;

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    @SerializedName("dip")
    /** 分辨率dip */
    private String dip;
    @SerializedName("channelId")
    /** 个推cid */
    private String channelId;
    @SerializedName("zoneId")
    /** 城市id */
    private String zoneId;
    @SerializedName("lng")
    /** 经度 */
    private String lng;
    @SerializedName("lat")
    /** 纬度 */
    private String lat;

    @SerializedName("machineCode")
    /** 机器码 */
    private String machineCode;

    public String getAxp() {
        return axp;
    }

    public void setAxp(String axp) {
        this.axp = axp;
    }

    @SerializedName("axp")
    /** MD5加密参数 */
    private String axp;

    @SerializedName("data")
    /** 主数据 */
    private T data;


    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDip() {
        return dip;
    }

    public void setDip(String dip) {
        this.dip = dip;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }


    public T getData() {
        return data;
    }

    public Request<T> setData(T data) {
        this.data = data;
        return this;
    }


    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
