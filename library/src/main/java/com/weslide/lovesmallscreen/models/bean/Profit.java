package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/3/24.
 */
public class Profit extends BaseModel{
    public String getExpectedProfit() {
        return expectedProfit;
    }

    public void setExpectedProfit(String expectedProfit) {
        this.expectedProfit = expectedProfit;
    }

    public String getSettlementProfit() {
        return settlementProfit;
    }

    public void setSettlementProfit(String settlementProfit) {
        this.settlementProfit = settlementProfit;
    }

    @SerializedName("expectedProfit")
    String expectedProfit;

    @SerializedName("settlementProfit")
    String settlementProfit;

    public String getRealProfit() {
        return realProfit;
    }

    public void setRealProfit(String realProfit) {
        this.realProfit = realProfit;
    }

    @SerializedName("realProfit")
    String realProfit;
}
