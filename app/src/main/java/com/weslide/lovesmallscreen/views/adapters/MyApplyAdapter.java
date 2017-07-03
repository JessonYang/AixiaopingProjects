package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Withdrawals;
import com.weslide.lovesmallscreen.network.DataList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/4.
 */
public class MyApplyAdapter extends SuperRecyclerViewAdapter<Withdrawals,MyApplyAdapter.MyApplysListViewHoler> {


    public MyApplyAdapter(Context context, DataList<Withdrawals> dataList) {
        super(context, dataList);
    }

    @Override
    public MyApplysListViewHoler onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        MyApplysListViewHoler viewHoler = new MyApplysListViewHoler(LayoutInflater.from(mContext).inflate(R.layout.item_my_apply,parent,false));
        return viewHoler;
    }

    @Override
    public void onSuperBindViewHolder(MyApplysListViewHoler holder, int position) {
        Withdrawals apply = mList.get(position);
        holder.name.setText(apply.getBankName());
        holder.date.setText(apply.getCreatetime());
        holder.code.setText(apply.getBankCode() + apply.getPayState());
        holder.free.setText("手续费：¥"+apply.getCounterFee());
        if(apply.getTotalMoney().charAt(0) == '-'){
            holder.money.setTextColor(mContext.getResources().getColor(R.color.red));
        }else {
            holder.money.setTextColor(mContext.getResources().getColor(R.color.mediumseagreen));
        }
        holder.money.setText(apply.getTotalMoney());

    }
    class MyApplysListViewHoler extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.tv_date)
        TextView date;
        @BindView(R.id.tv_money)
        TextView money;
        @BindView(R.id.tv_free)
        TextView free;
        @BindView(R.id.tv_code)
        TextView code;

        public MyApplysListViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
