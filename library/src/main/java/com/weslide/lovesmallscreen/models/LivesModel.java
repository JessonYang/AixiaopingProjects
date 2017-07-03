package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/6/14.
 */
public class LivesModel extends BaseModel {

    @SerializedName("logo")
    String logo;

    @SerializedName("name")
    String name;

    @SerializedName("livename")
    String livename;

    @SerializedName("liveid")
    String liveid;

    @SerializedName("uri")
    String uri;

    public String getImgae() {
        return imgae;
    }

    public void setImgae(String imgae) {
        this.imgae = imgae;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLivename() {
        return livename;
    }

    public void setLivename(String livename) {
        this.livename = livename;
    }

    public String getLiveid() {
        return liveid;
    }

    public void setLiveid(String liveid) {
        this.liveid = liveid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @SerializedName("imgae")
    String imgae;
}
