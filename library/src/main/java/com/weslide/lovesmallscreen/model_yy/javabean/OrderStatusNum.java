package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/11/10.
 */
public class OrderStatusNum extends BaseModel{

    //代付款订单数
    @SerializedName("payment")
    String payment;

    //待分享
    @SerializedName("share")
    String share;

    //待发货
    @SerializedName("sendOutGoods")
    String sendOutGoods;

    //待收货
    @SerializedName("receive")
    String receive;

    //退单售后
    @SerializedName("chargeback")
    String chargeback;

    public String getChargeback() {
        return chargeback;
    }

    public void setChargeback(String chargeback) {
        this.chargeback = chargeback;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getSendOutGoods() {
        return sendOutGoods;
    }

    public void setSendOutGoods(String sendOutGoods) {
        this.sendOutGoods = sendOutGoods;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }
}
