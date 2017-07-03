package com.weslide.lovesmallscreen.fragments.withdrawals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.withdrawals.WithdrawalsListActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Withdrawals;
import com.weslide.lovesmallscreen.models.bean.WithdrawalsBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.NewCashAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/12/30.
 */
public class NewCashFragment extends BaseFragment implements View.OnClickListener {

    View mView;
    @BindView(R.id.nobar)
    Toolbar nobar;
    @BindView(R.id.lv_cash_list)
    PullToRefreshListView lvCashList;
    WithdrawalsBean bean = new WithdrawalsBean();
    NewCashAdapter adapter;
    private List<Withdrawals> dataList = new ArrayList<>();
    private int pageIndex = 1;
    private View header;
    private TextView totalMoney;
    private LinearLayout withdraw_ll;
    private ListView listView;
    private TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fargment_cash_list, container, false);
        header = inflater.inflate(R.layout.layout_cash_head, lvCashList, false);

        ButterKnife.bind(this, mView);
        init();
        getUsersM();
        return mView;
    }

    private void init() {

        totalMoney = ((TextView) header.findViewById(R.id.tv_totle_money));
        withdraw_ll = ((LinearLayout) header.findViewById(R.id.tv_withdraw));
        title = ((TextView) header.findViewById(R.id.user_left_money));
        withdraw_ll.setOnClickListener(this);

        nobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        listView = lvCashList.getRefreshableView();
        listView.addHeaderView(header);
        adapter = new NewCashAdapter(getActivity(), dataList);
        lvCashList.setAdapter(adapter);
        lvCashList.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lvCashList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex++;
                getUsersM();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        reLoad();
    }

    private void reLoad(){
        getUsersM();
    }
    private void getUsersM(){
        Request<WithdrawalsBean> request = new Request<>();
        bean.setPageIndex(pageIndex);
        request.setData(bean);
        RXUtils.request(getActivity(), request, "getUsersM", new SupportSubscriber<Response<DataList<Withdrawals>>>() {

            @Override
            public void onNext(Response<DataList<Withdrawals>> dataListResponse) {
                if (dataListResponse.getData().getMoney() != null) {
                    totalMoney.setText(dataListResponse.getData().getMoney());
                }
                if (dataListResponse.getData().getTitle() != null) {
                    title.setText(dataListResponse.getData().getTitle());
                }
                dataList.addAll(dataListResponse.getData().getDataList());
                adapter.notifyDataSetChanged();
                lvCashList.onRefreshComplete();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_withdraw:
                AppUtils.toActivity(getActivity(),WithdrawalsListActivity.class);
                break;
        }
    }
}
