package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.DeleteCouponModel;
import com.weslide.lovesmallscreen.model_yy.javabean.TicketListObModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;

import java.util.List;

/**
 * Created by YY on 2017/6/6.
 */
public class MyTicketLvAdapter extends RecyclerView.Adapter {

    private List<TicketListObModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String ticketType;

    public MyTicketLvAdapter(List<TicketListObModel> list, Context context, String ticketType) {
        this.list = list;
        this.context = context;
        this.ticketType = ticketType;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.my_ticket_lv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        TicketListObModel model = list.get(position);
        myHolder.ticket_price_tv.setText(model.getFaceValue());
        myHolder.ticket_desc_tv.setText(model.getGoodsName());
        myHolder.consume_date.setText(model.getExpiryDate());
        if (model.getConsumeTack().equals("0")) {
            myHolder.consume_type.setBackgroundResource(R.drawable.ddxfbj);
            myHolder.consume_address_tag.setVisibility(View.VISIBLE);
            myHolder.consume_address.setVisibility(View.VISIBLE);
            myHolder.consume_address.setText(model.getConsumeAddress());
        } else if (model.getConsumeTack().equals("1")) {
            myHolder.consume_type.setBackgroundResource(R.drawable.bydjbj);
            myHolder.consume_address_tag.setVisibility(View.GONE);
            myHolder.consume_address.setVisibility(View.GONE);
        }
        if (ticketType.equals("0")) {
            myHolder.ticket_yiguoqi.setVisibility(View.GONE);
            myHolder.consume_type.setAlpha(1);
            myHolder.ticket_consume_way.setVisibility(View.GONE);
            if (model.getConsumeTack().equals("0")){
                myHolder.ticket_consume_way.setText("到店消费");
                myHolder.ticket_consume_way.setTextColor(Color.parseColor("#eb6572"));
                myHolder.ticket_consume_way.setBackground(null);
                myHolder.ticket_consume_way.setEnabled(false);
            } else if (model.getConsumeTack().equals("1")) {
                myHolder.ticket_consume_way.setText("立即使用");
                myHolder.ticket_consume_way.setTextColor(Color.parseColor("#f8a900"));
                myHolder.ticket_consume_way.setBackgroundResource(R.drawable.ticket_consume_way_bg);
                myHolder.ticket_consume_way.setEnabled(true);
            }
        } else if (ticketType.equals("1")) {
            myHolder.ticket_yiguoqi.setVisibility(View.GONE);
            myHolder.consume_type.setAlpha(0.4f);
            myHolder.ticket_consume_way.setVisibility(View.GONE);
            if (model.getConsumeTack().equals("0")){
                myHolder.ticket_consume_way.setText("已使用");
                myHolder.ticket_consume_way.setTextColor(Color.parseColor("#eb6572"));
                myHolder.ticket_consume_way.setBackground(null);
                myHolder.ticket_consume_way.setEnabled(false);
            } else if (model.getConsumeTack().equals("1")) {
                myHolder.ticket_consume_way.setText("已使用");
                myHolder.ticket_consume_way.setTextColor(Color.parseColor("#f8a900"));
                myHolder.ticket_consume_way.setBackground(null);
                myHolder.ticket_consume_way.setEnabled(false);
            }
        } else if (ticketType.equals("-1")) {
            myHolder.consume_type.setAlpha(0.4f);
            myHolder.ticket_yiguoqi.setVisibility(View.VISIBLE);
            myHolder.ticket_consume_way.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private RelativeLayout consume_type;
        private TextView ticket_price_tv, ticket_desc_tv, consume_address,consume_address_tag, consume_date, ticket_consume_way;
        private ImageView ticket_yiguoqi;

        public MyHolder(View itemView) {
            super(itemView);
            consume_type = (RelativeLayout) itemView.findViewById(R.id.consume_type_rll);
            ticket_price_tv = (TextView) itemView.findViewById(R.id.ticket_price_tv);
            ticket_desc_tv = (TextView) itemView.findViewById(R.id.ticket_desc_tv);
            consume_address = (TextView) itemView.findViewById(R.id.consume_address);
            consume_address_tag = (TextView) itemView.findViewById(R.id.consume_address_tag);
            consume_date = (TextView) itemView.findViewById(R.id.consume_date);
            ticket_consume_way = (TextView) itemView.findViewById(R.id.ticket_consume_way);
            ticket_yiguoqi = (ImageView) itemView.findViewById(R.id.ticket_yiguoqi);
            consume_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.toGoods(context,list.get(getLayoutPosition()).getGoodsId());
                }
            });
            consume_type.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(context).setTitle("提示")
                            .setMessage("是否删除优惠券?")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Request<DeleteCouponModel> request = new Request<>();
                                    DeleteCouponModel model = new DeleteCouponModel();
                                    model.setCouponId(list.get(getLayoutPosition()).getTicketId());
                                    model.setUserId(ContextParameter.getUserInfo().getUserId());
                                    request.setData(model);
                                    RXUtils.request(context,request,"delcoupon", new SupportSubscriber<Response>() {
                                        @Override
                                        public void onNext(Response response) {
                                            list.remove(getLayoutPosition());
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "删除成功!", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onResponseError(Response response) {
                                            Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .create()
                            .show();
                    return false;
                }
            });
        }
    }
}
