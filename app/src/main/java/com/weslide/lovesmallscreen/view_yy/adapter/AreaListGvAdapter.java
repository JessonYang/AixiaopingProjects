package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Zone;

import java.util.List;

/**
 * Created by YY on 2017/9/27.
 */
public class AreaListGvAdapter extends BaseAdapter {

    private Context context;
    private List<Zone> list;
    private LayoutInflater inflater;

    public AreaListGvAdapter(Context context, List<Zone> list) {
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
        MyViewHolder holder;
        if (view == null) {
            holder = new MyViewHolder();
            view = inflater.inflate(R.layout.area_list_gv_item,viewGroup,false);
            holder.cityName = (TextView) view.findViewById(R.id.city_name);
            view.setTag(holder);
        }else {
            holder = (MyViewHolder) view.getTag();
        }
        holder.cityName.setText(list.get(i).getName());
        return view;
    }

    class MyViewHolder{
        TextView cityName;
    }
}
