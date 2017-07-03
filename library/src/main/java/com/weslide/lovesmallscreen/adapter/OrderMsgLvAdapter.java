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
 * Created by YY on 2017/5/5.
 */
public class OrderMsgLvAdapter extends BaseAdapter {

    private List<OrderMsgDtOb> list;
    private Context context;
    private LayoutInflater inflater;

    public OrderMsgLvAdapter(Context context, List<OrderMsgDtOb> list) {
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
        if (view == null){
            view = inflater.inflate(R.layout.order_msg_lv_item,viewGroup,false);
            holder = new MyHolder();
            holder.order_msg_time = (TextView) view.findViewById(R.id.order_msg_time);
            holder.order_status = (TextView) view.findViewById(R.id.order_status);
            holder.order_express_name = (TextView) view.findViewById(R.id.order_express_name);
            holder.order_content = (TextView) view.findViewById(R.id.order_content);
            holder.order_code = (TextView) view.findViewById(R.id.order_code);
            holder.order_icon = (ImageView) view.findViewById(R.id.order_icon);
            view.setTag(holder);
        }else holder = (MyHolder) view.getTag();

        OrderMsgDtOb orderMsgDtOb = list.get(i);
        String statusId = orderMsgDtOb.getOrderStatusId();
        if (statusId.equals("60")) {
            holder.order_status.setTextColor(Color.parseColor("#ff0000"));
        } else if (statusId.equals("30")) {
            holder.order_status.setTextColor(Color.parseColor("#398fe2"));
        } else if (statusId.equals("20")) {
            holder.order_status.setTextColor(Color.parseColor("#ff6600"));
        } else if (statusId.equals("50")) {
            holder.order_status.setTextColor(Color.parseColor("#45d95f"));
        }
        holder.order_code.setText(orderMsgDtOb.getOrderNumber());
        holder.order_msg_time.setText(orderMsgDtOb.getTime());
        holder.order_content.setText(orderMsgDtOb.getOrderDesc());
        holder.order_express_name.setText(orderMsgDtOb.getOrderFreight());
        holder.order_status.setText(orderMsgDtOb.getOrderStatus());
        Glide.with(context).load(orderMsgDtOb.getOrderIcon()).into(holder.order_icon);

        return view;
    }

    class MyHolder {
        TextView order_msg_time,order_status,order_express_name,order_content,order_code;
        ImageView order_icon;
    }
}
