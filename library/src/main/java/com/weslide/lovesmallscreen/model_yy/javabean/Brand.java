package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/6/16.
 */
public class Brand extends BaseModel {

    @SerializedName("brand")
    List<BrandModel> brand;

    @SerializedName("shareTitle")
    String shareTitle;

    @SerializedName("shareContent")
    String shareContent;

    @SerializedName("shareIconUrl")
    String shareIconUrl;

    @SerializedName("shareTargetUrl")
    String shareTargetUrl;

    public String getShareTargetUrl() {
        return shareTargetUrl;
    }

    public void setShareTargetUrl(String shareTargetUrl) {
        this.shareTargetUrl = shareTargetUrl;
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

    public String getShareIconUrl() {
        return shareIconUrl;
    }

    public void setShareIconUrl(String shareIconUrl) {
        this.shareIconUrl = shareIconUrl;
    }

    public List<BrandModel> getBrand() {
        return brand;
    }

    public void setBrand(List<BrandModel> brand) {
        this.brand = brand;
    }
}
