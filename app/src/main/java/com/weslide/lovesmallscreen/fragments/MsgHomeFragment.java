package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rey.material.widget.Button;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.OrderMsgActivity;
import com.weslide.lovesmallscreen.activitys.SystemMsgActivity;
import com.weslide.lovesmallscreen.adapter.MsgHomeLvAdapter;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.MsgHomeModel;
import com.weslide.lovesmallscreen.models.MsgHomeObModel;
import com.weslide.lovesmallscreen.models.RefreshPushMsg;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/5/8.
 */
public class MsgHomeFragment extends Fragment {
    private View view;
    private PullToRefreshListView lv;
    private List<MsgHomeObModel> list = new ArrayList<>();
    private MsgHomeLvAdapter mAdapter;
    private LoadingDialog loadingDialog;
    private Toolbar toolbar;
    private LinearLayout empty_layout;
    private Button reload_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_msg_home, container, false);
        initView();
        loadingDialog = new LoadingDialog(getActivity());
        initData();
        EventBus.getDefault().register(this);
        mAdapter = new MsgHomeLvAdapter(getActivity(), list);
        lv.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLvList();
    }

    private void initData() {
        getLvList();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int typeId = list.get(i - 1).getTypeId();
                Bundle bundle = new Bundle();
                bundle.putInt("typeId", typeId);
                if (typeId == 1 || typeId == 3) {
                    AppUtils.toActivity(getActivity(), SystemMsgActivity.class, bundle);
                } else if (typeId == 2) {
                    AppUtils.toActivity(getActivity(), OrderMsgActivity.class, bundle);
                }
                /*if (list.get(i - 1).getUnread().equals("0") || list.get(i - 1).getUnread().length() == 0 || list.get(i - 1).getUnread() == null) {
                    lv.getRefreshableView().getChildAt(i).findViewById(R.id.system_msg_unread_count).setVisibility(View.GONE);
//                    Log.d("雨落无痕丶", "onItemClick: unread");
                }*/
            }
        });
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getLvList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
        reload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLvList();
            }
        });
    }

    private void getLvList() {
        loadingDialog.show();
        Request<MsgHomeModel> request = new Request();
        MsgHomeModel msgHomeModel = new MsgHomeModel();
        msgHomeModel.setUserId(ContextParameter.getUserInfo().getUserId());
        request.setData(msgHomeModel);
        RXUtils.request(getActivity(), request, "getMsgTypes", new SupportSubscriber<Response<MsgHomeModel>>() {
            @Override
            public void onNext(Response<MsgHomeModel> msgHomeModelResponse) {
                list.clear();
                list.addAll(msgHomeModelResponse.getData().getMsgTypes());
                loadingDialog.dismiss();
                if (list.size() == 0 || list == null){
                    lv.setVisibility(View.GONE);
                    empty_layout.setVisibility(View.VISIBLE);
                }else {
                    lv.setVisibility(View.VISIBLE);
                    empty_layout.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();
                lv.onRefreshComplete();
            }
        });
    }

    private void initView() {
        lv = ((PullToRefreshListView) view.findViewById(R.id.all_msg_type_lv));
        toolbar = ((Toolbar) view.findViewById(R.id.msg_home_toolbar));
        empty_layout = ((LinearLayout) view.findViewById(R.id.msg_home_empty_layout));
        reload_btn = ((Button) view.findViewById(R.id.btn_empty_reload));
    }

    @Subscribe
    public void refreshMsg(RefreshPushMsg refreshPushMsg){
        getLvList();
    }
}
