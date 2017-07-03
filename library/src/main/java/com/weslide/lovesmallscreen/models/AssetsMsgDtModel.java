package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/5/9.
 */
public class AssetsMsgDtModel extends BaseModel {
    @SerializedName("msgDetail")
    AssetsMsgDtObModel msgDetail;

    @SerializedName("msgId")
    String msgId;

    @SerializedName("userId")
    String userId;

    @SerializedName("typeId")
    String typeId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public AssetsMsgDtObModel getMsgDetail() {
        return msgDetail;
    }

    public void setMsgDetail(AssetsMsgDtObModel msgDetail) {
        this.msgDetail = msgDetail;
    }
}
