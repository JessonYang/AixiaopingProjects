package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Zone;

import java.util.List;

/**
 * Created by YY on 2017/9/26.
 */
public class CityDistrictAdapter extends BaseAdapter {

    private Context context;
    private List<Zone> list;
    private LayoutInflater inflater;

    public CityDistrictAdapter(Context context, List<Zone> list) {
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
            view = inflater.inflate(R.layout.city_district_list_item, viewGroup, false);
            holder = new MyViewHolder();
            holder.city_letter_tv = (TextView) view.findViewById(R.id.city_letter_tv);
            holder.city_name_tv = (TextView) view.findViewById(R.id.city_name_tv);
            holder.city_letter_rll = (RelativeLayout) view.findViewById(R.id.city_letter_rll);
            view.setTag(holder);
        }else{
            holder = (MyViewHolder) view.getTag();
        }
        Zone zone = list.get(i);
        String curCityLetter = zone.getEnglishChar();
        holder.city_letter_tv.setText(curCityLetter);
        String cityName = zone.getName();
        holder.city_name_tv.setText(cityName);
        holder.city_name_tv.setTag(zone);
        if (i > 0) {
            String preCityLetter = list.get(i - 1).getEnglishChar();
            if (preCityLetter.equals(curCityLetter)) {
                holder.city_letter_rll.setVisibility(View.GONE);
            }else {
                holder.city_letter_rll.setVisibility(View.VISIBLE);
            }
        }else {
            holder.city_letter_rll.setVisibility(View.VISIBLE);
        }
        return view;
    }

    class MyViewHolder {
        TextView city_letter_tv, city_name_tv;
        RelativeLayout city_letter_rll;
    }
}
