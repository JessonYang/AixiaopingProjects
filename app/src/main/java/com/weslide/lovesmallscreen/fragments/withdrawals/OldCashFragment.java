package com.weslide.lovesmallscreen.fragments.withdrawals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Withdrawals;
import com.weslide.lovesmallscreen.models.bean.WithdrawalsBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.CashAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/12/30.
 */
public class OldCashFragment extends BaseFragment {

    View mView;
    @BindView(R.id.nobar)
    Toolbar nobar;
    @BindView(R.id.lv_cash_list)
    SuperRecyclerView lvCashList;
    WithdrawalsBean bean = new WithdrawalsBean();
    CashAdapter adapter;
    DataList<RecyclerViewModel> dataList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fargment_cash_list, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {

        nobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        dataList = new DataList<>();
        dataList.setDataList(new ArrayList<>());
        adapter = new CashAdapter(getActivity(), dataList);
        lvCashList.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvCashList.setAdapter(adapter);
        lvCashList.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getMoreUsersM(), 3);
        lvCashList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reLoad();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoad();
    }

    private void reLoad(){
        bean.setPageIndex(0);
        dataList.getDataList().clear();
        getUsersM();
    }
    private void getUsersM(){
        Request<WithdrawalsBean> request = new Request<>();
        bean.setPageIndex(bean.getPageIndex()+1);
        request.setData(bean);
        RXUtils.request(getActivity(), request, "getUsersM", new SupportSubscriber<Response<DataList<Withdrawals>>>() {

            @Override
            public void onNext(Response<DataList<Withdrawals>> dataListResponse) {
                handlerData(dataListResponse);
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void handlerData(Response<DataList<Withdrawals>> response) {
        DataList<RecyclerViewModel> recyclerViewModel = new DataList<>();
        List<RecyclerViewModel> list = new ArrayList<>();
        RecyclerViewModel money = new RecyclerViewModel();
        money.setData(response.getData().getMoney());
        money.setItemType(CashAdapter.TYPE_HEAD);
        dataList.getDataList().add(money);
        //  list.add(money);
        if (response.getData().getDataList() != null && response.getData().getDataList().size() > 0) {
            for (Withdrawals asset : response.getData().getDataList()) {
                RecyclerViewModel assetModle = new RecyclerViewModel();
                assetModle.setData(asset);
                assetModle.setItemType(CashAdapter.TYPE_ASSETS_CLASSIFYS);
                list.add(assetModle);
                dataList.getDataList().add(assetModle);
            }
        }
    }

    private void handlerMoreData(Response<DataList<Withdrawals>> response) {
        if (response.getData().getDataList() != null && response.getData().getDataList().size() > 0) {
            for (Withdrawals asset : response.getData().getDataList()) {
                RecyclerViewModel assetModle = new RecyclerViewModel();
                assetModle.setData(asset);
                assetModle.setItemType(CashAdapter.TYPE_ASSETS_CLASSIFYS);
                dataList.getDataList().add(assetModle);
            }
        }
    }
    private void getMoreUsersM(){
        Request<WithdrawalsBean> request = new Request<>();
        bean.setPageIndex(bean.getPageIndex()+1);
        request.setData(bean);
        RXUtils.request(getActivity(), request, "getUsersM", new SupportSubscriber<Response<DataList<Withdrawals>>>() {

            @Override
            public void onNext(Response<DataList<Withdrawals>> dataListResponse) {
                handlerMoreData(dataListResponse);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
