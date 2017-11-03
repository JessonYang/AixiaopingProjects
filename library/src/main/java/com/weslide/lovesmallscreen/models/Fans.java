package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/7/4.
 */
public class Fans extends BaseModel{
    @SerializedName("name")
    private String name;
    @SerializedName("headImage")
    private String headImage;
    @SerializedName("date")
    private String date;
    @SerializedName("sex")
    private String sex;

    private String chat_latest_time;

    public String getChat_latest_msg() {
        return chat_latest_msg;
    }

    public void setChat_latest_msg(String chat_latest_msg) {
        this.chat_latest_msg = chat_latest_msg;
    }

    public String getChat_latest_time() {
        return chat_latest_time;
    }

    public void setChat_latest_time(String chat_latest_time) {
        this.chat_latest_time = chat_latest_time;
    }

    private String chat_latest_msg;

    public String getFansId() {
        return fansId;
    }

    public void setFansId(String fansId) {
        this.fansId = fansId;
    }

    @SerializedName("fansId")
    private String fansId;

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void setHeadImage(String image) { this.headImage = image; }

    public String getHeadImage() { return headImage; }

    public void setDate(String date) { this.date = date; }

    public String getDate() { return date; }

    public void setSex(String sex) { this.sex = sex; }

   public String getSex() { return sex; }
}
