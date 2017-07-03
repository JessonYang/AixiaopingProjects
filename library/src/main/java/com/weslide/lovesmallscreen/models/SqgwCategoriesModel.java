package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/6/15.
 */
public class SqgwCategoriesModel extends BaseModel {

    @SerializedName("subTitle")
    String subTitle;

    @SerializedName("title")
    String title;

    @SerializedName("price")
    String price;

    @SerializedName("org_price")
    String org_price;

    @SerializedName("image")
    String image;

    @SerializedName("uri")
    String uri;

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrg_price() {
        return org_price;
    }

    public void setOrg_price(String org_price) {
        this.org_price = org_price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @SerializedName("cid")
    String cid;
}
