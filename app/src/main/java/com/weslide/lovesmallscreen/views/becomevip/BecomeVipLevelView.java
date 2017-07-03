package com.weslide.lovesmallscreen.views.becomevip;

import android.content.Context;
import android.graphics.Color;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.config.VipLevel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/14.
 * 购买的会员等级详情
 */
public class BecomeVipLevelView extends FrameLayout {
    @BindView(R.id.tv_level_price)
    TextView tvLevelPrice;
    @BindView(R.id.tv_level_detail)
    TextView tvLevelDetail;
    @BindView(R.id.tb_select_level)
    RadioButton tbSelectLevel;

    public BecomeVipLevelView(Context context) {
        super(context);
        initView();
    }

    public BecomeVipLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BecomeVipLevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.become_vip_level_view, this, true);
        ButterKnife.bind(this);
    }

    public void bindView(VipLevel level) {
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);

        tvLevelPrice.setText("￥" + level.getMoney());
        tvLevelDetail.setText("可获得" + level.getFreeCount() + "张免单券");
        tbSelectLevel.setId(Integer.parseInt(level.getVipLevelId()));
    }
}
