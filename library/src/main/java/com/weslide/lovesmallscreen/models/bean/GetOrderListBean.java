package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/7/1.
 * 获取订单列表
 */
public class GetOrderListBean extends BaseModel {


    @SerializedName("pageIndex")
    /** 页索引 */
    private int pageIndex = 0;

    @SerializedName("status")
    /** 订单状态 Constants中有定义 */
    private String status;

    @SerializedName("sellerId")
    /** 获取商家订单列表时有使用 */
    private String sellerId;


    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
