package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/7/15.
 * 退单状态
 */
public class BackOrderStatus extends BaseModel {

//    name	状态名称	审核不通过  -15
//    商家未确认  0
//    待审核      10
//    已审核      20
//    已支付      30
//    退单完成    40
//    statusId	状态编号

    @SerializedName("name")
    /** 状态名称 */
    private String name;

    @SerializedName("statusId")
    /** 状态编号 */
    private String statusId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
}
