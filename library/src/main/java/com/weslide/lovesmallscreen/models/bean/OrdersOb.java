package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/3/25.
 */
public class OrdersOb extends BaseModel{
    @SerializedName("orderId")
    private String orderId;

    @SerializedName("orderNumber")
    private String orderNumber;

    @SerializedName("orderStatus")
    private String orderStatus;

    @SerializedName("userAccount")
    private String userAccount;

    @SerializedName("userName")
    private String userName;

    @SerializedName("userIcon")
    private String userIcon;

    @SerializedName("orderName")
    private String orderName;

    @SerializedName("statusDesc")
    private String statusDesc;

    @SerializedName("payMoney")
    private String payMoney;

    @SerializedName("distributeMoney")
    private String distributeMoney;

    @SerializedName("orderFrom")
    private String orderFrom;

    @SerializedName("orderPic")
    private String orderPic;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @SerializedName("orderDate")
    private String orderDate;

    public String getStatusPic() {
        return statusPic;
    }

    public void setStatusPic(String statusPic) {
        this.statusPic = statusPic;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getDistributeMoney() {
        return distributeMoney;
    }

    public void setDistributeMoney(String distributeMoney) {
        this.distributeMoney = distributeMoney;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderPic() {
        return orderPic;
    }

    public void setOrderPic(String orderPic) {
        this.orderPic = orderPic;
    }

    @SerializedName("statusPic")
    private String statusPic;
}

