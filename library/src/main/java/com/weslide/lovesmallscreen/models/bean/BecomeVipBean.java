package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/7/14.
 * 成为会员
 */
public class BecomeVipBean  extends BaseModel{

//    inviteCode	邀请码
//    vipLevelId	支付的会员类型
//    payType	支付类型
//    200:支付宝
//    300:微信


    @SerializedName("inviteCode")
    /** 邀请码 */
    private String inviteCode;

    @SerializedName("vipLevelId")
    /** 支付的会员类型 */
    private String vipLevelId;

    @SerializedName("payType")
    /** 支付类型 */
    private String payType;

//    payInfo	支付信息

    @SerializedName("sign")
    private String payInfo;

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getVipLevelId() {
        return vipLevelId;
    }

    public void setVipLevelId(String vipLevelId) {
        this.vipLevelId = vipLevelId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }


    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }
}
