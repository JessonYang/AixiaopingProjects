package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/7/13.
 * 商家中心
 */
public class SellerInfo extends BaseModel{

    @SerializedName("sellerId")//商家id
    private String sellerId;

    @SerializedName("seller")//店铺信息
    private Seller seller;

    @SerializedName("inviteCode")//店铺邀请码
    private String inviteCode;

    @SerializedName("availableMoney")//商家的可用现金
    private String availableMoney = "0";

    @SerializedName("unavailableMoney")//商家的冻结现金
    private String unavailableMoney = "0";

    @SerializedName("pendingExchangeNumber")//待兑换订单数量
    private String pendingExchangeNumber;

    @SerializedName("pendingConfirmationNumber")//待确认订单数量
    private String pendingConfirmationNumber;

    @SerializedName("pendingReceiptNumber")//待发货订单数量
    private String pendingReceiptNumber;

    @SerializedName("backAndCustomerServiceNumber")//退单售后订单数量
    private String backAndCustomerServiceNumber;

    @SerializedName("fansNumber")//粉丝数量
    private String fansNumber;

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public void setAvailableMoney(String availableMoney) {
        this.availableMoney = availableMoney;
    }

    public void setUnavailableMoney(String unavailableMoney) {
        this.unavailableMoney = unavailableMoney;
    }

    public void setPendingExchangeNumber(String pendingExchangeNumber) {
        this.pendingExchangeNumber = pendingExchangeNumber;
    }

    public void setPendingConfirmationNumber(String pendingConfirmationNumber) {
        this.pendingConfirmationNumber = pendingConfirmationNumber;
    }

    public void setPendingReceiptNumber(String pendingReceiptNumber) {
        this.pendingReceiptNumber = pendingReceiptNumber;
    }

    public void setBackAndCustomerServiceNumber(String backAndCustomerServiceNumber) {
        this.backAndCustomerServiceNumber = backAndCustomerServiceNumber;
    }

    public void setFansNumber(String fansNumber) {
        this.fansNumber = fansNumber;
    }

    public String getSellerId() {
        return sellerId;
    }

    public Seller getSeller() {
        return seller;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public String getAvailableMoney() {
        return availableMoney;
    }

    public String getUnavailableMoney() {
        return unavailableMoney;
    }

    public String getPendingExchangeNumber() {
        return pendingExchangeNumber;
    }

    public String getPendingConfirmationNumber() {
        return pendingConfirmationNumber;
    }

    public String getPendingReceiptNumber() {
        return pendingReceiptNumber;
    }

    public String getBackAndCustomerServiceNumber() {
        return backAndCustomerServiceNumber;
    }

    public String getFansNumber() {
        return fansNumber;
    }
}
