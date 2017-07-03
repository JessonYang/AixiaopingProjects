package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.activitys.LiveDetails;
import com.weslide.lovesmallscreen.models.Live;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/10/19.
 */
public class MyLiveAdapter extends SuperRecyclerViewAdapter<Live,MyLiveAdapter.MyLiveViewHolder>{


    public MyLiveAdapter(Context context, DataList<Live> dataList) {
        super(context, dataList);
    }

    @Override
    public MyLiveViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        MyLiveViewHolder viewHolder = new MyLiveViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_live_list,parent,false));
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(MyLiveViewHolder holder, int position) {
       Live live = mList.get(position);
        holder.liveName.setText(live.getLivename());
        holder.name.setText(live.getName());
        Glide.with(mContext).load(live.getLogo()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(holder.logo) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.logo.setImageDrawable(circularBitmapDrawable);

            }
        });
        Glide.with(mContext).load(live.getImgae()).into(holder.liveImg);
        holder.liveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("liveId",live.getLiveid());
                AppUtils.toActivity(mContext, LiveDetails.class,bundle);
            }
        });
        /*if(!StringUtils.isBlank(live.getUri())){
            holder.liveImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    URIResolve.resolve(mContext, live.getUri());
                }
            });
        }*/
    }

    class  MyLiveViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.tv_live_name)
        TextView liveName;
        @BindView(R.id.iv_logo)
        ImageView logo;
        @BindView(R.id.iv_live_img)
        ImageView liveImg;
        public MyLiveViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
