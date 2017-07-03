package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by YY on 2017/6/14.
 */
public class LivemoduleModel extends BaseModel {

    @SerializedName("lives")
    List<LivesModel> lives;

    @SerializedName("liveHeader")
    NfcpModel liveHeader;

    public List<LivesModel> getLives() {
        return lives;
    }

    public void setLives(List<LivesModel> lives) {
        this.lives = lives;
    }

    public NfcpModel getLiveHeader() {
        return liveHeader;
    }

    public void setLiveHeader(NfcpModel liveHeader) {
        this.liveHeader = liveHeader;
    }
}
