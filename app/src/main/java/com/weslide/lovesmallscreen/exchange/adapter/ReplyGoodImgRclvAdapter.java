package com.weslide.lovesmallscreen.exchange.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.utils.AppUtils;

import java.util.List;

/**
 * Created by YY on 2018/1/22.
 */
public class ReplyGoodImgRclvAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ImageText> list;

    public ReplyGoodImgRclvAdapter(Context context, List<ImageText> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImgHolder(LayoutInflater.from(context).inflate(R.layout.replt_good_img_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(((ImgHolder) holder).imgIv);
        ((ImgHolder) holder).imgIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toExchangeGoods(context,list.get(position).getGoodsOrder());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ImgHolder extends RecyclerView.ViewHolder{
        private ImageView imgIv;

        public ImgHolder(View itemView) {
            super(itemView);
            imgIv = (ImageView) itemView.findViewById(R.id.reply_good_iv);
        }

    }
}
