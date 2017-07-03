package com.weslide.lovesmallscreen.view_yy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.PartnerIconListModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;

/**
 * Created by YY on 2017/6/7.
 */
public class PartnerListFragment extends BaseFragment {
    private View mFgView;
    private GridView gv;
    private TextView partner_num_tv;
    private Button add_partner_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFgView = inflater.inflate(R.layout.fragment_partner_list,container,false);
        initView();
        initData();
        return mFgView;
    }

    private void initData() {

        Request<PartnerIconListModel> request = new Request<>();
        RXUtils.request(getActivity(),request,"partnerList", new SupportSubscriber<Response<PartnerIconListModel>>() {
            @Override
            public void onNext(Response<PartnerIconListModel> partnerIconListModelResponse) {

            }
        });
    }

    private void initView() {
        gv = ((GridView) mFgView.findViewById(R.id.partner_list_gv));
        partner_num_tv = ((TextView) mFgView.findViewById(R.id.partner_all_num));
        add_partner_btn = ((Button) mFgView.findViewById(R.id.partner_list_btn));
    }
}
