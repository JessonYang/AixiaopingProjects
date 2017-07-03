package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Administrator on 2017/3/13.
 */
public class SecondKill_New extends BaseModel {
    @SerializedName("secondKill")
    private CurrentTime secondKill;

    public CurrentTime getSecondKill() {
        return secondKill;
    }

    public void setSecondKill(CurrentTime secondKill) {
        this.secondKill = secondKill;
    }
}
