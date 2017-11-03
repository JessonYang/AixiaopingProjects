package com.weslide.lovesmallscreen.model_yy;

import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/9/28.
 */
public class DeleteCouponModel extends BaseModel {

    private String couponId;

    private String userId;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
