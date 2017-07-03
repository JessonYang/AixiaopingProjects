package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.NetworkUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Dong on 2016/7/30.
 */
public class ForgetMyPasswordFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tv_phone_number)
    EditText tvPhoneNumber;
    @BindView(R.id.edt_auth_code)
    EditText edtAuthCode;
    @BindView(R.id.btn_send_auth_code)
    TextView btnSendAuthCode;
    @BindView(R.id.tv_not_receive_sms)
    TextView tvNotReceiveSms;
    @BindView(R.id.btn_register)
    ButtonRectangle btnRegister;
    String phoneNumber, captcha;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    private TimeCount time;
    LoginOptionActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_retrieve_password, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        time = new TimeCount(60000, 1000);
        mActivity = (LoginOptionActivity) getActivity();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }

    /**
     * 获得验证码
     */
    private void getCaptcha() {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(tvPhoneNumber.getText().toString());
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
                } else {
                    T.showShort(getActivity(), "发送验证码出错，请重试!");
                }
            }
        });
    }

    @OnClick({R.id.btn_send_auth_code, R.id.btn_register, R.id.tv_not_receive_sms})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_auth_code:
                phoneNumber = tvPhoneNumber.getText().toString();
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
                captcha = edtAuthCode.getText().toString();
                phoneNumber = tvPhoneNumber.getText().toString();
                if (!StringUtils.isBlank(phoneNumber)) {
                    if (!StringUtils.isBlank(captcha)) {
                        mActivity.changePassWord(true, phoneNumber, captcha);
                    } else {
                        T.showShort(getActivity(), "请输入短信验证码");
                    }
                } else {
                    T.showShort(getActivity(), "请输入手机号码");
                }

                break;
            case R.id.tv_not_receive_sms:
                URIResolve.resolve(mActivity, HTTP.URL_NOT_RECEIVED_IDENTIFYING_CODE + HTTP.formatJSONData(new Request()));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        time.cancel();
        edtAuthCode.setText("");
        tvPhoneNumber.setText("");
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            btnSendAuthCode.setText(R.string.get_auth_code);
            btnSendAuthCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btnSendAuthCode.setClickable(false);//防止重复点击
            btnSendAuthCode.setText(millisUntilFinished / 1000 + " s");

        }
    }
}
