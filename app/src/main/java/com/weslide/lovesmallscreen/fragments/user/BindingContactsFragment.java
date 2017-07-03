package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.UserInfo;
import com.weslide.lovesmallscreen.models.bean.LeaderBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/6/16.
 * 绑定联系人
 */
public class BindingContactsFragment extends BaseFragment {
    View mView;
    @BindView(R.id.edt_invite_code)
    EditText edtInviteCode;
    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    @BindView(R.id.tv_error_code)
    TextView tvErrorCode;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    String inviteCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_binding_contacts, container, false);

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

    }

    private void bangdingContacts() {
        Request<UserInfo> request = new Request<>();
        UserInfo userInfo = new UserInfo();

        userInfo.setInviteCode(inviteCode);
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "bangdingContacts", new SupportSubscriber<Response<LeaderBean>>() {
            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);

                tvUserId.setVisibility(View.GONE);
                tvErrorCode.setVisibility(View.VISIBLE);
                tvErrorCode.setText(response.getMessage());

            }

            @Override
            public void onNext(Response<LeaderBean> response) {
                tvUserId.setVisibility(View.VISIBLE);
                tvErrorCode.setVisibility(View.GONE);
                tvUserId.setText(response.getData().getName());
                getActivity().finish();
            }
        });
    }

    @OnClick(R.id.btn_sure)
    public void onClick() {
        inviteCode = edtInviteCode.getText().toString();
        if(!StringUtils.isBlank(inviteCode)) {
            bangdingContacts();
        }else{
            T.showShort(getActivity(),"请输入邀请码");
        }
    }
}
