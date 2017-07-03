package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by xu on 2016/7/19.
 * 99特惠
 */
public class Preferential99 extends BaseModel {

//    headBanners	头部轮播图	[{headBanner},{headBanner}]
//    freeGoods	免单商品	[{goods},{goods}]
//    becomeVipBanners	成为会员横幅广告	[{becomeVipBanner }]

    @SerializedName("headBanners")
    /** 头部轮播图 */
    private List<ImageText> headBanners;
    @SerializedName("freeGoods")
    /** 免单商品	[{goods},{goods}] */
    private List<Goods> freeGoods;
    @SerializedName("becomeVipBanner")
    /** 成为会员横幅广告	[{becomeVipBanner }] */
    private List<ImageText> becomeVipBanners;


    public List<ImageText> getHeadBanners() {
        return headBanners;
    }

    public void setHeadBanners(List<ImageText> headBanners) {
        this.headBanners = headBanners;
    }

    public List<Goods> getFreeGoods() {
        return freeGoods;
    }

    public void setFreeGoods(List<Goods> freeGoods) {
        this.freeGoods = freeGoods;
    }

    public List<ImageText> getBecomeVipBanners() {
        return becomeVipBanners;
    }

    public void setBecomeVipBanners(List<ImageText> becomeVipBanners) {
        this.becomeVipBanners = becomeVipBanners;
    }
}
