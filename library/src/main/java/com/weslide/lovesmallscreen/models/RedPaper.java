package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by xu on 2016/8/10.
 * 红包
 */
public class RedPaper extends BaseModel {
    /** 红包id */
    @SerializedName("settingId")
    private int settingId;

    @SerializedName("headImg")
    private String headImg;

    @SerializedName("sellerId")
    private String sellerId;

    @SerializedName("description")
    private String description;

    @SerializedName("name")
    private String name;

    @SerializedName("money")
    private String money;

    @SerializedName("uri")
    private String uri;

    @SerializedName("type")
    private String type;

    @SerializedName("goodsList")
    private List<Goods> goodsList;


    public int getSettingId() { return settingId; }

    public void setSettingId(int settingId) { this.settingId = settingId; }

    public String getHeadImg() { return headImg; }

    public void setHeadImg(String headImg) { this.headImg = headImg; }

    public String getSellerId() { return sellerId; }

    public void setSellerId(String sellerId) { this.sellerId = sellerId; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getMoney() { return money; }

    public void setMoney(String money) { this.money = money; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public List<Goods> getGoodsList() { return goodsList; }

    public void setGoodsList(List<Goods> goodsList) { this.goodsList = goodsList; }

    public String getUri() { return uri; }

    public void setUri(String uri) { this.uri = uri; }
}
