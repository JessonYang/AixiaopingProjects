package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/9/12.
 * 监控的账号密码
 */
public class MonitorAccount extends BaseModel{

    @SerializedName("name")
    private String name;
    @SerializedName("pwd")
    private String pwd;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPwd() { return pwd; }

    public void setPwd(String pwd) { this.pwd = pwd; }
}
