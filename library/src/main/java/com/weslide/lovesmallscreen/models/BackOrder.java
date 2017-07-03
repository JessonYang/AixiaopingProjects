package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xu on 2016/7/15.
 * 退单
 */
public class BackOrder extends Order{

//    backOrderItem	申请退单的订单项	{orderItem} getOrder中定义
//    backOrderStatus	退单状态	{backOrderStatus}
//    seller	订单商家信息	{seller}
//    backOrderInfo	退单信息	{backOrderInfo}
//    backOrderVerify	审核信息	{backOrderVerify}
//    drawbackDate	退款时间
//    还未退款的情况下该值为空
//realityMoney	订单实际支付金额
//    也就是钱包或支付宝支付的金额
//    realityScore	订单实际支付的积分
//    realityCashpoint	订单实际支付的红包

    @SerializedName("backOrderItemId")
    /** 退单id */
    private String backOrderItemId;

//    @SerializedName("realityMoney")
//    /** 订单实际支付金额 也就是钱包或支付宝支付的金额 */
//    private String realityMoney;
//    @SerializedName("realityScore")
//    /** 订单实际支付的积分 */
//    private String realityScore;
//    @SerializedName("realityCashpoint")
//    /** 订单实际支付的红包 */
//    private String realityCashpoint;

    @SerializedName("backOrderItem")
    /** 申请退单的订单项 */
    private OrderItem backOrderItem;
    @SerializedName("backOrderStatus")
    /** 退单状态 */
    private BackOrderStatus backOrderStatus;
    //    @SerializedName("seller")
//    /** 订单商家信息 */
//    private Seller seller;
    @SerializedName("backOrderInfo")
    /** 退单信息 */
    private BackOrderInfo backOrderInfo;
    @SerializedName("backOrderVerify")
    /** 审核信息 */
    private BackOrderVerify backOrderVerify;
    @SerializedName("drawbackDate")
    /** 退款时间 还未退款的情况下该值为空 */
    private String drawbackDate;


    //    user	订单所属的用户	{userInfo} userInfo在getBaseInfo中定义，商家订单列表需要传递该字段
//    orderNumber	订单号
//    orderDate	订单创建时间	2016-04-12
//    @SerializedName("user")
//    /** 订单所属的用户	{userInfo} userInfo在getBaseInfo中定义，商家订单列表需要传递该字段 */
//    private UserInfo user;

//    @SerializedName("orderNumber")
//    /** 订单号 */
//    private String orderNumber;
//    @SerializedName("orderDate")
//    /** 订单创建时间	2016-04-12 */
//    private String orderDate;

//    details	订单费用明细	[{detail},{detail}]
//    status	订单状态	{ orderStatus }
//    exchangeCode	兑换码
//    username	收货人姓名
//    phone	收货人电话
//    address	收货人地址
//    expressName	快递公司
//    expressNumber	快递单号

//    @SerializedName("details")
//    private List<OrderDetail> details;


    public OrderItem getBackOrderItem() {
        return backOrderItem;
    }

    public void setBackOrderItem(OrderItem backOrderItem) {
        this.backOrderItem = backOrderItem;
    }

    public BackOrderStatus getBackOrderStatus() {
        return backOrderStatus;
    }

    public void setBackOrderStatus(BackOrderStatus backOrderStatus) {
        this.backOrderStatus = backOrderStatus;
    }


    public BackOrderInfo getBackOrderInfo() {
        return backOrderInfo;
    }

    public void setBackOrderInfo(BackOrderInfo backOrderInfo) {
        this.backOrderInfo = backOrderInfo;
    }

    public BackOrderVerify getBackOrderVerify() {
        return backOrderVerify;
    }

    public void setBackOrderVerify(BackOrderVerify backOrderVerify) {
        this.backOrderVerify = backOrderVerify;
    }

    public String getDrawbackDate() {
        return drawbackDate;
    }

    public void setDrawbackDate(String drawbackDate) {
        this.drawbackDate = drawbackDate;
    }


    public String getBackOrderItemId() {
        return backOrderItemId;
    }

    public void setBackOrderItemId(String backOrderItemId) {
        this.backOrderItemId = backOrderItemId;
    }

}
