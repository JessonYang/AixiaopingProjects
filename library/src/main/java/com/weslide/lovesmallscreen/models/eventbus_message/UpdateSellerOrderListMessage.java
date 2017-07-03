package com.weslide.lovesmallscreen.models.eventbus_message;

/**
 * Created by xu on 2016/7/16.
 * 商家订单
 */
public class UpdateSellerOrderListMessage {
    private String status;


    /** 订单状态 Constants中有定义 */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
