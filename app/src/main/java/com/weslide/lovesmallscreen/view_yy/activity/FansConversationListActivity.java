package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class FansConversationListActivity extends AppCompatActivity {

    private PullToRefreshListView lv;
    private FansConversationListAdapter mAdapter;
    private List<Fans> mList = new ArrayList<>();
    private View lvHeader;
    private TextView recommender_name, fans_num, fans_latest_msg, fans_chat_time, recommender_name2;
    private ImageView recommender_iv;
    private Handler mHandler = new Handler();
    private LoadingDialog loadingDialog;
    private Toolbar fans_toolbar;
    private int page = 1;
    private LinearLayout no_data_ll;
    private RelativeLayout recommend_rll;
    private RecomenderModel recommender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans_conversation_list);
        initView();
        initData();
    }

    private void initData() {
        fans_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        mAdapter = new FansConversationListAdapter(this, mList);
        lv.setAdapter(mAdapter);
        lv.getRefreshableView().addHeaderView(lvHeader);
        lv.setMode(PullToRefreshBase.Mode.DISABLED);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getFansList();
            }
        });
        getFansList();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 1) {
                    RongIM.getInstance().startConversation(FansConversationListActivity.this, Conversation.ConversationType.PRIVATE, mList.get(i - 2).getFansId(), mList.get(i - 2).getName());
                }
            }
        });
    }

    private void initView() {
        lv = ((PullToRefreshListView) findViewById(R.id.lv));
        fans_toolbar = ((Toolbar) findViewById(R.id.fans_toolbar));
        no_data_ll = ((LinearLayout) findViewById(R.id.no_data_ll));
        lvHeader = LayoutInflater.from(this).inflate(R.layout.fans_conversation_lv_header_item, null);
        recommender_name = ((TextView) lvHeader.findViewById(R.id.recommender_name));
        recommender_name2 = ((TextView) lvHeader.findViewById(R.id.recommender_name2));
        recommend_rll = ((RelativeLayout) lvHeader.findViewById(R.id.recommend_rll));
        fans_num = ((TextView) lvHeader.findViewById(R.id.fans_num));
        fans_latest_msg = ((TextView) lvHeader.findViewById(R.id.fans_latest_msg));
        fans_chat_time = ((TextView) lvHeader.findViewById(R.id.fans_chat_time));
        recommender_iv = ((ImageView) lvHeader.findViewById(R.id.recommender_iv));
    }

    /**
     * 获取我的粉丝
     */
    private void getFansList() {
        Request<FansBean> request = new Request<>();
        FansBean mFansListReqeust = new FansBean();
        mFansListReqeust.setPageIndex(page);
        request.setData(mFansListReqeust);
        RXUtils.request(this, request, "getFansList2", new SupportSubscriber<Response<DataList<Fans>>>() {
            @Override
            public void onNext(Response<DataList<Fans>> dataListResponse) {
                recommender = dataListResponse.getData().getRecommender();
                if (dataListResponse.getData().getDataList() != null && dataListResponse.getData().getDataList().size() > 0) {
                    mList.addAll(dataListResponse.getData().getDataList());
                } else {
                    lv.onRefreshComplete();
                }
                if (recommender != null) {
                    if (recommender.getRecommenderName() != null && recommender.getRecommenderName().length() > 0) {
                        recommend_rll.setVisibility(View.VISIBLE);
                        recommender_name.setText(recommender.getRecommenderName());
                        recommender_name2.setText("推荐人:" + recommender.getRecommenderName());
                    } else {
                        recommender_name.setText("暂无推荐人");
                        recommend_rll.setVisibility(View.GONE);
                        Log.d("雨落无痕丶", "暂无推荐人: ");
                        mAdapter.notifyDataSetChanged();
                    }
                    if (recommender.getRecommenderHeadImage() != null && recommender.getRecommenderHeadImage().length() > 0) {
                        Glide.with(FansConversationListActivity.this).load(recommender.getRecommenderHeadImage()).into(recommender_iv);
                    }
                    recommend_rll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (recommender.getRecommenderId() != null && recommender.getRecommenderId().length() > 0) {
                                RongIM.getInstance().startConversation(FansConversationListActivity.this, Conversation.ConversationType.PRIVATE, recommender.getRecommenderId(), recommender.getRecommenderName() + "");
                            }
                        }
                    });
                } else {
                    recommend_rll.setVisibility(View.GONE);
                }
                if (recommender.getFansNum() != null && recommender.getFansNum().length() > 0) {
                    fans_num.setText(recommender.getFansNum());
                } else {
                    fans_num.setText(mList.size() + "");
                }
                if (mList.size() > 0) {
                    no_data_ll.setVisibility(View.GONE);
                    getConversationList();
                } else {
                    no_data_ll.setVisibility(View.VISIBLE);
                    loadingDialog.dismiss();
                }
            }
        });
    }

    private void getConversationList() {
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if (conversations != null && conversations.size() > 0) {
                    moveFans(conversations);
                } else {
                    mAdapter.notifyDataSetChanged();
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    private void moveFans(List<Conversation> conversations) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Collections.reverse(conversations);
                for (int i = 0; i < conversations.size(); i++) {
                    for (int j = 0; j < mList.size(); j++) {
                        Fans fans = mList.get(j);
                        if (conversations.get(i).getTargetId().equals(fans.getFansId())) {
                            mList.remove(j);
                            mList.add(0, fans);
                        }

                        //推荐人聊天信息展示
                        /*if (recommender.getRecommenderId() != null && recommender.getRecommenderId().length() > 0 && conversations.get(i).getTargetId().equals(recommender.getRecommenderId())) {
                            fans_latest_msg.setVisibility(View.VISIBLE);
                            fans_chat_time.setVisibility(View.VISIBLE);
                            MessageContent messageContent = conversations.get(i).getLatestMessage();

                            if (messageContent instanceof TextMessage) {//文本消息
                                TextMessage textMessage = (TextMessage) messageContent;
                                fans_latest_msg.setText(textMessage.getContent());
                            } else if (messageContent instanceof ImageMessage) {//图片消息
                                ImageMessage imageMessage = (ImageMessage) messageContent;
                                fans_latest_msg.setText("[图片消息]");
                            } else if (messageContent instanceof VoiceMessage) {//语音消息
                                VoiceMessage voiceMessage = (VoiceMessage) messageContent;
                                fans_latest_msg.setText("[语音消息]");
                            } else if (messageContent instanceof RichContentMessage) {//图文消息
                                RichContentMessage richContentMessage = (RichContentMessage) messageContent;
                                fans_latest_msg.setText("[图文消息]");
                            } else {//其他消息

                            }
                        }*/
                    }
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        lv.onRefreshComplete();
                        loadingDialog.dismiss();
                    }
                });
            }
        }).start();
    }
}
