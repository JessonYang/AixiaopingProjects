package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/6/9.
 */
public class PartnerIconModel extends BaseModel {

    @SerializedName("partnerId")
    String partnerId;

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

    @SerializedName("partnerIcon")
    String partnerIcon;
}
