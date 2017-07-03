package com.weslide.lovesmallscreen.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.handler.UMWXHandler;
import com.umeng.socialize.utils.Log;
import com.weslide.lovesmallscreen.ArchitectureAppliation;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.HomeActivity;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.dao.sp.UserInfoSP;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateWXAuthMessage;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.network.StatusCode;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.NetworkUtils;
import com.weslide.lovesmallscreen.utils.ReflectionUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Dong on 2016/5/25.
 * 免费登录以及QQ WeChat登录
 */
public class FreeRegisterFragment extends BaseFragment {


    UMShareAPI umShareAPI;

    View mView;
    private String openid, access_token, headImg, relName, sex;

    LoginOptionActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        mView = inflater.inflate(R.layout.fragment_register, container, false);
        umShareAPI = UMShareAPI.get(ArchitectureAppliation.getAppliation());
        ButterKnife.bind(this, mView);

        mActivity = (LoginOptionActivity) getActivity();
        return mView;
    }

    @OnClick({R.id.btn_free_register, R.id.tv_login, R.id.iv_login_qq, R.id.iv_login_weixin, R.id.tv_stroll})
    public void onClick(View view) {
        SHARE_MEDIA platform = null;
        switch (view.getId()) {
            case R.id.btn_free_register:
                mActivity.register(true);
                break;

            case R.id.tv_login:
                mActivity.login(true);
                break;
            case R.id.iv_login_qq:
                platform = SHARE_MEDIA.QQ;
                umShareAPI.doOauthVerify(getActivity(), platform, umAuthListener);
                break;
            case R.id.iv_login_weixin:
//                platform = SHARE_MEDIA.WEIXIN;
//                umShareAPI.doOauthVerify(getActivity(),platform,umWeixinAuthListener);
                weixin();
                break;
            case R.id.tv_stroll:
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//               AppUtils.toActivity(getActivity(), HomeActivity.class);
                break;
        }

    }

    LoadingDialog wxloadingDialog;

    @Subscribe
    public void onEvent(UpdateWXAuthMessage message) {
        switch (message.getResp().errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.e("提示", "授权成功");

                if (message.getResp() instanceof SendAuth.Resp) {
                    authWX((SendAuth.Resp) message.getResp());
                } else {
                    if(wxloadingDialog!= null && wxloadingDialog.isShowing()){
                        wxloadingDialog.dismiss();
                    }
                }


                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.e("提示", "用户取消授权");
                if(wxloadingDialog!= null && wxloadingDialog.isShowing()){
                    wxloadingDialog.dismiss();
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.e("提示", "授权失败");
                if(wxloadingDialog!= null && wxloadingDialog.isShowing()){
                    wxloadingDialog.dismiss();
                }
                break;
        }
    }

    public void authWX(SendAuth.Resp resp) {

        Observable.just(null).filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                if (NetworkUtils.isConnected(getActivity())) {
                    return true;
                } else {
                    T.showShort(getActivity(), "请链接网络");
                    return false;
                }
            }
        }).observeOn(Schedulers.computation()).map(new Func1<Object, Object>() {
            @Override
            public Object call(Object o) {


                Gson gson = new Gson();

                try {

                    //创建一个Request
                    final okhttp3.Request getTokenRequest = new okhttp3.Request.Builder()
                            .url("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.WEXIN_APP_ID +
                                    "&secret=" + Constants.WEXIN_APP_APPSECRET +
                                    "&code=" + resp.code +
                                    "&grant_type=authorization_code")
                            .build();

                    String getTokenJson = HTTP.getOkHttpClient().newCall(getTokenRequest).execute().body().string();
                    ArrayMap<String, String> getTokenArray = gson.fromJson(getTokenJson, ArrayMap.class);

                    access_token = getTokenArray.get("access_token");
                    openid = getTokenArray.get("openid");

                    //创建一个Request
                    final okhttp3.Request getUserInfoRequest = new okhttp3.Request.Builder()
                            .url("https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token +
                                    "&openid=" + openid)
                            .build();

                    String getUserInfoJson = HTTP.getOkHttpClient().newCall(getUserInfoRequest).execute().body().string();
                    Map<String, Object> getUserInfoArray = gson.fromJson(getUserInfoJson, Map.class);


                    headImg = getUserInfoArray.get("headimgurl").toString();
                    relName = getUserInfoArray.get("nickname").toString();
                    double _sex = Double.parseDouble(getUserInfoArray.get("sex").toString());
                    if (_sex == 1) {
                        sex = "男";
                    } else if (_sex == 2) {
                        sex = "女";
                    } else {
                        sex = "保密";
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                wxloadingDialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                L.e("err", "err", e);
                onCompleted();
            }

            @Override
            public void onNext(Object o) {
                login(openid, access_token, headImg, relName, sex);
            }
        });


    }

    public void weixin() {

        wxloadingDialog = new LoadingDialog(getActivity());
        wxloadingDialog.show();

        final IWXAPI msgApi = WXAPIFactory.createWXAPI(ArchitectureAppliation.getAppliation(), null);
        // 将该app注册到微信
        msgApi.registerApp(Constants.WEXIN_APP_ID);
        // send oauth request
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "aixiaoping";
        msgApi.sendReq(req);
    }

    //QQ授权回调
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            openid = data.get("openid");
            access_token = data.get("access_token");
            umShareAPI.getPlatformInfo(getActivity(), platform, mAuthListener);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    };

    //获取QQ个人信息回调
    private UMAuthListener mAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            if (data != null) {
                headImg = data.get("profile_image_url");
                relName = data.get("screen_name");
                sex = data.get("gender");

            }
            if (openid != null && access_token != null) {
                login(openid, access_token, headImg, relName, sex);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    };
    //微信授权回调
    private UMAuthListener umWeixinAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            openid = data.get("openid");
            access_token = data.get("access_token");
            umShareAPI.getPlatformInfo(getActivity(), platform, mWeixinAuthListener);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) { }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) { }
    };

    //获取微信个人信息回调
    private UMAuthListener mWeixinAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data != null) {
                headImg = data.get("headimgurl");
                relName = data.get("nickname");
                if (data.get("sex").equals("1")) {
                    sex = "男";
                } else if (data.get("sex").equals("2")) {
                    sex = "女";
                } else {
                    sex = "保密";
                }
                ;
            }
            if (openid != null && access_token != null) {
                login(openid, access_token, headImg, relName, sex);
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }


    //拿到openid，登录
    private void login(String openid, String access_token, String headImg, String relName, String sex) {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId(openid);
        userInfo.setTokenId(access_token);
        userInfo.setHeadimage(headImg);
        userInfo.setRealname(relName);
        userInfo.setSex(sex);
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        Observable.just(userInfo)
                .filter((UserInfo u) -> {
                    if (!NetworkUtils.isConnected(getActivity())) {
                        T.showShort(getActivity(), R.string.alert_no_network);
                        return false;
                    }
                    return true;
                })  //有网络的情况下继续执行

                .observeOn(Schedulers.computation())       //切换线程池中执行
                .map(u -> { //开始登录请求数据

                    try {

                        Request<UserInfo> request = new Request<>();
                        request.setData(u);

                        Response<UserInfo> response = HTTP.getAPI().login(HTTP.formatJSONData(request)).execute().body();
                        return response;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(userInfoResponse -> StatusCode.validateSuccessIfFailToast(getActivity(), userInfoResponse)) //验证网络数据
                .observeOn(Schedulers.computation())
                .map(new Func1<Response<UserInfo>, Response<UserInfo>>() {  //开始加载个人信息
                    @Override
                    public Response<UserInfo> call(Response<UserInfo> userInfoResponse) {
                        try {
                            Request request = new Request();
                            request.setUserId(userInfoResponse.getData().getUserId());
                            Response<UserInfo> response = HTTP.getAPI().getUserInfo(HTTP.formatJSONData(request)).execute().body();
                            return response;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(userInfoResponse -> StatusCode.validateSuccessIfFailToast(getActivity(), userInfoResponse)) //验证网络数据
                .map(new Func1<Response<UserInfo>, UserInfo>() {  //将个人信息缓存
                    @Override
                    public UserInfo call(Response<UserInfo> userInfoResponse) {
                        UserInfoSP.setUserInfo(userInfoResponse.getData());
                        return userInfoResponse.getData();
                    }
                })
                .subscribe(new Subscriber<UserInfo>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        loadingDialog.show(); }

                    @Override
                    public void onCompleted() {
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onCompleted();
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });


    }

    @Override
    public void onDestroyView() {

        //利用反射处理腾讯友盟内存泄露
        Field tencentField = ReflectionUtils.getDeclaredField(Tencent.class, "sInstance");
        Field UMShareAPIApifield = ReflectionUtils.getDeclaredField(UMShareAPI.class, "b");
        tencentField.setAccessible(true);
        UMShareAPIApifield.setAccessible(true);
        try {
            tencentField.set(null, null);
            UMShareAPIApifield.set(null, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        EventBus.getDefault().unregister(this);

        super.onDestroyView();


    }
}
