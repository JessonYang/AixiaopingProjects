package com.weslide.lovesmallscreen.core;

import android.os.Bundle;

/**
 * Created by xu on 2016/8/13.
 * 用于绑定Activity的生命周期
 */
public interface APeriod {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onPause();

    void onStop();

    void onResume();

    void onRestart();

    void onDestroy();

}
