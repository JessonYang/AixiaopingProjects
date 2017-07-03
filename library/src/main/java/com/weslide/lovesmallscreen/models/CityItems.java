package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2017/3/10.
 */
public class CityItems extends BaseModel{

    @SerializedName("cityId")
    private String cityId;

    @SerializedName("cityName")
    private String cityName;

    private Boolean select;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Boolean getSelect() {
        return select == null ? false : select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }
}
