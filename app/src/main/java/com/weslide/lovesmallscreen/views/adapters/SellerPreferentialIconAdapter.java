package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.ImageText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2016/6/20.
 * 商家优惠图标适配器
 */
public class SellerPreferentialIconAdapter extends BaseAdapter {

    List<ImageText> mList = new ArrayList<>();
    Context mContext;

    public SellerPreferentialIconAdapter(Context context, List<ImageText> list){
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        //最多显示四个
        return mList.size() > 4 ? 4 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(mContext);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                (int) mContext.getResources().getDimension(R.dimen.seller_layout_preferential_icon) ,
                (int) mContext.getResources().getDimension(R.dimen.seller_layout_preferential_icon));
        imageView.setLayoutParams(params);
        Glide.with(mContext).load(mList.get(position).getImage()).into(imageView);
        return imageView;
    }
}
