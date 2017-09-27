package com.weslide.lovesmallscreen.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.utils.L;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xu on 2016/5/4.
 * 发送HTTP请求
 */
public class HTTP {

    /** 开发服务器 */

    /**
     * OKHTTP日志级别
     */
    public static final HttpLoggingInterceptor.Level LOGGING_LEVEL;
    /**
     * 正式服务器
     */
    public static final String API_SERVER;
//    public static final String API_SERVER = "http://192.168.1.124:8080/aixiaopingAPI/invoke/";
//    public static final String API_SERVER = "http://15x30g3106.iok.la:32443/aixiaopingAPI/invoke/";

    /** 小黄服务器 */
//    public static final String API_SERVER = "http://192.168.1.108:8089/invoke/";
//   public static final String API_SERVER = "http://192.168.1.109:8082/invoke/";

    static {

        if (Constants.RELEASE_SERVICE) {
            API_SERVER = "http://www.aixiaoping.com:8080/aixiaopingAPI/invoke/";
        } else {
//            API_SERVER = "http://27.54.226.210:8080/aixiaopingAPI/invoke/";
//            API_SERVER = "http://192.168.200.201:8080/aixiaopingAPI/invoke/";
            API_SERVER = "http://test.aixiaoping.cn:8888/aixiaopingAPI/invoke/";
        }

        if (Constants.RELEASE) {
            LOGGING_LEVEL = HttpLoggingInterceptor.Level.NONE;
        } else {
            LOGGING_LEVEL = HttpLoggingInterceptor.Level.BODY;
        }
    }

    /**
     * 使用OKHttp
     */
    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;

    public static Retrofit getRetrofit() {
        return getRetrofit(API_SERVER);
    }

    public static OkHttpClient getOkHttpClient() {

        if (mOkHttpClient == null) {
            getRetrofit();
        }

        return mOkHttpClient;
    }

    /**
     * 获取单例的Retrofit对象
     *
     * @param baseUrl
     * @return
     */
    public static Retrofit getRetrofit(String baseUrl) {
        if (mRetrofit == null) {


            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(LOGGING_LEVEL);

            //设置请求头
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new AddCookiesInterceptor())
                    .addInterceptor(new ReceivedCookiesInterceptor())
                    .build();

            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                    .create();

            //构建Retrofit
            mRetrofit = new Retrofit.Builder()
                    //配置服务器路径
                    .baseUrl(baseUrl)
                    //配置转化库，默认是Gson
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //配置回调库，采用RxJava
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //设置OKHttpClient为网络客户端
                    .client(mOkHttpClient)
                    .build();
        }
        return mRetrofit;
    }


    /**
     * 获取所有API
     *
     * @return
     */
    public static API getAPI() {
        API api = getRetrofit().create(API.class);
        return api;
    }

    /**
     * 将Reqeust转换为json
     *
     * @param request
     * @return
     */
    public static String formatJSONData(Request request) {
        Gson gson = new Gson();
        String json = gson.toJson(request);
        L.i("请求JSON: = " + json);
        return json;
    }


    /**
     * 商品图文详情页面链接
     */
    public static final String URL_GOODS_DETAIL = API_SERVER + "mall/goodsDetail?data=";
    /**
     * 用户安装协议
     */
    public static final String URL_USER_INSTALLATION_PROTOCOL = API_SERVER + "users/userInstallationProtocol?data=";
    /**
     * 必看攻略
     */
    public static final String URL_SEE_STRATEGY = API_SERVER + "users/seeStrategy?data=";
    /**
     * 关于爱小屏
     */
    public static final String URL_ABOUT = API_SERVER + "users/about?data=";

    /**
     * 收不到验证码
     */
    public static final String URL_NOT_RECEIVED_IDENTIFYING_CODE = API_SERVER + "users/notReceivedIdentifyingCode?data=";

    /**
     * 怎么获得积分
     */
    public static final String URL_HOW_GET_SCORE = API_SERVER + "users/howGetScore?data=";

    /**
     * 返利能提现吗？ rebate can cash
     */
    public static final String URL_REBATE_CAN_CASH = API_SERVER + "users/rebateCanCash?data=";

    /**
     * 怎么送生日礼物？send birthday gift
     */
    public static final String URL_SEND_BIRTHDAY_GIFT = API_SERVER + "users/sendBirthdayGift?data=";

    /**
     * 打开红包(users/openRedPaper)
     */
    public static final String URL_OPEN_RED_PAPER = API_SERVER + "users/openRedPaper?data=";

    /**
     * 红包列表(users/redPaperList)
     */
    public static final String URL_RED_PAPER_LIST = API_SERVER + "users/redPaperList?data=";

    /**
     * 打开现金列表（users/moneyLink）
     */
    public static final String URL_MONEY_LINK_LIST = API_SERVER + "users/moneyLink?data=";
//    public static final String

    /**
     * 换货会
     */
    public static final String URL_EXCHENGE_GOODS = "http://hhh.aixiaoping.cn/?";
    /**
     * 下载爱小屏链接
     */
    public static final String URL_LOAD_AIXIAOPING = API_SERVER + "download.jsp";
    public static final String PREF_COOKIES = "PREF_COOKIES";
    public static final String PREF_COOKIES_VALUE = "PREF_COOKIES_VALUE";

    /**
     * 将Cookies传递给服务器
     */
    static class AddCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            okhttp3.Request.Builder builder = chain.request().newBuilder();
            SharedPreferences sp = ArchitectureAppliation.getAppliation().getSharedPreferences(PREF_COOKIES,
                    Context.MODE_PRIVATE);
            HashSet<String> preferences = (HashSet) sp.getStringSet(PREF_COOKIES_VALUE, new HashSet<String>());
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }

            return chain.proceed(builder.build());
        }
    }

    /**
     * 将服务器Cookies保存起来
     */
    static class ReceivedCookiesInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = new HashSet<>();

                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                }

                SharedPreferences sp = ArchitectureAppliation.getAppliation().getSharedPreferences(PREF_COOKIES,
                        Context.MODE_PRIVATE);
                sp.edit().putStringSet(PREF_COOKIES_VALUE, cookies).commit();
            }

            return originalResponse;
        }
    }


}
