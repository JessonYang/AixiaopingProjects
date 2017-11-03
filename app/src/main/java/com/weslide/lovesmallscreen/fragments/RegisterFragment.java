package com.weslide.lovesmallscreen.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.umeng.analytics.MobclickAgent;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.activitys.RegisterSuccessfulActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.UploadSubscriber;
import com.weslide.lovesmallscreen.dao.sp.UserInfoSP;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.models.bean.UploadFileBean;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.network.StatusCode;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.NetworkUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Dong on 2016/6/1.
 * 实现手机短信验证
 * 注册功能
 */
public class RegisterFragment extends BaseFragment {
    @BindView(R.id.edt_regist_phone)
    EditText edtPhoneNumber;
    @BindView(R.id.edt_captcha)
    EditText edtCaptcha;
    @BindView(R.id.edt_regist_password)
    EditText edtPassWord;
    @BindView(R.id.edt_invitation_code)
    EditText edtInvitationCode;
    @BindView(R.id.cb_consent_agreement)
    CheckBox agreement;
    @BindView(R.id.btn_send_captcha)
    TextView btnSendCaptcha;

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.headimg)
    ImageView headimg;
    @BindView(R.id.tv_have_invitor)
    RelativeLayout tvHaveInvitor;

    private String phoneNumber, captcha, password, invationcode, headUrl;
    private TimeCount time;

    MultiImageSelector selector = MultiImageSelector.create(getActivity());
    private ArrayList<String> mSelectPath;
    private SimpleDateFormat formatMD5 = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_register_sms, container, false);

        ButterKnife.bind(this, mView);
        time = new TimeCount(60000, 1000);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        return mView;

    }

    @OnClick({R.id.btn_send_captcha, R.id.btn_register, R.id.tv_installation_and_use, R.id.back, R.id.headimg, R.id.login, R.id.tv_have_invitor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_captcha:
                phoneNumber = edtPhoneNumber.getText().toString();
                if (!StringUtils.isBlank(phoneNumber)) {
                    if (StringUtils.validataPhoneNumber(phoneNumber) == false) {
                        T.showShort(getActivity(), "请输入正确的手机号码");
                    } else {
                        getCaptcha();
                        time.start();
                    }
                } else {
                    T.showShort(getActivity(), "请输入手机号码");
                }
                break;
            case R.id.btn_register:
                phoneNumber = edtPhoneNumber.getText().toString();
                captcha = edtCaptcha.getText().toString();
                password = edtPassWord.getText().toString();
                invationcode = edtInvitationCode.getText().toString();

                if (!StringUtils.isBlank(phoneNumber) && !StringUtils.isBlank(captcha) && !StringUtils.isBlank(password)) {
                    if (agreement.isChecked()) {
                        if (password.length() < 6 || password.length() > 16) {
                            T.showShort(getActivity(), "密码的长度不能小于六位或者大于十六位");
                        } else {
                            register();
                        }
                    } else {
                        T.showShort(getActivity(), "请勾选用户协议");
                    }
                } else {
                    T.showShort(getActivity(), "请完善你的信息");
                }

                break;
            case R.id.tv_installation_and_use:
                URIResolve.resolve(getActivity(), HTTP.URL_USER_INSTALLATION_PROTOCOL + HTTP.formatJSONData(new Request()));
                break;

            case R.id.back:
                getFragmentManager().popBackStack();
                break;
            case R.id.headimg:
                selector.single();
                selector.start(getActivity(), 2);
                break;
            case R.id.login:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_have_invitor:
                edtInvitationCode.setVisibility(View.VISIBLE);
                tvHaveInvitor.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onEvent(getActivity(),"register_enter");
        /*edtCaptcha.setText("");
        edtInvitationCode.setText("");
        edtPassWord.setText("");
        edtPhoneNumber.setText("");
        agreement.setChecked(false);*/
        time.cancel();
    }

    /**
     * 获得验证码
     */
    private void getCaptcha() {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(edtPhoneNumber.getText().toString());
        Observable.just(userInfo)
                .filter((UserInfo u) -> {
                    if (!NetworkUtils.isConnected(getActivity())) {
                        T.showShort(getActivity(), R.string.alert_no_network);
                        return false;
                    }
                    return true;
                }).observeOn(Schedulers.computation())
                .map((UserInfo u) -> { //开始注册请求数据

                    try {
                        Request<UserInfo> request = new Request<UserInfo>();
                        request.setData(u);

                        Response<UserInfo> response = HTTP.getAPI().sendCaptcha(HTTP.formatJSONData(request)).execute().body();
                        return response;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<UserInfo>>() {
            @Override
            public void call(Response<UserInfo> userInfoResponse) {
                Log.d("雨落无痕丶", "call: ======" + userInfoResponse.getMessage() + ";status:" + userInfoResponse.getStatus());
                if (userInfoResponse.getStatus() == 1) {
                    T.showShort(getActivity(), userInfoResponse.getMessage());
                }

            }
        });


    }

    /**
     * 注册
     */
    private void register() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(edtPhoneNumber.getText().toString());
        userInfo.setPwd(edtPassWord.getText().toString());
        userInfo.setCaptcha(edtCaptcha.getText().toString());
        userInfo.setInviteCode(edtInvitationCode.getText().toString());
        userInfo.setHeadimage(headUrl);
        Observable.just(userInfo)
                .filter((UserInfo u) -> {
                    if (!NetworkUtils.isConnected(getActivity())) {
                        T.showShort(getActivity(), R.string.alert_no_network);
                        return false;
                    }
                    return true;
                })  //有网络的情况下继续执行
                .observeOn(Schedulers.computation())       //切换线程池中执行
                .map((UserInfo u) -> { //开始注册请求数据

                    try {
                        Request<UserInfo> request = new Request<UserInfo>();
                        request.setData(u);

                        Response<UserInfo> response = HTTP.getAPI().register(HTTP.formatJSONData(request)).execute().body();
                        return response;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })//注册成功
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Response<UserInfo>, Response<UserInfo>>() {
                    @Override
                    public Response<UserInfo> call(Response<UserInfo> userInfoResponse) {
                        MobclickAgent.onEvent(getActivity(), "register_commit");
                        HashMap<String,String> map1 = new HashMap<String,String>();
                        HashMap<String,String> map2 = new HashMap<String,String>();
                        HashMap<String,String> map3 = new HashMap<String,String>();
                        HashMap<String,String> map4 = new HashMap<String,String>();
                        map1.put("phoneNum",phoneNumber);
                        map2.put("captcha",captcha);
                        map3.put("password",password);
                        if (invationcode != null) {
                            map4.put("invationcode",invationcode);
                            MobclickAgent.onEvent(getActivity(), "register_inviteCode",map4);
                        }
                        MobclickAgent.onEvent(getActivity(), "register_getverifycode",map2);
                        MobclickAgent.onEvent(getActivity(), "register_mobileNum",map1);
                        MobclickAgent.onEvent(getActivity(), "register_password",map3);
                        return userInfoResponse;
                    }
                })
                .filter(userInfoResponse -> StatusCode.validateSuccessIfFailToast(getActivity(), userInfoResponse)) //验证网络数据
                .observeOn(Schedulers.computation())       //切换线程池中执行
                .map((Response<UserInfo> u) -> { //开始登录请求数据

                    try {
                        Request<UserInfo> request = new Request<UserInfo>();
                        String axpabc = md5("axp" + u.getData().getUserId());
                        String s = md5(axpabc + formatMD5.format(System.currentTimeMillis() + ContextParameter.getTimeExtra()));
                        request.setAxp(s);
                        request.setData(u.getData());

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
                            String axpabc = md5("axp" + userInfoResponse.getData().getUserId());
                            String s = md5(axpabc + formatMD5.format(System.currentTimeMillis() + ContextParameter.getTimeExtra()));
                            request.setAxp(s);
                            Response<UserInfo> response = HTTP.getAPI().getUserInfo(HTTP.formatJSONData(request)).execute().body();
                            return response;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }).filter(userInfoResponse -> StatusCode.validateSuccessIfFailToast(getActivity(), userInfoResponse)) //验证网络数据
                .map(new Func1<Response<UserInfo>, UserInfo>() {  //将个人信息缓存
                    @Override
                    public UserInfo call(Response<UserInfo> userInfoResponse) {
                        UserInfoSP.setUserInfo(userInfoResponse.getData());
                        return userInfoResponse.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserInfo>() {           //结束操作
                    @Override
                    public void call(UserInfo userInfo) {
                        Bundle bundle = new Bundle();
                        bundle.putString("inviteCode", userInfo.getInviteCode());
                        AppUtils.toActivity(getActivity(), RegisterSuccessfulActivity.class, bundle);
                    }
                });
    }


    private void inputHeadImg() {

        List<UploadFileBean> uploadFileBeen = new ArrayList<>();
        if (mSelectPath != null && mSelectPath.size() > 0) {

            for (int i = 0; i < mSelectPath.size(); i++) {
                UploadFileBean bean = new UploadFileBean();
                bean.setFile(new File(mSelectPath.get(i)));
                bean.setUserId(ContextParameter.getUserInfo().getUserId());
                bean.setFileUse(Constants.UPLOAD_FEED_BACK);

                uploadFileBeen.add(bean);
            }
        }
        RXUtils.uploadImages(getActivity(), uploadFileBeen, new UploadSubscriber() {

            LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }

            @Override
            public void onNext(List<Response<UploadFileBean>> responses) {
                headUrl = responses.get(0).getData().getOppositeUrl();
                Glide.with(getActivity()).load(responses.get(0).getData().getAbsoluteUrl()).asBitmap()
                        .error(R.drawable.appicon_img).placeholder(R.drawable.appicon_img)
                        .centerCrop().into(new BitmapImageViewTarget(headimg) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        headimg.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == -1) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                inputHeadImg();
            }
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            btnSendCaptcha.setText(R.string.get_auth_code);
            btnSendCaptcha.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btnSendCaptcha.setClickable(false);//防止重复点击
            btnSendCaptcha.setText(millisUntilFinished / 1000 + " s");
        }
    }

    private String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
