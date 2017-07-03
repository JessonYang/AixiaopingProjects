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
import com.weslide.lovesmallscreen.models.LivesModel;

import java.util.List;

/**
 * Created by YY on 2017/6/16.
 */
public class LiveGvAdapter extends BaseAdapter {

    private Context context;

    public LiveGvAdapter(Context context, List<LivesModel> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    private List<LivesModel> list;
    private LayoutInflater inflater;

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
            view = inflater.inflate(R.layout.new_home_page_small_gv_item,viewGroup,false);
            holder = new MyHolder();
            holder.iv_live_item = (ImageView) view.findViewById(R.id.iv_live_item);
            holder.tv_live_name = (TextView) view.findViewById(R.id.tv_live_name);
            view.setTag(holder);
        }else holder = (MyHolder) view.getTag();

        Glide.with(context).load(list.get(i).getImgae()).into(holder.iv_live_item);
        holder.tv_live_name.setText(list.get(i).getLivename());
        return view;
    }

    class MyHolder {
        ImageView iv_live_item;
        TextView tv_live_name;
    }
}
