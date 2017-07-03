package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

import java.util.List;

/**
 * Created by YY on 2017/3/27.
 */
public class MyPartners extends BaseModel {
    @SerializedName("partnerCount")
    private String partnerCount;

    @SerializedName("partnerVacanc")
    private String partnerVacanc;

    @SerializedName("partnerPlace")
    private String partnerPlace;

    @SerializedName("pageIndex")
    private int pageIndex;

    @SerializedName("seach")
    private String seach;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @SerializedName("totalPage")
    private String totalPage;

    public String getPartnerCount() {
        return partnerCount;
    }

    public void setPartnerCount(String partnerCount) {
        this.partnerCount = partnerCount;
    }

    public String getPartnerVacanc() {
        return partnerVacanc;
    }

    public void setPartnerVacanc(String partnerVacanc) {
        this.partnerVacanc = partnerVacanc;
    }

    public String getPartnerPlace() {
        return partnerPlace;
    }

    public void setPartnerPlace(String partnerPlace) {
        this.partnerPlace = partnerPlace;
    }

    @SerializedName("partners")
    private List<PartnersOb> partnersObs;

    public String getUser_id() {
        return user_id;
    }

    public String getSeach() {
        return seach;
    }

    public void setSeach(String seach) {
        this.seach = seach;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @SerializedName("user_id")
    private String user_id;

    public List<PartnersOb> getPartnersObs() {
        return partnersObs;
    }

    public void setPartnersObs(List<PartnersOb> partnersObs) {
        this.partnersObs = partnersObs;
    }
}
