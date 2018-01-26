package com.weslide.lovesmallscreen.views.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.Utils;
import com.weslide.lovesmallscreen.views.widget.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/27.
 * <p>
 * 首页附近商家推荐的积分商品
 */
public class SellerItemScoreGoodsView extends FrameLayout {
    @BindView(R.id.iv_goods_image)
    SquareImageView ivGoodsImage;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_goods_costPrice)
    TextView tvGoodsCostPrice;


    public SellerItemScoreGoodsView(Context context) {
        super(context);
        initView();
    }

    public SellerItemScoreGoodsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SellerItemScoreGoodsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.home_view_seller_score_goods_item, this, true);
        ButterKnife.bind(this);


    }

    /**
     * 绑定数据
     *
     * @param goods
     */
    public void bindView(Goods goods) {
        Utils.strikethrough(tvGoodsCostPrice);
        Glide.with(getContext()).load(goods.getCoverPic()).into(ivGoodsImage);
        tvPrice.setText("￥" + goods.getPrice());
        tvGoodsCostPrice.setText("原价：￥" + goods.getCostPrice());
        tvGoodsCostPrice.setVisibility(GONE);
        setOnClickListener(view -> AppUtils.toGoods(getContext(), goods.getGoodsId()));
    }
}
