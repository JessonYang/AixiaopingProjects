package com.weslide.lovesmallscreen.models;


import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2017/1/17.
 */
public class ImageUp extends BaseModel {

    @SerializedName("image")
    private String image;

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }
}
