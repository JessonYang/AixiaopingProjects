package com.weslide.lovesmallscreen.models.bean;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.core.BaseModel;
import com.weslide.lovesmallscreen.dao.serialize.GlideScreenClientSerialize;

/**
 * Created by xu on 2016/5/30.
 * 用于请求滑屏广告的参数
 * 请求参数已经才构造函数中设置
 */
public class GetAdverImgsBean extends BaseModel {

    /** 广告池 */
    @SerializedName("pool1")
    private String pool1 = "0";
    @SerializedName("pool2")
    private String pool2 = "0";
    @SerializedName("pool3")
    private String pool3 = "0";
    @SerializedName("pool4")
    private String pool4 = "0";


    public String getPool1() {
        return pool1;
    }

    public void setPool1(String pool1) {
        this.pool1 = pool1;
    }

    public String getPool2() {
        return pool2;
    }

    public void setPool2(String pool2) {
        this.pool2 = pool2;
    }

    public String getPool3() {
        return pool3;
    }

    public void setPool3(String pool3) {
        this.pool3 = pool3;
    }

    public String getPool4() {
        return pool4;
    }

    public void setPool4(String pool4) {
        this.pool4 = pool4;
    }
}
