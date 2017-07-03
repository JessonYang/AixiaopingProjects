package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2016/5/24.
 * 滑屏广告
 */
public class GlideAdvert extends BaseModel {

    @SerializedName("tradingAreaId")
    private String tradingAreaId;
    @SerializedName("pool1")
    private String pool1;
    @SerializedName("pool2")
    private String pool2;
    @SerializedName("pool3")
    private String pool3;
    @SerializedName("pool4")
    private String pool4;

    @SerializedName("dataList")
    private ArrayList<AdvertImg> advertImgs;


    /** 商圈ID */
    public String getTradingAreaId() {
        return tradingAreaId;
    }

    public void setTradingAreaId(String tradingAreaId) {
        this.tradingAreaId = tradingAreaId;
    }

    /** 广告池1 */
    public String getPool1() {
        return pool1;
    }

    public void setPool1(String pool1) {
        this.pool1 = pool1;
    }

    /** 广告池2 */
    public String getPool2() {
        return pool2;
    }

    public void setPool2(String pool2) {
        this.pool2 = pool2;
    }

    /** 广告池3 */
    public String getPool3() {
        return pool3;
    }

    public void setPool3(String pool3) {
        this.pool3 = pool3;
    }

    /** 广告池4 */
    public String getPool4() {
        return pool4;
    }

    public void setPool4(String pool4) {
        this.pool4 = pool4;
    }

    /** 广告图片列表 */
    public ArrayList<AdvertImg> getAdvertImgs() {
        return advertImgs;
    }

    public void setAdvertImgs(ArrayList<AdvertImg> advertImgs) {
        this.advertImgs = advertImgs;
    }
}


