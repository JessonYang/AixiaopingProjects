package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.TaoBaoUrlModel;
import com.weslide.lovesmallscreen.models.bean.HomePprmModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.UserUtils;
import com.weslide.lovesmallscreen.view_yy.activity.SaveMoneyBrandActivity;
import com.weslide.lovesmallscreen.view_yy.activity.SaveMoneyHomeActivity;
import com.weslide.lovesmallscreen.view_yy.fragment.HomeMainFragment;

import java.util.List;

/**
 * Created by YY on 2017/6/15.
 */
public class HomePprmAdapter extends RecyclerView.Adapter {

    private final int type;
    private List<HomePprmModel> list;
    private LayoutInflater inflater;
    private Context context;

    public HomePprmAdapter(List<HomePprmModel> list, Context context,int type) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < list.size()) {
            return new MyHolder(inflater.inflate(R.layout.home_hot_brand_rclv_item, parent, false));
        }else {
            return new MoreDiscountHolder(inflater.inflate(R.layout.home_main_more_discount, parent, false),type);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) < list.size()) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.good_name.setText(list.get(position).getD_title());
            myHolder.good_price.setText(list.get(position).getOrg_price());
            myHolder.discount_price_rtv.setText("抵"+list.get(position).getCut_price()+"");
            Glide.with(context).load(list.get(position).getPic()).into(myHolder.good_iv);
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView good_name;
        private TextView discount_price_rtv;
        private TextView good_price;
        private ImageView good_iv;

        public MyHolder(View itemView) {
            super(itemView);
            good_name = (TextView) itemView.findViewById(R.id.good_name);
            good_price = (TextView) itemView.findViewById(R.id.good_price);
            discount_price_rtv = (TextView) itemView.findViewById(R.id.discount_price_rtv);
            good_iv = (ImageView) itemView.findViewById(R.id.good_iv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Request<TaoBaoUrlModel> request = new Request<TaoBaoUrlModel>();
                    TaoBaoUrlModel model = new TaoBaoUrlModel();
                    model.setPid(HomeMainFragment.pid);
                    model.setGoodsId(list.get(getLayoutPosition()).getGoods_id());
                    request.setData(model);
                    RXUtils.request(context,request,"findGoodsUrl", new SupportSubscriber<Response<TaoBaoUrlModel>>() {
                        @Override
                        public void onNext(Response<TaoBaoUrlModel> taoBaoUrlModelResponse) {
                            UserUtils.toTaobao(taoBaoUrlModelResponse.getData().getLink(),context);
                        }
                    });
                }
            });
        }
    }

    class MoreDiscountHolder extends RecyclerView.ViewHolder{
        ImageView more_discount;

        public MoreDiscountHolder(View itemView,int type) {
            super(itemView);
            more_discount = ((ImageView) itemView.findViewById(R.id.more_discount));
            more_discount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (type == 0) {//品牌专区
                        AppUtils.toActivity(context, SaveMoneyBrandActivity.class);
                    } else if (type == 1) {//省钱购物
                        Bundle saveMoneyBundle = new Bundle();
                        saveMoneyBundle.putString("toolbarType", "省钱购物");
                        saveMoneyBundle.putString("searchValue", "");
                        saveMoneyBundle.putString("cid", "-1");
                        AppUtils.toActivity(context, SaveMoneyHomeActivity.class, saveMoneyBundle);
                    }
                }
            });
        }
    }
}
