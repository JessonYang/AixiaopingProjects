package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/12/26.
 */
public class TaoKe extends BaseModel{

    @SerializedName("title")
    private String title;

    @SerializedName("price")
    private String price;

    @SerializedName("image")
    private String image;

    @SerializedName("org_price")
    private String org_price;

    @SerializedName("uri")
    private String uri;

    @SerializedName("cid")
    private String cid;

    @SerializedName("total")
    private String total;

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public String getOrg_price() { return org_price; }

    public void setOrg_price(String org_price) { this.org_price = org_price; }

    public String getUri() { return uri; }

    public void setUri(String uri) { this.uri = uri; }

    public String getCid() { return cid; }

    public void setCid(String cid) { this.cid = cid; }

    public String getTotal() { return total; }

    public void setTotal(String total) { this.total = total; }
}
