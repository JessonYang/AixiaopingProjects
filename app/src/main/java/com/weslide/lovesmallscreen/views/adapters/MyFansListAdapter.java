package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Fans;
import com.weslide.lovesmallscreen.models.Fans;
import com.weslide.lovesmallscreen.network.DataList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/4.
 */
public class MyFansListAdapter extends SuperRecyclerViewAdapter<Fans,MyFansListAdapter.MyFansListViewHoler> {

    public MyFansListAdapter(Context context, DataList<Fans> dataList) {
        super(context, dataList);
    }

    @Override
    public MyFansListViewHoler onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        MyFansListViewHoler viewHoler = new MyFansListViewHoler(LayoutInflater.from(mContext).inflate(R.layout.item_my_fans,parent,false));
        return viewHoler;
    }

    @Override
    public void onSuperBindViewHolder(MyFansListViewHoler holder, int position) {
        Fans fans = mList.get(position);
        holder.name.setText(fans.getName());
        holder.date.setText(fans.getDate());
        if(fans.getSex()==null||fans.getSex().equals("")){
            holder.man.setVisibility(View.GONE);
            holder.woman.setVisibility(View.GONE);
        }else if(fans.getSex().equals("男")) {
            holder.man.setVisibility(View.VISIBLE);
            holder.woman.setVisibility(View.GONE);
        }else if(fans.getSex().equals("女")) {
            holder.man.setVisibility(View.GONE);
            holder.woman.setVisibility(View.VISIBLE);
        }
        if (fans.getHeadImage()==null||fans.getHeadImage().equals("")) {
            holder.headImg.setImageResource(R.drawable.icon_defult);
        } else {
            Glide.with(mContext).load(fans.getHeadImage()).asBitmap().error(R.drawable.icon_defult).placeholder(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(holder.headImg) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.headImg.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
    }
    class MyFansListViewHoler extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_fans_name)
        TextView name;
        @BindView(R.id.tv_date)
        TextView date;
        @BindView(R.id.iv_man)
        ImageView man;
        @BindView(R.id.iv_woman)
        ImageView woman;
        @BindView(R.id.iv_head_img)
        ImageView headImg;

        public MyFansListViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
