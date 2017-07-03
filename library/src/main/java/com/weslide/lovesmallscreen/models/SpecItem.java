package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/6/4.
 * 规格项
 */
public class SpecItem extends BaseModel {
    @SerializedName("specItemName")
    /** 规格项名称 */
    private String specItemName;
    @SerializedName("specItemId")
    /** 规格项Id */
    private String specItemId;


    public String getSpecItemName() {
        return specItemName;
    }

    public void setSpecItemName(String specItemName) {
        this.specItemName = specItemName;
    }

    public String getSpecItemId() {
        return specItemId;
    }

    public void setSpecItemId(String specItemId) {
        this.specItemId = specItemId;
    }
}
