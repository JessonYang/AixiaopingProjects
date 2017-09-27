package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/8/2.
 */
public class CouponsBean extends BaseModel {
    @SerializedName("ticketid")
    String ticketid;

    @SerializedName("seller_name")
    String seller_name;

    @SerializedName("shareContent")
    String shareContent;

    @SerializedName("shareUrl")
    String shareUrl;

    @SerializedName("shareIcon")
    String shareIcon;

    @SerializedName("shareTitle")
    String shareTitle;

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    @SerializedName("logo")
    String logo;

    @SerializedName("ticketprice")
    String ticketprice;

    @SerializedName("startTime")
    String startTime;

    @SerializedName("stopTime")
    String stopTime;

    @SerializedName("goods_name")
    String goods_name;

    @SerializedName("salesVolume")
    String salesVolume;

    @SerializedName("price")
    String price;

    @SerializedName("lastprice")
    String lastprice;

    @SerializedName("goodsMall")
    String goodsMall;

    @SerializedName("status")
    String status;

    @SerializedName("content")
    String content;

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    @SerializedName("goodsImg")
    String goodsImg;

    @SerializedName("transportationType")
    String transportationType;

    @SerializedName("transportation")
    String transportation;

    public List<String> getActivitydetails() {
        return activitydetails;
    }

    public void setActivitydetails(List<String> activitydetails) {
        this.activitydetails = activitydetails;
    }

    public String getTicketid() {
        return ticketid;
    }

    public void setTicketid(String ticketid) {
        this.ticketid = ticketid;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(String ticketprice) {
        this.ticketprice = ticketprice;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLastprice() {
        return lastprice;
    }

    public void setLastprice(String lastprice) {
        this.lastprice = lastprice;
    }

    public String getGoodsMall() {
        return goodsMall;
    }

    public void setGoodsMall(String goodsMall) {
        this.goodsMall = goodsMall;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(String transportationType) {
        this.transportationType = transportationType;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    @SerializedName("activitydetails")
    List<String> activitydetails;

    @SerializedName("ticketId")
    String ticketId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    @SerializedName("shareId")
    String shareId;

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

}
