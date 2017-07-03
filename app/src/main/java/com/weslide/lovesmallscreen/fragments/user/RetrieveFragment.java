package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.NetworkUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
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
 * Created by Dong on 2016/7/1.
 * 发送验证码
 */
public class RetrieveFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tv_phone_number)
    EditText tvPhoneNumber;
    @BindView(R.id.edt_auth_code)
    EditText edtAuthCode;
    @BindView(R.id.btn_send_auth_code)
    Button btnSendAuthCode;
    private String phoneNumber;
    private TimeCount time;
    private String captcha;
    private int type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_binding_phone, container, false);

        ButterKnife.bind(this, mView);
        time = new TimeCount(60000, 1000);
        Bundle bundle = getArguments();
        if(bundle == null){
            type = 1;
        }else {
           type = 2;
        }
        return mView;
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
                if (userInfoResponse.getStatus() == 1) {
                    T.showShort(getActivity(), userInfoResponse.getMessage());
                }

            }
        });
    }

    @OnClick({R.id.btn_send_auth_code, R.id.btn_register,R.id.tv_not_receive_sms})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_auth_code:
                phoneNumber = tvPhoneNumber.getText().toString();
                if (!StringUtils.isBlank(phoneNumber)) {
                    if(StringUtils.validataPhoneNumber(phoneNumber)==false){
                        T.showShort(getActivity(), "请输入正确的电话号码");
                    }else {
                        getCaptcha();
                        time.start();
                    }
                } else {
                    T.showShort(getActivity(), "请输入手机号码");
                }
                break;
            case R.id.btn_register:
                if(type ==2) {
                    captcha = edtAuthCode.getText().toString();
                    phoneNumber = tvPhoneNumber.getText().toString();
                    if(!StringUtils.isBlank(phoneNumber)){
                    if (!StringUtils.isBlank(captcha)) {
                        bindNumber();
                    } else {
                        T.showShort(getActivity(), "请输入验证码");
                    }
                }else{
                        T.showShort(getActivity(), "请输入手机号码");
                    }
                }
                break;
            case R.id.tv_not_receive_sms:
                URIResolve.resolve(getActivity(),HTTP.URL_NOT_RECEIVED_IDENTIFYING_CODE+ HTTP.formatJSONData(new Request()));

                break;
        }
    }

    private void bindNumber() {
        Request<UserInfo> request = new Request<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phoneNumber);
        userInfo.setCaptcha(captcha);
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "changeBaseInfo", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response response) {
                ContextParameter.getUserInfo().setPhone(phoneNumber);
                Toast.makeText(RetrieveFragment.this.getActivity(), "绑定成功!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getActivity(),response.getMessage());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        time.cancel();
    }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            btnSendAuthCode.setText(R.string.get_auth_code);
             try {
                 btnSendAuthCode.setBackgroundColor(getResources().getColor(R.color.lightskyblue));
             }catch (Exception e) {
                      }
            ;

            btnSendAuthCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btnSendAuthCode.setClickable(false);//防止重复点击
            try {
                btnSendAuthCode.setBackgroundColor(getResources().getColor(R.color.lightgray));
            }catch (Exception e){

            }

            btnSendAuthCode.setText(millisUntilFinished / 1000 + " s");

        }
    }
}
