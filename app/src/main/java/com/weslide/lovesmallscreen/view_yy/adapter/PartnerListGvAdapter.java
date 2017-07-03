package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.model_yy.javabean.PartnerIconModel;

import java.util.List;

/**
 * Created by YY on 2017/6/7.
 */
public class PartnerListGvAdapter extends BaseAdapter {

    private Context context;
    private List<PartnerIconModel> list;
    private LayoutInflater inflater;
    private int count;

    public PartnerListGvAdapter(Context context, List<PartnerIconModel> list, int count) {
        this.context = context;
        this.list = list;
        this.count = count;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return null;
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
            view = inflater.inflate(R.layout.partner_gv_item, viewGroup, false);
            holder.icon = (ImageView) view.findViewById(R.id.partner_list_icon);
            holder.itemBg = (RelativeLayout) view.findViewById(R.id.partner_list_icon_bg);
            view.setTag(holder);
        } else holder = (MyViewHolder) view.getTag();

        if (i == list.size()) {
            holder.itemBg.setBackgroundResource(R.drawable.weifenpei);

        }
        if (i < list.size()) {
            holder.itemBg.setBackgroundResource(R.drawable.yifenpei);
            Glide.with(context).load(list.get(i).getPartnerIcon()).asBitmap().placeholder(R.drawable.morentouxiang_yuan).error(R.drawable.morentouxiang_yuan).centerCrop().into(new BitmapImageViewTarget(holder.icon) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.icon.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
        return view;
    }

    class MyViewHolder {
        RelativeLayout itemBg;
        ImageView icon;
    }
}
