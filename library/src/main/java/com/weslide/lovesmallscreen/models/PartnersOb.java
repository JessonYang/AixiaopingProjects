package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/3/27.
 */
public class PartnersOb extends BaseModel {
    @SerializedName("partnerName")
    private String partnerName;

    @SerializedName("partnerId")
    private String partnerId;

    @SerializedName("partnerIcon")
    private String partnerIcon;

    @SerializedName("partnerPhone")
    private String partnerPhone;

    @SerializedName("ordersToday")
    private String ordersToday;

    @SerializedName("predictToday")
    private String predictToday;

    @SerializedName("remarks")
    private String remarks;

    public String getPartnerName() {
        return partnerName;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerIcon() {
        return partnerIcon;
    }

    public void setPartnerIcon(String partnerIcon) {
        this.partnerIcon = partnerIcon;
    }

    public String getPartnerPhone() {
        return partnerPhone;
    }

    public void setPartnerPhone(String partnerPhone) {
        this.partnerPhone = partnerPhone;
    }

    public String getOrdersToday() {
        return ordersToday;
    }

    public void setOrdersToday(String ordersToday) {
        this.ordersToday = ordersToday;
    }

    public String getPredictToday() {
        return predictToday;
    }

    public void setPredictToday(String predictToday) {
        this.predictToday = predictToday;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

}
