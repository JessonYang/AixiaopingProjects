package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xu on 2016/6/30.
 * 处理提交订单数据
 */
public class PayOrderBean {

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
    @SerializedName("type")
    /** 支付方式 (组合或非组合支付) */
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

}
