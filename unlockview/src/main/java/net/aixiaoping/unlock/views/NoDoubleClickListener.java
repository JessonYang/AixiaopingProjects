package net.aixiaoping.unlock.views;

import android.view.View;

import java.util.Calendar;

/**
 * Created by YY on 2018/1/22.
 * 防止重复点击
 */
public abstract class NoDoubleClickListener implements View.OnClickListener{

    public abstract void noDoubleClick(View view);
    private final long CLICK_MIN_DELAY_TIME = 2000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > CLICK_MIN_DELAY_TIME) {
            noDoubleClick(view);
            lastClickTime = currentTime;
        }
    }
}
