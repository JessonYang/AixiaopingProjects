package com.weslide.lovesmallscreen.views.order;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.OrderItem;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.views.widget.AXPRadioGroup;
import com.weslide.lovesmallscreen.views.widget.TextUploadImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/15.
 * 申请退单界面
 */
public class ApplyBackOrderView extends FrameLayout {
    @BindView(R.id.tv_value_lable)
    TextView tvValueLable;
    @BindView(R.id.tv_back_value)
    TextView tvValue;
    @BindView(R.id.tv_alert)
    TextView tvAlert;
    @BindView(R.id.tuliv)
    TextUploadImageView tuliv;
    @BindView(R.id.rg_drawback_mode)
    AXPRadioGroup rgDrawbackMode;

    String drawbackMode = "0";
    @BindView(R.id.gtv_goods)
    GoodsItemView gtvGoods;

    public ApplyBackOrderView(Context context) {
        super(context);
        initView();
    }

    public ApplyBackOrderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ApplyBackOrderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * fragment 或 activity中的onActivityResult必须调用该方法才能生效
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        tuliv.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.order_back_view, this, true);
        ButterKnife.bind(this);

        rgDrawbackMode.setOnCheckedChangeListener(new AXPRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AXPRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_re:
                        drawbackMode = "0";
                        break;
                    case R.id.rb_wallet:
                        drawbackMode = Constants.PAY_WALLET;
                        break;
                }
            }
        });
    }

    public String getContent() {
        return tuliv.getContent();
    }

    public ArrayList<String> getSelectPath() {
        return tuliv.getSelectPath();
    }

    public String getDrawbackMode() {
        return drawbackMode;
    }

    public void bindView(OrderItem orderItem) {
        switch (orderItem.getType()) {
            case 1:
                tvAlert.setVisibility(View.GONE);
                tvValueLable.setText("退单积分：");
                tvValue.setText(orderItem.getScore() + "积分");
                break;
            case 2:
            case 4:
                tvAlert.setVisibility(View.VISIBLE);
                tvValueLable.setText( "退单金额：");
                tvValue.setText(orderItem.getMoney() + "元");
                break;
            case 3:
                L.e("ApplyBackOrderView", "免单商品不允许退单！");
//                this.setVisibility(View.GONE);
                break;
        }

        gtvGoods.bindView(orderItem);
    }
}
