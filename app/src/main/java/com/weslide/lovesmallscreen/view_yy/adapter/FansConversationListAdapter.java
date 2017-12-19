package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Fans;

import java.util.List;

/**
 * Created by YY on 2017/11/2.
 */
public class FansConversationListAdapter extends BaseAdapter {

    private Context context;
    private List<Fans> list;
    private LayoutInflater inflater;

    public FansConversationListAdapter(Context context, List<Fans> list) {
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
        MyViewholder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.fans_conversation_lv_item, viewGroup, false);
            holder = new MyViewholder();
            holder.fans_name = (TextView) view.findViewById(R.id.fans_name);
            holder.fans_chat_time = (TextView) view.findViewById(R.id.fans_chat_time);
            holder.fans_latest_msg = (TextView) view.findViewById(R.id.fans_latest_msg);
            holder.fans_iv = (ImageView) view.findViewById(R.id.fans_iv);
            view.setTag(holder);
        } else {
            holder = (MyViewholder) view.getTag();
        }
        Fans fans = list.get(i);
        Glide.with(context).load(fans.getHeadImage()).into(holder.fans_iv);
        holder.fans_name.setText("粉丝:" + fans.getName());
        if (fans.getChat_latest_time() != null && fans.getChat_latest_time().length() > 0) {
            holder.fans_chat_time.setText(fans.getChat_latest_time());
        } else {
            holder.fans_chat_time.setText(fans.getDate());
        }
        if (fans.getChat_latest_msg() != null && fans.getChat_latest_msg().length() > 0) {
            holder.fans_latest_msg.setText(fans.getChat_latest_msg());
        }

        return view;
    }

    class MyViewholder {
        TextView fans_latest_msg, fans_name, fans_chat_time;
        ImageView fans_iv;
    }
}
