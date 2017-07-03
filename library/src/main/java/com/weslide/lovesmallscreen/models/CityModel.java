package com.weslide.lovesmallscreen.models;
import java.util.List;
/**
 * Created by Dong on 2016/6/14.
 */

public class CityModel {
    private String currentName;
    private List<CityModel> cityList;

    private String zipcode;

    public CityModel(){
        super();
    }

    public CityModel(String name,List<CityModel> list){
        super();
        this.currentName = name;
        this.cityList = list;
    }

    public CityModel(String zipcode,String name){
        super();
        this.zipcode = zipcode;
        this.currentName = name;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public List<CityModel> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList = cityList;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
