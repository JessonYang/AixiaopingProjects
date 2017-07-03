package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/6/9.
 */
public class PartnerIconListModel extends BaseModel {

    @SerializedName("partnerCount")
    String partnerCount;

    public List<PartnerIconModel> getPartners() {
        return partners;
    }

    public void setPartners(List<PartnerIconModel> partners) {
        this.partners = partners;
    }

    public String getPartnerCount() {
        return partnerCount;
    }

    public void setPartnerCount(String partnerCount) {
        this.partnerCount = partnerCount;
    }

    @SerializedName("partners")
    List<PartnerIconModel> partners;
}
