package com.weslide.lovesmallscreen.utils;

import com.weslide.lovesmallscreen.views.beans.OriginalCityAgencyOrderBean;

import java.util.List;

/**
 * Created by YY on 2017/3/24.
 */
public interface CustomCallBack {
    public void onFailure(String errMsg);

    public void onSuccess(List<OriginalCityAgencyOrderBean> list);
}
