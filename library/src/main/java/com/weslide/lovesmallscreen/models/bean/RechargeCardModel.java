package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2018/1/11.
 */
public class RechargeCardModel extends BaseModel {

    @SerializedName("score")
    String score;

    @SerializedName("cardNum")
    String cardNum;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    @SerializedName("password")
    String password;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
