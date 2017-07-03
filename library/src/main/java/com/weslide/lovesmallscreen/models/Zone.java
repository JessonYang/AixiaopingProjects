package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by xu on 2016/5/25.
 * 城市节点
 */
public  class Zone extends BaseModel {
    private Long id;

    @SerializedName("level")
    private String level = "2";
//    private String level ;
    @SerializedName("name")
    private String name = "广州市";
//    private String name ;
    @SerializedName("englishChar")
    private String englishChar = "G";
//    private String englishChar ;


    @SerializedName("zoneId")
    private String zoneId = "1961";
//    private String zoneId ;

    @SerializedName("parentZoneId")
    /** 上级地区Id，如无上级则为-1 */
    private String parentZoneId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 级别 2：市  3：区、县、地级市
     */
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 名字
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 首字母标示
     */
    public String getEnglishChar() {
        return englishChar;
    }

    public void setEnglishChar(String englishChar) {
        this.englishChar = englishChar;
    }

    /**
     * 城市的唯一标示
     */
    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }


    public String getParentZoneId() {
        return parentZoneId;
    }

    public void setParentZoneId(String parentZoneId) {
        this.parentZoneId = parentZoneId;
    }
}
