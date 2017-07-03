package com.weslide.lovesmallscreen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.models.OrderMsgDtOb;

import net.aixiaoping.library.R;

import java.util.List;

/**
 * Created by YY on 2017/5/8.
 */
public class SystemMsgLvAdapter extends BaseAdapter {

    private List<OrderMsgDtOb> list;
    private Context context;
    private LayoutInflater inflater;

    public SystemMsgLvAdapter(Context context, List<OrderMsgDtOb> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

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
            view = inflater.inflate(R.layout.system_msg_lv_item,viewGroup,false);
            holder = new MyHolder();
            holder.system_icon = (ImageView) view.findViewById(R.id.system_icon);
            holder.system_msg_desc = (TextView) view.findViewById(R.id.system_msg_desc);
            holder.system_msg_time = (TextView) view.findViewById(R.id.system_msg_time);
            holder.system_msg_unread_count = (TextView) view.findViewById(R.id.system_msg_unread_count);
            holder.msg_type_tv = (TextView) view.findViewById(R.id.msg_type_tv);
            view.setTag(holder);
        }else holder = (MyHolder) view.getTag();

        OrderMsgDtOb orderMsgDtOb = list.get(i);
        Glide.with(context).load(orderMsgDtOb.getIcon()).placeholder(R.drawable.icon_morentouxiang).into(holder.system_icon);
        holder.msg_type_tv.setText(orderMsgDtOb.getTitle());
        holder.system_msg_desc.setText(orderMsgDtOb.getContent());
        holder.system_msg_time.setText(orderMsgDtOb.getTime());
        holder.system_msg_unread_count.setVisibility(View.GONE);
        int isread = orderMsgDtOb.getIsread();
        if (isread == 0 ){
            holder.system_icon.setAlpha(1f);
            holder.msg_type_tv.setTextColor(Color.parseColor("#333333"));
            holder.system_msg_desc.setTextColor(Color.parseColor("#999999"));
            holder.system_msg_time.setTextColor(Color.parseColor("#999999"));
        }else if (isread == 1){
            holder.system_icon.setAlpha(0.5f);
            holder.msg_type_tv.setTextColor(Color.parseColor("#cccccc"));
            holder.system_msg_desc.setTextColor(Color.parseColor("#cccccc"));
            holder.system_msg_time.setTextColor(Color.parseColor("#cccccc"));
        }
        return view;
    }

    class MyHolder{
        ImageView system_icon;
        TextView msg_type_tv,system_msg_desc,system_msg_time,system_msg_unread_count;
    }
}
