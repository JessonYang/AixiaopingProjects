package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weslide.lovesmallscreen.models.bean.OrdersOb;

import java.util.List;

/**
 * Created by YY on 2017/3/25.
 */
public class CityAgencyOrdersAdapter extends BaseAdapter {

    private List<OrdersOb> list;
    private LayoutInflater inflater;
    private Context context;

    public CityAgencyOrdersAdapter(List<OrdersOb> list, Context context) {
        this.list = list;
        this.context = context;
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

        return view;
    }

    class MyHolder {
        ImageView city_agenty_userFace_iv;
        ImageView city_agenty_order_img;
        TextView city_agenty_userPhone;
        TextView city_agenty_userName;
        TextView city_agenty_order_state;
        TextView city_agenty_order_num;
        TextView city_agenty_order_describtion;
        TextView city_agenty_pay_money;
        TextView city_agenty_estimates_result;
        TextView city_agenty_order_resource;
        TextView city_agenty_order_date_day;
        TextView city_agenty_order_date_hour;
    }
}
