package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Administrator on 2017/3/13.
 */
public class Score extends BaseModel{
    @SerializedName("scoreGoods")
    private ScoreGoods scoreGoods;

    public ScoreGoods getScoreGoods() {
        return scoreGoods;
    }

    public void setScoreGoods(ScoreGoods scoreGoods) {
        this.scoreGoods = scoreGoods;
    }
}
