package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/5/9.
 */
public class OrderMsgDtModel extends BaseModel {
    @SerializedName("msgId")
    String msgId;

    @SerializedName("typeId")
    String typeId;

    @SerializedName("userId")
    String userId;

    @SerializedName("msgDetail")
    OrderMsgDtObModel msgDetail;

    public String getMsgId() {
        return msgId;
    }

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

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public OrderMsgDtObModel getMsgDetail() {
        return msgDetail;
    }

    public void setMsgDetail(OrderMsgDtObModel msgDetail) {
        this.msgDetail = msgDetail;
    }
}
