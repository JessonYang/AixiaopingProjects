package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by YY on 2017/5/9.
 */
public class OrderMsgDtObModel extends BaseModel {
    @SerializedName("goods")
    List<OrderMsgDtGoodsModel> goods;

    @SerializedName("orderNumber")
    String orderNumber;

    @SerializedName("orderStatusId")
    String orderStatusId;

    @SerializedName("orderStatus")
    String orderStatus;

    @SerializedName("orderFreightNumber")
    String orderFreightNumber;

    @SerializedName("orderId")
    String orderId;

    @SerializedName("backOrderId")
    String backOrderId;

    @SerializedName("content")
    List<String> content;

    @SerializedName("time")
    String time;

    @SerializedName("unread")
    String unread;

    public List<OrderMsgDtGoodsModel> getGoods() {
        return goods;
    }

    public void setGoods(List<OrderMsgDtGoodsModel> goods) {
        this.goods = goods;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(String orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderFreightNumber() {
        return orderFreightNumber;
    }

    public void setOrderFreightNumber(String orderFreightNumber) {
        this.orderFreightNumber = orderFreightNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBackOrderId() {
        return backOrderId;
    }

    public void setBackOrderId(String backOrderId) {
        this.backOrderId = backOrderId;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUnread() {
        return unread;
    }

    public void setUnread(String unread) {
        this.unread = unread;
    }
}
