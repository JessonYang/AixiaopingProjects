package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/7/15.
 * 待兑换
 */
public class SellerConfirmExchangeBean extends BaseModel{
    @SerializedName("orderId")
    private String orderId;

    @SerializedName("exchangeCode")
    private String exchangeCode;//兑换码

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }
}
