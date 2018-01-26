package com.weslide.lovesmallscreen.utils;

import android.content.Context;

import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.core.UploadSubscriber;
import com.weslide.lovesmallscreen.models.bean.UploadFileBean;
import com.weslide.lovesmallscreen.network.API;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.network.StatusCode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xu on 2016/6/13.
 */
public class RXUtils {

    private RXUtils() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 网络数据请求
     *
     * @param context
     * @param request
     * @param apiMethodName
     * @param subscriber
     */
    public static void request(final Context context, final Request request, final String apiMethodName, final SupportSubscriber subscriber) {

        Observable.just(null)
                .filter(new Func1<Object, Boolean>() {  //判断
                    @Override
                    public Boolean call(Object o) {
                        boolean network = NetworkUtils.isConnected(context);

                        if (!network) {
                            L.i("RXUtils----------onNoNetwork");
                            subscriber.onNoNetwork();
                        }
                        return network;
                    }
                })
                .observeOn(Schedulers.computation())
                .map(new Func1<Object, Response>() {
                    @Override
                    public Response call(Object o) {
                        retrofit2.Call call = (retrofit2.Call) ReflectionUtils.methodInvoke(API.class.getName(), apiMethodName, new Class[]{String.class}, new Object[]{HTTP.formatJSONData(request)}, HTTP.getAPI());
                        Object obj = null;
                        try {
                            obj = call.execute().body();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (obj == null) {
                            return null;
                        }

                        Response response = (Response) obj;
                        if (response.getStatus() == StatusCode.SUCCESS) {
                            L.i("RXUtils----------handlerResponse");
                            subscriber.handlerResponse(response);
                        } else {
                            L.e("请求错误，错误码：" + response.getStatus() + ", 错误内容：" + response.getMessage());
                        }
                        return response;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<Response, Boolean>() {
                    @Override
                    public Boolean call(Response response) {
                        boolean success = StatusCode.validateSuccess(context, response);
                        if (!success) {
                            L.i("RXUtils----------onResponseError");
                            subscriber.onResponseError(response);
                        }
                        return success;
                    }
                })
                .map(new Func1<Response, Response>() {
                    @Override
                    public Response call(Response response) {
                        L.i("RXUtils----------handlerResponseMainThread");
                        subscriber.handlerResponseMainThread(response);

                        return response;
                    }
                })
                .subscribe(subscriber);

    }

    /**
     * 同步网络数据请求
     *
     * @param request
     * @param apiMethodName
     */
    public static Response requestSync(final Request request, final String apiMethodName) {
        retrofit2.Call call = (retrofit2.Call) ReflectionUtils.methodInvoke(API.class.getName(),
                apiMethodName, new Class[]{String.class}, new Object[]{HTTP.formatJSONData(request)}, HTTP.getAPI());
        Object obj = null;
        try {
            obj = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (obj == null) {
            return null;
        }

        Response response = (Response) obj;
        if (response.getStatus() == StatusCode.SUCCESS) {
            return response;
        } else {
            L.e("请求错误，错误码：" + response.getStatus() + ", 错误内容：" + response.getMessage());
            return response;
        }
    }


    /**
     * 图片上传
     *
     * @param context
     * @param uploadFileBeen
     * @param subscriber
     */
    public static void uploadImages(final Context context, final List<UploadFileBean> uploadFileBeen, final UploadSubscriber subscriber) {

        Observable.just(uploadFileBeen).filter(new Func1<List<UploadFileBean>, Boolean>() {  //判断
            @Override
            public Boolean call(List<UploadFileBean> o) {
                boolean network = NetworkUtils.isConnected(context);
                if (!network) subscriber.onNoNetwork();
                return network;
            }
        })
                .observeOn(Schedulers.computation()).map(new Func1<List<UploadFileBean>, List<Response<UploadFileBean>>>() {
            @Override
            public List<Response<UploadFileBean>> call(List<UploadFileBean> beans) {

                List<Response<UploadFileBean>> responses = new ArrayList<Response<UploadFileBean>>();

                for (UploadFileBean bean : beans) {
                    File file = bean.getFile();

                    file = FileUtils.compressImage(file.getPath());

                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("uploadFile", file.getName(), requestBody);
                    Response<UploadFileBean> response = null;
                    try {
                        L.e("正在上传：" + bean.getFile().getPath());
                        response = HTTP.getAPI().uploadFile(bean.getUserId(), bean.getFileUse(), body).execute().body();
                        response.getData().setFileUse(bean.getFileUse());
                        response.getData().setFile(bean.getFile());
                        response.getData().setUserId(bean.getUserId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    responses.add(response);
                }

                return responses;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<List<Response<UploadFileBean>>, Boolean>() {
                    @Override
                    public Boolean call(List<Response<UploadFileBean>> response) {

                        for (Response<UploadFileBean> bean : response) {
                            boolean success = StatusCode.validateSuccess(context, bean);
                            if (!success) {
                                subscriber.onResponseError(bean);
                                return false;
                            }
                        }
                        return true;
                    }
                }).subscribe(subscriber);

    }

}
