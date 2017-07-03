package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/5/5.
 */
public class OrderMsgDtOb extends BaseModel{
    @SerializedName("icon")
    String icon;

    @SerializedName("title")
    String title;

    @SerializedName("content")
    String content;

    @SerializedName("time")
    String time;

    @SerializedName("isread")
    int isread;

    @SerializedName("msgId")
    int msgId;

    @SerializedName("orderStatusId")
    String orderStatusId;

    @SerializedName("orderStatus")
    String orderStatus;

    @SerializedName("orderFreight")
    String orderFreight;

    @SerializedName("orderIcon")
    String orderIcon;

    @SerializedName("orderDesc")
    String orderDesc;

    @SerializedName("orderMoney")
    String orderMoney;

    @SerializedName("orderUserName")
    String orderUserName;

    @SerializedName("orderNumber")
    String orderNumber;

    @SerializedName("orderFreightNumber")
    String orderFreightNumber;

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

    public String getOrderFreight() {
        return orderFreight;
    }

    public void setOrderFreight(String orderFreight) {
        this.orderFreight = orderFreight;
    }

    public String getOrderIcon() {
        return orderIcon;
    }

    public void setOrderIcon(String orderIcon) {
        this.orderIcon = orderIcon;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderFreightNumber() {
        return orderFreightNumber;
    }

    public void setOrderFreightNumber(String orderFreightNumber) {
        this.orderFreightNumber = orderFreightNumber;
    }
}
