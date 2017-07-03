package com.weslide.lovesmallscreen.view_yy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gc.materialdesign.widgets.SnackBar;
import com.rey.material.widget.Button;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.HomeActivity;
import com.weslide.lovesmallscreen.dao.sp.UserInfoSP;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.network.StatusCode;
import com.weslide.lovesmallscreen.utils.NetworkUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.view_yy.customview.AXPTextView_Line;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by YY on 2017/6/6.
 */
public class LoginForPhoneFragment extends Fragment implements View.OnClickListener {
    private View fgView;
    private EditText phoneNum_edt;
    private EditText phoneCode_edt;
    private TextView getCodeTv;
    private Button login_btn;
    private AXPTextView_Line kefu;
    private String phoneNumber;
    private TimeCount time;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fgView = inflater.inflate(R.layout.fragment_login_for_phone, container, false);
        initView();
        initData();
        return fgView;
    }

    private void initData() {
        getCodeTv.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        kefu.setOnClickListener(this);
    }

    private void initView() {
        phoneNum_edt = ((EditText) fgView.findViewById(R.id.tv_name_for_phone));
        phoneCode_edt = ((EditText) fgView.findViewById(R.id.tv_pwd_for_phone));
        getCodeTv = ((TextView) fgView.findViewById(R.id.getCode));
        login_btn = ((Button) fgView.findViewById(R.id.btn_login_for_phone));
        kefu = ((AXPTextView_Line) fgView.findViewById(R.id.axp_kefu));
        time = new TimeCount(60000, 1000);
        loadingDialog = new LoadingDialog(getActivity());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getCode:
                phoneNumber = phoneNum_edt.getText().toString();
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
            case R.id.btn_login_for_phone:
                login();
                break;
            case R.id.axp_kefu:

                break;
        }
    }

    /**
     * 开始登录
     */
    private void login() {
        Subscriber<UserInfo> subscriber = new Subscriber<UserInfo>() {
            @Override
            public void onCompleted() {
                loadingDialog.hide(); //运行结束后执行
            }

            @Override
            public void onError(Throwable e) {  //发生错误后执行
                Log.e("error", "错误", e);
                loadingDialog.hide();
                SnackBar snackBar = new SnackBar(getActivity(), "未知错误");
                snackBar.show();
            }

            @Override
            public void onNext(UserInfo userInfo) {  //执行完成后执行
                getActivity().finish();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        };

        UserInfo userInfo = new UserInfo();
        userInfo.setName(phoneNum_edt.getText().toString());
        userInfo.setPwd("");
        userInfo.setVerifyCode(phoneCode_edt.getText().toString());
        loadingDialog.show();
        Observable.just(userInfo)
                .filter((UserInfo u) -> {
                    if (!NetworkUtils.isConnected(getActivity())) {
                        T.showShort(getActivity(), R.string.alert_no_network);
                        return false;
                    }
                    return true;
                })  //有网络的情况下继续执行
                .filter(u -> validate(u))  //验证数据是否正确
                .map(userInfo1 -> {
                    loadingDialog.show();
                    return userInfo1;
                })
                .observeOn(Schedulers.computation())       //切换线程池中执行
                .map(u -> { //开始登录请求数据

                    try {
                        Request<UserInfo> request = new Request<UserInfo>();
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
                .observeOn(Schedulers.computation())       //切换线程池中执行
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
                .observeOn(Schedulers.computation())       //切换线程池中执行
                .map(new Func1<Response<UserInfo>, UserInfo>() {  //将个人信息缓存
                    @Override
                    public UserInfo call(Response<UserInfo> userInfoResponse) {
                        UserInfoSP.setUserInfo(userInfoResponse.getData());
                        return userInfoResponse.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


    }

    /**
     * 获得验证码
     */
    private void getCaptcha() {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phoneNum_edt.getText().toString());
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
                String message = userInfoResponse.getMessage();
                if (message != null) {
                    T.showShort(getActivity(), message);
                }
            }
        });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            getCodeTv.setText(R.string.get_auth_code);
            getCodeTv.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            getCodeTv.setClickable(false);//防止重复点击
            getCodeTv.setText(millisUntilFinished / 1000 + " s");

        }
    }

    /**
     * 验证用户输入
     *
     * @param userInfo
     * @return
     */
    private boolean validate(UserInfo userInfo) {

        if (StringUtils.isBlank(userInfo.getName()) || StringUtils.isBlank(userInfo.getVerifyCode())) {
            T.showShort(getActivity(), "用户名或验证码不能为空!");
            return false;
        } else if (!StringUtils.validataPhoneNumber(userInfo.getName())) {
            T.showShort(getActivity(), R.string.alert_illegal_phone_number);
            return false;
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        time.cancel();
        phoneNum_edt.setText("");
        phoneCode_edt.setText("");
    }
}
