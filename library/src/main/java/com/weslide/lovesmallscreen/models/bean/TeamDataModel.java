package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;

/**
 * Created by YY on 2017/12/26.
 */
public class TeamDataModel extends BaseModel {

    //拼主头像
    @SerializedName("userHead")
    String userHead;

    //剩余时间
    @SerializedName("surplusTime")
    long surplusTime;

    //剩余人数
    @SerializedName("surplusNum")
    String surplusNum;

    //分享标题
    @SerializedName("shareTitle")
    String shareTitle;

    //分享内容
    @SerializedName("shareContent")
    String shareContent;

    //分享图标
    @SerializedName("shareIcon")
    String shareIcon;

    //分享链接

    public String getShareTargetUrl() {
        return shareTargetUrl;
    }

    public void setShareTargetUrl(String shareTargetUrl) {
        this.shareTargetUrl = shareTargetUrl;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public long getSurplusTime() {
        return surplusTime;
    }

    public void setSurplusTime(long surplusTime) {
        this.surplusTime = surplusTime;
    }

    public String getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(String surplusNum) {
        this.surplusNum = surplusNum;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }

    @SerializedName("shareTargetUrl")
    String shareTargetUrl;
}
