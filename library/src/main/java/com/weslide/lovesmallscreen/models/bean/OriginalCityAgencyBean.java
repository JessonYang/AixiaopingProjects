package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;
import com.weslide.lovesmallscreen.model_yy.javabean.OrderDetailModel;
import com.weslide.lovesmallscreen.model_yy.javabean.PartnerInfoModel;
import com.weslide.lovesmallscreen.model_yy.javabean.ProfitDetailModel;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketAllModel;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketTypesModel;

import java.util.List;

/**
 * Created by YY on 2017/3/24.
 */
public class OriginalCityAgencyBean extends BaseModel {
    public int getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(int isPartner) {
        this.isPartner = isPartner;
    }

    @SerializedName("isPartner")
    private int isPartner;

    @SerializedName("profitDetail")
    private ProfitDetailModel profitDetail;

    @SerializedName("orderDetail")
    private OrderDetailModel orderDetail;

    public ProfitDetailModel getProfitDetail() {
        return profitDetail;
    }

    public void setProfitDetail(ProfitDetailModel profitDetail) {
        this.profitDetail = profitDetail;
    }

    public OrderDetailModel getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailModel orderDetail) {
        this.orderDetail = orderDetail;
    }

    public PartnerInfoModel getPartnerInfo() {
        return partnerInfo;
    }

    public void setPartnerInfo(PartnerInfoModel partnerInfo) {
        this.partnerInfo = partnerInfo;
    }

    public List<TicketTypesModel> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<TicketTypesModel> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    public List<TicketAllModel> getTicketAll() {
        return ticketAll;
    }

    public void setTicketAll(List<TicketAllModel> ticketAll) {
        this.ticketAll = ticketAll;
    }

    @SerializedName("profit")
    private Profit profit;

    @SerializedName("partnerInfo")
    private PartnerInfoModel partnerInfo;

    @SerializedName("ticketTypes")
    private List<TicketTypesModel> ticketTypes;

    @SerializedName("ticketAll")
    private List<TicketAllModel> ticketAll;

    @SerializedName("user_id")
    private String user_id;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    @SerializedName("totalPage")
    private String totalPage;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @SerializedName("direction")
    private String direction;

    @SerializedName("profitDirection")
    private String profitDirection;

    @SerializedName("realProfitDirection")
    private String realProfitDirection;

    public String getSettleDirection() {
        return settleDirection;
    }

    public void setSettleDirection(String settleDirection) {
        this.settleDirection = settleDirection;
    }

    public String getProfitDirection() {
        return profitDirection;
    }

    public void setProfitDirection(String profitDirection) {
        this.profitDirection = profitDirection;
    }

    public String getRealProfitDirection() {
        return realProfitDirection;
    }

    public void setRealProfitDirection(String realProfitDirection) {
        this.realProfitDirection = realProfitDirection;
    }

    @SerializedName("settleDirection")
    private String settleDirection;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Profit getProfit() {
        return profit;
    }

    public void setProfit(Profit profit) {
        this.profit = profit;
    }
}
