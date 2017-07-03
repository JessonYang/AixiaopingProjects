package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/5/25.
 * 获得积分
 */
public class AcquireScore extends BaseModel {

    @Expose  //生成json时排除
    private Long id;
    @SerializedName("acquireTime")
    private String acquireTime;
    @SerializedName("advertId")
    private String advertId;
    @SerializedName("type")
    private String type;
    @SerializedName("score")
    private String score;


    /** id标示 */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    /** 获得时间 */
    public String getAcquireTime() {
        return acquireTime;
    }

    public void setAcquireTime(String acquireTime) {
        this.acquireTime = acquireTime;
    }

    /** 通过广告获得的加分 */
    public String getAdvertId() {
        return advertId;
    }

    public void setAdvertId(String advertId) {
        this.advertId = advertId;
    }

    /** 积分获得类型 */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /** 增加的加分 */
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
