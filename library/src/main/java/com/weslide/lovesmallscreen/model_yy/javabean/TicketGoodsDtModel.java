package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/6/2.
 */
public class TicketGoodsDtModel extends BaseModel {
    @SerializedName("ticketId")
    String ticketId;

    @SerializedName("goodsId")
    String goodsId;

    @SerializedName("goodsIcon")
    String goodsIcon;

    @SerializedName("goodsName")
    String goodsName;

    @SerializedName("goodsPriceAfterTicket")
    String goodsPriceAfterTicket;

    @SerializedName("goodsOrieignPrice")
    String goodsOrieignPrice;

    @SerializedName("ticketFaceValue")
    String ticketFaceValue;

    @SerializedName("profit")
    String profit;

    @SerializedName("consumeTack")
    String consumeTack;

    @SerializedName("ticketVacancy")
    String ticketVacancy;

    @SerializedName("goodsSold")
    String goodsSold;

    @SerializedName("daysInUse")
    String daysInUse;

    @SerializedName("expiryDate")
    String expiryDate;

    @SerializedName("shareTitle")
    String shareTitle;

    @SerializedName("shareContent")
    String shareContent;

    @SerializedName("shareIconUrl")
    String shareIconUrl;

    public String getShareTargetUrl() {
        return shareTargetUrl;
    }

    public void setShareTargetUrl(String shareTargetUrl) {
        this.shareTargetUrl = shareTargetUrl;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsIcon() {
        return goodsIcon;
    }

    public void setGoodsIcon(String goodsIcon) {
        this.goodsIcon = goodsIcon;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPriceAfterTicket() {
        return goodsPriceAfterTicket;
    }

    public void setGoodsPriceAfterTicket(String goodsPriceAfterTicket) {
        this.goodsPriceAfterTicket = goodsPriceAfterTicket;
    }

    public String getGoodsOrieignPrice() {
        return goodsOrieignPrice;
    }

    public void setGoodsOrieignPrice(String goodsOrieignPrice) {
        this.goodsOrieignPrice = goodsOrieignPrice;
    }

    public String getTicketFaceValue() {
        return ticketFaceValue;
    }

    public void setTicketFaceValue(String ticketFaceValue) {
        this.ticketFaceValue = ticketFaceValue;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getConsumeTack() {
        return consumeTack;
    }

    public void setConsumeTack(String consumeTack) {
        this.consumeTack = consumeTack;
    }

    public String getTicketVacancy() {
        return ticketVacancy;
    }

    public void setTicketVacancy(String ticketVacancy) {
        this.ticketVacancy = ticketVacancy;
    }

    public String getGoodsSold() {
        return goodsSold;
    }

    public void setGoodsSold(String goodsSold) {
        this.goodsSold = goodsSold;
    }

    public String getDaysInUse() {
        return daysInUse;
    }

    public void setDaysInUse(String daysInUse) {
        this.daysInUse = daysInUse;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareIconUrl() {
        return shareIconUrl;
    }

    public void setShareIconUrl(String shareIconUrl) {
        this.shareIconUrl = shareIconUrl;
    }

    @SerializedName("shareTargetUrl")
    String shareTargetUrl;
}
