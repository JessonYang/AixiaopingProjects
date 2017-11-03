package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.OrderMsgDtOb;

import java.util.List;

/**
 * Created by YY on 2017/10/10.
 */
public class ActionMsgLvAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<OrderMsgDtOb> list;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getItemViewType(int position) {
        String picture = list.get(position).getPicture();
        int type = 0;
        if (picture == null || picture.length() == 0) {
            type = 0;
        }else {
            type = 1;
        }
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public ActionMsgLvAdapter(Context context, List<OrderMsgDtOb> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder1 holder1 = null;
        MyViewHolder2 holder2 = null;
        if (view == null) {
            switch (getItemViewType(i)) {
                case 0:
                    holder1 = new MyViewHolder1();
                    view = inflater.inflate(R.layout.action_msg_lv_item1,viewGroup,false);
                    holder1.action_msg_time = (TextView) view.findViewById(R.id.action_msg_time);
                    holder1.action_content = (TextView) view.findViewById(R.id.action_content);
                    holder1.action_icon = (ImageView) view.findViewById(R.id.action_icon);
                    view.setTag(holder1);
                    break;
                case 1:
                    holder2 = new MyViewHolder2();
                    view = inflater.inflate(R.layout.action_msg_lv_item2,viewGroup,false);
                    holder2.action_msg_time = (TextView) view.findViewById(R.id.action_msg_time);
                    holder2.action_msg_title = (TextView) view.findViewById(R.id.action_msg_title);
                    holder2.action_msg_desc = (TextView) view.findViewById(R.id.action_msg_desc);
                    holder2.action_msg_pic = (ImageView) view.findViewById(R.id.action_msg_pic);
                    view.setTag(holder2);
                    break;
            }
        }else {
            switch (getItemViewType(i)) {
                case 0:
                    holder1 = (MyViewHolder1) view.getTag();
                    break;
                case 1:
                    holder2 = (MyViewHolder2) view.getTag();
                    break;
            }
        }

        OrderMsgDtOb orderMsgDtOb = list.get(i);
        int isread = orderMsgDtOb.getIsread();
        switch (getItemViewType(i)) {
            case 0:
                holder1.action_msg_time.setText(orderMsgDtOb.getTime());
                holder1.action_content.setText(orderMsgDtOb.getTitle());
                Glide.with(context).load(orderMsgDtOb.getIcon()).into(holder1.action_icon);
                if (isread == 0 ){
                    holder1.action_icon.setAlpha(1f);
                    holder1.action_content.setTextColor(Color.parseColor("#333333"));
                }else if (isread == 1){
                    holder1.action_icon.setAlpha(0.5f);
                    holder1.action_content.setTextColor(Color.parseColor("#cccccc"));
                }
                break;
            case 1:
                holder2.action_msg_time.setText(orderMsgDtOb.getTime());
                holder2.action_msg_title.setText(orderMsgDtOb.getTitle());
                holder2.action_msg_desc.setText(orderMsgDtOb.getContent());
                Glide.with(context).load(orderMsgDtOb.getPicture()).into(holder2.action_msg_pic);
                if (isread == 0 ){
                    holder2.action_msg_pic.setAlpha(1f);
                    holder2.action_msg_title.setTextColor(Color.parseColor("#333333"));
                    holder2.action_msg_desc.setTextColor(Color.parseColor("#666666"));
                }else if (isread == 1){
                    holder2.action_msg_pic.setAlpha(0.5f);
                    holder2.action_msg_title.setTextColor(Color.parseColor("#cccccc"));
                    holder2.action_msg_desc.setTextColor(Color.parseColor("#cccccc"));
                }
                break;
        }
        return view;
    }

    class MyViewHolder1{
        TextView action_msg_time,action_content;
        ImageView action_icon;
    }

    class MyViewHolder2{
        TextView action_msg_time,action_msg_title,action_msg_desc;
        ImageView action_msg_pic;
    }
}
