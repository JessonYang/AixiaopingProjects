package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by Dong on 2016/7/6.
 */
public class FeedBack extends BaseModel{
    @SerializedName("content")
    private String content;
    @SerializedName("Images")
    private List<String> Images;

    public void setContent(String content) { this.content = content; }

    public String getContent() { return content; }


    public void setImages(List<String> images) { Images = images; }



    public List<String> getImages() { return Images; }
}
