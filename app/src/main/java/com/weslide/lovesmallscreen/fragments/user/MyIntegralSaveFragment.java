package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.ScoreExchangeActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.MyScore;
import com.weslide.lovesmallscreen.models.bean.ScoreBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.MyScoreAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/6/12.
 * 积分列表
 */
public class MyIntegralSaveFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tv_to_use)
    TextView tvToUse;
    @BindView(R.id.tv_integral_save)
    TextView tvIntegralSave;
    @BindView(R.id.lv_integral_details)
    SuperRecyclerView lvIntegralDetails;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    MyScoreAdapter adapter;
    ScoreBean mScoreListReqeust = new ScoreBean();
    DataList<MyScore> myScore = new DataList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_my_integral, container, false);

        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(v -> getActivity().finish());

        init();//初始化

        return mView;
    }

    private void init() {
        tvIntegralSave.setText(ContextParameter.getUserInfo().getScore());
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        adapter = new MyScoreAdapter(getActivity(),myScore);

        reLoadData();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        lvIntegralDetails.setLayoutManager(manager);

        lvIntegralDetails.setAdapter(adapter);

        lvIntegralDetails.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getScoreDetails(), 2);

        lvIntegralDetails.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reLoadData();
            }
        });
        lvIntegralDetails.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoadData();
            }
        });
    }

    public void reLoadData(){
        mScoreListReqeust.setPageIndex(0);
        getScoreDetails();
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoadData();
    }

    /**
     * 获取我的积分
     */
    private void getScoreDetails() {
        Request<ScoreBean> request = new Request<>();
        mScoreListReqeust.setPageIndex(mScoreListReqeust.getPageIndex() + 1);
        request.setData(mScoreListReqeust);
        RXUtils.request(getActivity(), request, "getScoreList", new RecyclerViewSubscriber<Response<DataList<MyScore>>>(adapter, myScore){

            @Override
            public void onSuccess(Response<DataList<MyScore>> dataListResponse) {
                tvIntegralSave.setText(dataListResponse.getData().getScore());
                adapter.addDataListNotifyDataSetChanged(dataListResponse.getData());
            }
        });
    }

    @OnClick(R.id.tv_to_use)
    public void onClick() {
        AppUtils.toActivity(getActivity(), ScoreExchangeActivity.class);
    }
}
