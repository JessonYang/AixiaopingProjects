package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.model_yy.javabean.BrandModel;

import java.util.List;

/**
 * Created by YY on 2017/6/17.
 */
public class BrandGvAdapter extends BaseAdapter {

    private Context context;

    public BrandGvAdapter(Context context, List<BrandModel> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    private List<BrandModel> list;
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
            view = inflater.inflate(R.layout.brand_gv_item,viewGroup,false);
            holder = new MyHolder();
            holder.brandIv = (ImageView) view.findViewById(R.id.brandIv);
            view.setTag(holder);
        }else holder = (MyHolder) view.getTag();
        Glide.with(context).load(list.get(i).getLogo()).into(holder.brandIv);
        return view;
    }

    class MyHolder {
        ImageView brandIv;
    }
}
