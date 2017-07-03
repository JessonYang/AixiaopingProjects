package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by xu on 2016/6/4.
 * 商品库存
 */
public class Spec extends BaseModel {

    @SerializedName("name")
    /** 规格名称 */
    private String name;
    @SerializedName("specId")
    /** 规格Id */
    private int specId;
    @SerializedName("specItems")
    /** 规格项 */
    private List<SpecItem> specItems;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<SpecItem> getSpecItems() {
        return specItems;
    }

    public void setSpecItems(List<SpecItem> specItems) {
        this.specItems = specItems;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }
}
