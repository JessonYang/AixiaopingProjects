package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.network.DataList;

/**
 * Created by xu on 2016/7/11.
 * 城市列表
 */
public class ZoneList extends DataList<Zone> {

    @SerializedName("version")
    /** 因为城市列表数据量过大，不能频繁的请求。通过给城市列表设置版本号，后台决定是否需要更新。 */
    private String version;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
