package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/7/6.
 */
public class ScoreBean extends BaseModel{
    @SerializedName("pageIndex")
    /** 分页页码，从1开始 */
    private int pageIndex = 0;

    public int getPageIndex() { return pageIndex; }

    public void setPageIndex(int pageIndex) { this.pageIndex = pageIndex; }
}
