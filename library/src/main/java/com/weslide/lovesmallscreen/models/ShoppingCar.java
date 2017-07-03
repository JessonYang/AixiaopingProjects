package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by xu on 2016/6/20.
 * 购物车对象
 */
public class ShoppingCar extends BaseModel {


    @SerializedName("seller")
    private Seller seller;
    @SerializedName("shoppingCarItems")
    private List<ShoppingCarItem> shoppingCarItems;


    /** 购物车商品对应的商家 */
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    /** 购物车中的商品项列表	[{shoppingCarItem},{ shoppingCarItem }] */
    public List<ShoppingCarItem> getShoppingCarItems() {
        return shoppingCarItems;
    }

    public void setShoppingCarItems(List<ShoppingCarItem> shoppingCarItems) {
        this.shoppingCarItems = shoppingCarItems;
    }
}
