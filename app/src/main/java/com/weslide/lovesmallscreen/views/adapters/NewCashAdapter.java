package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Withdrawals;

import java.util.List;

/**
 * Created by YY on 2017/3/24.
 */
public class NewCashAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Withdrawals> list;

    public NewCashAdapter(Context context, List<Withdrawals> list) {
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MyHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_cash_list, viewGroup, false);
            holder = new MyHolder();
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(holder);
        } else holder = (MyHolder) convertView.getTag();


        Withdrawals withdrawals = list.get(i);
        holder.name.setText(withdrawals.getRemark());
        holder.date.setText(withdrawals.getCreatetime());
        holder.money.setText(withdrawals.getMoney());

        if(withdrawals.getMoney().charAt(0) == '-'){
            holder.money.setTextColor(context.getResources().getColor(R.color.red));
        }else {
            holder.money.setTextColor(context.getResources().getColor(R.color.mediumseagreen));
        }

        return convertView;
    }

    class MyHolder {
        TextView name;

        TextView date;

        TextView money;
    }
}
