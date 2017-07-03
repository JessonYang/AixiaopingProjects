package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.bean.OrdersOb;

import java.util.List;

/**
 * Created by YY on 2017/3/24.
 */
public class CityAgencyOrderAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<OrdersOb> list;

    public CityAgencyOrderAdapter(Context context,List<OrdersOb> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
//        Log.d("雨落无痕丶", "CityAgencyOrderAdapter: =============" + list.size());
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MyHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.city_agenty_today_order_lv_item, viewGroup, false);
            holder = new MyHolder();
            holder.city_agenty_userFace_iv = (ImageView) convertView.findViewById(R.id.city_agenty_userFace_iv);
            holder.city_agenty_order_img = (ImageView) convertView.findViewById(R.id.city_agenty_order_img);
            holder.city_agency_order_status_img = (ImageView) convertView.findViewById(R.id.city_agency_order_status_img);
            holder.city_agenty_userPhone = (TextView) convertView.findViewById(R.id.city_agenty_userPhone);
            holder.city_agenty_userName = (TextView) convertView.findViewById(R.id.city_agenty_userName);
            holder.city_agenty_order_state = (TextView) convertView.findViewById(R.id.city_agenty_order_state);
            holder.city_agenty_order_num = (TextView) convertView.findViewById(R.id.city_agenty_order_num);
            holder.city_agenty_order_describtion = (TextView) convertView.findViewById(R.id.city_agenty_order_describtion);
            holder.city_agenty_pay_money = (TextView) convertView.findViewById(R.id.city_agenty_pay_money);
            holder.city_agenty_estimates_result = (TextView) convertView.findViewById(R.id.city_agenty_estimates_result);
            holder.city_agenty_order_resource = (TextView) convertView.findViewById(R.id.city_agenty_order_resource);
            holder.city_agenty_order_date_day = (TextView) convertView.findViewById(R.id.city_agenty_order_date_day);
//            holder.city_agenty_order_date_hour = (TextView) view.findViewById(R.id.city_agenty_order_date_hour);
            convertView.setTag(holder);
        } else holder = (MyHolder) convertView.getTag();

        if (list.size()>0 && list != null) {
            OrdersOb ordersOb = list.get(i);
            Glide.with(context).load(ordersOb.getUserIcon()).placeholder(R.drawable.csdl_yhtx).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.city_agenty_userFace_iv);
            Glide.with(context).load(ordersOb.getOrderPic()).placeholder(R.drawable.csdl_yhtx).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.city_agenty_order_img);
            Glide.with(context).load(ordersOb.getStatusPic()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.city_agency_order_status_img);
            holder.city_agenty_userPhone.setText(ordersOb.getUserAccount());
            holder.city_agenty_userName.setText(ordersOb.getUserName());
            String statusDesc = ordersOb.getStatusDesc();
            if (statusDesc != null) {
                if (statusDesc.equals("订单失效")){
                    holder.city_agenty_order_state.setTextColor(Color.parseColor("#999999"));
                }else if (statusDesc.equals("订单结算")){
                    holder.city_agenty_order_state.setTextColor(Color.parseColor("#2871bb"));
                }else if (statusDesc.equals("订单付款")){
                    holder.city_agenty_order_state.setTextColor(Color.parseColor("#ff6500"));
                }
                holder.city_agenty_order_state.setText(statusDesc);
            }
            holder.city_agenty_order_num.setText(ordersOb.getOrderNumber());
            holder.city_agenty_order_describtion.setText(ordersOb.getOrderName());
            holder.city_agenty_pay_money.setText(ordersOb.getPayMoney());
            holder.city_agenty_estimates_result.setText(ordersOb.getDistributeMoney());
            holder.city_agenty_order_resource.setText(ordersOb.getOrderFrom());
            holder.city_agenty_order_date_day.setText(ordersOb.getOrderDate());
        }
        return convertView;
    }

    class MyHolder {
        ImageView city_agenty_userFace_iv;
        ImageView city_agenty_order_img;
        ImageView city_agency_order_status_img;
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
