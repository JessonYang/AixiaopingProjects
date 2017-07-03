package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/6/29.
 */
public class CurrentTime extends BaseModel {
    @SerializedName("startDate")//积分操作时间

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSecondKillName() {
        return secondKillName;
    }

    public void setSecondKillName(String secondKillName) {
        this.secondKillName = secondKillName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSecondKillId() {
        return secondKillId;
    }

    public void setSecondKillId(String secondKillId) {
        this.secondKillId = secondKillId;
    }

    private String startDate;
    @SerializedName("secondKillName")
    private String secondKillName;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("secondKillId")
    private String secondKillId;

}
