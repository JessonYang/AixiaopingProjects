package com.weslide.lovesmallscreen.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.model_yy.javabean.SpecificationModel;

import java.util.List;

/**
 * Created by YY on 2018/1/19.
 */
public class ExchangeExtensionSpecGvAdapter extends BaseAdapter {
    private Context context;
    private List<SpecificationModel> list;
    private LayoutInflater inflater;
    private int selectPos = 0;

    public ExchangeExtensionSpecGvAdapter(Context context, List<SpecificationModel> list) {
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
            view = inflater.inflate(R.layout.exchange_extension_spec_gv_item, viewGroup, false);
            holder.specNameTv = (TextView) view.findViewById(R.id.spec_name);
            view.setTag(holder);
        } else holder = (MyViewHolder) view.getTag();

        holder.specNameTv.setText(list.get(i).getSpecStr());
        if (i == selectPos) {
            holder.specNameTv.setBackgroundResource(R.drawable.select_txt_bg);
        } else {
            holder.specNameTv.setBackgroundResource(R.drawable.select_txt_bg_gray);
        }
        return view;
    }

    public void changeSelect(int pos) {
        selectPos = pos;
        notifyDataSetChanged();
    }

    class MyViewHolder {
        private TextView specNameTv;
    }
}
