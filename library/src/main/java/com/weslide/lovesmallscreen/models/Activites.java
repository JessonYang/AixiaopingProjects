package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Administrator on 2017/3/13.
 */
public class Activites extends BaseModel {
    @SerializedName("secondKill")
    private SecondKills_New secondKills_new;

    @SerializedName("score")
    private Score score;

    @SerializedName("header")
    private Header header;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public SecondKills_New getSecondKills_new() {
        return secondKills_new;
    }

    public void setSecondKills_new(SecondKills_New secondKills_new) {
        this.secondKills_new = secondKills_new;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

}
