package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.eventbus_message.UploadShopConcernMessage;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.APIUtils;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.home.SellerItemScoreGoodsView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by YY on 2017/6/27.
 */
public class HomeSellerListLvAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Seller> list;

    public HomeSellerListLvAdapter(Context context, List<Seller> list) {
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
        MyHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.home_view_seller_item,viewGroup,false);
            holder = new MyHolder();
            holder.cbConcern = (CheckBox) view.findViewById(R.id.cb_concern);
            holder.glGoods = (GridLayout) view.findViewById(R.id.gl_goods);
            holder.glPreferentials = (GridLayout) view.findViewById(R.id.gl_preferentials);
            holder.goodsScore1 = (SellerItemScoreGoodsView) view.findViewById(R.id.goods_score_1);
            holder.goodsScore2 = (SellerItemScoreGoodsView) view.findViewById(R.id.goods_score_2);
            holder.goodsScore3 = (SellerItemScoreGoodsView) view.findViewById(R.id.goods_score_3);
            holder.ivPreferentials1 = (ImageView) view.findViewById(R.id.iv_preferentials_1);
            holder.ivPreferentials2 = (ImageView) view.findViewById(R.id.iv_preferentials_2);
            holder.ivPreferentials3 = (ImageView) view.findViewById(R.id.iv_preferentials_3);
            holder.ivSellerIcon = (ImageView) view.findViewById(R.id.iv_seller_icon);
            holder.tvDistance = (TextView) view.findViewById(R.id.tv_distance);
            holder.tvSellerName = (TextView) view.findViewById(R.id.tv_seller_name);
            holder.tvSellerAddress = (TextView) view.findViewById(R.id.tv_seller_address);
            holder.btn_seller = (Button) view.findViewById(R.id.btn_seller);
            view.setTag(holder);
        }else {
            holder = (MyHolder) view.getTag();
        }

        Seller mSeller = list.get(i);
        holder.tvSellerName.setText(mSeller.getSellerName());
        holder.tvSellerAddress.setText(mSeller.getSellerAddress());
        // tvDistance.setText(ContextParameter.getDistanceForCurrentLocationAddUnit(mSeller));
        double location = Double.parseDouble(mSeller.getDistance());
        String result = null;
        String results = null;
       /* if (location < 1000) {
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
            result = df.format(location) + "m";
        } else {*/
        double value = location / 1000;
        java.text.DecimalFormat df = new java.text.DecimalFormat("###.00");
        results = df.format(value);
        Double d = new Double(results);
        result = d.toString() + "km";
        //  }
        holder.tvDistance.setText(result);
        Glide.with(context).load(mSeller.getSellerIcon()).skipMemoryCache(true).into(holder.ivSellerIcon);
        holder.bind = true;
        holder.cbConcern.setChecked(mSeller.getConcern());
        holder.bind = false;

        //商家优惠政策  为了性能，只能使用这种写法了
        if (mSeller.getPreferentials() == null || mSeller.getPreferentials().size() == 0) {
            holder.glPreferentials.setVisibility(View.GONE);
        } else {
            if (mSeller.getPreferentials().size() > 0) {
                holder.ivPreferentials1.setVisibility(View.VISIBLE);
                Glide.with(context).load(mSeller.getPreferentials().get(0).getImage()).skipMemoryCache(true).into(holder.ivPreferentials1);
            } else {
                holder.ivPreferentials1.setVisibility(View.INVISIBLE);
                holder.ivPreferentials2.setVisibility(View.INVISIBLE);
                holder.ivPreferentials3.setVisibility(View.INVISIBLE);
            }

            if (mSeller.getPreferentials().size() > 1) {
                holder.ivPreferentials2.setVisibility(View.VISIBLE);
                Glide.with(context).load(mSeller.getPreferentials().get(1).getImage()).skipMemoryCache(true).into(holder.ivPreferentials1);
            } else {
                holder.ivPreferentials2.setVisibility(View.INVISIBLE);
                holder.ivPreferentials3.setVisibility(View.INVISIBLE);
            }

            if (mSeller.getPreferentials().size() > 2) {
                holder.ivPreferentials3.setVisibility(View.VISIBLE);
                Glide.with(context).load(mSeller.getPreferentials().get(2).getImage()).skipMemoryCache(true).into(holder.ivPreferentials1);
            } else {
                holder.ivPreferentials3.setVisibility(View.INVISIBLE);
            }
        }

        //商家推荐商品
        if (mSeller.getRecommendSellerGoodsList() == null || mSeller.getRecommendSellerGoodsList().size() == 0) {
            holder.glGoods.setVisibility(View.GONE);
        } else {
            if (mSeller.getRecommendSellerGoodsList().size() > 0) {
                holder.goodsScore1.setVisibility(View.VISIBLE);
                holder.goodsScore1.bindView(mSeller.getRecommendSellerGoodsList().get(0));
            } else {
                holder.goodsScore1.setVisibility(View.INVISIBLE);
                holder.goodsScore2.setVisibility(View.INVISIBLE);
                holder.goodsScore3.setVisibility(View.INVISIBLE);

            }

            if (mSeller.getRecommendSellerGoodsList().size() > 1) {
                holder.goodsScore2.setVisibility(View.VISIBLE);
                holder.goodsScore2.bindView(mSeller.getRecommendSellerGoodsList().get(1));
            } else {
                holder.goodsScore2.setVisibility(View.INVISIBLE);
                holder.goodsScore3.setVisibility(View.INVISIBLE);
            }

            if (mSeller.getRecommendSellerGoodsList().size() > 2) {
                holder.goodsScore3.setVisibility(View.VISIBLE);
                holder.goodsScore3.bindView(mSeller.getRecommendSellerGoodsList().get(2));
            } else {
                holder.goodsScore3.setVisibility(View.INVISIBLE);
            }
        }
        holder.btn_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toSeller(context, mSeller.getSellerId());
            }
        });
        holder.cbConcern.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.bind) {
                    return;
                }

                APIUtils.concernSeller(context, mSeller.getSellerId(), holder.cbConcern.isChecked(), new SupportSubscriber<Response>() {

                    private void setCheckValue() {
                        holder.bind = true;
                        holder.cbConcern.setChecked(!holder.cbConcern.isChecked());
                        holder.bind = false;
                    }

                    @Override
                    public void onNoNetwork() {
                        super.onNoNetwork();
                        T.showShort(context, "请链接网络~");
                        setCheckValue();
                    }

                    @Override
                    public void onResponseError(Response response) {
                        super.onResponseError(response);
                        T.showShort(context, "未知错误~");
                        setCheckValue();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        T.showShort(context, "未知错误~");
                        setCheckValue();
                    }

                    @Override
                    public void onNext(Response response) {

                        if (holder.cbConcern.isChecked()) {
                            T.showShort(context, "恭喜，店铺收藏成功！");
                        } else {
                            T.showShort(context, "已取消店铺收藏");
                        }

                        //提示关注商家列表更新
                        EventBus.getDefault().post(new UploadShopConcernMessage());
                    }
                });
            }
        });
        return view;
    }

    class MyHolder{
        ImageView ivPreferentials1,ivPreferentials2,ivPreferentials3,ivSellerIcon;
        TextView tvDistance,tvSellerName,tvSellerAddress;
        GridLayout glPreferentials,glGoods;
        CheckBox cbConcern;
        SellerItemScoreGoodsView goodsScore1,goodsScore2,goodsScore3;
        Button btn_seller;
        private boolean bind = false;
    }
}
