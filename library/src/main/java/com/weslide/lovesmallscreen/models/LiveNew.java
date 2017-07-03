package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by Dong on 2017/3/9.
 */
public class LiveNew extends BaseModel{

    @SerializedName("lives")
    private List<Live> lives;

    @SerializedName("header")
    private TaoKe header;

    public List<Live> getLives() {
        return lives;
    }

    public void setLives(List<Live> lives) {
        this.lives = lives;
    }

    public TaoKe getHeader() {
        return header;
    }

    public void setHeader(TaoKe header) {
        this.header = header;
    }
}
