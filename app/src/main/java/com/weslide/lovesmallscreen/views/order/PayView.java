package com.weslide.lovesmallscreen.views.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
    @BindView(R.id.rb_wallet2)
    CheckBox rb_wallet2;
    @BindView(R.id.rb_bank2)
    CheckBox rb_bank2;
    @BindView(R.id.rb_alipay2)
    CheckBox rb_alipay2;
    @BindView(R.id.rb_weixin2)
    CheckBox rb_weixin2;
    @BindView(R.id.tv_wallet)
    TextView tvWallet;
    @BindView(R.id.tv_wallet2)
    TextView tvWallet2;
    @BindView(R.id.single_way)
    LinearLayout single_way;
    @BindView(R.id.mullti_way)
    LinearLayout mullti_way;
    public static int SINGLE = 0;
    public static int MULLTI = 1;
    public int type;
    public static boolean isWalletCheck = false;
    public static boolean isWeixinCheck = true;
    public static boolean isAliyunCheck = false;
    public static boolean isBankCheck = false;

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

        tvWallet.setText(ContextParameter.getUserInfo().getAvailableMoney());
        tvWallet2.setText(ContextParameter.getUserInfo().getAvailableMoney());

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
                    case R.id.rb_bank:
                        onPayChangeListener.payChange(Constants.PAY_BANK);
                        break;
                }
            }
        });

        rb_wallet2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isWalletCheck = b;
            }
        });

        rb_weixin2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isWeixinCheck = b;
                if (b) {
                    if (type == 1) {
                        rb_alipay2.setChecked(false);
                        rb_bank2.setChecked(false);
                    }
                }
            }
        });

        rb_alipay2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAliyunCheck = b;
                if (b) {
                    if (type == 1) {
                        rb_weixin2.setChecked(false);
                        rb_bank2.setChecked(false);
                    }
                }
            }
        });
        rb_bank2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isBankCheck = b;
                if (b) {
                    if (type == 1) {
                        rb_weixin2.setChecked(false);
                        rb_alipay2.setChecked(false);
                    }
                }
            }
        });
    }

    public OnPayChangeListener getOnPayChangeListener() {
        return onPayChangeListener;
    }

    public void setOnPayChangeListener(OnPayChangeListener onPayChangeListener) {
        this.onPayChangeListener = onPayChangeListener;

        //默认微信(单选形式)
        rbWeixin.setChecked(true);

        //默认微信(复选形式)
        rb_weixin2.setChecked(true);
    }

    public void changeWay(int type) {
        this.type = type;
        if (type == 0) {
            mullti_way.setVisibility(GONE);
            single_way.setVisibility(VISIBLE);
        } else if (type == 1) {
            mullti_way.setVisibility(VISIBLE);
            single_way.setVisibility(GONE);
        }
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
