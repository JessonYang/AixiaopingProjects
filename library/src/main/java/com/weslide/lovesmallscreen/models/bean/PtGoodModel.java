package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;

/**
 * Created by YY on 2017/12/20.
 */
public class PtGoodModel extends BaseModel {

    //商品id
    @SerializedName("goodsId")
    String goodsId;

    //图片链接
    @SerializedName("image")
    String image;

    //商品名称
    @SerializedName("name")
    String name;

    //商品展示价格

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("price")
    String price;
}
