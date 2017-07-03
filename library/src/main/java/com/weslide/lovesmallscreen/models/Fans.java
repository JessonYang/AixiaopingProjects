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

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void setHeadImage(String image) { this.headImage = image; }

    public String getHeadImage() { return headImage; }

    public void setDate(String date) { this.date = date; }

    public String getDate() { return date; }

    public void setSex(String sex) { this.sex = sex; }

   public String getSex() { return sex; }
}
