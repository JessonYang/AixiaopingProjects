package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by Dong on 2017/3/10.
 */
public class CityType extends BaseModel{
    @SerializedName("provinceId")
    private String provinceId;

    @SerializedName("provinceName")
    private String provinceName;

    @SerializedName("cityItems")
    private List<CityItems> cityItemses;

    private Boolean select;

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<CityItems> getCityItemses() {
        return cityItemses;
    }

    public void setCityItemses(List<CityItems> cityItemses) {
        this.cityItemses = cityItemses;
    }

    public Boolean getSelect() {
        return select == null ? false : select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }
}
