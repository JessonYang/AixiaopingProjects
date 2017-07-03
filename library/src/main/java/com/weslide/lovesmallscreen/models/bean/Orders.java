package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by YY on 2017/3/25.
 */
public class Orders extends BaseModel {
    @SerializedName("orders")
    private List<OrdersOb> ordersObs;

    @SerializedName("user_id")
    private String user_id;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @SerializedName("orderType")
    private String orderType;

    @SerializedName("timeNum")
    private String time_num;

    @SerializedName("center")
    private String center;

    @SerializedName("pageIndex")
    private String page;

    @SerializedName("seach")
    private String seach;


    public String getSeach() {
        return seach;
    }

    public void setSeach(String seach) {
        this.seach = seach;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTime_num() {
        return time_num;
    }

    public void setTime_num(String time_num) {
        this.time_num = time_num;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<OrdersOb> getOrdersObs() {
        return ordersObs;
    }

    public void setOrdersObs(List<OrdersOb> ordersObs) {
        this.ordersObs = ordersObs;
    }
}
