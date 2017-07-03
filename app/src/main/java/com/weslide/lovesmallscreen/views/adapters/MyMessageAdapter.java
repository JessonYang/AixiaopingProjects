package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.models.Message;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/6/30.
 */
public class MyMessageAdapter extends SuperRecyclerViewAdapter<Message,MyMessageAdapter.MyMessageViewHolder> {


    public MyMessageAdapter(Context context, DataList<Message> dataList) {
        super(context, dataList);
    }

    @Override
    public MyMessageViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        MyMessageViewHolder viewHoler = new MyMessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_message,parent,false));
        return viewHoler;
    }

    @Override
    public void onSuperBindViewHolder(MyMessageViewHolder holder, int position) {
        Message message = mList.get(position);
        if(!StringUtils.isBlank(message.getUri())){
            holder.llMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    URIResolve.resolve(mContext, message.getUri());
                }
            });
        }
        Glide.with(mContext).load(message.getImage()).into(holder.messageImg);
        holder.messageTitle.setText(message.getTitle());
        holder.messageDetails.setText(message.getDetail());
        holder.messageDate.setText(message.getDate());
    }

    class MyMessageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_message_img)
        ImageView messageImg;
        @BindView(R.id.tv_message_title)
        TextView messageTitle;
        @BindView(R.id.tv_message_details)
        TextView messageDetails;
        @BindView(R.id.tv_message_data)
        TextView messageDate;
        @BindView(R.id.ll_message)
        LinearLayout llMessage;

        public MyMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
