package com.weslide.lovesmallscreen.models.push;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/5/15.
 */
public class SystemMsg extends BaseModel {
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @SerializedName("msgId")
    private String msgId;
    @SerializedName("channelId")
    private String channelId;
    @SerializedName("message")
    private String message;
    @SerializedName("msg_unread_count")
    private String msg_unread_count;
    @SerializedName("typeId")
    private String typeId;
    @SerializedName("userId")
    String userId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsg_unread_count() {
        return msg_unread_count;
    }

    public void setMsg_unread_count(String msg_unread_count) {
        this.msg_unread_count = msg_unread_count;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
