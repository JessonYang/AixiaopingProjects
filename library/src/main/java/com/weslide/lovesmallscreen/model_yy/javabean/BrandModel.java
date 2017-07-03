package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/6/16.
 */
public class BrandModel extends BaseModel {

    @SerializedName("bid")
    String bid;

    @SerializedName("name")
    String name;

    @SerializedName("logo")
    String logo;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @SerializedName("keyword")
    String keyword;
}
