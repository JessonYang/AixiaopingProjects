package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.model_yy.javabean.Shopcate;
import com.weslide.lovesmallscreen.view_yy.activity.InputStoreTypeActivity;

import java.util.List;


/**
 * Created by Dong on 2017/2/28.
 */
public class MyShopcategoryAdapter extends BaseAdapter{
    private Context context;
    private List<Shopcate> strings;
    public static int mPosition;

    public MyShopcategoryAdapter(Context context, List<Shopcate> strings){
        this.context =context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        convertView = LayoutInflater.from(context).inflate(R.layout.layout_shopcate_item, null);
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        mPosition = position;
        tv.setText(strings.get(position).getCategoryName());
        if (position == InputStoreTypeActivity.mPosition) {
            convertView.setBackgroundColor(Color.parseColor("#f4f4f4"));
            tv.setTextColor(context.getResources().getColor(R.color.main_color));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        return convertView;
    }
}
