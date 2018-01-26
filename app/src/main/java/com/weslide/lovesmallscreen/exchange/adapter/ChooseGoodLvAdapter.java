package com.weslide.lovesmallscreen.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.model_yy.javabean.ExchangeGoodDtModel;

import java.util.List;

/**
 * Created by YY on 2018/1/25.
 */
public class ChooseGoodLvAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<ExchangeGoodDtModel> list;
    private int curPos = 0;

    public ChooseGoodLvAdapter(Context context, List<ExchangeGoodDtModel> list) {
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
            view = inflater.inflate(R.layout.choose_good_lv_item, viewGroup, false);
            holder = new MyHolder();
            holder.goodName = (TextView) view.findViewById(R.id.good_name);
            holder.goodPrice = (TextView) view.findViewById(R.id.good_price);
            holder.goodPic = (ImageView) view.findViewById(R.id.good_iv);
            holder.selectStatus = (ImageView) view.findViewById(R.id.select_status_iv);
            view.setTag(holder);
        } else {
            holder = (MyHolder) view.getTag();
        }
        ExchangeGoodDtModel model = list.get(i);
        holder.goodName.setText(model.getGoodsName());
        holder.goodPrice.setText(model.getDisplayPrice() + "");
        Glide.with(context).load(model.getCoverPicOne()).into(holder.goodPic);
        if (i == curPos) {
            holder.selectStatus.setImageResource(R.drawable.xuanzhong);
        }else holder.selectStatus.setImageResource(R.drawable.weixuanzhong);
        return view;
    }

    public void changPos(int pos){
        curPos = pos;
        notifyDataSetChanged();
    }

    class MyHolder {
        TextView goodName, goodPrice;
        ImageView goodPic, selectStatus;
    }

}
