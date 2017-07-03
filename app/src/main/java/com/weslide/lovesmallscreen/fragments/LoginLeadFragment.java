package com.weslide.lovesmallscreen.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.HomeActivity;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/26.
 */
public class LoginLeadFragment extends BaseFragment {

    View mView;
    LoginOptionActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_login_first, container, false);

        ButterKnife.bind(this, mView);
        activity = (LoginOptionActivity) getActivity();
        return mView;
    }

    @OnClick({R.id.btn_login, R.id.btn_register, R.id.back_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //跳转到登录界面
                activity.login(true);
                break;
            case R.id.btn_register:
                //跳转到注册界面
                activity.register(true);
                break;
            case R.id.back_main:
                //首页
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}
