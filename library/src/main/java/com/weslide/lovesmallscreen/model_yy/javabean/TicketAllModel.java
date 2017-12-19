package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/6/7.
 */
public class TicketAllModel extends BaseModel {

    @SerializedName("ticketId")
    String ticketId;

    public String getConsumeTack() {
        return consumeTack;
    }

    public void setConsumeTack(String consumeTack) {
        this.consumeTack = consumeTack;
    }

    @SerializedName("consumeTack")
    String consumeTack;

    @SerializedName("goodsName")
    String goodsName;

    @SerializedName("goodsIcon")
    String goodsIcon;

    @SerializedName("goodsSoldPerMonty")
    String goodsSoldPerMonty;

    @SerializedName("goodsPrice")
    String goodsPrice;

    @SerializedName("ticketPrice")
    String ticketPrice;

    public String getRpCash() {
        return rpCash;
    }

    public void setRpCash(String rpCash) {
        this.rpCash = rpCash;
    }

    @SerializedName("rpCash")
    String rpCash;

    @SerializedName("goodsPriceAfterTicket")
    String goodsPriceAfterTicket;

    @SerializedName("ticketVacancy")
    String ticketVacancy;

    @SerializedName("ticketSentPercent")
    String ticketSentPercent;

    public String getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(String profitMoney) {
        this.profitMoney = profitMoney;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsIcon() {
        return goodsIcon;
    }

    public void setGoodsIcon(String goodsIcon) {
        this.goodsIcon = goodsIcon;
    }

    public String getGoodsSoldPerMonty() {
        return goodsSoldPerMonty;
    }

    public void setGoodsSoldPerMonty(String goodsSoldPerMonty) {
        this.goodsSoldPerMonty = goodsSoldPerMonty;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getGoodsPriceAfterTicket() {
        return goodsPriceAfterTicket;
    }

    public void setGoodsPriceAfterTicket(String goodsPriceAfterTicket) {
        this.goodsPriceAfterTicket = goodsPriceAfterTicket;
    }

    public String getTicketVacancy() {
        return ticketVacancy;
    }

    public void setTicketVacancy(String ticketVacancy) {
        this.ticketVacancy = ticketVacancy;
    }

    public String getTicketSentPercent() {
        return ticketSentPercent;
    }

    public void setTicketSentPercent(String ticketSentPercent) {
        this.ticketSentPercent = ticketSentPercent;
    }

    @SerializedName("profitMoney")
    String profitMoney;
}
