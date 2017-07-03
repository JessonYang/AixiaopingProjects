package com.weslide.lovesmallscreen.fragments.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.rey.material.widget.Button;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.order.ApplyBackOrderActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.core.UploadSubscriber;
import com.weslide.lovesmallscreen.models.BackOrderInfo;
import com.weslide.lovesmallscreen.models.OrderItem;
import com.weslide.lovesmallscreen.models.bean.UploadFileBean;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateOrderListMessage;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.utils.Utils;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;
import com.weslide.lovesmallscreen.views.order.ApplyBackOrderView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/15.
 * 申请退单
 */
public class ApplyBackOrderFragment extends BaseFragment {

    /**
     * 订单所属的订单状态,用于刷新列表
     */
    public static final String KEY_STATUS = "KEY_STATUS";

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.layout_container)
    LinearLayout layoutContainer;

    List<ApplyBackOrderView> applyBackOrderViews = new ArrayList<>();
    List<OrderItem> orderItems;

    String status;
    @BindView(R.id.btn_post)
    Button btnPost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_apply_back_order, container, false);

        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        loadBundle();

        for (int i = 0; i < orderItems.size(); i++) {
            ApplyBackOrderView view = new ApplyBackOrderView(getActivity());
            view.bindView(orderItems.get(i));
            applyBackOrderViews.add(view);
            layoutContainer.addView(view);
        }

        return mView;
    }

    private void loadBundle() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            status = bundle.getString(KEY_STATUS);
            orderItems = (ArrayList) bundle.getSerializable(ApplyBackOrderActivity.KEY_ORDER_ITEM_LIST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (int i = 0; i < applyBackOrderViews.size(); i++) {
            applyBackOrderViews.get(i).onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.btn_post)
    public void onClick() {

        if(Utils.isFastClick()){
            return;
        }

        List<BackOrderInfo> backOrderInfos = new ArrayList<>();

        for (int i = 0; i < applyBackOrderViews.size(); i++) {
            if (StringUtils.isEmpty(applyBackOrderViews.get(i).getContent())) {
                T.showShort(getActivity(), "请完善退单信息");
                return;
            }

            BackOrderInfo info = new BackOrderInfo();
            info.setOrderItemId(orderItems.get(i).getOrderItemId());
            info.setContent(applyBackOrderViews.get(i).getContent());
            info.setDrawbackWay(applyBackOrderViews.get(i).getDrawbackMode());
            info.setImages(applyBackOrderViews.get(i).getSelectPath());

            backOrderInfos.add(info);
        }

        btnPost.setClickable(false);

        List<UploadFileBean> uploadFileBeans = new ArrayList<>();
        //图片上传
        for (BackOrderInfo backOrderInfo : backOrderInfos) {
            for (String img : backOrderInfo.getImages()) {
                UploadFileBean bean = new UploadFileBean();
                bean.setFile(new File(img));
                bean.setUserId(ContextParameter.getUserInfo().getUserId());
                bean.setFileUse(Constants.UPLOAD_APPLY_BACK_ORDER);

                uploadFileBeans.add(bean);
            }
        }

        LoadingDialog dialog = new LoadingDialog(getActivity());

        RXUtils.uploadImages(getActivity(), uploadFileBeans, new UploadSubscriber() {

            @Override
            public void onStart() {
                super.onStart();
                dialog.show();

            }

            @Override
            public void onStop() {
                super.onStop();
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                btnPost.setClickable(true);
                T.showShort(getActivity(), "未知错误");

            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getActivity(), "上传失败");
            }

            @Override
            public void onNext(List<Response<UploadFileBean>> responses) {
                for (BackOrderInfo backOrderInfo : backOrderInfos) {
                    for (int i = 0; i < backOrderInfo.getImages().size(); i++) {
                        for (Response<UploadFileBean> resonse : responses) {
                            if (backOrderInfo.getImages().get(i).equals(resonse.getData().getFile().getPath())) {
                                backOrderInfo.getImages().set(i, resonse.getData().getOppositeUrl());
                                break;
                            }
                        }
                    }
                }

                Map<String, List<BackOrderInfo>> value = new HashMap<String, List<BackOrderInfo>>();
                value.put("dataList", backOrderInfos);

                Request request = new Request();
                request.setData(value);

                RXUtils.request(getActivity(), request, "applyBackOrder", new SupportSubscriber<Response>() {

                    @Override
                    public void onStop() {
                        super.onStop();
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        btnPost.setClickable(true);
                    }



                    @Override
                    public void onNext(Response response) {

                        if (!StringUtils.isEmpty(status)) {
                            //发送列表数据更新消息
                            UpdateOrderListMessage message = new UpdateOrderListMessage();
                            message.setStatus(status);
                            EventBus.getDefault().post(message);
                        }


                        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
                        Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
                            @Override
                            public void onPositiveActionClicked(DialogFragment fragment) {
                                super.onPositiveActionClicked(fragment);
                                fragment.dismiss();

                            }

                            @Override
                            public void onNegativeActionClicked(DialogFragment fragment) {
                                super.onNegativeActionClicked(fragment);
                            }

                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                super.onDismiss(dialog);

                                getActivity().finish();
                            }
                        };

                        ((SimpleDialog.Builder) builder).message("您的退单申请已经成功提交了，请耐心等待吧~")
                                .title("申请退单")
                                .positiveAction("知道了");
                        DialogFragment fragment = DialogFragment.newInstance(builder);
                        fragment.show(getFragmentManager(), null);

                    }
                });
            }
        });

    }
}
