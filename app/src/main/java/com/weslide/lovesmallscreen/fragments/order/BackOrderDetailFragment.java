package com.weslide.lovesmallscreen.fragments.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.BackOrder;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.order.GoodsItemView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/25.
 * 退单详情
 */
public class BackOrderDetailFragment extends BaseFragment {

    /**
     * 退单id
     */
    public static final String KEY_BACK_ORDER_ITEM_ID = "KEY_BACK_ORDER_ITEM_ID";
    String backOrderId;

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.layout_goods_list)
    LinearLayout layoutGoodsList;
    @BindView(R.id.tv_apply_price)
    TextView tvApplyPrice;
    @BindView(R.id.order_num_tv)
    TextView order_num_tv;
    @BindView(R.id.tv_apply_content)
    TextView tvApplyContent;
    @BindView(R.id.tv_apply_date)
    TextView tvApplyDate;
    @BindView(R.id.tv_drawback_date)
    TextView tvDrawbackDate;
    @BindView(R.id.layout_drawback_date)
    TableRow layoutDrawbackDate;
    @BindView(R.id.tv_verify_status)
    TextView tvVerifyStatus;
    @BindView(R.id.tv_verify_date)
    TextView tvVerifyDate;
    @BindView(R.id.tv_verify_price)
    TextView tvVerifyPrice;
    @BindView(R.id.layout_verify_price)
    TableRow layoutVerifyPrice;
    @BindView(R.id.tv_verify_content)
    TextView tvVerifyContent;
    @BindView(R.id.layout_verify_content)
    TableLayout layoutVerifyContent;
    @BindView(R.id.tv_drawback_way)
    TextView tvDrawbackWay;
    @BindView(R.id.layout_drawback_way)
    LinearLayout layoutDrawbackWay;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_back_order_detail, container, false);

        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        loadBundle();
        loadData();

        return mView;
    }

    private void loadBundle() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            backOrderId = bundle.getString(KEY_BACK_ORDER_ITEM_ID);
        }
    }

    private void bindView(BackOrder backOrder) {
        tvSellerName.setText(backOrder.getSeller().getSellerName());
        tvOrderStatus.setText(backOrder.getBackOrderStatus().getName());

        tvApplyContent.setText(backOrder.getBackOrderInfo().getContent());
        tvApplyDate.setText(backOrder.getBackOrderInfo().getDate());
        tvApplyPrice.setText(backOrder.getBackOrderInfo().getValue());
        order_num_tv.setText(backOrder.getOrderNumber());

        GoodsItemView goodsItemView = new GoodsItemView(getActivity());
        goodsItemView.bindView(backOrder.getBackOrderItem());
        layoutGoodsList.addView(goodsItemView);

//        审核不通过  -15
//        商家未确认  0
//        待审核      10
//        已审核      20
//        已支付      30
//        退单完成    40

        switch (backOrder.getBackOrderStatus().getStatusId()) {
            case Constants.BACK_ORDER_STATUS_VERIFY:    //已审核
                tvVerifyStatus.setText("商家已同意了您的申请，退款申请达成");
                layoutVerifyContent.setVisibility(View.VISIBLE);
                tvApplyDate.setVisibility(View.VISIBLE);

                tvVerifyContent.setText(backOrder.getBackOrderVerify().getReplyContent());
                tvVerifyDate.setText(backOrder.getBackOrderVerify().getDate());
                tvVerifyPrice.setText(backOrder.getBackOrderVerify().getMoney());
                break;

            case Constants.BACK_ORDER_STATUS_NO_ACCEPT: //审核不通过
                tvVerifyStatus.setText("商家审核未通过");
                layoutVerifyContent.setVisibility(View.VISIBLE);
                tvApplyDate.setVisibility(View.VISIBLE);
                layoutVerifyPrice.setVisibility(View.GONE);

                tvVerifyContent.setText(backOrder.getBackOrderVerify().getReplyContent());
                tvVerifyDate.setText(backOrder.getBackOrderVerify().getDate());
                break;
            case Constants.BACK_ORDER_STATUS_WAIT_CONFIRM:  //商家未确认
            case Constants.BACK_ORDER_STATUS_WAIT_VERIFY:  //等待审核
                tvVerifyStatus.setText("等待商家审核");
                layoutVerifyContent.setVisibility(View.GONE);
                tvApplyDate.setVisibility(View.GONE);
                break;

            case Constants.BACK_ORDER_STATUS_PAY:       //已支付
            case Constants.BACK_ORDER_STATUS_ACCEPT:  //退单完成

                tvVerifyStatus.setText("商家已同意了您的申请，退款申请达成");
                layoutVerifyContent.setVisibility(View.VISIBLE);
                tvApplyDate.setVisibility(View.VISIBLE);

                tvVerifyContent.setText(backOrder.getBackOrderVerify().getReplyContent());
                tvVerifyDate.setText(backOrder.getBackOrderVerify().getDate());
                tvVerifyPrice.setText(backOrder.getBackOrderVerify().getMoney());

                layoutDrawbackWay.setVisibility(View.VISIBLE);

                StringBuffer sb = new StringBuffer();
                sb.append("钱款已经");

                switch (backOrder.getBackOrderInfo().getDrawbackWay()) {
                    case "0":
                        sb.append("原路返回到您的账户中");
                        break;
                    case Constants.PAY_ALIPAY:
                        sb.append("退回到您的支付宝里");
                        break;
                    case Constants.PAY_WEIXIN:
                        sb.append("退回到您的微信钱包里");
                        break;
                    case Constants.PAY_WALLET:
                        sb.append("退回到您的钱包里");
                        break;
                    case Constants.PAY_BANK:
                        sb.append("退回到您的银行卡里");
                        break;
                }
                tvDrawbackWay.setText(sb.toString());

                layoutDrawbackDate.setVisibility(View.VISIBLE);
                tvDrawbackDate.setText(backOrder.getDrawbackDate());

                break;
        }


    }

    private void loadData() {
        Map<String, String> values = new HashMap<>();
        values.put("backOrderItemId", backOrderId);
        RXUtils.request(getActivity(), new Request().setData(values), "getBackOrder", new SupportSubscriber<Response<BackOrder>>() {

            @Override
            public void onNoNetwork() {
                super.onNoNetwork();
                T.showShort(getActivity(), "请链接网络");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                T.showShort(getActivity(), "未知错误");
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getActivity(), "服务器加载失败");
            }

            @Override
            public void onNext(Response<BackOrder> backOrderResponse) {
                bindView(backOrderResponse.getData());
            }
        });
    }
}
