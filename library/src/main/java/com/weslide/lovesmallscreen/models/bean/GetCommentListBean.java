package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/8/16.
 */
public class GetCommentListBean extends BaseModel{
    @SerializedName("pageIndex")
    /** 分页页码，从1开始 */
    private int pageIndex = 0;
    @SerializedName("goodsId")
    /** 分页页码，从1开始 */
    private String goodsId;

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public String getGoodsId() {
        return goodsId;
    }
}
