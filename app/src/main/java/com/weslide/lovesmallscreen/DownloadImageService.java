package com.weslide.lovesmallscreen;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.weslide.lovesmallscreen.dao.sp.LocalConfigSP;
import com.weslide.lovesmallscreen.models.AdvertImg;
import com.weslide.lovesmallscreen.models.GlideAdvert;
import com.weslide.lovesmallscreen.models.bean.GetAdverImgsBean;
import com.weslide.lovesmallscreen.models.eventbus_message.DownloadImageMessage;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.StatusCode;
import com.weslide.lovesmallscreen.utils.FileUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.MD5Tool;
import com.weslide.lovesmallscreen.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xu on 2016/5/25.
 * 用于下载图片的Service
 */
public class DownloadImageService extends Service {
    public static final String KEY_FRESHEN = "KEY_FRESHEN";
    private boolean freshen = false;

    private String photoSavePath;//保存路径
    private File photoSaveFile;
    public static final String IMAGE_PATH = "LoveSmallScreenProject/new_advert_image";
    private SharedPreferences cityInfo;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                freshen = bundle.getBoolean(KEY_FRESHEN, false);
            }
        }


        photoSavePath = Environment.getExternalStorageDirectory() + "/" + IMAGE_PATH + "/";

        //判断本地是否存在缓存目录
        photoSaveFile = new File(photoSavePath);
        if (!photoSaveFile.exists()) {
            photoSaveFile.mkdirs();
        }

        Observable.just(null)
                .filter(new Func1<Object, Boolean>() {  //判断时间间隔是否已到达
                    @Override
                    public Boolean call(Object o) {
                        long interval = new Date().getTime() - ContextParameter.getLocalConfig().getGlideImgRequestTime();
                        if (freshen) {
                            L.e("用户主动强制刷新");
                            freshen = false;
                            return true;
                        } else if (interval >= ContextParameter.getClientConfig().getScreenAdvertLoadTime()) {
                            L.e("间隔时间已到达");
                            return true;
                        } else {
                            L.e("间隔时间未到达，剩余：" + (ContextParameter.getClientConfig().getScreenAdvertLoadTime() - interval));
                            return false;
                        }
                    }
                })
                .filter(o -> NetworkUtils.isConnected(DownloadImageService.this)) //检查网络
                .observeOn(Schedulers.computation())
                .map(new Func1<Object, com.weslide.lovesmallscreen.network.Response<GlideAdvert>>() {
                    @Override
                    public com.weslide.lovesmallscreen.network.Response<GlideAdvert> call(Object o) {

                        String p1 = getSharedPreferences("poolInfo", MODE_PRIVATE).getString("pool1", "0");
                        String p2 = getSharedPreferences("poolInfo", MODE_PRIVATE).getString("pool2", "0");
                        String p3 = getSharedPreferences("poolInfo", MODE_PRIVATE).getString("pool3", "0");
                        String p4 = getSharedPreferences("poolInfo", MODE_PRIVATE).getString("pool4", "0");

//                        Log.d("雨落无痕丶", "call 1: "+p1+";"+p2+";"+p3+";"+p4);

                        com.weslide.lovesmallscreen.network.Request<GetAdverImgsBean> request = new com.weslide.lovesmallscreen.network.Request<GetAdverImgsBean>();
                        GetAdverImgsBean imgsBean = new GetAdverImgsBean();
                        imgsBean.setPool1(p1);
                        imgsBean.setPool2(p2);
                        imgsBean.setPool3(p3);
                        imgsBean.setPool4(p4);
//                        request.setData(GlideScreenClientSerialize.getClientConfig(DownloadImageService.this));
                        request.setData(imgsBean);
                        try {
                            com.weslide.lovesmallscreen.network.Response<GlideAdvert> response = HTTP.getAPI().getAdverImgs(HTTP.formatJSONData(request)).execute().body();
                            return response;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .filter(glideAdvertResponse -> StatusCode.validateSuccess(this, glideAdvertResponse))
                .map(new Func1<com.weslide.lovesmallscreen.network.Response<GlideAdvert>, com.weslide.lovesmallscreen.network.Response<GlideAdvert>>() {
                    @Override
                    public com.weslide.lovesmallscreen.network.Response<GlideAdvert> call(com.weslide.lovesmallscreen.network.Response<GlideAdvert> glideAdvertResponse) {

                        //下载图片
                        downloadImage(glideAdvertResponse.getData().getAdvertImgs());

                        return glideAdvertResponse;
                    }
                })
                .map(new Func1<com.weslide.lovesmallscreen.network.Response<GlideAdvert>, ArrayList<AdvertImg>>() {
                    @Override
                    public ArrayList<AdvertImg> call(com.weslide.lovesmallscreen.network.Response<GlideAdvert> glideAdvertResponse) {


                        //将线程池的数据进行缓存
                        /*GetAdverImgsBean bean = new GetAdverImgsBean();
                        bean.setPool1(glideAdvertResponse.getData().getPool1());
                        bean.setPool2(glideAdvertResponse.getData().getPool2());
                        bean.setPool3(glideAdvertResponse.getData().getPool3());
                        bean.setPool4(glideAdvertResponse.getData().getPool4());
                        GlideScreenClientSerialize.setClientConfig(DownloadImageService.this, bean);*/

                        cityInfo = getSharedPreferences("poolInfo", MODE_PRIVATE);
                        SharedPreferences.Editor edit = cityInfo.edit();
                        edit.putString("pool1", glideAdvertResponse.getData().getPool1());
                        edit.putString("pool2", glideAdvertResponse.getData().getPool2());
                        edit.putString("pool3", glideAdvertResponse.getData().getPool3());
                        edit.putString("pool4", glideAdvertResponse.getData().getPool4());
                        edit.commit();

//                        Log.d("雨落无痕丶", "call2: "+glideAdvertResponse.getData().getPool1()+";"+glideAdvertResponse.getData().getPool2()+";"+glideAdvertResponse.getData().getPool3()+";"+glideAdvertResponse.getData().getPool4());

                        //将广告数据进行缓存
                        ArchitectureAppliation.getDaoSession().getAdvertImgDao().deleteAll();
                        ArchitectureAppliation.getDaoSession().getAdvertImgDao().insertInTx(glideAdvertResponse.getData().getAdvertImgs());

                        return glideAdvertResponse.getData().getAdvertImgs();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<AdvertImg>>() {

                    @Override
                    public void onCompleted() {
                        //发送下载完成广播
                        EventBus.getDefault().post(new DownloadImageMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("error", "error", e);
                        //发送下载完成广播
                        EventBus.getDefault().post(new DownloadImageMessage());
                    }

                    @Override
                    public void onNext(ArrayList<AdvertImg> advertImgs) {

                        //设置滑屏广告请求事件
                        ContextParameter.getLocalConfig().setGlideImgRequestTime(new Date().getTime());
                        LocalConfigSP.setLocalConfig(ContextParameter.getLocalConfig());

                    }
                });
        return START_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    public void downloadImage(List<AdvertImg> advertImgList) {

        String[] imageFiles = photoSaveFile.list();

        X:
        for (int i = 0; i < advertImgList.size(); i++) {

            String url = advertImgList.get(i).getImage();

            if (imageFiles != null && imageFiles.length != 0) {
                for (String string : imageFiles) {

                    //MD5匹配
                    if (MD5Tool.isValidate(url, string)) {
                        //缓存已经存在
                        advertImgList.get(i).setImageFile(photoSavePath + "/" + string);
                        continue X;
                    }
                }
            }

            L.e("下载匹配成功，即将开始下载" + url);

            OkHttpClient client = HTTP.getOkHttpClient();

            try {
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                InputStream is = response.body().byteStream();

                String filePath = photoSavePath + "/" + MD5Tool.MD5Encode(url);

                FileUtils.inputStreamSaveFile(is, filePath);

                is.close();

                advertImgList.get(i).setImageFile(filePath);

                L.e(advertImgList.get(i).getImageFile() + "下载完成");

            } catch (IOException e) {
                e.printStackTrace();

                L.e(advertImgList.get(i).getImageFile() + "下载失败");
            }


        }

    }

}
