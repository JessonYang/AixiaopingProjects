package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.model_yy.javabean.ShopcategoryInfo;
import com.weslide.lovesmallscreen.view_yy.activity.InputStoreTypeActivity;

import java.util.List;

/**
 * Created by Dong on 2017/2/28.
 */
public class MyShopcateAdapter extends BaseAdapter {
    private Context context;
    private List<ShopcategoryInfo> strings;
    public static int mPosition;
    private SparseBooleanArray statusArray = new SparseBooleanArray();

    public MyShopcateAdapter(Context context, List<ShopcategoryInfo> strings) {
        this.context = context;
        this.strings = strings;
        for (int i = 0; i < strings.size(); i++) {
            statusArray.put(i,false);
        }
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
        convertView = LayoutInflater.from(context).inflate(R.layout.layout_shopcate_fragment_item, null);
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.cb);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                statusArray.put(position,b);
            }
        });
        checkBox.setChecked(statusArray.get(position));
//        checkBox.setEnabled(false);
        mPosition = position;
        tv.setText(strings.get(position).getCategoryName());
        if (position == InputStoreTypeActivity.mPosition) {
            convertView.setBackgroundColor(Color.parseColor("#f4f4f4"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        return convertView;
    }

    public void changeStatus(int p){
        for (int i = 0; i < strings.size(); i++) {
            if (i != p) {
                statusArray.put(i,false);
            }
        }
        notifyDataSetChanged();
    }

}
