package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/6/26.
 */
public class ApplyPartnerModel extends BaseModel {

    @SerializedName("links")
    List<String> links;

    @SerializedName("tips")
    List<List<String>> tips;

    @SerializedName("tipsPhone")
    List<String> tipsPhone;

    @SerializedName("shareIconUrl")
    String shareIconUrl;

    @SerializedName("shareTitle")
    String shareTitle;

    @SerializedName("shareTargetUrl")
    String shareTargetUrl;

    @SerializedName("shareContent")
    String shareContent;

    public String getShareIconUrl() {
        return shareIconUrl;
    }

    public void setShareIconUrl(String shareIconUrl) {
        this.shareIconUrl = shareIconUrl;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareTargetUrl() {
        return shareTargetUrl;
    }

    public void setShareTargetUrl(String shareTargetUrl) {
        this.shareTargetUrl = shareTargetUrl;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public List<List<String>> getTips() {
        return tips;
    }

    public void setTips(List<List<String>> tips) {
        this.tips = tips;
    }

    public List<String> getTipsPhone() {
        return tipsPhone;
    }

    public void setTipsPhone(List<String> tipsPhone) {
        this.tipsPhone = tipsPhone;
    }
}
