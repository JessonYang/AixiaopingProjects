package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/10/26.
 */
public class Show extends BaseModel{

    @SerializedName("showChange")
    private String showChange;

    public String getShowChange() { return showChange; }

    public void setShowChange(String showChange) { this.showChange = showChange; }
}
