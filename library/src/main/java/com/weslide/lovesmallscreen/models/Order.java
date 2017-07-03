package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;
import com.weslide.lovesmallscreen.utils.StringUtils;

import java.util.List;

/**
 * Created by xu on 2016/6/20.
 * 订单
 */
public class Order extends BaseModel {

    @SerializedName("orderId")
    private String orderId;
    @SerializedName("orderNumber")
    private String orderNumber;
    @SerializedName("seller")
    private Seller seller;
    @SerializedName("user")
    /** 该订单所属用户 */
    private UserInfo user;
    @SerializedName("orderDate")
    private String orderDate;
    @SerializedName("details")
    private List<OrderDetail> details;
    @SerializedName("money")
    private String money;
    @SerializedName("score")
    private String score;
    @SerializedName("cashpoint")
    private String cashpoint;
    @SerializedName("orderItems")
    private List<OrderItem> orderItems;
    @SerializedName("expressTactics")
    private String expressTactics;
    @SerializedName("status")
    private OrderStatus status;
    @SerializedName("username")
    private String username;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;
    @SerializedName("expressName")
    private String expressName;
    @SerializedName("expressNumber")
    private String expressNumber;
    @SerializedName("realityMoney")
    /** 订单实际支付金额 */
    private String realityMoney;
    @SerializedName("exchangeCode")
    /** 兑换码 */
    private String exchangeCode;


    /** 订单id */
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /** 订单号 */
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /** 订单所属的商家	{seller} */
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    /** 订单创建时间	2016-04-12 */
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /** 订单费用明细	[{detail},{detail}] */
    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    /** 订单需要支付的现金 */
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    /** 订单需要支付的积分 */
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    /** 订单需要支付的红包 */
    public String getCashpoint() {
        return cashpoint;
    }

    public void setCashpoint(String cashpoint) {
        this.cashpoint = cashpoint;
    }

    /** 定单包含的订单项列表	[{orderItem},{orderItem}] */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /** 配送策略	包邮 、随单包邮 、单笔满固额包邮 、邮费代付 、到店消费 、上门自提 、周边配送 */
    public String getExpressTactics() {
        return expressTactics;
    }

    public void setExpressTactics(String expressTactics) {
        this.expressTactics = expressTactics;
    }

    /** 订单状态 */
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /** 收货人姓名 */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /** 收货人电话 */
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** 收货人地址 */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /** 快递公司 */
    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    /** 快递单号 */
    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }


    public String getRealityMoney() {
        return realityMoney;
    }

    public void setRealityMoney(String realityMoney) {
        this.realityMoney = realityMoney;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }


    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    /**
     * 获取当前订单项的购买类型
     * 1.积分商品
     * 2.现金商品
     * 3.免单商品
     * 4.现金加红包商品
     * @return
     */
    public int getType(){
        if(!StringUtils.isNumberEmpty(getScore())){ //积分商品
            return 1;
        } else if(!StringUtils.isNumberEmpty(getMoney())){ //现金商品
            if(!StringUtils.isNumberEmpty(getCashpoint())){ //现金红包商品
                return 4;
            }
            return 2;
        } else {  //免单商品
            return 3;
        }
    }
}
