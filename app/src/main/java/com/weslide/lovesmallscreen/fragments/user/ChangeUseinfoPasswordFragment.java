package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.showmo.ipc360.util.StringUtil;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Password;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.L;
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
public class ChangeUseinfoPasswordFragment extends BaseFragment {
    View mView;
    @BindView(R.id.edt_old_password)
    EditText edtOldPassword;
    @BindView(R.id.edt_input_password)
    EditText edtInputPassword;
    @BindView(R.id.edt_input_password_again)
    EditText edtInputPasswordAgain;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    private String oldPwd,newPassword,confirmpwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_change_password_useinfo, container, false);

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
    }

    @OnClick(R.id.btn_sure)
    public void onClick() {
        oldPwd = edtOldPassword.getText().toString();
        newPassword = edtInputPassword.getText().toString();
        confirmpwd = edtInputPasswordAgain.getText().toString();
        if(!StringUtils.isBlank(oldPwd)&&!StringUtils.isBlank(newPassword)&&!StringUtils.isBlank(newPassword)) {
            if(newPassword.length()<6||newPassword.length()>16){
                T.showShort(getActivity(), "密码的长度不能小于六位或者大于十六位");
            }else {
                if (!newPassword.equals(confirmpwd)) {

                    T.showShort(getActivity(), "两次输入的新密码不一致，请重新输入");
                    edtInputPassword.setText("");
                    edtInputPasswordAgain.setText("");
                } else {
                    changePassword(oldPwd, newPassword, confirmpwd);
                }
            }
        }else{
            T.showShort(getActivity(),"请填完整你要输入的内容");
        }
    }

    private void changePassword(String oldPwd,String newPassword,String confirmpwd){
        Request<Password> request = new Request<>();
        Password password = new Password();
        password.setOldpwd(oldPwd);
        password.setNewpwd(newPassword);
        password.setConfirmpwd(confirmpwd);
        request.setData(password);
        RXUtils.request(getActivity(), request, "changePassword", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response response) {
                L.e("========getMessage====="+response.getMessage());
                T.showShort(getActivity(),response.getMessage());
                getActivity().finish();
            }
        });
    }
}
