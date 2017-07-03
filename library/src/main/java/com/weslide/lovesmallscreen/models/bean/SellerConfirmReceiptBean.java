package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/7/15.
 * 商家发货
 */
public class SellerConfirmReceiptBean extends BaseModel{
    @SerializedName("orderId")
    private String orderId;//订单ID

    @SerializedName("sendGoodsAddress")
    private String sendGoodsAddress;//发货地址

    @SerializedName("backGoodsAddress")
    private String backGoodsAddress;//退货地址

    @SerializedName("expressName")
    private String expressName;//快到公司

    @SerializedName("expressNumber")
    private String expressNumber;//快递单号

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setSendGoodsAddress(String sendGoodsAddress) {
        this.sendGoodsAddress = sendGoodsAddress;
    }

    public void setBackGoodsAddress(String backGoodsAddress) {
        this.backGoodsAddress = backGoodsAddress;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSendGoodsAddress() {
        return sendGoodsAddress;
    }

    public String getBackGoodsAddress() {
        return backGoodsAddress;
    }

    public String getExpressName() {
        return expressName;
    }

    public String getExpressNumber() {
        return expressNumber;
    }
}
