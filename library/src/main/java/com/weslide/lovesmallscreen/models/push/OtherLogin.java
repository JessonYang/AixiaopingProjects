package com.weslide.lovesmallscreen.models.push;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xu on 2016/6/2.
 * 单点登录数据
 */
public class OtherLogin {
    @SerializedName("userId")
    private String userId;
    @SerializedName("channelId")
    private String channelId;
    @SerializedName("message")
    private String message;


    /** 登出的用户ID */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /** 登录用户的个推cid */
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /** 提示数据 */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
