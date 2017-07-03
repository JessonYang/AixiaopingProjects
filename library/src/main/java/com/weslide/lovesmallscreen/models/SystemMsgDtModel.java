package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/5/9.
 */
public class SystemMsgDtModel extends BaseModel {
    @SerializedName("msgDetail")
    SystemMsgDtObModel systemMsgDtObModel;

    @SerializedName("msgId")
    String msgId;

    @SerializedName("typeId")
    String typeId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @SerializedName("userId")
    String userId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public SystemMsgDtObModel getSystemMsgDtObModel() {
        return systemMsgDtObModel;
    }

    public void setSystemMsgDtObModel(SystemMsgDtObModel systemMsgDtObModel) {
        this.systemMsgDtObModel = systemMsgDtObModel;
    }

}
