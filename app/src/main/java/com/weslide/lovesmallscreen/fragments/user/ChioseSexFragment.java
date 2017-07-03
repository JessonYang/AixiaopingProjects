package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.gc.materialdesign.views.ButtonRectangle;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.widget.AXPRadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/7/1.
 * 选择性别
 */
public class ChioseSexFragment extends BaseFragment {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rb_undefined)
    RadioButton rbUndefined;
    @BindView(R.id.rg_pay)
    AXPRadioGroup rgPay;
    @BindView(R.id.btn_logout)
    ButtonRectangle btnLogout;
    String sex = "1";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_chiose_sex, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        rbMan.setChecked(true);
        rgPay.setOnCheckedChangeListener(new AXPRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AXPRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_man:
                        sex = "1";
                        break;
                    case R.id.rb_woman:
                        sex = "2";
                        break;
                    case R.id.rb_undefined:
                        sex = "3";
                        break;

                }
            }
        });

    }

    @OnClick(R.id.btn_logout)
    public void onClick() {
        choiseSex();
    }

    /**
     * 选择性别
     */
    private void choiseSex() {
        Request<UserInfo> request = new Request<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setSex(sex);
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "changeBaseInfo", new SupportSubscriber<Response>() {
            @Override
            public void onNext(Response response) {
                getActivity().finish();

            }
        });

    }
}
