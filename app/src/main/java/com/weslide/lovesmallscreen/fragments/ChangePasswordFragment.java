package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Password;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/7/30.
 * 修改密码
 */
public class  ChangePasswordFragment extends BaseFragment {
    String phone, captcha, newPassword, confirmpwd;

    View mView;
    @BindView(R.id.edt_input_password)
    EditText edtInputPassword;
    @BindView(R.id.edt_input_password_again)
    EditText edtInputPasswordAgain;
    LoginOptionActivity mActivity;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_change_password, container, false);

        ButterKnife.bind(this, mView);

        init();

        return mView;
    }

    private void init() {
        mActivity = (LoginOptionActivity) getActivity();
        Bundle bundle = getArguments();
        phone = bundle.getString("phone");
        captcha = bundle.getString("captcha");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void changePassword(String phone, String captcha, String newPassword, String confirmpwd) {
        Request<Password> request = new Request();
        Password password = new Password();
        password.setPhone(phone);
        password.setCaptcha(captcha);
        password.setNewpwd(newPassword);
        password.setConfirmpwd(confirmpwd);
        request.setData(password);
        RXUtils.request(getActivity(), request, "forgetPassword", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response response) {
                T.showShort(getActivity(), response.getMessage());
                mActivity.login(true);
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getActivity(), response.getMessage());
            }
        });

    }

    @OnClick(R.id.btn_next)
    public void onClick() {

        newPassword = edtInputPassword.getText().toString();
        confirmpwd = edtInputPasswordAgain.getText().toString();
        if (!StringUtils.isBlank(newPassword) && !StringUtils.isBlank(confirmpwd)) {
            if(newPassword.length()<6||newPassword.length()>16){
                T.showShort(getActivity(), "密码的长度不能小于六位或者大于十六位");
            }else {
                if (!newPassword.equals(confirmpwd)) {
                    T.showShort(getActivity(), "两次输入的密码不一致,请重新输入");
                    edtInputPassword.setText("");
                    edtInputPasswordAgain.setText("");
                } else {
                    changePassword(phone, captcha, newPassword, confirmpwd);
                }
            }
        } else {
            T.showShort(getActivity(), "请填完整你要输入的内容");
        }

    }
}
