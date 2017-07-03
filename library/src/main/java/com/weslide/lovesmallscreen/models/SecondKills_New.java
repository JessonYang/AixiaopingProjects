package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */
public class SecondKills_New extends BaseModel {
    @SerializedName("currentTime")
    private CurrentTime currentTime;

    @SerializedName("secondKill")
    private List<SecondKill_New> SecondKill_New;

    @SerializedName("seckGoods")
    private SeckGoods seckGoods;

    public CurrentTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(CurrentTime currentTime) {
        this.currentTime = currentTime;
    }

    public List<com.weslide.lovesmallscreen.models.SecondKill_New> getSecondKill_New() {
        return SecondKill_New;
    }

    public void setSecondKill_New(List<com.weslide.lovesmallscreen.models.SecondKill_New> secondKill_New) {
        SecondKill_New = secondKill_New;
    }

    public SeckGoods getSeckGoods() {
        return seckGoods;
    }

    public void setSeckGoods(SeckGoods seckGoods) {
        this.seckGoods = seckGoods;
    }
}
