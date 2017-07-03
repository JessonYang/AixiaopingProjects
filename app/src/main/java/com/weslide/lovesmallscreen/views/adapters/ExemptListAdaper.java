package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Exempt;
import com.weslide.lovesmallscreen.network.DataList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/7.
 */
public class ExemptListAdaper extends SuperRecyclerViewAdapter<Exempt,ExemptListAdaper.ExemptViewHolder> {

    public ExemptListAdaper(Context context, DataList<Exempt> dataList) {
        super(context, dataList);
    }

    @Override
    public ExemptViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        ExemptViewHolder viewHolder = new ExemptViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_free_exchange,parent,false));
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(ExemptViewHolder holder, int position) {
        Exempt exempt = mList.get(position);
        holder.name.setText(exempt.getName());
        holder.date.setText(exempt.getDate());
        if(exempt.getType().equals("1")){
            holder.bacound.setBackgroundResource(R.drawable.bg_huang);
            holder.image.setImageResource(R.drawable.icon_weishiyong);
            holder.hint.setText("【请注意有效期哦】");
        }else if(exempt.getType().equals("2")){
            holder.bacound.setBackgroundResource(R.drawable.bg_hui);
            holder.image.setImageResource(R.drawable.icon_yishiyong);
            holder.hint.setText("亲，已使用啦");
        }else if(exempt.getType().equals("3")){
            holder.bacound.setBackgroundResource(R.drawable.bg_hui);
            holder.image.setImageResource(R.drawable.icon_yishiyong);
            holder.hint.setText("亲，已过期啦");
        }
    }

    class ExemptViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_name)
        TextView name;
        @BindView(R.id.ll_exempt_bacound)
        LinearLayout bacound;
        @BindView(R.id.iv_free_image)
        ImageView image;
        @BindView(R.id.tv_date)
        TextView date;
        @BindView(R.id.tv_free_hint)
        TextView hint;

        public ExemptViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}