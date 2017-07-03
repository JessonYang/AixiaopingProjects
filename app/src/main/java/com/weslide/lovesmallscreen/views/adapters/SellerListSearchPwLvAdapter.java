package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;

import java.util.List;

/**
 * Created by YY on 2017/4/24.
 */
public class SellerListSearchPwLvAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    public SellerListSearchPwLvAdapter(Context context, List<String> list) {
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        MyHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.seller_list_search_pw_lv_item, viewGroup, false);
            holder = new MyHolder();
            holder.historyName = (TextView) convertView.findViewById(R.id.search_history_name);
            holder.historyRm = (ImageView) convertView.findViewById(R.id.search_history_iv);
            convertView.setTag(holder);
        } else holder = (MyHolder) convertView.getTag();
        holder.historyName.setText(list.get(position));
        holder.historyRm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDeleteListenner != null) {
                    onDeleteListenner.onDeleteClick(position);
                }
            }
        });
        return convertView;
    }

    class MyHolder {
        TextView historyName;
        ImageView historyRm;
    }

    public interface OnDeleteListenner {
        void onDeleteClick(int position);
    }

    private OnDeleteListenner onDeleteListenner;

    public void setOnDeleteListenner(OnDeleteListenner onDeleteListenner) {
        this.onDeleteListenner = onDeleteListenner;
    }
}
