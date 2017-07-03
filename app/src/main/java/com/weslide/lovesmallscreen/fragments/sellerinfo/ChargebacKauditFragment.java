package com.weslide.lovesmallscreen.fragments.sellerinfo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.ImageShowActivity;
import com.weslide.lovesmallscreen.core.LoadingSubscriber;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.BackOrder;
import com.weslide.lovesmallscreen.models.BackOrderVerify;
import com.weslide.lovesmallscreen.models.Order;
import com.weslide.lovesmallscreen.models.OrderItem;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateSellerBackOrderMessage;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.order.OrderAddress;
import com.weslide.lovesmallscreen.views.order.OrderView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/7/16.
 * 退单审核
 */
public class ChargebacKauditFragment extends BaseFragment {

    public static final String KEY_BACK_ORDER_ITEM_ID = "KEY_BACK_ORDER_ITEM_ID";
    @BindView(R.id.oa_address)
    OrderAddress oaAddress;
    private String orderId;

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.ll_order_item)
    OrderView llOrderItem;
    @BindView(R.id.tv_argument)
    TextView tvArgument;
    @BindView(R.id.gv_img)
    GridView gvImg;
    @BindView(R.id.edt_logistics_company)
    EditText edtLogisticsCompany;
    @BindView(R.id.edt_money)
    EditText edtMoney;

    BackOrder backOrder;
    MyAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_chargebac_kaudit, container, false);

        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(view -> getActivity().finish());

        loadBundle();
        getOrder();
        return mView;
    }

    private void loadBundle() {
        Bundle bundle = getActivity().getIntent().getExtras();

        if (bundle != null) {
            orderId = bundle.getString(KEY_BACK_ORDER_ITEM_ID);
        }
    }


    /**
     * 获得商品
     */
    private void getOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("backOrderItemId", orderId);

        RXUtils.request(getActivity(), new Request<>().setData(map), "getBackOrder", new SupportSubscriber<Response<BackOrder>>() {

            @Override
            public void onNoNetwork() {
                super.onNoNetwork();
                T.showShort(getActivity(), "请连接网络");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
            }

            @Override
            public void onNext(Response<BackOrder> orderResponse) {
                backOrder = orderResponse.getData();
                bindView();

            }
        });
    }

    private void bindView() {

        tvOrderCode.setText(backOrder.getOrderNumber());
        tvOrderType.setText(backOrder.getBackOrderStatus().getName());
        tvArgument.setText(backOrder.getBackOrderInfo().getContent());

        backOrder.getOrderNumber();
        backOrder.getRealityMoney();

        oaAddress.bindView(backOrder, false);

        ArrayList<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(backOrder.getBackOrderItem());
        backOrder.setOrderItems(orderItems);
        llOrderItem.bindView(backOrder);

        if(backOrder.getBackOrderInfo().getImages() != null && backOrder.getBackOrderInfo().getImages().size() != 0){
            adapter = new MyAdapter();
            gvImg.setAdapter(adapter);
            gvImg.setOnItemClickListener((adapterView, view, i, l) -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImageShowActivity.KEY_IMAGE_LIST_URI, backOrder.getBackOrderInfo().getImages());
                AppUtils.toActivity(getActivity(), ImageShowActivity.class, bundle);
            });
        }

    }

    @OnClick({R.id.btn_refuse, R.id.btn_stop_order, R.id.btn_accept})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_refuse:
                sellerBackOrderVerify("10");
                break;
            case R.id.btn_stop_order:
                sellerBackOrderVerify("40");
                break;
            case R.id.btn_accept:
                sellerBackOrderVerify("30");
                break;
        }
    }

    public void sellerBackOrderVerify(String result) {

        String company = edtLogisticsCompany.getText().toString();
        String money = edtMoney.getText().toString();

        String message = null;
        String title = null;

        switch (result) {
            case "10":
                if (StringUtils.isEmpty(company)) {
                    T.showShort(getActivity(), "请填写拒绝退单理由");
                    return;
                }
                message = "您确定拒绝用户的退单吗？";
                title = "拒绝退单";
                break;
            case "40":
                if (StringUtils.isEmpty(company)) {
                    T.showShort(getActivity(), "请填写不可退单理由");
                    return;
                }
                message = "您确定拒绝用户的退单吗？用户将不可再次提交退单申请。";
                title = "不可退单";
                break;
            case "30":
               /* if (StringUtils.isNumberEmpty(money)) {
                    T.showShort(getActivity(), "请填写退款金额");
                    return;
                }*/
                message = "您确定通过买家的退单申请吗？退款金额务必核实。";
                title = "接受退单";
                break;
        }

        BackOrderVerify verify = new BackOrderVerify();
        verify.setResult(result);
        verify.setMoney(money);
        verify.setReplyContent(company);
        verify.setBackOrderItemId(orderId);

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                super.onPositiveActionClicked(fragment);

                RXUtils.request(getActivity(), new Request().setData(verify), "sellerBackOrderVerify", new LoadingSubscriber<Response>(getActivity()) {

                    @Override
                    public void onNext(Response response) {
                        getActivity().finish();

                        EventBus.getDefault().post(new UpdateSellerBackOrderMessage());
                    }
                });
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder) builder).message(message)
                .title(title)
                .positiveAction("确定").negativeAction("再等等");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return backOrder.getBackOrderInfo().getImages().size();
        }

        @Override
        public Object getItem(int position) {
            return backOrder.getBackOrderInfo().getImages().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    com.weslide.lovesmallscreen.R.layout.item_comment_image, parent, false);
            ImageView iv = (ImageView) convertView.findViewById(com.weslide.lovesmallscreen.R.id.iv_photo);
            Glide.with(getContext()).load(backOrder.getBackOrderInfo().getImages().get(position)).into(iv);
            return convertView;
        }
    }
}
