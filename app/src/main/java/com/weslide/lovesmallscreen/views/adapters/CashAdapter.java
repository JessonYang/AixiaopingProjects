package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.withdrawals.WithdrawalsListActivity;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.Withdrawals;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/12/8.
 * 资产管理适配器
 */
public class CashAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel,RecyclerView.ViewHolder> {

    /**
     * 头部轮播图
     */
    public static final int TYPE_HEAD = 0;
    /**
     * 商品分类
     */
    public static final int TYPE_ASSETS_CLASSIFYS = 1;
    public CashAdapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEAD:
                viewHolder = new TotleMoneyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_cash_head, parent, false));

                break;
            case TYPE_ASSETS_CLASSIFYS:

                viewHolder = new AssetsListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_cash_list,parent,false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEAD:
                String money = (String) mList.get(position).getData();
                ((TotleMoneyViewHolder)holder).money.setText(money);
                ((TotleMoneyViewHolder)holder).withdraw.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {
                    AppUtils.toActivity(mContext,WithdrawalsListActivity.class);
                }});//跳转到提现
                break;
            case TYPE_ASSETS_CLASSIFYS:
                Withdrawals assets = (Withdrawals) mList.get(position).getData();
                ((AssetsListViewHolder)holder).name.setText(assets.getRemark());
                ((AssetsListViewHolder)holder).date.setText(assets.getCreatetime());
                if(assets.getMoney().charAt(0) == '-'){
                    ((AssetsListViewHolder)holder).money.setTextColor(mContext.getResources().getColor(R.color.red));
                }else {
                    ((AssetsListViewHolder)holder).money.setTextColor(mContext.getResources().getColor(R.color.mediumseagreen));
                }
                ((AssetsListViewHolder)holder).money.setText(assets.getMoney());
                break;
        }

    }
    @Override
    public int getItemViewType(int position) {
        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;
    }
    class TotleMoneyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_totle_money)
        TextView money;
        @BindView(R.id.tv_withdraw)
        LinearLayout withdraw;

       public TotleMoneyViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
       }

    }
    class AssetsListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.tv_date)
        TextView date;
        @BindView(R.id.tv_money)
        TextView money;
        public AssetsListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
