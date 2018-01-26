package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;

/**
 * Created by YY on 2018/1/23.
 */
public class MyChangeListResModel extends BaseModel{

    //我的帖子数量
    @SerializedName("noteCount")
    int noteCount;

    //我发起的数量
    @SerializedName("inviteCount")
    int inviteCount;

    //邀约我的数量
    @SerializedName("acceptCount")
    int acceptCount;

    //我交易的数量
    @SerializedName("trading")
    int trading;

    public int getNoteCount() {
        return noteCount;
    }

    public void setNoteCount(int noteCount) {
        this.noteCount = noteCount;
    }

    public int getInviteCount() {
        return inviteCount;
    }

    public void setInviteCount(int inviteCount) {
        this.inviteCount = inviteCount;
    }

    public int getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(int acceptCount) {
        this.acceptCount = acceptCount;
    }

    public int getTrading() {
        return trading;
    }

    public void setTrading(int trading) {
        this.trading = trading;
    }
}
