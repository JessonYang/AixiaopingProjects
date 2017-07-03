package com.weslide.lovesmallscreen.fragments.withdrawals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Withdrawals;
import com.weslide.lovesmallscreen.models.bean.WithdrawalsBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.MyApplyAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/12/13.
 */
public class ApplyListFragment extends BaseFragment {
    @BindView(R.id.list)
    SuperRecyclerView list;
    private String isChecked;
    View mView;
    MyApplyAdapter adapter;
    DataList<Withdrawals> apply = new DataList<>();
    WithdrawalsBean mApplyListReqeust = new WithdrawalsBean();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_apply, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init(){
        adapter = new MyApplyAdapter(getActivity(),apply);
        getApply();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        list.setLayoutManager(manager);

        list.setAdapter(adapter);

        list.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getApply(), 2);

        list.setRefreshListener(() -> reLoadData());

        list.setDifferentSituationOptionListener(v -> reLoadData());
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoadData();
    }

    private void reLoadData() {

        mApplyListReqeust.setPageIndex(0);
        getApply();
    }

    private void getApply() {
        Request<WithdrawalsBean> request = new Request<>();
        mApplyListReqeust.setPageIndex(mApplyListReqeust.getPageIndex() + 1);
        request.setData(mApplyListReqeust);
        RXUtils.request(getActivity(), request, "getwithdrawalsInfo", new SupportSubscriber<Response<DataList<Withdrawals>>>() {

            @Override
            public void onNext(Response<DataList<Withdrawals>> dataListResponse) {
                adapter.addDataListNotifyDataSetChanged(dataListResponse.getData());
            }
        });
    }
}
