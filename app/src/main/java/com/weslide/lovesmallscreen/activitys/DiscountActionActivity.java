package com.weslide.lovesmallscreen.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.DeleteMsgModel;
import com.weslide.lovesmallscreen.models.OrderMsgDtOb;
import com.weslide.lovesmallscreen.models.OrderMsgModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.view_yy.activity.ActionDtActivity;
import com.weslide.lovesmallscreen.view_yy.adapter.ActionMsgLvAdapter;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/9/27.
 */
public class DiscountActionActivity extends BaseActivity {

    private Toolbar toolbar;
    private LinearLayout empty_layout;
    //    private Button reload_btn;
    private PullToRefreshListView mLv;
    private List<OrderMsgDtOb> list = new ArrayList<>();
    private ActionMsgLvAdapter adapter;
    private LoadingDialog loadingDialog;
    private int typeId;
    private int position;
    private View pup_item;
    private TextView delete_tv;
    private ImageView delete_all;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_action);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loadingDialog != null && !loadingDialog.isShowing()){
            loadingDialog.show();
        }
        getOrderMsgList(this, typeId + "");
    }

    private void initData() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DiscountActionActivity.this);
                builder.setMessage("确认删除所有消息?");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteAllData();
                            }
                        })
                        .create()
                        .show();
            }
        });
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        typeId = getIntent().getExtras().getInt("typeId");
        adapter = new ActionMsgLvAdapter(this, list);
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("msgId", list.get(i - 1).getMsgId() + "");
                bundle.putString("typeId", typeId + "");
                AppUtils.toActivity(DiscountActionActivity.this, ActionDtActivity.class, bundle);
            }
        });
        mLv.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                PopupWindow pw = new PopupWindow(pup_item, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                pw.setFocusable(true);
                pw.setOutsideTouchable(true);
                pw.setBackgroundDrawable(new BitmapDrawable());
                pw.showAsDropDown(view, view.getWidth() / 2, -view.getHeight());
                delete_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteOneData();
                        pw.dismiss();
                    }
                });
                return true;
            }
        });
        /*reload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loadingDialog != null && !loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                getOrderMsgList(DiscountActionActivity.this, typeId + "");
            }
        });*/
    }

    private void initView() {
        toolbar = ((Toolbar) findViewById(R.id.action_msg_toolbar));
        empty_layout = ((LinearLayout) findViewById(R.id.msg_home_empty_layout));
//        reload_btn = ((Button) findViewById(R.id.btn_empty_reload));
        mLv = ((PullToRefreshListView) findViewById(R.id.action_msg_lv));
        delete_all = ((ImageView) findViewById(R.id.system_msg_del_all));
        pup_item = LayoutInflater.from(this).inflate(R.layout.delete_pup_item, null);
        delete_tv = ((TextView) pup_item.findViewById(R.id.delete_tv));
    }

    public void getOrderMsgList(Context context, String typeId) {
        Request<OrderMsgModel> request = new Request<>();
        OrderMsgModel orderMsgModel = new OrderMsgModel();
        orderMsgModel.setUserId(ContextParameter.getUserInfo().getUserId());
        orderMsgModel.setTypeId(typeId);
        request.setData(orderMsgModel);
        RXUtils.request(context, request, "getMsgList", new SupportSubscriber<Response<OrderMsgModel>>() {
            @Override
            public void onNext(Response<OrderMsgModel> orderMsgModelResponse) {
                list.clear();
                list.addAll(orderMsgModelResponse.getData().getMsgList());
                adapter.notifyDataSetChanged();
                if (list.size() == 0) {
                    mLv.setVisibility(View.GONE);
                    empty_layout.setVisibility(View.VISIBLE);
                } else {
                    mLv.setVisibility(View.VISIBLE);
                    empty_layout.setVisibility(View.GONE);
                }
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("雨落无痕丶", "onError: " + e.toString());
            }

            @Override
            public void onResponseError(Response response) {
                Log.d("雨落无痕丶", "onResponseError: " + response.getMessage());
            }
        });
    }

    private void deleteOneData() {
        Request<DeleteMsgModel> request = new Request();
        DeleteMsgModel deleteMsgModel = new DeleteMsgModel();
        deleteMsgModel.setUserId(ContextParameter.getUserInfo().getUserId());
        deleteMsgModel.setTypeId(typeId + "");
        deleteMsgModel.setMsgId(list.get(position - 1).getMsgId() + "");
        request.setData(deleteMsgModel);
        RXUtils.request(this, request, "deleteMsg", new SupportSubscriber<Response<DeleteMsgModel>>() {
            @Override
            public void onNext(Response<DeleteMsgModel> deleteMsgModelResponse) {
                list.remove(position - 1);
                adapter.notifyDataSetChanged();
                if (list.size() == 0) {
                    mLv.setVisibility(View.GONE);
                    empty_layout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void deleteAllData() {
        Request<DeleteMsgModel> request = new Request();
        DeleteMsgModel deleteMsgModel = new DeleteMsgModel();
        deleteMsgModel.setUserId(ContextParameter.getUserInfo().getUserId());
        deleteMsgModel.setTypeId(typeId + "");
        deleteMsgModel.setMsgId("-1");
        request.setData(deleteMsgModel);
        RXUtils.request(this, request, "deleteMsg", new SupportSubscriber<Response<DeleteMsgModel>>() {
            @Override
            public void onNext(Response<DeleteMsgModel> deleteMsgModelResponse) {
                list.clear();
                adapter.notifyDataSetChanged();
                mLv.setVisibility(View.GONE);
                empty_layout.setVisibility(View.VISIBLE);
            }
        });
    }

}
