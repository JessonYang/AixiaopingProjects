package com.weslide.lovesmallscreen.view_yy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketListModel;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketListObModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.view_yy.adapter.MyTicketLvAdapter;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/6/6.
 */
public class MyTicketVpBaseFragment extends BaseFragment {
    private View mFgView;
    private PullLoadMoreRecyclerView mLv;
    private String ticketType = "0";
    private int page = 1;
    private List<TicketListObModel> mLvList = new ArrayList<>();
    private int ASKMODE = 0;
    private MyTicketLvAdapter mAdapter;
    private LoadingDialog loadingDialog;
    private ImageView no_data_iv;

    public static MyTicketVpBaseFragment getInstance(Bundle bundle) {
        MyTicketVpBaseFragment myTicketVpBaseFragment = new MyTicketVpBaseFragment();
        myTicketVpBaseFragment.setArguments(bundle);
        return myTicketVpBaseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ticketType = getArguments().getString("ticketType");
        mFgView = inflater.inflate(R.layout.fragment_my_ticket_vp_base, container, false);
        initView();
        initData();
        return mFgView;
    }

    private void initData() {
        loadingDialog.show();
        mLv.setLinearLayout();
        mAdapter = new MyTicketLvAdapter(mLvList, getSupportActivity(), ticketType);
        mLv.setAdapter(mAdapter);
        ASKMODE = 0;
        askNetData();
        mLv.setFooterViewText("加载中...");
        mLv.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                ASKMODE = 0;
                askNetData();
            }

            @Override
            public void onLoadMore() {
                page++;
                ASKMODE = 1;
                askNetData();
            }
        });
    }

    private void askNetData() {
        Request<TicketListModel> request = new Request<>();
        TicketListModel ticketListModel = new TicketListModel();
        ticketListModel.setTicketType(ticketType);
        ticketListModel.setPageIndex(page + "");
        request.setData(ticketListModel);
        RXUtils.request(getSupportActivity(), request, "getTicketList", new SupportSubscriber<Response<TicketListModel>>() {
            @Override
            public void onNext(Response<TicketListModel> ticketListModelResponse) {
                if (ASKMODE == 0) {
                    mLvList.clear();
                }
                mLvList.addAll(ticketListModelResponse.getData().getTickets());
                mAdapter.notifyDataSetChanged();
                mLv.setPullLoadMoreCompleted();
                if (mLvList == null || mLvList.size() == 0) {
                    no_data_iv.setVisibility(View.VISIBLE);
                    mLv.setVisibility(View.GONE);
                }else {
                    no_data_iv.setVisibility(View.GONE);
                    mLv.setVisibility(View.VISIBLE);
                }
                if (loadingDialog != null && loadingDialog.isShowing()){
                    loadingDialog.dismiss();
                }
            }
        });
    }

    private void initView() {
        mLv = ((PullLoadMoreRecyclerView) mFgView.findViewById(R.id.my_ticket_vp_base_fg_lv));
        no_data_iv = ((ImageView) mFgView.findViewById(R.id.no_data_iv));
        loadingDialog = new LoadingDialog(getSupportActivity());
    }
}
