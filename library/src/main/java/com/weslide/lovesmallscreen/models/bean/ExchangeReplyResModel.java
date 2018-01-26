package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;

import java.util.List;

/**
 * Created by YY on 2018/1/20.
 */
public class ExchangeReplyResModel extends BaseModel {

    @SerializedName("noteList")
    List<ExchangeReplyModel> noteList;

    @SerializedName("pageIndex")
    int pageIndex;

    @SerializedName("pageSize")
    int pageSize;

    @SerializedName("pageItemCount")
    int pageItemCount;

    public List<ExchangeReplyModel> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<ExchangeReplyModel> noteList) {
        this.noteList = noteList;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageItemCount() {
        return pageItemCount;
    }

    public void setPageItemCount(int pageItemCount) {
        this.pageItemCount = pageItemCount;
    }
}
