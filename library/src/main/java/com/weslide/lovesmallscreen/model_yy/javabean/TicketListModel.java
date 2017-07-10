package com.weslide.lovesmallscreen.model_yy.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YY on 2017/6/8.
 */
public class TicketListModel extends BaseModel {
    public List<TicketListObModel> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketListObModel> tickets) {
        this.tickets = tickets;
    }

    @SerializedName("tickets")
    List<TicketListObModel> tickets;

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    @SerializedName("ticketType")
    String ticketType;

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    @SerializedName("pageIndex")
    String pageIndex;
}
