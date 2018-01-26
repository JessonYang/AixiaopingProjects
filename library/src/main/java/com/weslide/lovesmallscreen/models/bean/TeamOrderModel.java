package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;

/**
 * Created by YY on 2017/12/25.
 */
public class TeamOrderModel extends BaseModel {

    //拼团用户头像
    @SerializedName("userHead")
    String userHead;

    //拼团剩余人数
    @SerializedName("surplusNum")
    String surplusNum;

    //拼团剩余时间
    @SerializedName("surplusTime")
    long surplusTime;

    //拼主订单id

    public String getTeamOrderId() {
        return teamOrderId;
    }

    public void setTeamOrderId(String teamOrderId) {
        this.teamOrderId = teamOrderId;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(String surplusNum) {
        this.surplusNum = surplusNum;
    }

    public long getSurplusTime() {
        return surplusTime;
    }

    public void setSurplusTime(long surplusTime) {
        this.surplusTime = surplusTime;
    }

    @SerializedName("teamOrderId")
    String teamOrderId;

    public String getOrderUserId() {
        return OrderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        OrderUserId = orderUserId;
    }

    @SerializedName("OrderUserId")
    String OrderUserId;
}
