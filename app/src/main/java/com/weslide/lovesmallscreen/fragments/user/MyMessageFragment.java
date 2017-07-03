package com.weslide.lovesmallscreen.fragments.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Message;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.models.bean.MessageBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.MyMessageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/6/30.
 * 我的消息
 */
public class MyMessageFragment extends BaseFragment {
    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_message_details)
    SuperRecyclerView lvMessageDetails;
    MyMessageAdapter adapter;
    DataList<Message> dataList = new DataList<>();
    MessageBean mMessageListReqeust = new MessageBean();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_my_message, container, false);

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

        adapter = new MyMessageAdapter(getActivity(),dataList);

        loadData();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        lvMessageDetails.setLayoutManager(manager);

        lvMessageDetails.setAdapter(adapter);

        lvMessageDetails.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> loadData(), 2);

        lvMessageDetails.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reLoadData();
            }
        });

    }

    public void reLoadData(){
        mMessageListReqeust.setPageIndex(0);
        loadData();
    }

    /**
     * 获取消息列表
     */
    private void loadData() {
        Request<MessageBean> request = new Request<>();
        mMessageListReqeust.setPageIndex(mMessageListReqeust.getPageIndex() + 1);
        request.setData(mMessageListReqeust);
        RXUtils.request(getActivity(), request, "getMessageList", new RecyclerViewSubscriber<Response<DataList<Message>>>(adapter, dataList){


            @Override
            public void onSuccess(Response<DataList<Message>> dataListResponse) {
                adapter.addDataListNotifyDataSetChanged(dataListResponse.getData());
            }
        });
    }
}
