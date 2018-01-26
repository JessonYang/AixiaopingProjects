package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;

import java.util.List;

/**
 * Created by YY on 2018/1/20.
 */
public class ExchangeGoodModel extends BaseModel {

    //商品id
    @SerializedName("goodsId")
    String goodsId;

    //goodsOrder
    @SerializedName("goodsOrder")
    String goodsOrder;

    //商品名
    @SerializedName("name")
    String name;

    //商品市场价
    @SerializedName("price")
    int price;

    //商品图
    @SerializedName("coverPic")
    String coverPic;

    //想换什么
    @SerializedName("wantlable")
    List<String> wantlable;

    public List<String> getWantlable() {
        return wantlable;
    }

    public void setWantlable(List<String> wantlable) {
        this.wantlable = wantlable;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getGoodsOrder() {
        return goodsOrder;
    }

    public void setGoodsOrder(String goodsOrder) {
        this.goodsOrder = goodsOrder;
    }
}
