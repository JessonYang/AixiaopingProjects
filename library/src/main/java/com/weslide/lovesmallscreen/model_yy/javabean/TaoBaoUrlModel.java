package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YY on 2017/9/6.
 */
public class TaoBaoUrlModel extends BaseModel {

    @SerializedName("link")
    String link;

    @SerializedName("pid")
    String pid;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @SerializedName("goodsId")
    String goodsId;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
