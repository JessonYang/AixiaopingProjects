package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.TopLocalModel;
import com.weslide.lovesmallscreen.utils.AppUtils;

import java.util.List;

/**
 * Created by YY on 2017/6/15.
 */
public class CountyGoodRcLvAdapter extends RecyclerView.Adapter {

    private List<TopLocalModel> list;
    private LayoutInflater inflater;
    private Context context;

    public CountyGoodRcLvAdapter(List<TopLocalModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.new_home_page_county_product_rclv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.product_good_title_tv.setText(list.get(position).getName());
        myHolder.product_good_price_tv.setText("￥" + list.get(position).getPrice());
        Glide.with(context).load(list.get(position).getImage()).into(myHolder.product_good_iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView product_good_title_tv;
        private TextView product_good_price_tv;
        private ImageView product_good_iv;

        public MyHolder(View itemView) {
            super(itemView);
            product_good_title_tv = (TextView) itemView.findViewById(R.id.product_good_title_tv);
            product_good_price_tv = (TextView) itemView.findViewById(R.id.product_good_price_tv);
            product_good_iv = (ImageView) itemView.findViewById(R.id.product_good_iv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.toGoods(context, list.get(getLayoutPosition()).getGoodsId());
                }
            });
        }
    }
}
