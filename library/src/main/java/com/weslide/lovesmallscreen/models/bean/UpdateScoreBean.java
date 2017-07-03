package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.models.AcquireScore;

import java.util.List;

/**
 * Created by xu on 2016/6/1.
 * 用于接收积分提交后的返回数据
 */
public class UpdateScoreBean {
    @SerializedName("totalScore")
    private String totalScore;

    @SerializedName("dataList")
    /** 获取的积分列表 */
    private List<AcquireScore> acquireScores;


    public List<AcquireScore> getAcquireScores() {
        return acquireScores;
    }

    public void setAcquireScores(List<AcquireScore> acquireScores) {
        this.acquireScores = acquireScores;
    }


    /**
     * 总积分
     */
    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }



}
