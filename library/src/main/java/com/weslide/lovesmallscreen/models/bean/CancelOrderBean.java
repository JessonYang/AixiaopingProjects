package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;
import com.weslide.lovesmallscreen.models.Order;

import java.util.List;

/**
 * Created by xu on 2016/7/15.
 * 取消订单
 */
public class CancelOrderBean extends BaseModel {

    @SerializedName("orderId")
    /** 订单id */
    private String orderId;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
