package com.weslide.lovesmallscreen.models;

import com.google.gson.annotations.SerializedName;
import com.weslide.lovesmallscreen.core.BaseModel;

/**
 * Created by Dong on 2016/8/3.
 */
public class Password extends BaseModel{
    @SerializedName("phone")//电话号码
    private String phone;
    @SerializedName("captcha")//验证码
    private String captcha;
    @SerializedName("oldpwd")//旧密码
    private String oldpwd;
    @SerializedName("newpwd")//新密码
    private String newpwd ;
    @SerializedName("confirmpwd")//确认新密码
    private String confirmpwd;

    public String getPhone() { return phone; }

    public String getCaptcha() { return captcha; }

    public String getOldpwd() { return oldpwd; }

    public String getNewpwd() { return newpwd; }

    public String getConfirmpwd() { return confirmpwd; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setCaptcha(String captcha) { this.captcha = captcha; }

    public void setOldpwd(String oldpwd) { this.oldpwd = oldpwd; }

    public void setNewpwd(String newpwd) { this.newpwd = newpwd; }

    public void setConfirmpwd(String confirmpwd) { this.confirmpwd = confirmpwd; }
}
