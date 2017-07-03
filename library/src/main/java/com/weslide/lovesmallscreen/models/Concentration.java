package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by Dong on 2016/7/18.
 * 积分兑换精选
 */
public class Concentration extends BaseModel{
    @SerializedName("concentrationBanners")
    private List<ImageText> concentrationBanners;
    @SerializedName("concentrationImageTexts")
    private List<ImageText> concentrationImageTexts;

    public void setConcentrationBanners(List<ImageText> concentrationBanners) {
        this.concentrationBanners = concentrationBanners;
    }

    public void setConcentrationImageTexts(List<ImageText> concentrationImageTexts) {
        this.concentrationImageTexts = concentrationImageTexts;
    }

    public List<ImageText> getConcentrationBanners() {
        return concentrationBanners;
    }

    public List<ImageText> getConcentrationImageTexts() {
        return concentrationImageTexts;
    }
}
