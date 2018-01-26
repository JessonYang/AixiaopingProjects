package com.weslide.lovesmallscreen.view_yy.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

/**
 * Created by YY on 2017/11/27.
 */
public class HomeSellerListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView ivPreferentials1, ivPreferentials2, ivPreferentials3, ivSellerIcon;
    TextView tvDistance, tvSellerName, tvSellerAddress;
    GridLayout glPreferentials, glGoods;
    CheckBox cbConcern;
    SellerItemScoreGoodsView goodsScore1, goodsScore2, goodsScore3;
    Button btn_seller;
    private boolean bind = false;
    private Context mContext;

    public HomeSellerListHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        cbConcern = (CheckBox) itemView.findViewById(R.id.cb_concern);
        glGoods = (GridLayout) itemView.findViewById(R.id.gl_goods);
        glPreferentials = (GridLayout) itemView.findViewById(R.id.gl_preferentials);
        goodsScore1 = (SellerItemScoreGoodsView) itemView.findViewById(R.id.goods_score_1);
        goodsScore2 = (SellerItemScoreGoodsView) itemView.findViewById(R.id.goods_score_2);
        goodsScore3 = (SellerItemScoreGoodsView) itemView.findViewById(R.id.goods_score_3);
        ivPreferentials1 = (ImageView) itemView.findViewById(R.id.iv_preferentials_1);
        ivPreferentials2 = (ImageView) itemView.findViewById(R.id.iv_preferentials_2);
        ivPreferentials3 = (ImageView) itemView.findViewById(R.id.iv_preferentials_3);
        ivSellerIcon = (ImageView) itemView.findViewById(R.id.iv_seller_icon);
        tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
        tvSellerName = (TextView) itemView.findViewById(R.id.tv_seller_name);
        tvSellerAddress = (TextView) itemView.findViewById(R.id.tv_seller_address);
        btn_seller = (Button) itemView.findViewById(R.id.btn_seller);
    }

    public void oprateView(Seller mSeller) {
        tvSellerName.setText(mSeller.getSellerName());
        tvSellerAddress.setText(mSeller.getSellerAddress());
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
        tvDistance.setText(result);
        Glide.with(mContext).load(mSeller.getSellerIcon()).skipMemoryCache(true).into(ivSellerIcon);
        bind = true;
        cbConcern.setChecked(mSeller.getConcern());
        bind = false;

        //商家优惠政策  为了性能，只能使用这种写法了
        if (mSeller.getPreferentials() == null || mSeller.getPreferentials().size() == 0) {
            glPreferentials.setVisibility(View.GONE);
        } else {
            if (mSeller.getPreferentials().size() > 0) {
                ivPreferentials1.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mSeller.getPreferentials().get(0).getImage()).skipMemoryCache(true).into(ivPreferentials1);
            } else {
                ivPreferentials1.setVisibility(View.INVISIBLE);
                ivPreferentials2.setVisibility(View.INVISIBLE);
                ivPreferentials3.setVisibility(View.INVISIBLE);
            }

            if (mSeller.getPreferentials().size() > 1) {
                ivPreferentials2.setVisibility(View.VISIBLE);

                Glide.with(mContext).load(mSeller.getPreferentials().get(1).getImage()).skipMemoryCache(true).into(ivPreferentials1);

            } else {
                ivPreferentials2.setVisibility(View.INVISIBLE);
                ivPreferentials3.setVisibility(View.INVISIBLE);
            }

            if (mSeller.getPreferentials().size() > 2) {
                ivPreferentials3.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mSeller.getPreferentials().get(2).getImage()).skipMemoryCache(true).into(ivPreferentials1);
            } else {
                ivPreferentials3.setVisibility(View.INVISIBLE);
            }
        }

        //商家推荐商品
        if (mSeller.getRecommendSellerGoodsList() == null || mSeller.getRecommendSellerGoodsList().size() == 0) {
            glGoods.setVisibility(View.GONE);
        } else {
            if (mSeller.getRecommendSellerGoodsList().size() > 0) {
                goodsScore1.setVisibility(View.VISIBLE);
                goodsScore1.bindView(mSeller.getRecommendSellerGoodsList().get(0));
            } else {
                goodsScore1.setVisibility(View.INVISIBLE);
                goodsScore2.setVisibility(View.INVISIBLE);
                goodsScore3.setVisibility(View.INVISIBLE);

            }

            if (mSeller.getRecommendSellerGoodsList().size() > 1) {
                goodsScore2.setVisibility(View.VISIBLE);
                goodsScore2.bindView(mSeller.getRecommendSellerGoodsList().get(1));
            } else {
                goodsScore2.setVisibility(View.INVISIBLE);
                goodsScore3.setVisibility(View.INVISIBLE);
            }

            if (mSeller.getRecommendSellerGoodsList().size() > 2) {
                goodsScore3.setVisibility(View.VISIBLE);
                goodsScore3.bindView(mSeller.getRecommendSellerGoodsList().get(2));
            } else {
                goodsScore3.setVisibility(View.INVISIBLE);
            }
        }
        btn_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toSeller(mContext, mSeller.getSellerId());
            }
        });
        cbConcern.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (bind) {
                    return;
                }

                APIUtils.concernSeller(mContext, mSeller.getSellerId(), cbConcern.isChecked(), new SupportSubscriber<Response>() {

                    private void setCheckValue() {
                        bind = true;
                        cbConcern.setChecked(!cbConcern.isChecked());
                        bind = false;
                    }

                    @Override
                    public void onNoNetwork() {
                        super.onNoNetwork();
                        T.showShort(mContext, "请链接网络~");
                        setCheckValue();
                    }

                    @Override
                    public void onResponseError(Response response) {
                        super.onResponseError(response);
                        T.showShort(mContext, "未知错误~");
                        setCheckValue();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        T.showShort(mContext, "未知错误~");
                        setCheckValue();
                    }

                    @Override
                    public void onNext(Response response) {

                        if (cbConcern.isChecked()) {
                            T.showShort(mContext, "恭喜，店铺收藏成功！");
                        } else {
                            T.showShort(mContext, "已取消店铺收藏");
                        }

                        //提示关注商家列表更新
                        EventBus.getDefault().post(new UploadShopConcernMessage());
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exchange_iv:

                break;
            case R.id.zbdp_rll:

                break;
        }
    }
}
