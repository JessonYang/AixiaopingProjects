package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.MyScore;
import com.weslide.lovesmallscreen.network.DataList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/29.
 */
public class MyScoreAdapter extends SuperRecyclerViewAdapter<MyScore,MyScoreAdapter.MyScoreViewHolder> {
    public MyScoreAdapter(Context context, DataList<MyScore> dataList) {
        super(context, dataList);
    }

    @Override
    public MyScoreViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        MyScoreViewHolder viewHolder = new MyScoreViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_score,parent,false));
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(MyScoreViewHolder holder, int position) {
        MyScore myScore = mList.get(position);
        holder.tvTypeName.setText(myScore.getTypeName());
        holder.tvTime.setText(myScore.getAcquireTime());
        holder.tvScoreNumber.setText(myScore.getScore());
    }


    class MyScoreViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_type_name)
        TextView tvTypeName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_score_number)
        TextView tvScoreNumber;
        public MyScoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
