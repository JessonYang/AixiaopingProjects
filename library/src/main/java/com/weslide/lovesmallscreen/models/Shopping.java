package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by Dong on 2017/3/7.
 */
public class Shopping extends BaseModel{
    @SerializedName("fruit")
    TaoKe fruit;

    @SerializedName("life")
    TaoKe life;

    @SerializedName("xiaopingCentre")
    TaoKe xiaopingCentre;

    @SerializedName("hot")
    TaoKe hot;

    @SerializedName("shopping")
    TaoKe shopping;

    public TaoKe getFruit() { return fruit; }

    public void setFruit(TaoKe fruit) { this.fruit = fruit; }

    public TaoKe getLife() { return life; }

    public void setLife(TaoKe life) { this.life = life; }

    public TaoKe getXiaopingCentre() { return xiaopingCentre; }

    public void setXiaopingCentre(TaoKe xiaopingCentre) { this.xiaopingCentre = xiaopingCentre; }

    public TaoKe getHot() { return hot; }

    public void setHot(TaoKe hot) { this.hot = hot; }

    public TaoKe getShopping() { return shopping; }

    public void setShopping(TaoKe shopping) { this.shopping = shopping; }
}
