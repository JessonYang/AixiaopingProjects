package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/6/8.
 */
public class TicketListObModel extends BaseModel {

    @SerializedName("ticketId")
    String ticketId;

    @SerializedName("goodsId")
    String goodsId;

    @SerializedName("consumeTack")
    String consumeTack;

    @SerializedName("faceValue")
    String faceValue;

    @SerializedName("goodsName")
    String goodsName;

    @SerializedName("consumeAddress")
    String consumeAddress;

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getConsumeTack() {
        return consumeTack;
    }

    public void setConsumeTack(String consumeTack) {
        this.consumeTack = consumeTack;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getConsumeAddress() {
        return consumeAddress;
    }

    public void setConsumeAddress(String consumeAddress) {
        this.consumeAddress = consumeAddress;
    }

    @SerializedName("expiryDate")
    String expiryDate;
}
