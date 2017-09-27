package com.weslide.lovesmallscreen.model_yy.javabean;

/**
 * Created by YY on 2017/7/21.
 */
public class ContactBean extends BaseModel {
    private int iconId;
    private String title;
    private String phoneNum;
    private String firstHeadLetter;
    private boolean isSelect;

    public ContactBean(int iconId, String title, String phoneNum, String firstHeadLetter, boolean isSelect) {
        this.iconId = iconId;
        this.title = title;
        this.phoneNum = phoneNum;
        this.firstHeadLetter = firstHeadLetter;
        this.isSelect = isSelect;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public ContactBean() {

    }

    public int getIconId() {
        return iconId;
    }

    public String getFirstHeadLetter() {
        return firstHeadLetter;
    }

    public void setFirstHeadLetter(String firstHeadLetter) {
        this.firstHeadLetter = firstHeadLetter;
    }
    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

}
