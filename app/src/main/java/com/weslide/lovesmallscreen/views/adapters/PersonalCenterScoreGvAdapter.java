package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.utils.Utils;
import com.weslide.lovesmallscreen.views.widget.SquareImageView;

import java.util.List;

/**
 * Created by YY on 2018/1/13.
 */
public class PersonalCenterScoreGvAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    public PersonalCenterScoreGvAdapter(Context context, List<Goods> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    private List<Goods> list;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyHolder holder;
        if (view == null) {
            holder = new MyHolder();
            view = inflater.inflate(R.layout.score_mall_goods_view, viewGroup, false);
            holder.ivGoodsImage = (SquareImageView) view.findViewById(R.id.iv_goods_image);
            holder.expressTactics = (TextView) view.findViewById(R.id.expressTactics);
            holder.tvGoodsName = (TextView) view.findViewById(R.id.tv_goods_name);
            holder.tvGoodsPrice = (TextView) view.findViewById(R.id.tv_goods_price);
            holder.tvGoodsCostPrice = (TextView) view.findViewById(R.id.tv_goods_costPrice);
            holder.tvSalesVolume = (TextView) view.findViewById(R.id.tv_sales_volume);
            holder.soldOutTv = (TextView) view.findViewById(R.id.sold_out_tv);
            holder.tvLocaiont = (TextView) view.findViewById(R.id.tv_locaiont);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();

        bindView(holder,list.get(i));
        return view;
    }

    private void bindView(MyHolder holder,Goods goods) {
        Glide.with(context).load(goods.getCoverPic()).into(holder.ivGoodsImage);
        holder.tvGoodsName.setText(goods.getName());
        holder.tvGoodsPrice.setText("积分 " + goods.getScore());
        holder.tvSalesVolume.setText("已换" + goods.getSalesVolume() + "件");
        holder.tvGoodsCostPrice.setText("价值：￥" + goods.getCostPrice());
        holder.expressTactics.setText(goods.getExpressTactics());
        Utils.strikethrough(holder.tvGoodsCostPrice);
        if (goods.getStockNumber() > 0) {
            holder.soldOutTv.setVisibility(View.GONE);
        }else holder.soldOutTv.setVisibility(View.VISIBLE);
    }

    class MyHolder {
        SquareImageView ivGoodsImage;
        TextView expressTactics, tvGoodsName, tvGoodsPrice, tvGoodsCostPrice, tvSalesVolume, tvLocaiont,soldOutTv;
    }
}
