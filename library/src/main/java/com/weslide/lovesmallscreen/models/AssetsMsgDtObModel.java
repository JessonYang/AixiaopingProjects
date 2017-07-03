package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by YY on 2017/5/9.
 */
public class AssetsMsgDtObModel extends BaseModel {
    @SerializedName("type")
    String type;

    @SerializedName("icon")
    String icon;

    @SerializedName("title")
    String title;

    @SerializedName("money")
    String money;

    @SerializedName("moneySucceed")
    String moneySucceed;

    @SerializedName("content")
    List<String> content;

    @SerializedName("userName")
    String userName;

    @SerializedName("goodsName")
    String goodsName;

    @SerializedName("time")
    String time;

    @SerializedName("tips")
    String tips;

    @SerializedName("unread")
    String unread;

    @SerializedName("nav_title")
    String nav_title;

    public String getNav_title() {
        return nav_title;
    }

    public void setNav_title(String nav_title) {
        this.nav_title = nav_title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoneySucceed() {
        return moneySucceed;
    }

    public void setMoneySucceed(String moneySucceed) {
        this.moneySucceed = moneySucceed;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getUnread() {
        return unread;
    }

    public void setUnread(String unread) {
        this.unread = unread;
    }
}
