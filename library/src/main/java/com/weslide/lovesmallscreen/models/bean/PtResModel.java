package com.weslide.lovesmallscreen.models.bean;

import com.weslide.lovesmallscreen.model_yy.javabean.BaseModel;
import com.weslide.lovesmallscreen.models.NfcpModel;

import java.util.List;

/**
 * Created by YY on 2017/12/20.
 */
public class PtResModel extends BaseModel {

    private String ptBgImageUrl;

    private List<NfcpModel> ptImages;

    public List<PtGoodModel> getPtGoods() {
        return ptGoods;
    }

    public void setPtGoods(List<PtGoodModel> ptGoods) {
        this.ptGoods = ptGoods;
    }

    public String getPtBgImageUrl() {
        return ptBgImageUrl;
    }

    public void setPtBgImageUrl(String ptBgImageUrl) {
        this.ptBgImageUrl = ptBgImageUrl;
    }

    public List<NfcpModel> getPtImages() {
        return ptImages;
    }

    public void setPtImages(List<NfcpModel> ptImages) {
        this.ptImages = ptImages;
    }

    private List<PtGoodModel> ptGoods;
}
