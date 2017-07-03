package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/7/7.
 */
public class Exempt extends BaseModel {

    @SerializedName("name")
    private String name;
    @SerializedName("date")
    private String date;
    @SerializedName("type")
    private String type;

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void setDate(String date) { this.date = date; }

    public String getDate() { return date; }

    public void setType(String type) { this.type = type; }

    public String getType() { return type; }
}
