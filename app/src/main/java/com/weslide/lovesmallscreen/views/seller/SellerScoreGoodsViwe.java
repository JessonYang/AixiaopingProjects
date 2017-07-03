package com.weslide.lovesmallscreen.views.seller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.views.adapters.SellerScoreGoodsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/12.
 * 商家积分商品
 */
public class SellerScoreGoodsViwe extends FrameLayout {
    @BindView(R.id.list)
    SuperRecyclerView list;
    SellerScoreGoodsAdapter mAdpager;

    public SellerScoreGoodsViwe(Context context) {
        super(context);

        initView();
    }

    public SellerScoreGoodsViwe(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public SellerScoreGoodsViwe(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    public void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.seller_view_score_goods, this, true);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        list.setLayoutManager(layoutManager);
    }

    public void bindView(List<Goods> goodsList){
        mAdpager = new SellerScoreGoodsAdapter(getContext(), goodsList);
        list.setAdapter(mAdpager);
    }

}
