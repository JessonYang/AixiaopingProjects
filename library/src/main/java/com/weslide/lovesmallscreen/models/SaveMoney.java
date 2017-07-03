package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by Dong on 2017/3/7.
 */
public class SaveMoney extends BaseModel{
    @SerializedName("20yuan")
    TaoKe twenty;

    @SerializedName("cjyh")
    TaoKe superPreferential;

    @SerializedName("sqgw")
    TaoKe save;

    @SerializedName("9kuai9")
    TaoKe nine;

    public TaoKe getTwenty() { return twenty; }

    public void setTwenty(TaoKe twenty) { this.twenty = twenty; }

    public TaoKe getSuperPreferential() { return superPreferential; }

    public void setSuperPreferential(TaoKe superPreferential) { this.superPreferential = superPreferential; }

    public TaoKe getSave() { return save; }

    public void setSave(TaoKe save) { this.save = save; }

    public TaoKe getNine() { return nine; }

    public void setNine(TaoKe nine) { this.nine = nine; }
}
