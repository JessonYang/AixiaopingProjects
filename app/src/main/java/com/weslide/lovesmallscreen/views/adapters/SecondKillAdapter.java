package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.SecondKillActivity;
import com.weslide.lovesmallscreen.fragments.SecondKillFragment;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.utils.Utils;
import com.weslide.lovesmallscreen.views.widget.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/22.
 * 秒杀列表适配器
 */

public class SecondKillAdapter extends SuperRecyclerViewAdapter<Goods,SecondKillAdapter.SecondKillViewHolder> {

    SecondKillFragment fragment;
    public SecondKillAdapter(Context context, DataList<Goods> dataList,SecondKillFragment fragment) {
        super(context, dataList);
        this.fragment = fragment;
    }

    @Override
    public SecondKillViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        SecondKillViewHolder viewHolder = new SecondKillViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_secondkill,parent,false));

        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(SecondKillViewHolder holder, int position) {
        Goods goods = mList.get(position);
        holder.goodsName.setText(goods.getName());
        holder.price.setText("¥"+goods.getPrice());
        holder.castPrice.setText("¥"+goods.getCostPrice());
        Utils.strikethrough(holder.castPrice);
        holder.tvSale.setText(goods.getDiscount());
        Glide.with(mContext).load(goods.getCoverPic()).into(holder.ivGoodsImage);
        holder.ivGoodsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toGoods(mContext, goods.getGoodsId(),fragment.startSecondKill);
            }
        });
        if((int)(Float.parseFloat(goods.getSalesRatio()))>=100){
            holder.num.setText("已售完");
            holder.line.setVisibility(View.GONE);
        }else {
            holder.num.setText("已售" + goods.getSalesRatio() + "%");
            holder.line.setVisibility(View.VISIBLE);
        }
        if(goods.getExpressTactics().equals("包邮")){
           holder.tvExpress.setTextColor(Color.parseColor("#FF0000"));
        }
        holder.tvExpress.setText("("+goods.getExpressTactics()+")");

    holder.come.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(fragment.startSecondKill==false){
                T.showShort(mContext,"敬请期待");
            }else {
                if((int)(Float.parseFloat(goods.getSalesRatio()))>=100){
                    T.showShort(mContext,"已售完");
                }else {
                    AppUtils.toGoods(mContext, goods.getGoodsId(),fragment.startSecondKill);
                }

            }
        }
    });
        if(fragment.startSecondKill==false){
            holder.come.setText("未开始");
            holder.come.setBackgroundResource(R.drawable.button_background_gray);
        }else if(fragment.startSecondKill==true){
            if((int)(Float.parseFloat(goods.getSalesRatio()))>=100){
                holder.come.setText("已售完");
                holder.come.setBackgroundResource(R.drawable.button_background_gray);
            }else {
                holder.come.setBackgroundResource(R.drawable.button_background);
                holder.come.setText("立即抢购");
            }

        }
        int width = holder.line.getMeasuredWidth();
        float num = width*Float.parseFloat(goods.getSalesRatio());
        holder.numLine.setWidth((int)(num/100));
    }

    class SecondKillViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_goods_name)
        TextView goodsName;
        @BindView(R.id.tv_sale)
        TextView tvSale;
        @BindView(R.id.tv_express)
        TextView tvExpress;
        @BindView(R.id.tv_price)
        TextView price;
        @BindView(R.id.tv_cast_price)
        TextView castPrice;
        @BindView(R.id.tv_num)
        TextView num;
        @BindView(R.id.tv_num_line)
        TextView numLine;
        @BindView(R.id.ll_line)
        LinearLayout line;
        @BindView(R.id.btn_come)
        TextView come;
        @BindView(R.id.iv_goods_image)
        SquareImageView ivGoodsImage;

        public SecondKillViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
