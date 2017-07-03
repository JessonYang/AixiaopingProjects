package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/6/29.
 */
public class MyScore extends BaseModel{
    @SerializedName("createtime")//积分操作时间

    private String acquireTime;

    @SerializedName("typeName")//获得或失去的积分类型名

    private String typeName;

    @SerializedName("remark")//获得或失去的积分额

    private String score;

    public String getAcquireTime() { return acquireTime; }

    public void setAcquireTime(String acquireTime) { this.acquireTime = acquireTime; }

    public String getTypeName() { return typeName; }

    public void setTypeName(String typeName) { this.typeName = typeName; }

    public String getScore() { return score; }

    public void setScore(String score) { this.score = score; }
}
