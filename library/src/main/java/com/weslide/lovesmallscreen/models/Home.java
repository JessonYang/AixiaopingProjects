package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by xu on 2016/6/3.
 * 首页使用的数据
 */
public class Home extends BaseModel {

    @SerializedName("headBanners")
    private
    /** 头部轮播图 */
    List<ImageText> headBanners;
    @SerializedName("goodsClassifys")
    private
    /** 商品分类 */
    List<ImageText> goodsClassifys;
    @SerializedName("concentrationSellers")
    private
    /** 精选商家 */
    List<ImageText> concentrationSellers;
    @SerializedName("scoreGoodsClassifys")
    private
    /** 积分商品分类 */
    List<ImageText> scoreGoodsClassifys;
    @SerializedName("secondKill")
    private
    /** 限时秒杀 */
    List<SecondKill> secondKills;

    @SerializedName("costEfficients")
    private
    /** 爱划算 */
    List<ImageText> costEfficients;
    @SerializedName("saleExchanges")
    private
    /** 特卖汇 */
    List<ImageText> saleExchanges;
    @SerializedName("preferential99")
    private
    /** 99特惠图文 */
    ImageText preferential99;
    @SerializedName("specialLocalProduct")
    private
    /** 各地特产图文 */
    ImageText specialLocalProduct;
    @SerializedName("scoreMall")
    private
    /** 积分商城图文 */
    ImageText scoreMall;
    /** 直播 */
    @SerializedName("live")
    private List<Live> live;

    @SerializedName("taokeTitle")
    private TaoKe taokeTitle;

    @SerializedName("taokeType")
    private List<TaoKe> taokeType;

    @SerializedName("nineImage")
    private
    /** 各地特产图文 */
            ImageText nineImage;

    @SerializedName("twentyImage")
    private
    /** 各地特产图文 */
            ImageText twentyImage;

    @SerializedName("sqgwImage")
    private
    /** 各地特产图文 */
            ImageText sqgwImage;

    public List<Live> getLive() { return live; }

    public void setLive(List<Live> live) { this.live = live; }

    public List<ImageText> getHeadBanners() {
        return headBanners;
    }

    public void setHeadBanners(List<ImageText> headBanners) {
        this.headBanners = headBanners;
    }

    public List<ImageText> getGoodsClassifys() {
        return goodsClassifys;
    }

    public void setGoodsClassifys(List<ImageText> goodsClassifys) { this.goodsClassifys = goodsClassifys; }

    public List<ImageText> getConcentrationSellers() {
        return concentrationSellers;
    }

    public void setConcentrationSellers(List<ImageText> concentrationSellers) { this.concentrationSellers = concentrationSellers; }

    public List<ImageText> getScoreGoodsClassifys() {
        return scoreGoodsClassifys;
    }

    public void setScoreGoodsClassifys(List<ImageText> scoreGoodsClassifys) { this.scoreGoodsClassifys = scoreGoodsClassifys; }


    public List<SecondKill> getSecondKills() {
        return secondKills;
    }

    public void setSecondKills(List<SecondKill> secondKills) {
        this.secondKills = secondKills;
    }


    public List<ImageText> getCostEfficients() {
        return costEfficients;
    }

    public void setCostEfficients(List<ImageText> costEfficients) {
        this.costEfficients = costEfficients;
    }

    public List<ImageText> getSaleExchanges() {
        return saleExchanges;
    }

    public void setSaleExchanges(List<ImageText> saleExchanges) {
        this.saleExchanges = saleExchanges;
    }

    public ImageText getPreferential99() {
        return preferential99;
    }

    public void setPreferential99(ImageText preferential99) {
        this.preferential99 = preferential99;
    }

    public ImageText getSpecialLocalProduct() {
        return specialLocalProduct;
    }

    public void setSpecialLocalProduct(ImageText specialLocalProduct) {
        this.specialLocalProduct = specialLocalProduct;
    }

    public ImageText getScoreMall() {
        return scoreMall;
    }

    public void setScoreMall(ImageText scoreMall) {
        this.scoreMall = scoreMall;
    }

    public TaoKe getTaokeTitle() { return taokeTitle; }

    public void setTaokeTitle(TaoKe taokeTitle) { this.taokeTitle = taokeTitle; }

    public List<TaoKe> getTaokeType() { return taokeType; }

    public void setTaokeType(List<TaoKe> taokeType) { this.taokeType = taokeType; }

    public ImageText getNineImage() { return nineImage; }

    public void setNineImage(ImageText nineImage) { this.nineImage = nineImage; }

    public ImageText getTwentyImage() { return twentyImage; }

    public void setTwentyImage(ImageText twentyImage) { this.twentyImage = twentyImage; }

    public ImageText getSqgwImage() { return sqgwImage; }

    public void setSqgwImage(ImageText sqgwImage) { this.sqgwImage = sqgwImage; }
}
