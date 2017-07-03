package com.weslide.lovesmallscreen;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.utils.L;

/**
 * Created by Dong on 2016/9/27.
 */
public class RefushService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.e("服务有");
        new Thread(){
            @Override
            public void run() {
                while (true) {

                    L.e("test", "启动下载服务");

                    Intent advertIntent = new Intent(RefushService.this, DownloadImageService.class);
                    advertIntent.putExtra(DownloadImageService.KEY_FRESHEN, true);
                    RefushService.this.startService(advertIntent);

                    try {
                        Thread.sleep(1000 * 60 * 30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

//    private class MyThread extends Thread{
//        @Override
//        public void run() {
//            while (true){
//
//                try {
//                    Thread.sleep(30 * (1000 * 60));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Intent intent = new Intent(RefushService.this, DownloadImageService.class);
//                intent.putExtra(DownloadImageService.KEY_FRESHEN, true);
//                RefushService.this.startService(intent);
//            }
//        }
//    }
}
