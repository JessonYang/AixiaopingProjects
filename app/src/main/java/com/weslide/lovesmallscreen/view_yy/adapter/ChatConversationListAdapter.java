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

import java.util.List;

import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;

/**
 * Created by YY on 2017/11/2.
 */
public class ChatConversationListAdapter extends BaseAdapter {

    private Context context;
    private List<Conversation> list;
    private LayoutInflater inflater;

    public ChatConversationListAdapter(Context context, List<Conversation> list) {
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
        Conversation fans = list.get(i);
        Glide.with(context).load(fans.getPortraitUrl()).into(holder.fans_iv);
        holder.fans_name.setText(fans.getSenderUserName());

        holder.fans_chat_time.setText(fans.getSentTime() + "");

        if (fans.getLatestMessage() != null) {
            holder.fans_latest_msg.setText(((TextMessage) fans.getLatestMessage()).getContent());
        }

        return view;
    }

    class MyViewholder {
        TextView fans_latest_msg, fans_name, fans_chat_time;
        ImageView fans_iv;
    }
}
