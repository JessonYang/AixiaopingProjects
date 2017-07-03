package com.weslide.lovesmallscreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.models.MsgHomeObModel;

import net.aixiaoping.library.R;

import java.util.List;

/**
 * Created by YY on 2017/5/8.
 */
public class MsgHomeLvAdapter extends BaseAdapter {

    private List<MsgHomeObModel> list;
    private Context context;
    private LayoutInflater inflater;

    public MsgHomeLvAdapter(Context context, List<MsgHomeObModel> list) {
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
            view = inflater.inflate(R.layout.system_msg_lv_item, viewGroup, false);
            holder = new MyHolder();
            holder.system_icon = (ImageView) view.findViewById(R.id.system_icon);
            holder.system_msg_desc = (TextView) view.findViewById(R.id.system_msg_desc);
            holder.system_msg_time = (TextView) view.findViewById(R.id.system_msg_time);
            holder.system_msg_unread_count = (TextView) view.findViewById(R.id.system_msg_unread_count);
            holder.msg_type_tv = (TextView) view.findViewById(R.id.msg_type_tv);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();

        if (list != null && list.size() > 0) {
            MsgHomeObModel obModel = list.get(i);
            String unread = obModel.getUnread();
            Glide.with(context).load(obModel.getIcon()).placeholder(R.drawable.icon_morentouxiang).into(holder.system_icon);
            holder.msg_type_tv.setText(obModel.getTitle());
            holder.system_msg_desc.setText(obModel.getContent());
            holder.system_msg_time.setText(obModel.getTime());
            if (unread != null && unread.length() > 0 && !unread.equals("0")) {
                holder.system_msg_unread_count.setVisibility(View.VISIBLE);
                if (Integer.parseInt(unread) > 9) {
                    holder.system_msg_unread_count.setText("9+");
                } else {
                    holder.system_msg_unread_count.setText(unread);
                }
            } else holder.system_msg_unread_count.setVisibility(View.GONE);
        }
        return view;
    }

    class MyHolder {
        ImageView system_icon;
        TextView msg_type_tv, system_msg_desc, system_msg_time, system_msg_unread_count;
    }
}
