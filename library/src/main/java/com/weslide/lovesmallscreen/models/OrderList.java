package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.network.DataList;

/**
 * Created by xu on 2016/6/29.
 * 订单列表
 */
public class OrderList extends DataList<Order> {

    @SerializedName("totalMoney")
    /** 所有订单需要支付的现金 */
    private Float totalMoney;
    @SerializedName("totalScore")
    /** 所有订单需要支付的积分 */
    private Float totalScore;
    @SerializedName("totalCashpoint")
    /** 所有订单需要支付的总红包 */
    private Float totalCashpoint;


    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Float totalScore) {
        this.totalScore = totalScore;
    }

    public Float getTotalCashpoint() {
        return totalCashpoint;
    }

    public void setTotalCashpoint(Float totalCashpoint) {
        this.totalCashpoint = totalCashpoint;
    }
}
