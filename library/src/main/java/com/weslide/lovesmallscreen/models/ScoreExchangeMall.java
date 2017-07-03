package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by Dong on 2016/7/18.
 * 积分兑换精选
 */
public class ScoreExchangeMall extends BaseModel{
    @SerializedName("goodsClassifys")
    private List<ImageText> goodsClassifys;
    @SerializedName("scoreGoodsClassifys")
    private List<ImageText> scoreGoodsClassifys;
    @SerializedName("concentration")
    private Concentration concentration;

    public void setGoodsClassifys(List<ImageText> goodsClassifys) {
        this.goodsClassifys = goodsClassifys;
    }

    public void setScoreGoodsClassifys(List<ImageText> scoreGoodsClassifys) {
        this.scoreGoodsClassifys = scoreGoodsClassifys;
    }

    public void setConcentration(Concentration concentration) {
        this.concentration = concentration;
    }

    public List<ImageText> getGoodsClassifys() {
        return goodsClassifys;
    }

    public List<ImageText> getScoreGoodsClassifys() {
        return scoreGoodsClassifys;
    }

    public Concentration getConcentration() {
        return concentration;
    }
}
