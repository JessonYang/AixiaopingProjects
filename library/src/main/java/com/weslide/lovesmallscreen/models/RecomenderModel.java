package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by YY on 2017/11/2.
 */
public class RecomenderModel extends BaseModel {

    @SerializedName("recommenderName")
    String recommenderName;

    @SerializedName("recommenderHeadImage")
    String recommenderHeadImage;

    @SerializedName("recommenderId")
    String recommenderId;

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }

    @SerializedName("fansNum")
    String fansNum;

    public String getRecommenderId() {
        return recommenderId;
    }

    public void setRecommenderId(String recommenderId) {
        this.recommenderId = recommenderId;
    }

    public String getRecommenderName() {
        return recommenderName;
    }

    public void setRecommenderName(String recommenderName) {
        this.recommenderName = recommenderName;
    }

    public String getRecommenderHeadImage() {
        return recommenderHeadImage;
    }

    public void setRecommenderHeadImage(String recommenderHeadImage) {
        this.recommenderHeadImage = recommenderHeadImage;
    }

}
