package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/6/3.
 * 秒杀
 */
public class SecondKill extends BaseModel {


    @SerializedName("secondKillId")
    /** 秒杀场次id标识 */
    private String secondKillId;
    @SerializedName("secondKillName")
    /** 秒杀场次名称 */
    private String secondKillName;
    @SerializedName("startDate")
    /** 秒杀开始时间 */
    private String startDate;
    @SerializedName("endDate")
    /** 秒杀结束时间 */
    private String endDate;


    public String getSecondKillId() {
        return secondKillId;
    }

    public void setSecondKillId(String secondKillId) {
        this.secondKillId = secondKillId;
    }

    public String getSecondKillName() {
        return secondKillName;
    }

    public void setSecondKillName(String secondKillName) {
        this.secondKillName = secondKillName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
