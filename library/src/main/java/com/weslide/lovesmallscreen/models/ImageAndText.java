package com.weslide.lovesmallscreen.models;

/**
 * Created by Dong on 2016/7/15.
 *
 */
public class ImageAndText {
    private String text;
    private int imageId;
    private int id;

    public void setText(String text) {
        this.text = text;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public int getImageId() {
        return imageId;
    }

    public int getId() {
        return id;
    }
}
