package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;

/**
 * Created by YY on 2017/12/25.
 */
public class NewGoodDetailModel extends BaseModel {

    @SerializedName("detailType")
    String detailType;

    @SerializedName("text")
    String text;

    @SerializedName("pictureHeight")
    String pictureHeight;

    @SerializedName("pictureWidth")
    String pictureWidth;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPictureHeight() {
        return pictureHeight;
    }

    public void setPictureHeight(String pictureHeight) {
        this.pictureHeight = pictureHeight;
    }

    public String getPictureWidth() {
        return pictureWidth;
    }

    public void setPictureWidth(String pictureWidth) {
        this.pictureWidth = pictureWidth;
    }

    @SerializedName("picture")
    String picture;
}
