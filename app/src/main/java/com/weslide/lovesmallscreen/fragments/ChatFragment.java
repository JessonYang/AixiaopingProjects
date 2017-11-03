package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.view_yy.adapter.ChatConversationListAdapter;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by YY on 2017/10/26.
 */
public class ChatFragment extends Fragment {
    private RelativeLayout to_fans_rll;
    private View view;
    private ListView lv;
    private ChatConversationListAdapter mAdapter;
    private List<Conversation> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat,container,false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        mAdapter = new ChatConversationListAdapter(getActivity(),mList);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RongIM.getInstance().startPrivateChat(getActivity(),mList.get(i).getTargetId(),mList.get(i).getConversationTitle());
            }
        });
    }

    private void getConversationList() {
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                mList.clear();
                mList.addAll(conversations);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    private void initView() {
        lv = ((ListView) view.findViewById(R.id.conversation_lv));
        ((TextView) view.findViewById(R.id.chat_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().startConversation(getActivity(), Conversation.ConversationType.PRIVATE,"49851","");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getConversationList();
    }

}
