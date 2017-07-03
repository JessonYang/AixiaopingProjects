package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by xu on 2016/6/21.
 * 商品评论
 */
public class Comment extends BaseModel {
    @SerializedName("userInfo")
    private UserInfo userInfo;
    @SerializedName("seller")
    private Seller seller;
    @SerializedName("orderItem")
    private OrderItem orderItem;
    @SerializedName("commentGoal")
    private String commentGoal;
    @SerializedName("commentContent")
    private String commentContent;
    @SerializedName("commentDate")
    private String commentDate;
    @SerializedName("commentImages")
    private List<String> commentImages;


    /** 发表评论的用户	{userInfo} userInfo就是个人信息的定义 */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /** 商品所属商家	{seller}  getSeller 中定义 */
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    /** 评论的订单项	{orderItem} getOrder 中定义 */
    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    /** 评论得分(总分十分) */
    public String getCommentGoal() {
        return commentGoal;
    }

    public void setCommentGoal(String commentGoal) {
        this.commentGoal = commentGoal;
    }

    /** 评论内容 */
    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    /** 评论时间 */
    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    /** 评论图片	[String,String] */
    public List<String> getCommentImages() {
        return commentImages;
    }

    public void setCommentImages(List<String> commentImages) {
        this.commentImages = commentImages;
    }
}
