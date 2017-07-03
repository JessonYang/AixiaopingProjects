package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.network.DataList;

/**
 * Created by xu on 2016/6/21.
 * 商品评论列表
 */
public class CommentList extends DataList<Comment> {

    @SerializedName("goodCommentPraise")
    private String goodCommentPraise;
    @SerializedName("commentCount")
    private String commentCount;


    /** 好评率(99%) */
    public String getGoodCommentPraise() {
        return goodCommentPraise;
    }

    public void setGoodCommentPraise(String goodCommentPraise) {
        this.goodCommentPraise = goodCommentPraise;
    }

    /** 总评论数 */
    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
