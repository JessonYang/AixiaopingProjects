package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/6/16.
 */
public class Cate extends BaseModel {

    @SerializedName("bigCate")
    List<CateModel> bigCate;

    @SerializedName("shareTitle")
    String shareTitle;

    @SerializedName("shareContent")
    String shareContent;

    @SerializedName("shareIconUrl")
    String shareIconUrl;

    @SerializedName("shareTargetUrl")
    String shareTargetUrl;

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

    public String getShareTargetUrl() {
        return shareTargetUrl;
    }

    public void setShareTargetUrl(String shareTargetUrl) {
        this.shareTargetUrl = shareTargetUrl;
    }

    public List<CateModel> getCate() {
        return cate;
    }

    public void setCate(List<CateModel> cate) {
        this.cate = cate;
    }

    public List<CateModel> getBigCate() {
        return bigCate;
    }

    public void setBigCate(List<CateModel> bigCate) {
        this.bigCate = bigCate;
    }

    @SerializedName("cate")
    List<CateModel> cate;
}
