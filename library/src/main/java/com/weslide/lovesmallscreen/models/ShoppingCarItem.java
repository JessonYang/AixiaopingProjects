package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xu on 2016/6/20.
 * 购物车项
 */
public class ShoppingCarItem {

    private boolean selected;
    private float sumPrice;

    @SerializedName("shoppingCarItemId")
    private String shoppingCarItemId;
    @SerializedName("goods")
    private Goods goods;
    @SerializedName("number")
    private int number;
    @SerializedName("specString")
    private String specString;
    @SerializedName("stock")
    /** 商品库存 */
    private int stock;


    /** 购物车商品项id */
    public String getShoppingCarItemId() {
        return shoppingCarItemId;
    }

    public void setShoppingCarItemId(String shoppingCarItemId) {
        this.shoppingCarItemId = shoppingCarItemId;
    }

    /** 商品信息	{goods} */
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    /** 数量 */
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /** 前台用于显示的规格信息 */
    public String getSpecString() {
        return specString;
    }

    public void setSpecString(String specString) {
        this.specString = specString;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    /** 是否被用户选中 */
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    /** 该购物车项需要支付的现金 */
    public float getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(float sumPrice) {
        this.sumPrice = sumPrice;
    }
}
