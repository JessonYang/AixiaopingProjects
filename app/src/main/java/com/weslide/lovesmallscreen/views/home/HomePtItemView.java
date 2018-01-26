package com.weslide.lovesmallscreen.views.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.bean.PtGoodModel;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.view_yy.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YY on 2017/12/20.
 * <p>
 * 首页拼团商品
 */
public class HomePtItemView extends FrameLayout {
    @BindView(R.id.pt_good_iv)
    ImageView pt_good_iv;
    @BindView(R.id.pt_good_tv)
    TextView pt_good_tv;
    @BindView(R.id.pt_good_price)
    TextView pt_good_price;

    public HomePtItemView(Context context) {
        super(context);
        initView();
    }

    public HomePtItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HomePtItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.home_pt_good_item, this, true);
        ButterKnife.bind(this);
    }

    /**
     * 绑定数据
     *
     * @param goods
     */
    public void bindView(PtGoodModel goods) {
        if (!StringUtil.isBlank(goods.getImage())) {
            Glide.with(getContext()).load(goods.getImage()).into(pt_good_iv);
        }
        if (!StringUtil.isBlank(goods.getPrice())) {
            pt_good_price.setText("￥" + goods.getPrice());
        }
        if (!StringUtil.isBlank(goods.getName())) {
            pt_good_tv.setText(goods.getName());
        }
        setOnClickListener(view -> AppUtils.toGoods(getContext(), goods.getGoodsId()));
    }
}
