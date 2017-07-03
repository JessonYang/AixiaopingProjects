package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;

/**
 * Created by YY on 2017/3/23.
 */
public class MyPartnerVpBaseFg extends BaseFragment {

    private View vpFg;
    private TextView my_partner_vp_base_money;
    private TextView my_partner_vp_base_order;
    private TextView personalPredict_tv;
    private TextView partnerPredict_tv;
    private TextView personalOrder_tv;
    private TextView partnerOrder_tv;

    public static MyPartnerVpBaseFg getInstance(Bundle bundle) {
        MyPartnerVpBaseFg myPartnerVpBaseFg = new MyPartnerVpBaseFg();
        myPartnerVpBaseFg.setArguments(bundle);
        return myPartnerVpBaseFg;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String personalOrder = getArguments().getString("personalOrder");
        String personalPredict = getArguments().getString("personalPredict");
        String partnerOrder = getArguments().getString("partnerOrder");
        String partnerPredict = getArguments().getString("partnerPredict");
        String totalPredict = getArguments().getString("totalPredict");
        String totalOrder = getArguments().getString("totalOrder");
        vpFg = inflater.inflate(R.layout.fragment_my_partner_vp, container, false);
        initView();
        personalOrder_tv.setText(personalOrder);
        personalPredict_tv.setText(personalPredict);
        partnerOrder_tv.setText(partnerOrder);
        partnerPredict_tv.setText(partnerPredict);
        my_partner_vp_base_money.setText(totalPredict);
        my_partner_vp_base_order.setText(totalOrder);
        return vpFg;
    }

    private void initView() {
        my_partner_vp_base_money = ((TextView) vpFg.findViewById(R.id.my_partner_vp_base_money));
        my_partner_vp_base_order = ((TextView) vpFg.findViewById(R.id.my_partner_vp_base_order));
        personalPredict_tv = ((TextView) vpFg.findViewById(R.id.my_partner_vp_base_personal_dintinct));
        partnerPredict_tv = ((TextView) vpFg.findViewById(R.id.my_partner_vp_base_partner_dintinct));
        personalOrder_tv = ((TextView) vpFg.findViewById(R.id.my_partner_vp_base_personal_order));
        partnerOrder_tv = ((TextView) vpFg.findViewById(R.id.my_partner_vp_base_partner_order));
    }
}
