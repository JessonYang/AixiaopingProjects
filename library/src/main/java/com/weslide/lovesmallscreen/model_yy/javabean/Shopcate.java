package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dong on 2017/2/28.
 */
public class Shopcate extends BaseModel{
    @SerializedName("categoryName")
    private String categoryName;

    @SerializedName("categoryId")
    private String categoryId;

    @SerializedName("categoryItems")
    private List<ShopcategoryInfo> categoryItems;

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getCategoryId() { return categoryId; }

    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public List<ShopcategoryInfo> getCategoryItems() { return categoryItems; }

    public void setCategoryItems(List<ShopcategoryInfo> categoryItems) { this.categoryItems = categoryItems; }
}
