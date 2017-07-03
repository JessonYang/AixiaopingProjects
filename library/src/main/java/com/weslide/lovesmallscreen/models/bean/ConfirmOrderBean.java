package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xu on 2016/6/30.
 * 处理提交订单数据
 */
public class ConfirmOrderBean {



//    orderIds	提交的订单	[String,String]
//    payType	支付方式	ALIPAY:支付宝支付
//    WEIXIN:微信支付
//    WALLET:钱包支付

    //-------------请求

    @SerializedName("orderIds")
    /** 提交的订单	[String,String] */
    private List<String> orderIds;
    @SerializedName("payType")
    /** 支付方式 Constants中有定义常量 */
    private String payType;

//    name	收货人
//    phone	收货人电话
//    address	地址全部内容(province+city+district+detailedAddress)
    @SerializedName("name")
    /** 收货人 */
    private String name;
    @SerializedName("phone")
    /** 收货人电话 */
    private String phone;
    @SerializedName("address")
    /** 地址全部内容(province+city+district+detailedAddress) */
    private String address;



    //--------------响应
//    payInfo	用于支付的信息
    @SerializedName("payInfo")
    /** 用于支付的信息 */
    private String payInfo;


    public List<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<String> orderIds) {
        this.orderIds = orderIds;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
