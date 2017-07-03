package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/5/16.
 * 滑屏广告图片
 */
public class AdvertImg extends BaseModel {

    @Expose
    private Long id;
    @SerializedName("advertId")
    private String advertId;
    @SerializedName("level")
    private String level;
    @SerializedName("image")
    private String image;
    @SerializedName("url")
    private String uri;
    @SerializedName("sellerId")
    private String sellerId;

    private String acquireSocre = "false";
    private String acquireSocreNumber;

    /** 图片下载完后的本地路径 */
    private String imageFile;


    /**
     * 广告ID
     */
    public String getAdvertId() {
        return advertId;
    }

    public void setAdvertId(String advertId) {
        this.advertId = advertId;
    }

    /**
     * 广告所属等级
     */
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 广告图片
     */
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 该广告跳转的品牌官网
     */
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 该广告所属的商家
     */
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }


    /**
     * 判断是否已经通过滑屏获得积分了
     */
    public String getAcquireSocre() {
        return acquireSocre;
    }

    public void setAcquireSocre(String acquireSocre) {
        this.acquireSocre = acquireSocre;
    }

    /**
     * 该图片通过滑屏获得的积分
     */
    public String getAcquireSocreNumber() {
        return acquireSocreNumber;
    }

    public void setAcquireSocreNumber(String acquireSocreNumber) {
        this.acquireSocreNumber = acquireSocreNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }
}