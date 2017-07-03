package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/7/14.
 * 订单状态
 */
public class OrderStatus extends BaseModel {

//    name	状态名称	待支付       0
//    待确认       10
//    待发货       20
//    待兑换       25
//    待收货       30
//    待评价       40
//    已完成       50
//    已评价       70
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
