package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xu on 2016/6/28.
 * 购物车请求、响应bean
 */
public class ShoppingCarBean {

    //移除部分
//    shoppingCarItemIds	购物车中的商品项id	[String,String]
    @SerializedName("shoppingCarItemIds")
    /** 购物车中的商品项id */
    private List<String> shoppingCarItemIds;

    //更新部分
//    shoppingCarItemId	购物车中的商品项id
//    number	更新后的数量
//    前台后台都必须验证库存后才能完成修改

    @SerializedName("shoppingCarItemId")
    /** 购物车中的商品项id */
    private String shoppingCarItemId;
    @SerializedName("number")
    /** 更新后的数量 */
    private Integer number;


    //加入购物车部分
//    goodsId	加入购物车的商品ID
//    number	购买数量
//    specs	规格项id	[String,String]
    @SerializedName("goodsId")
    /** 加入购物车的商品ID */
    private String goodsId;
    @SerializedName("specs")
    /** 规格项id	[String,String] */
    private String[] specs;


    public List<String> getShoppingCarItemIds() {
        return shoppingCarItemIds;
    }

    public void setShoppingCarItemIds(List<String> shoppingCarItemIds) {
        this.shoppingCarItemIds = shoppingCarItemIds;
    }

    public String getShoppingCarItemId() {
        return shoppingCarItemId;
    }

    public void setShoppingCarItemId(String shoppingCarItemId) {
        this.shoppingCarItemId = shoppingCarItemId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }


    public String[] getSpecs() {
        return specs;
    }

    public void setSpecs(String[] specs) {
        this.specs = specs;
    }
}
