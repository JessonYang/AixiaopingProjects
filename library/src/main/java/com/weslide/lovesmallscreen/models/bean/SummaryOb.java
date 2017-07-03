package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/3/24.
 */
public class SummaryOb extends BaseModel {
    @SerializedName("personalPredict")
    String personalPredict;

    @SerializedName("personalOrder")
    String personalOrder;

    public String getPersonalPredict() {
        return personalPredict;
    }

    public void setPersonalPredict(String personalPredict) {
        this.personalPredict = personalPredict;
    }

    public String getPersonalOrder() {
        return personalOrder;
    }

    public void setPersonalOrder(String personalOrder) {
        this.personalOrder = personalOrder;
    }

    public String getPartnerPredict() {
        return partnerPredict;
    }

    public void setPartnerPredict(String partnerPredict) {
        this.partnerPredict = partnerPredict;
    }

    public String getPartnerOrder() {
        return partnerOrder;
    }

    public void setPartnerOrder(String partnerOrder) {
        this.partnerOrder = partnerOrder;
    }

    @SerializedName("partnerPredict")
    String partnerPredict;

    @SerializedName("partnerOrder")
    String partnerOrder;

    @SerializedName("totalPredict")
    String totalPredict;

    public String getTotalPredict() {
        return totalPredict;
    }

    public void setTotalPredict(String totalPredict) {
        this.totalPredict = totalPredict;
    }

    public String getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(String totalOrders) {
        this.totalOrders = totalOrders;
    }

    @SerializedName("totalOrders")
    String totalOrders;
}
