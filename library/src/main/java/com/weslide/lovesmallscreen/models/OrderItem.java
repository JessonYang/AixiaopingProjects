package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;
import com.weslide.lovesmallscreen.utils.StringUtils;

/**
 * Created by xu on 2016/6/20.
 * 订单项
 */
public class OrderItem extends BaseModel {


    @SerializedName("orderItemId")
    private String orderItemId;

    @SerializedName("goods")
    private Goods goods;

    @SerializedName("number")
    private int number;

    @SerializedName("specString")
    private String specString;

    @SerializedName("userComment")
    /** 购买用户对该订单的评论 */
    private Comment userComment;
    @SerializedName("money")
    /** 订单需要支付的现金 */
    private String money;
    @SerializedName("score")
    /** 订单需要支付的积分 */
    private String score;
    @SerializedName("cashpoint")
    /** 订单需要支付的红包 */
    private String cashpoint;

    @SerializedName("isBack")
    /** 是否可退单 */
    private boolean isBack;

    @Expose/** 是否被选中 */
    private boolean select;

    /** 订单项id */
    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    /** 商品信息	{goods} */
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    /** 数量 */
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /** 前台用于显示的规格信息 */
    public String getSpecString() {
        return specString;
    }

    public void setSpecString(String specString) {
        this.specString = specString;
    }


    public Comment getUserComment() {
        return userComment;
    }

    public void setUserComment(Comment userComment) {
        this.userComment = userComment;
    }


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCashpoint() {
        return cashpoint;
    }

    public void setCashpoint(String cashpoint) {
        this.cashpoint = cashpoint;
    }


    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
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

    public boolean isBack() {
        return isBack;
    }

    public void setBack(boolean back) {
        isBack = back;
    }
}
