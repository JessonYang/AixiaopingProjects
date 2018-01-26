package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;

import java.util.List;

/**
 * Created by YY on 2017/12/26.
 */
public class TeamResModel extends BaseModel {

    public List<TeamDataModel> getTeamOrderList() {
        return teamOrderList;
    }

    public void setTeamOrderList(List<TeamDataModel> teamOrderList) {
        this.teamOrderList = teamOrderList;
    }

    @SerializedName("teamOrderList")
    List<TeamDataModel> teamOrderList;

    public String getTeamOrderId() {
        return teamOrderId;
    }

    public void setTeamOrderId(String teamOrderId) {
        this.teamOrderId = teamOrderId;
    }

    @SerializedName("teamOrderId")
    String teamOrderId;
}
