package com.weslide.lovesmallscreen.models;

import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by YY on 2017/11/27.
 */
public class TopLocalProductModel extends BaseModel {
    private List<TopLocalModel> topLocalModel;
    private String titleImg;

    public List<TopLocalModel> getTopLocalModel() {
        return topLocalModel;
    }

    public void setTopLocalModel(List<TopLocalModel> topLocalModel) {
        this.topLocalModel = topLocalModel;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }
}
