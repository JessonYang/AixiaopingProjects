package com.weslide.lovesmallscreen.views.adapters.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/23.
 * 商家列表里的item
 */
public class ListSellerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_preferentials_1)
    ImageView ivPreferentials1;
    @BindView(R.id.iv_preferentials_2)
    ImageView ivPreferentials2;
    @BindView(R.id.iv_preferentials_3)
    ImageView ivPreferentials3;
    @BindView(R.id.gl_preferentials)
    GridLayout glPreferentials;
    @BindView(R.id.cb_concern)
    CheckBox cbConcern;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.goods_score_1)
    SellerItemScoreGoodsView goodsScore1;
    @BindView(R.id.goods_score_2)
    SellerItemScoreGoodsView goodsScore2;
    @BindView(R.id.goods_score_3)
    SellerItemScoreGoodsView goodsScore3;
    @BindView(R.id.iv_seller_icon)
    ImageView ivSellerIcon;
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.tv_seller_address)
    TextView tvSellerAddress;
    @BindView(R.id.gl_goods)
    GridLayout glGoods;

    Seller mSeller;
    Context mContext;


    public ListSellerViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.home_view_seller_item, parent, false));

        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    private boolean bind = false;

    public void bindView(Seller seller) {
        mSeller = seller;

        tvSellerName.setText(mSeller.getSellerName());
        tvSellerAddress.setText(mSeller.getSellerAddress());
       // tvDistance.setText(ContextParameter.getDistanceForCurrentLocationAddUnit(mSeller));
        String distance = mSeller.getDistance();
        double location = Double.parseDouble(distance.trim());
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
        Glide.with(mContext).load(seller.getSellerIcon()).skipMemoryCache(true).into(ivSellerIcon);
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
    }


    @OnCheckedChanged(R.id.cb_concern)
    public void onCheckedChanged() {

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

    @OnClick(R.id.btn_seller)
    public void onClick() {
        AppUtils.toSeller(mContext, mSeller.getSellerId());
    }
}
