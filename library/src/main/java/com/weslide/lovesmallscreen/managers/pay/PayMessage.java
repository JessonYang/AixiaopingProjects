package com.weslide.lovesmallscreen.managers.pay;

import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/7/22.
 * 支付消息
 */
public class PayMessage extends BaseModel {

    private int result;

    /**
     * 支付结果
     * 0.支付成功
     * -1.支付失败
     * -2.支付取消
     */
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
