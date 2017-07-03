package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/6/7.
 */
public class HomeTicketsModel extends BaseModel {

    @SerializedName("totalPage")
    String totalPage;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<TicketAllModel> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketAllModel> tickets) {
        this.tickets = tickets;
    }

    @SerializedName("tickets")
    List<TicketAllModel> tickets;

    @SerializedName("typeId")
    String typeId;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @SerializedName("page")
    String page;
}
