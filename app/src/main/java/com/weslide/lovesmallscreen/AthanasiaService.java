package com.weslide.lovesmallscreen;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by xu on 2016/8/11.
 * 不死服务
 */
public class AthanasiaService extends Service {

    LockScreenReceiver lockScreenReceiver = new LockScreenReceiver();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(lockScreenReceiver, filter);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // 重启服务标志，如果是正常退出，那么不会重启服务
        if(!ContextParameter.isAthanasiaService()){
            unregisterReceiver(lockScreenReceiver);
            super.onDestroy();
            return;
        }

        Intent localIntent = new Intent();
        localIntent.setClass(this, AthanasiaService.class); // 销毁时重新启动Service
        this.startService(localIntent);
    }
}
