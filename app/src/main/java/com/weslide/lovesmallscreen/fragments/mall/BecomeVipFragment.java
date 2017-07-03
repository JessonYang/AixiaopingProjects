package com.weslide.lovesmallscreen.fragments.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.LoadingSubscriber;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.managers.pay.Pay;
import com.weslide.lovesmallscreen.managers.pay.PayListener;
import com.weslide.lovesmallscreen.managers.pay.PayMessage;
import com.weslide.lovesmallscreen.managers.pay.PayModel;
import com.weslide.lovesmallscreen.models.bean.BecomeVipBean;
import com.weslide.lovesmallscreen.models.config.ClientConfig;
import com.weslide.lovesmallscreen.models.config.VipLevel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ReflectionUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.becomevip.BecomeVipLevelView;
import com.weslide.lovesmallscreen.views.widget.AXPRadioGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/14.
 * 成为会员
 */
public class BecomeVipFragment extends BaseFragment implements PayListener {

    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.layout_select_item)
    AXPRadioGroup layoutSelectItem;
    @BindView(R.id.tv_invite_code)
    EditText tvInviteCode;
    @BindView(R.id.rg_pay)
    AXPRadioGroup rgPay;

    String payModel;
    int vipLevelId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        mView = inflater.inflate(R.layout.fragment_become_vip, container, false);
        ButterKnife.bind(this, mView);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        loadData();

        return mView;
    }

    /**
     * 加载网络数据
     */
    private void loadData(){
        RXUtils.request(getActivity(), new Request(), "getClientConfig", new SupportSubscriber<Response<ClientConfig>>(){

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getActivity(), "服务器开小差了，请稍后再试~");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                T.showShort(getActivity(), "服务器开小差了，请稍后再试~");
            }

            @Override
            public void onNoNetwork() {
                T.showShort(getActivity(), "网络还未链接~");
            }

            @Override
            public void onNext(Response<ClientConfig> clientConfigResponse) {
                ContextParameter.setClientConfig(clientConfigResponse.getData());
                loadView();
            }
        });
    }

    private void loadView(){

        for (VipLevel level: ContextParameter.getClientConfig().getVipLevels()) {
            BecomeVipLevelView view = new BecomeVipLevelView(getActivity());
            view.bindView(level);

            layoutSelectItem.addView(view);
        }

        layoutSelectItem.setOnCheckedChangeListener(new AXPRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AXPRadioGroup group, int checkedId) {
                vipLevelId = checkedId;
            }
        });

        rgPay.setOnCheckedChangeListener(new AXPRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AXPRadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_alipay:
                        payModel = Constants.PAY_ALIPAY;
                        break;
                    case R.id.rb_weixin:
                        payModel = Constants.PAY_WEIXIN;
                        break;
                }
            }
        });
    }

    @Subscribe
    public void onEvent(PayMessage payMessage) {

        switch (payMessage.getResult()) {
            case 0:
                onSuccess();
                break;
            case -1:
                onDefeated();
                break;
            case -2:
                onCancel();
                break;
        }

    }

    private boolean valide(){
//        String inviteCode = tvInviteCode.getText().toString();
        if(vipLevelId == 0 || StringUtils.isEmpty(payModel)){
            return false;
        }

        return  true;
    }


    @OnClick(R.id.btn_pay)
    public void onClick() {

        if(!valide()){
            T.showShort(getActivity(), "请完善填写信息！");
            return;
        }

        String inviteCode = tvInviteCode.getText().toString();

        Request request = new Request();
        BecomeVipBean becomeVipBean = new BecomeVipBean();
        becomeVipBean.setInviteCode(inviteCode);
        becomeVipBean.setPayType(payModel);
        becomeVipBean.setVipLevelId(vipLevelId + "");
        request.setData(becomeVipBean);

        RXUtils.request(getActivity(), request, "becomeVip", new LoadingSubscriber<Response<PayModel>>(getActivity()) {

            @Override
            public void onResponseError(Response response) {
                T.showShort(getActivity(), response.getMessage());
            }

            @Override
            public void onNext(Response<PayModel> becomeVipBeanResponse) {
                if(Constants.PAY_ALIPAY.equals(payModel)){

                    Pay.payToAlipay(getActivity(), becomeVipBeanResponse.getData().getSign(), BecomeVipFragment.this);

                } else if(Constants.PAY_WEIXIN.equals(payModel)){
                    Pay.payToWeiXin(getActivity(), becomeVipBeanResponse.getData(), BecomeVipFragment.this);

                }
            }
        });



    }

    @Override
    public void onSuccess() {
        getActivity().finish();
        T.showShort(getActivity(), "恭喜您成功为会员~");
    }

    @Override
    public void onDefeated() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            //内存泄露风险
            L.e("正加载处理订单内存泄露");
            Class tenCla = Class.forName("com.tencent.a.a.a.a.g");
            Field iField = ReflectionUtils.getDeclaredField(tenCla, "i");
            iField.setAccessible(true);
            iField.set(null,null);

            L.e("提交订单内存泄露已处理");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
