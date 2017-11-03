package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Fans;
import com.weslide.lovesmallscreen.models.RecomenderModel;
import com.weslide.lovesmallscreen.models.bean.FansBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.view_yy.adapter.FansConversationListAdapter;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class FansConversationListActivity extends AppCompatActivity {

    private PullToRefreshListView lv;
    private FansConversationListAdapter mAdapter;
    private List<Fans> mList = new ArrayList<>();
    private View lvHeader;
    private TextView recommender_name,fans_num,fans_latest_msg,recommender_name2;
    private ImageView recommender_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans_conversation_list);
        initView();
        initData();
    }

    private void initData() {
        mAdapter = new FansConversationListAdapter(this,mList);
        lv.setAdapter(mAdapter);
        lv.getRefreshableView().addHeaderView(lvHeader);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        getFansList();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RongIM.getInstance().startConversation(FansConversationListActivity.this, Conversation.ConversationType.PRIVATE,mList.get(i-2).getFansId(),"消息");
            }
        });
    }

    private void initView() {
        lv = ((PullToRefreshListView) findViewById(R.id.lv));
        lvHeader = LayoutInflater.from(this).inflate(R.layout.fans_conversation_lv_header_item,null);
        recommender_name = ((TextView) lvHeader.findViewById(R.id.recommender_name));
        recommender_name2 = ((TextView) lvHeader.findViewById(R.id.recommender_name2));
        fans_num = ((TextView) lvHeader.findViewById(R.id.fans_num));
        fans_latest_msg = ((TextView) lvHeader.findViewById(R.id.fans_latest_msg));
        recommender_iv = ((ImageView) lvHeader.findViewById(R.id.recommender_iv));
    }

    /**
     * 获取我的粉丝
     */
    private void getFansList() {
        Request<FansBean> request = new Request<>();
        FansBean mFansListReqeust = new FansBean();
        mFansListReqeust.setPageIndex(mFansListReqeust.getPageIndex() + 1);
        request.setData(mFansListReqeust);
        RXUtils.request(this, request, "getFansList2", new SupportSubscriber<Response<DataList<Fans>>>() {
            @Override
            public void onNext(Response<DataList<Fans>> dataListResponse) {
                RecomenderModel recommender = dataListResponse.getData().getRecommender();
                if (recommender != null) {
                    recommender_name.setText(recommender.getRecommenderName());
                    recommender_name2.setText(recommender.getRecommenderName());
                }
                Glide.with(FansConversationListActivity.this).load(recommender.getRecommenderHeadImage()).into(recommender_iv);
                mList.addAll(dataListResponse.getData().getDataList());
                fans_num.setText(mList.size()+"");
                mAdapter.notifyDataSetChanged();
                getConversationList();
            }
        });
    }

    private void getConversationList() {
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                Toast.makeText(FansConversationListActivity.this, "列表数："+conversations.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }
}
