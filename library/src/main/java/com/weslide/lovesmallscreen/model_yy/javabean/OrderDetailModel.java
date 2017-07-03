package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/6/7.
 */
public class OrderDetailModel extends BaseModel {

    @SerializedName("personalToday")
    String personalToday;

    @SerializedName("personalLastday")
    String personalLastday;

    @SerializedName("personalThisMonth")
    String personalThisMonth;

    @SerializedName("personalLastMonth")
    String personalLastMonth;

    @SerializedName("partnerToday")
    String partnerToday;

    @SerializedName("partnerLastday")
    String partnerLastday;

    @SerializedName("partnerThisMonth")
    String partnerThisMonth;

    public String getPartnerLastMonth() {
        return partnerLastMonth;
    }

    public void setPartnerLastMonth(String partnerLastMonth) {
        this.partnerLastMonth = partnerLastMonth;
    }

    public String getPersonalToday() {
        return personalToday;
    }

    public void setPersonalToday(String personalToday) {
        this.personalToday = personalToday;
    }

    public String getPersonalLastday() {
        return personalLastday;
    }

    public void setPersonalLastday(String personalLastday) {
        this.personalLastday = personalLastday;
    }

    public String getPersonalThisMonth() {
        return personalThisMonth;
    }

    public void setPersonalThisMonth(String personalThisMonth) {
        this.personalThisMonth = personalThisMonth;
    }

    public String getPersonalLastMonth() {
        return personalLastMonth;
    }

    public void setPersonalLastMonth(String personalLastMonth) {
        this.personalLastMonth = personalLastMonth;
    }

    public String getPartnerToday() {
        return partnerToday;
    }

    public void setPartnerToday(String partnerToday) {
        this.partnerToday = partnerToday;
    }

    public String getPartnerLastday() {
        return partnerLastday;
    }

    public void setPartnerLastday(String partnerLastday) {
        this.partnerLastday = partnerLastday;
    }

    public String getPartnerThisMonth() {
        return partnerThisMonth;
    }

    public void setPartnerThisMonth(String partnerThisMonth) {
        this.partnerThisMonth = partnerThisMonth;
    }

    @SerializedName("partnerLastMonth")
    String partnerLastMonth;
}
