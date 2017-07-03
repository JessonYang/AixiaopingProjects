package com.weslide.lovesmallscreen.utils;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by YY on 2017/3/24.
 */
public class OkHttpUtils {
    private static OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build();
    private static Handler mHandler = new Handler();

    public void getNetData(String httpUrl,CustomCallBack customCallBack){

    }
}
