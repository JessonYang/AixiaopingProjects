package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.SystemMsgDtModel;
import com.weslide.lovesmallscreen.models.SystemMsgDtObModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import java.util.List;

/**
 * Created by YY on 2017/5/8.
 */
public class SystemMsgDtFragment extends BaseFragment {
    private View view;
    private Toolbar toolbar;
    private TextView title;
    private TextView content;
    private TextView sign;
    private TextView time;
    private int msgId;
    private int typeId;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_system_msg_dt, container, false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
        getBundleData();
        getSystemDtData(msgId + "", typeId + "");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void getSystemDtData(String msgId, String typeId) {
        Request<SystemMsgDtModel> request = new Request<>();
        SystemMsgDtModel systemMsgDtModel = new SystemMsgDtModel();
        systemMsgDtModel.setMsgId(msgId);
        systemMsgDtModel.setTypeId(typeId);
        systemMsgDtModel.setUserId(ContextParameter.getUserInfo().getUserId());
        request.setData(systemMsgDtModel);
        RXUtils.request(getActivity(), request, "getMsgDetailSystem", new SupportSubscriber<Response<SystemMsgDtModel>>() {
            @Override
            public void onNext(Response<SystemMsgDtModel> systemMsgDtModelResponse) {
                SystemMsgDtObModel systemMsgDtObModel = systemMsgDtModelResponse.getData().getSystemMsgDtObModel();
                title.setText(systemMsgDtObModel.getTitle());
                sign.setText(systemMsgDtObModel.getSign());
                time.setText(systemMsgDtObModel.getTime());
                StringBuilder stringBuilder = new StringBuilder();
                List<String> list = systemMsgDtObModel.getContent();
                for (String s : list) {
                    stringBuilder.append(s + "\n");
                }
                content.setText(stringBuilder.toString());
                loadingDialog.dismiss();
            }
        });
    }

    private void getBundleData() {
        msgId = getActivity().getIntent().getIntExtra("msgId", 1);
        typeId = getActivity().getIntent().getIntExtra("typeId", 1);
    }

    private void initView() {
        toolbar = ((Toolbar) view.findViewById(R.id.system_msg_dt_toolbar));
        title = ((TextView) view.findViewById(R.id.system_msg_dt_title));
        content = ((TextView) view.findViewById(R.id.system_msg_dt_content));
        sign = ((TextView) view.findViewById(R.id.system_msg_dt_sign));
        time = ((TextView) view.findViewById(R.id.system_msg_dt_time));
    }
}
