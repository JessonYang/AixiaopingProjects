package com.weslide.lovesmallscreen.models.config;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/7/14.
 * 会员级别
 */
public class VipLevel extends BaseModel {

//    vipLevelId	标识
//    money	需要支付金额
//    freeCount	可获得免单券数量

    @SerializedName("vipLevelId")
    /** 标识 */
    private String vipLevelId;

    @SerializedName("money")
    /** 需要支付金额 */
    private String money;

    @SerializedName("freeCount")
    /** 可获得免单券数量 */
    private String freeCount;


    public String getVipLevelId() {
        return vipLevelId;
    }

    public void setVipLevelId(String vipLevelId) {
        this.vipLevelId = vipLevelId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(String freeCount) {
        this.freeCount = freeCount;
    }
}
