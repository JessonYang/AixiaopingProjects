package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;
import com.weslide.lovesmallscreen.models.ImageText;

import java.util.List;

/**
 * Created by YY on 2018/1/20.
 */
public class ExchangeReplyModel extends BaseModel {

    //帖子内容
    @SerializedName("content")
    String content;

    //顶帖时间
    @SerializedName("topTime")
    long topTime;

    //用户名
    @SerializedName("userName")
    String userName;

    //发帖用户id
    @SerializedName("replyUserId")
    String replyUserId;

    //地址
    @SerializedName("address")
    String address;

    //用户头像
    @SerializedName("headImg")
    String headImg;

    //商品图
    @SerializedName("goodsImgs")
    List<ImageText> goodsImgs;

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTopTime() {
        return topTime;
    }

    public void setTopTime(long topTime) {
        this.topTime = topTime;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public List<ImageText> getGoodsImgs() {
        return goodsImgs;
    }

    public void setGoodsImgs(List<ImageText> goodsImgs) {
        this.goodsImgs = goodsImgs;
    }
}
