package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketAllModel;

import java.util.List;

/**
 * Created by YY on 2017/6/5.
 */
public class OriginalAgenceVpAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<TicketAllModel> list;

    public OriginalAgenceVpAdapter(Context context, List<TicketAllModel> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

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
        MyViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.original_agency_vp_lv_item, viewGroup, false);
            holder = new MyViewHolder();
            holder.ticket_good_desc = (TextView) view.findViewById(R.id.ticket_good_desc);
            holder.original_price = (TextView) view.findViewById(R.id.original_price);
            holder.month_sale_num = (TextView) view.findViewById(R.id.month_sale_num);
            holder.earn_money_tv = (TextView) view.findViewById(R.id.earn_money_tv);
            holder.final_price = (TextView) view.findViewById(R.id.final_price);
            holder.quan_left_num = (TextView) view.findViewById(R.id.quan_left_num);
            holder.quan_price = (TextView) view.findViewById(R.id.quan_price);
            holder.ticket_good_iv = (ImageView) view.findViewById(R.id.ticket_good_iv);
            holder.consume_tack_iv = (ImageView) view.findViewById(R.id.consume_tack_iv);
            holder.ticket_good_progress = (ProgressBar) view.findViewById(R.id.ticket_good_progress);
            holder.earn_ll = (LinearLayout) view.findViewById(R.id.earn_ll);
            view.setTag(holder);
        } else holder = (MyViewHolder) view.getTag();
        TicketAllModel model = list.get(i);
        Glide.with(context).load(model.getGoodsIcon()).into(holder.ticket_good_iv);
        holder.ticket_good_desc.setText(model.getGoodsName());
        holder.original_price.setText(model.getGoodsPrice());
        holder.month_sale_num.setText(model.getGoodsSoldPerMonty());
        holder.earn_money_tv.setText("赚￥"+model.getProfitMoney());
        holder.final_price.setText(model.getGoodsPriceAfterTicket());
        holder.quan_left_num.setText("余" + model.getTicketVacancy() + "张");
        holder.quan_price.setText(model.getTicketPrice() + "元");
        holder.ticket_good_progress.setMax(100);
        holder.ticket_good_progress.setProgress((int) (Double.parseDouble(model.getTicketSentPercent()) * 100));
        String consumeTack = model.getConsumeTack();
        if (consumeTack.equals("1")) {
            holder.consume_tack_iv.setImageResource(R.drawable.quanguobaoyou);
        } else if (consumeTack.equals("0")) {
            holder.consume_tack_iv.setImageResource(R.drawable.daodianxiaofei);
        }
        /*holder.earn_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        return view;
    }

    class MyViewHolder {
        TextView ticket_good_desc, original_price, month_sale_num, earn_money_tv, final_price, quan_left_num, quan_price;
        ImageView ticket_good_iv,consume_tack_iv;
        ProgressBar ticket_good_progress;
        LinearLayout earn_ll;
    }
}
