package com.weslide.lovesmallscreen.models.bean;


import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2018/1/11.
 */
public class QrCodeContentModel extends BaseModel {

    private String presenterId;

    public String getPresenterId() {
        return presenterId;
    }

    public void setPresenterId(String presenterId) {
        this.presenterId = presenterId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    private String score;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
}
