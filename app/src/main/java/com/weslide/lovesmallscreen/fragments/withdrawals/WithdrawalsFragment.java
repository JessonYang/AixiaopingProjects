package com.weslide.lovesmallscreen.fragments.withdrawals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Withdrawals;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/12/30.
 */
public class WithdrawalsFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_code)
    EditText code;
    @BindView(R.id.edt_address)
    EditText address;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.cb_consent_agreement)
    CheckBox agreement;
    String miniMoney;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_withdraws, container, false);
        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        Bundle bundle = getArguments();
        Withdrawals withdrawals = (Withdrawals) bundle.getSerializable("withdrawals");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        if (withdrawals != null) {
            money.setText(withdrawals.getTotalMoney());
            edtName.setText(withdrawals.getName());
            code.setText(withdrawals.getAccount());
            address.setText(withdrawals.getAddress());
            title.setText(withdrawals.getTitle());
            miniMoney = withdrawals.getMinMoney();
        }
    }

    @OnClick(R.id.btn_withdraw)
    public void onClick() {
        if (!StringUtils.isBlank(edtName.getText().toString()) && !StringUtils.isBlank(code.getText().toString()) && !StringUtils.isBlank(address.getText().toString()) && !StringUtils.isBlank(edtMoney.getText().toString())) {

           if(agreement.isChecked() ) {
               if(Double.parseDouble(edtMoney.getText().toString()) >= Double.parseDouble(miniMoney)) {
                   withdrawl();
               }else{
                   T.showShort(getActivity(),"提现金额不小于"+miniMoney+"元");
               }
           }else {
               T.showShort(getActivity(), "请勾选用户协议");
           }
        } else {
               T.showShort(getActivity(),"请完善您的提现信息");
        }
    }

    private void withdrawl() {
        Request<Withdrawals> request = new Request<>();
        Withdrawals withdrawals = new Withdrawals();
        withdrawals.setName(edtName.getText().toString());
        withdrawals.setBankCode(code.getText().toString());
        withdrawals.setAddress(address.getText().toString());
        withdrawals.setMoney(edtMoney.getText().toString());
        request.setData(withdrawals);
        RXUtils.request(getActivity(), request, "withdrawals", new SupportSubscriber<Response>() {

            @Override
            public void onNext(Response response) {
                T.showShort(getActivity(), response.getMessage());
                getActivity().finish();
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getActivity(), response.getMessage());
            }
        });
    }
}
