package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.network.DataList;

import java.util.List;

/**
 * Created by xu on 2016/6/23.
 * 商品列表
 */
public class GoodsList extends DataList<Goods>{

    @SerializedName("classifications")
    /** 商品分类，是一个二级分类 */
    private List<GoodsType> types;


    public List<GoodsType> getTypes() {
        return types;
    }

    public void setTypes(List<GoodsType> types) {
        this.types = types;
    }
}
