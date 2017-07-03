package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/6/20.
 * 订单明细
 */
public class OrderDetail extends BaseModel {

    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;


    /** 明细 */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 对价格的影响 */
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
