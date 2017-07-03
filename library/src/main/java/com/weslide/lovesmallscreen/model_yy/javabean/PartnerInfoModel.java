package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/6/7.
 */
public class PartnerInfoModel extends BaseModel {

    @SerializedName("partnerPlace")
    String partnerPlace;

    @SerializedName("assignedPlace")
    String assignedPlace;

    public String getUnassignPlace() {
        return unassignPlace;
    }

    public void setUnassignPlace(String unassignPlace) {
        this.unassignPlace = unassignPlace;
    }

    public String getPartnerPlace() {
        return partnerPlace;
    }

    public void setPartnerPlace(String partnerPlace) {
        this.partnerPlace = partnerPlace;
    }

    public String getAssignedPlace() {
        return assignedPlace;
    }

    public void setAssignedPlace(String assignedPlace) {
        this.assignedPlace = assignedPlace;
    }

    @SerializedName("unassignPlace")
    String unassignPlace;
}
