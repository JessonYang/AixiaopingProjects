package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by Dong on 2017/3/10.
 */
public class HeadquartersType extends BaseModel{

    @SerializedName("typeList")
    List<GoodsType> typeList;

    @SerializedName("cityList")
    List<CityType> cityList;

    public List<GoodsType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<GoodsType> typeList) {
        this.typeList = typeList;
    }

    public List<CityType> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityType> cityList) {
        this.cityList = cityList;
    }
}
