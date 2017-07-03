package com.weslide.lovesmallscreen.fragments.withdrawals;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.withdrawals.WithdrawalsActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.models.Withdrawals;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.NetworkUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Dong on 2016/12/30.
 */
public class AuthenticationFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.edt_auth_code)
    EditText edtAuthCode;
    @BindView(R.id.btn_send_auth_code)
    TextView btnSendAuthCode;
    private TimeCount time;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_authentication, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        time = new TimeCount(60000, 1000);
        phone.setText(ContextParameter.getUserInfo().getPhone());
    }
    @Override
    public void onResume() {
        super.onResume();
        time.cancel();
        edtAuthCode.setText("");
    }

    @OnClick({R.id.btn_send_auth_code, R.id.btn_withdraw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_auth_code:
                getCaptcha();
                time.start();
                break;
            case R.id.btn_withdraw:
                if (!StringUtils.isBlank(edtAuthCode.getText().toString())) {
                    withdrawls();
                }else{
                    T.showShort(getActivity(), "请输入验证码");
                }
                break;
        }
    }

    /**
     * 获得验证码
     */
    private void getCaptcha() {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(ContextParameter.getUserInfo().getPhone());
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
                T.showShort(getActivity(), userInfoResponse.getMessage());
            }
        });
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

    private void withdrawls(){
        Request<UserInfo> request = new Request<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(ContextParameter.getUserInfo().getPhone());
        userInfo.setiCode(edtAuthCode.getText().toString());
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "applyWithdrawals", new SupportSubscriber<Response<Withdrawals>>() {

            LoadingDialog dialog;

            @Override
            public void onStart() {
                dialog = new LoadingDialog(getActivity());
                dialog.show();
            }

            @Override
            public void onCompleted() {
                dialog.dismiss();
            }
            @Override
            public void onNext(Response<Withdrawals> withdrawalsResponse) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("withdrawals",withdrawalsResponse.getData());
                AppUtils.toActivity(getActivity(), WithdrawalsActivity.class,bundle);
                getActivity().finish();
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getActivity(),response.getMessage());
            }
        });

    }
}
