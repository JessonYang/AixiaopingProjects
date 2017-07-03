package com.weslide.lovesmallscreen.views.preferential99;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.FreeGoodsActivity;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.Utils;
import com.weslide.lovesmallscreen.views.adapters.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/19.
 * 99特惠免单专区
 */
public class Preferential99FreeGoodsView extends FrameLayout {
    @BindView(R.id.list)
    SuperRecyclerView list;
    FreeGoodsAdapter mAdapter;

    public Preferential99FreeGoodsView(Context context) {
        super(context);
        initView();
    }

    public Preferential99FreeGoodsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Preferential99FreeGoodsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.preferential99_view_free_goods, this, true);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        list.setLayoutManager(layoutManager);
    }

    public void bindView(List<Goods> goodsList) {
        mAdapter = new FreeGoodsAdapter(getContext(), goodsList);
        list.setAdapter(mAdapter);
    }

    @OnClick(R.id.tv_more)
    public void onClick() {
        AppUtils.toActivity(getContext(), FreeGoodsActivity.class);
    }

    class FreeGoodsAdapter extends SuperRecyclerViewAdapter<Goods, FreeGoodsViewHoload> {


        public FreeGoodsAdapter(Context context, List<Goods> list) {
            super(context, list);
        }

        @Override
        public FreeGoodsViewHoload onSuperCreateViewHolder(ViewGroup parent, int viewType) {
            return new FreeGoodsViewHoload(mContext, parent);
        }

        @Override
        public void onSuperBindViewHolder(FreeGoodsViewHoload holder, int position) {
            holder.bindView(mList.get(position));
        }
    }

    class FreeGoodsViewHoload extends BaseViewHolder<Goods> {

        @BindView(R.id.iv_goods_image)
        ImageView ivGoodsImage;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_cost_price)
        TextView tvCostPrice;

        public FreeGoodsViewHoload(Context context, ViewGroup parent) {
            super(context, parent, R.layout.preferential99_view_free_goods_item);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(Goods goods) {

            Utils.strikethrough(tvCostPrice);

            Glide.with(getContext()).load(goods.getCoverPic()).into(ivGoodsImage);
            tvGoodsName.setText(goods.getName());
            tvCostPrice.setText("价值：" + goods.getCostPriceString());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.toGoods(getContext(), goods.getGoodsId());
                }
            });

        }
    }
}
