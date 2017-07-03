package com.weslide.lovesmallscreen.models.eventbus_message;

import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/7/15.
 * 更新订单列表数据
 */
public class UpdateOrderListMessage extends BaseModel {

    public UpdateOrderListMessage() {
    }

    public UpdateOrderListMessage(String status) {
        this.status = status;
    }

    private String status;


    /**
     * 订单状态 Constants中有定义
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
