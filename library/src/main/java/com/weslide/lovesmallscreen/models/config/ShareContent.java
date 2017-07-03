package com.weslide.lovesmallscreen.models.config;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/8/8.
 * 分享内容
 */
public class ShareContent extends BaseModel {
//    title	标题
//    iconUrl	图标
//    targetUrl	跳转链接	必要参数userId、zoneId
//    content	内容

    @SerializedName("title")
    private String title;
    @SerializedName("iconUrl")
    private String iconUrl;
    @SerializedName("targetUrl")
    private String targetUrl;
    @SerializedName("content")
    private String content;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
