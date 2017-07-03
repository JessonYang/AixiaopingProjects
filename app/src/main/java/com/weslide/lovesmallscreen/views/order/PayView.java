package com.weslide.lovesmallscreen.views.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.views.widget.AXPRadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/29.
 * <p>
 * 支付视图
 */
public class PayView extends FrameLayout {
    @BindView(R.id.rg_pay)
    AXPRadioGroup rgPay;
    @BindView(R.id.rb_weixin)
    RadioButton rbWeixin;
    @BindView(R.id.tv_wallet)
    TextView tvWallet;

    private OnPayChangeListener onPayChangeListener;

    public PayView(Context context) {
        super(context);
        initView();
    }

    public PayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_pay, this, true);
        ButterKnife.bind(this);
        bindView();

    }

    private void bindView() {

        tvWallet.setText(ContextParameter.getUserInfo().getAvailableMoney() );

        rgPay.setOnCheckedChangeListener(new AXPRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AXPRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_alipay:
                        onPayChangeListener.payChange(Constants.PAY_ALIPAY);
                        break;
                    case R.id.rb_wallet:
                        onPayChangeListener.payChange(Constants.PAY_WALLET);
                        break;
                    case R.id.rb_weixin:
                        onPayChangeListener.payChange(Constants.PAY_WEIXIN);
                        break;
                }
            }
        });
    }

    public OnPayChangeListener getOnPayChangeListener() {
        return onPayChangeListener;
    }

    public void setOnPayChangeListener(OnPayChangeListener onPayChangeListener) {
        this.onPayChangeListener = onPayChangeListener;

        //默认微信
        rbWeixin.setChecked(true);
    }


    /**
     * 支付方式改变事件
     */
    public interface OnPayChangeListener {
        /**
         * Constants中有定义
         *
         * @param pay
         */
        void payChange(String pay);
    }

}
