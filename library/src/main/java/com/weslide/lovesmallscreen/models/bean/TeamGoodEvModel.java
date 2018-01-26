package com.weslide.lovesmallscreen.models.bean;

import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;

/**
 * Created by YY on 2017/12/26.
 */
public class TeamGoodEvModel extends BaseModel{
    private int goodType;
    private double money = -1;
    private String oriMoney;

    public String getOrderUserId() {
        return OrderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        OrderUserId = orderUserId;
    }

    private String OrderUserId;

    public String getTeamOrderId() {
        return teamOrderId;
    }

    public void setTeamOrderId(String teamOrderId) {
        this.teamOrderId = teamOrderId;
    }

    private String teamOrderId;

    public String getOriMoney() {
        return oriMoney;
    }

    public void setOriMoney(String oriMoney) {
        this.oriMoney = oriMoney;
    }

    public int getGoodType() {
        return goodType;
    }

    public void setGoodType(int goodType) {
        this.goodType = goodType;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
