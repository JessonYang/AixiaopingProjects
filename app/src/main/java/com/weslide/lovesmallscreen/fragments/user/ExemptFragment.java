package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.HomeActivity;
import com.weslide.lovesmallscreen.activitys.mall.BecomeVipActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.Exempt;
import com.weslide.lovesmallscreen.models.bean.ExemptBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.ExemptListAdaper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/6/28.
 * 免单券列表
 */
public class ExemptFragment extends BaseFragment {
    View mView;
    @BindView(R.id.ll_no_exempt)
    LinearLayout llNoExempt;
    @BindView(R.id.lv_exempt_details)
    SuperRecyclerView lvExemptDetails;
    @BindView(R.id.ll_have_exempt)
    LinearLayout llHaveExempt;
    ExemptListAdaper adapter;
    ExemptBean mExemptListReqeust = new ExemptBean();
    DataList<Exempt> mExempt = new DataList<>();
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_exempt, container, false);

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

        adapter = new ExemptListAdaper(getActivity(), mExempt);

        getExemptList();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        lvExemptDetails.setLayoutManager(manager);

        lvExemptDetails.setAdapter(adapter);

        lvExemptDetails.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getExemptList(), 2);

        lvExemptDetails.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reLoadData();
            }
        });
        lvExemptDetails.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoadData();
            }
        });


    }

    private void reLoadData() {
        mExemptListReqeust.setPageIndex(0);
        getExemptList();
    }

    private void getExemptList() {
        Request<ExemptBean> request = new Request<>();
        mExemptListReqeust.setPageIndex(mExemptListReqeust.getPageIndex() + 1);
        request.setData(mExemptListReqeust);
        RXUtils.request(getActivity(), request, "getExemptList", new RecyclerViewSubscriber<Response<DataList<Exempt>>>(adapter, mExempt) {


            @Override
            public void onSuccess(Response<DataList<Exempt>> dataListResponse) {
                adapter.addDataListNotifyDataSetChanged(dataListResponse.getData());
                if(dataListResponse.getData().getDataList().size()==0){
                    lvExemptDetails.setVisibility(View.GONE);
                    llNoExempt.setVisibility(View.VISIBLE);
                }else{
                    lvExemptDetails.setVisibility(View.VISIBLE);
                    llNoExempt.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoadData();
    }

    @OnClick(R.id.btn_dredge)
    public void onClick() {
        AppUtils.toActivity(getActivity(), BecomeVipActivity.class);
    }
}
