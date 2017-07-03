package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by xu on 2016/6/4.
 * 商家
 */
public class Seller extends BaseModel {

    @SerializedName("sellerId")
    /** 店铺id */
    private String sellerId;
    @SerializedName("sellerName")
    /** 店铺名 */
    private String sellerName;
    @SerializedName("sellerIcon")
    /** 店铺图标 */
    private String sellerIcon;
    @SerializedName("sellerAddress")
    /**  店铺地址 */
    private String sellerAddress;
    @SerializedName("sellerPhone")
    /** 店铺电话 */
    private String sellerPhone;
    @SerializedName("images")
    /** 店铺介绍轮播图 */
    private List<ImageText> images;
    @SerializedName("videos")
    /** 店铺介绍视频 */
    private List<VideoText> videos;
    @SerializedName("shopHours")
    /** 营业时间 */
    private String shopHours;
    /** 商家介绍 */
    @SerializedName("sellerIntroduce")
    private String sellerIntroduce;
    @SerializedName("concern")
    /** 是否关注 */
    private Boolean concern;
    @SerializedName("lat")
    /** 店铺所在纬度 */
    private String lat;
    @SerializedName("lng")
    /** 店铺所在经度 */
    private String lng;
    @SerializedName("branchs")
    /** 分店，点击进去的页面可以以后再做 */
    private List<Seller> branchs;
    @SerializedName("banner")
    /** 横幅广告 */
    private List<ImageText> banner;
    @SerializedName("preferentials")
    /** 优惠策略 */
    private List<ImageText> preferentials;

    @SerializedName("recommendScoreGoodsList")
    /** 推荐积分商品 */
    private List<Goods> recommendScoreGoodsList;

    @SerializedName("recommendSellerGoodsList")
    /** 推荐商家商品列表 */
    private List<Goods> recommendSellerGoodsList;

    /**商品评分*/
    @SerializedName("sellerRating")
    private String sellerRating;
    /**商家店铺距离*/
    @SerializedName("distance")
    private String distance;

    public void setDistance(String distance){ this.distance = distance; }

    public String getDistance(){ return distance; }

    public void setSellerRating(String sellerRating) { this.sellerRating = sellerRating; }

    public String getSellerRating() { return sellerRating; }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerIcon() {
        return sellerIcon;
    }

    public void setSellerIcon(String sellerIcon) {
        this.sellerIcon = sellerIcon;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public List<ImageText> getImages() {
        return images;
    }

    public void setImages(List<ImageText> images) {
        this.images = images;
    }

    public List<VideoText> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoText> videos) {
        this.videos = videos;
    }

    public String getShopHours() {
        return shopHours;
    }

    public void setShopHours(String shopHours) {
        this.shopHours = shopHours;
    }

    public String getSellerIntroduce() { return sellerIntroduce; }

    public void setSellerIntroduce(String sellerIntroduce) { this.sellerIntroduce = sellerIntroduce;}

    public Boolean getConcern() {
        return concern == null ? false : concern;
    }

    public void setConcern(Boolean concern) {
        this.concern = concern;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public List<Seller> getBranchs() {
        return branchs;
    }

    public void setBranchs(List<Seller> branchs) {
        this.branchs = branchs;
    }


    public List<ImageText> getPreferentials() {
        return preferentials;
    }

    public void setPreferentials(List<ImageText> preferentials) {
        this.preferentials = preferentials;
    }

    public List<Goods> getRecommendScoreGoodsList() {
        return recommendScoreGoodsList;
    }

    public void setRecommendScoreGoodsList(List<Goods> recommendScoreGoodsList) {
        this.recommendScoreGoodsList = recommendScoreGoodsList;
    }

    public List<ImageText> getBanner() {
        return banner;
    }

    public void setBanner(List<ImageText> banner) {
        this.banner = banner;
    }

    public List<Goods> getRecommendSellerGoodsList() {
        return recommendSellerGoodsList;
    }

    public void setRecommendSellerGoodsList(List<Goods> recommendSellerGoodsList) {
        this.recommendSellerGoodsList = recommendSellerGoodsList;
    }
}
