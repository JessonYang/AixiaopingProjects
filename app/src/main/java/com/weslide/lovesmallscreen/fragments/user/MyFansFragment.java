package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.models.Fans;
import com.weslide.lovesmallscreen.models.bean.FansBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.MyFansListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/6/20.
 * 我的粉丝列表
 */
public class MyFansFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tv_fans_number)
    TextView tvFansNumber;
    @BindView(R.id.lv_integral_details)
    SuperRecyclerView lvFansDetails;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    DataList<Fans> fans = new DataList<>();
    FansBean mFansListReqeust = new FansBean();
    MyFansListAdapter adapter;
    String fansNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_my_fans, container, false);

        ButterKnife.bind(this, mView);
        toolBar.setNavigationOnClickListener(v -> getActivity().finish());

        init();

        return mView;
    }

    private void init() {
        adapter = new MyFansListAdapter(getActivity(),fans);

        Bundle bundle = getArguments();

        fansNumber =  bundle.getString("number");

        tvFansNumber.setText(fansNumber);

        reLoadData();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        lvFansDetails.setLayoutManager(manager);

        lvFansDetails.setSupeRecyclerView_parent_type(0);

        lvFansDetails.setAdapter(adapter);

        lvFansDetails.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> getFansDetails(), 2);

        lvFansDetails.setRefreshListener(() -> reLoadData());

        lvFansDetails.setDifferentSituationOptionListener(v -> reLoadData());

    }
    public void reLoadData() {
        mFansListReqeust.setPageIndex(0);
        getFansDetails();
    }

    /**
     * 获取我的粉丝
     */
    private void getFansDetails() {
        Request<FansBean> request = new Request<>();
        mFansListReqeust.setPageIndex(mFansListReqeust.getPageIndex() + 1);
        request.setData(mFansListReqeust);
        RXUtils.request(getActivity(), request, "getFansList", new RecyclerViewSubscriber<Response<DataList<Fans>>>(adapter, fans){
            @Override
            public void onSuccess(Response<DataList<Fans>> dataListResponse) {
                adapter.addDataListNotifyDataSetChanged(dataListResponse.getData());
            }
        });
    }
}
