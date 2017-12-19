package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.view_yy.activity.FansConversationListActivity;
import com.weslide.lovesmallscreen.view_yy.adapter.ChatConversationListAdapter;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by YY on 2017/10/26.
 * 自定义会话列表fragment
 */
public class ChatFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout to_fans_rll;
    private View view;
    private ListView lv;
    private ChatConversationListAdapter mAdapter;
    private List<Conversation> mList = new ArrayList<>();
    private View header;
    private RelativeLayout header_rll;
    private LinearLayout empty_layout;
    private UserInfo userInfo;
    private List<String> userIds;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        userIds = new ArrayList<>();
        mAdapter = new ChatConversationListAdapter(getActivity(), mList);
        lv.setAdapter(mAdapter);
        lv.addHeaderView(header);
        header_rll.setOnClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RongIM.getInstance().startPrivateChat(getActivity(), mList.get(i-1).getTargetId(), mList.get(i-1).getConversationTitle());
            }
        });
    }

    private void getConversationList() {
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if (conversations != null && conversations.size() > 0) {
                    //设置信息提供者
                    /*RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                        @Override
                        public UserInfo getUserInfo(String s) {
                            Log.d("雨落无痕丶", "getUserInfo: ===");
                            Request<RongUserInfo> request = new Request<>();
                            RongUserInfo rongUserInfo = new RongUserInfo();
                            rongUserInfo.setUserId(s);
                            request.setData(rongUserInfo);
                            RXUtils.request(getActivity(),request,"getRongUserInfo", new SupportSubscriber<Response<RongUserInfo>>() {
                                @Override
                                public void onNext(Response<RongUserInfo> rongUserInfoResponse) {
                                    userInfo =  new UserInfo(rongUserInfoResponse.getData().getUserId(), rongUserInfoResponse.getData().getName(), Uri.parse(rongUserInfoResponse.getData().getPortraitUri()));
                                    Log.d("雨落无痕丶", "userInfo头像: "+rongUserInfoResponse.getData().getPortraitUri());
                                    RongIM.getInstance().refreshUserInfoCache(userInfo);
                                }
                            });
                            return userInfo;
                        }
                    },true);*/
                    empty_layout.setVisibility(View.GONE);
                    mList.clear();
                    mList.addAll(conversations);
                    mAdapter.notifyDataSetChanged();
                }else {
                    empty_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    private void initView() {
        lv = ((ListView) view.findViewById(R.id.conversation_lv));
        empty_layout = ((LinearLayout) view.findViewById(R.id.rc_conversation_list_empty_layout));
        header = LayoutInflater.from(getActivity()).inflate(R.layout.conversation_list_header,null);
        header_rll = ((RelativeLayout) header.findViewById(R.id.header_rll));
        /*((TextView) view.findViewById(R.id.chat_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().startConversation(getActivity(), Conversation.ConversationType.PRIVATE, "49851", "");
            }
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();
        getConversationList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_rll:
                AppUtils.toActivity(getActivity(), FansConversationListActivity.class);
                break;
        }
    }
}
