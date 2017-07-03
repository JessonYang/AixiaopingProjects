package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/6/30.
 */
public class Message extends BaseModel{
    @SerializedName("title")
    private String title;//标题
    @SerializedName("detail")
    private String detail;//消息详情
    @SerializedName("image")
    private String image;//消息头像
    @SerializedName("uri")
    private String uri;//消息链接
    @SerializedName("date")
    private String date;//消息时间

    public void setTitle(String title) { this.title = title; }

    public String getTitle() { return title; }

    public void setDetail(String detail) { this.detail = detail; }

    public String getDetail() { return detail; }

    public void setUri(String uri) { this.uri = uri; }

    public String getUri() { return uri; }

    public void setImage(String image) { this.image = image; }

    public String getImage() { return image; }

    public void setDate(String date) { this.date = date; }

    public String getDate(){ return date; }
}
