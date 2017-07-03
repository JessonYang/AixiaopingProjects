package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/10/17.
 */
public class Live extends BaseModel{
    @SerializedName("name")
    private String name;//直播名字
    @SerializedName("uri")
    private String uri;//直播链接
    @SerializedName("imgae")
    private String imgae;//直播封面图
    @SerializedName("livename")
    private String livename;
    @SerializedName("viewname")
    private String viewname;
    @SerializedName("address")
    private String address;
    @SerializedName("sellerId")
    private String sellerId;
    @SerializedName("liveid")
    private String liveid;
    @SerializedName("sellerimgae")
    private String sellerimgae;
    @SerializedName("logo")
    private String logo;

    public String getViewname() { return viewname; }

    public String getAddress() { return address; }

    public String getSellerId() { return sellerId;}

    public String getLiveid() { return liveid; }

    public String getSellerimgae() { return sellerimgae; }

    public void setViewname(String viewname) { this.viewname = viewname; }

    public void setAddress(String address) { this.address = address; }

    public void setSellerId(String sellerId) { this.sellerId = sellerId; }

    public void setLiveid(String liveid) { this.liveid = liveid; }

    public void setSellerimgae(String sellerimgae) { this.sellerimgae = sellerimgae; }

    public String getLivename() { return livename; }

    public void setLivename(String livename) { this.livename = livename; }

    public String getLogo() { return logo; }

    public void setLogo(String logo) { this.logo = logo; }

    public String getName() { return name; }

    public String getUri() { return uri; }

    public String getImgae() { return imgae; }

    public void setName(String name) { this.name = name; }

    public void setUri(String uri) { this.uri = uri; }

    public void setImgae(String imgae) { this.imgae = imgae; }
}
