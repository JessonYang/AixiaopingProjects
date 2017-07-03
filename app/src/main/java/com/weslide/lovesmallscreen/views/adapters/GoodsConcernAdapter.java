package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.fragments.user.GoodsConcernFragment;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.views.widget.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/24.
 * 商品关注适配器
 */
public class GoodsConcernAdapter extends SuperRecyclerViewAdapter<Goods,GoodsConcernAdapter.GoodsConcernViewHolder>{

   int state = 0;
    public GoodsConcernAdapter(Context context, DataList<Goods> dataList, GoodsConcernFragment fragment) {
        super(context, dataList);
    }
    public void setState(int state){
        this.state = state;
    }

    @Override
    public GoodsConcernViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        GoodsConcernViewHolder viewHolder = new GoodsConcernViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goods_concern,parent,false));
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(GoodsConcernViewHolder holder, int position) {
        Goods goods = mList.get(position);
        holder.llGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toGoods(mContext, goods.getGoodsId());
            }
        });
        holder.rlGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toGoods(mContext, goods.getGoodsId());
            }
        });
        holder.checkView.setChecked(false);
        if(state == 0){

            holder.checkView.setVisibility(View.GONE);
        }else{

            holder.checkView.setVisibility(View.VISIBLE);
        }
        holder.checkView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    goods.setSelect(true);
                    goods.setConcern(false);
                }else{
                    goods.setSelect(false);
                    goods.setConcern(true);
                }
            }
        });


        holder.price.setText(goods.getPrice());
        holder.name.setText(goods.getName());
        holder.rb.setRating(Float.parseFloat(goods.getCommentList().getGoodCommentPraise())/20);
        Glide.with(mContext).load(goods.getCoverPic()).into(holder.imag);
        if(StringUtils.isEmpty(goods.getScore())==false) {
            if (Float.parseFloat(goods.getScore()) > 0) {
                holder.score.setText("或" + goods.getScore() + "积分兑换");
            }
            if(StringUtils.isEmpty(goods.getSalesVolume())==true){
                holder.num.setText("已售" + 0);
            }else {
                holder.num.setText("已售" + goods.getSalesVolume());
            }
        }
    }

    class GoodsConcernViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cb_check)
        CheckBox checkView;
        @BindView(R.id.iv_goods_image)
        SquareImageView imag;
        @BindView(R.id.tv_goods_name)
        TextView name;
        @BindView(R.id.tv_price)
        TextView price;
        @BindView(R.id.tv_score_hint)
        TextView score;
        @BindView(R.id.tv_socre)
        RatingBar rb;
        @BindView(R.id.tv_num)
        TextView num;
        @BindView(R.id.ll_goods)
        LinearLayout llGoods;
        @BindView(R.id.rl_goods)
        RelativeLayout rlGoods;

        public GoodsConcernViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
