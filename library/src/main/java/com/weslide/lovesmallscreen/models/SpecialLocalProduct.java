package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by xu on 2016/7/19.
 * 特产速递
 */
public class SpecialLocalProduct extends BaseModel {

//    headBanners	头部轮播图	[{headBanner},{headBanner}]


    @SerializedName("headBanners")
    /** 头部轮播图	[{headBanner},{headBanner}] */
    private List<ImageText> headBanners;


    public List<ImageText> getHeadBanners() {
        return headBanners;
    }

    public void setHeadBanners(List<ImageText> headBanners) {
        this.headBanners = headBanners;
    }
}
