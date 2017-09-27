package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.models.GoodsType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/8/1.
 */
public class TypeListBean {

    @SerializedName("typeList")
    private List<GoodsType> typeList = new ArrayList<>();

    @SerializedName("areaType")
    private String areaType;

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public List<GoodsType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<GoodsType> typeList) {
        this.typeList = typeList;
    }
}
