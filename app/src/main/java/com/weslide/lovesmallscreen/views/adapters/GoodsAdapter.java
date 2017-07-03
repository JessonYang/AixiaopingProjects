package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.GoodsCommentListActivity;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.fragments.goods.GoodsFragment;
import com.weslide.lovesmallscreen.models.Comment;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.Utils;
import com.weslide.lovesmallscreen.views.Banner;
import com.weslide.lovesmallscreen.views.adapters.viewholder.GoodsCommentItemViewHolder;
import com.weslide.lovesmallscreen.views.adapters.viewholder.GoodsCommentTitleViewHolder;
import com.weslide.lovesmallscreen.views.goods.SpecView;
import com.weslide.lovesmallscreen.views.widget.AddAndSubtractView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/21.
 * 商品详情适配器
 */
public class GoodsAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {

    GoodsFragment mGoodsFragment;
    private String goodsId;

    /**
     * 商品信息
     */
    public static final int TYPE_INFO = 1;
    /**
     * 评论标题
     */
    public static final int TYPE_COMMENT_TITLE = 2;
    /**
     * 评论项
     */
    public static final int TYPE_COMMENT_ITEM = 3;

    /**
     * 图片横幅
     */
    public static final int TYPE_BANNER = 4;

    /**
     * 加载详情
     */
    public static final int TYPE_LOAD_DETAIL = 5;

    public GoodsAdapter(Context context, GoodsFragment goodsFragment, DataList<RecyclerViewModel> list) {
        super(context, list);
        mGoodsFragment = goodsFragment;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case TYPE_INFO:
                viewHolder = new GoodsInfoViewHolder(mContext, mGoodsFragment, parent);
                break;
            case TYPE_COMMENT_TITLE:
                viewHolder = new GoodsCommentTitleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.goods_view_comment_title, parent, false));
                break;
            case TYPE_COMMENT_ITEM:
                viewHolder = new GoodsCommentItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.goods_view_comment_item, parent, false));
                break;
//            case TYPE_DETAIL:
//                AXPWebView axpWebView = new AXPWebView(mContext);
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                axpWebView.setLayoutParams(params);
//                viewHolder = new SimpleViewHolder(axpWebView);
//                break;
            case TYPE_BANNER:
                viewHolder = new GoodsBannerViewHolder(mContext, parent);
                break;
            case TYPE_LOAD_DETAIL:
                viewHolder = new SimpleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.goods_view_load_detials, parent, false));
                break;
        }

        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_INFO:

                if (mList.get(position).isFirst()) {
                    mList.get(position).setFirst(false);
                    ((GoodsInfoViewHolder) holder).bindView((Goods) mList.get(position).getData());
                }

                break;
            case TYPE_COMMENT_TITLE:

                Goods goods = (Goods) mList.get(position).getData();
                goodsId = goods.getGoodsId();
                GoodsCommentTitleViewHolder commentTitleViewHolder = (GoodsCommentTitleViewHolder) holder;
                commentTitleViewHolder.tvCommentCount.setText(goods.getCommentList().getCommentCount());
                commentTitleViewHolder.tvCommentPraise.setText(goods.getCommentList().getGoodCommentPraise());
                commentTitleViewHolder.comeList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("goodsId",goods.getGoodsId());
                        AppUtils.toActivity(mContext, GoodsCommentListActivity.class,bundle);
                    }
                });
                break;
            case TYPE_COMMENT_ITEM:

                Comment comment = (Comment) mList.get(position).getData();
                GoodsCommentItemViewHolder commentItemViewHolder = (GoodsCommentItemViewHolder) holder;
                commentItemViewHolder.tvGoodsCommentName.setText(comment.getUserInfo().getUsername());
                commentItemViewHolder.tvGoodsCommentContent.setText(comment.getCommentContent());
                commentItemViewHolder.tvGoodsCommentDate.setText(comment.getCommentDate());
                commentItemViewHolder.ratingBar.setRating(Float.parseFloat(comment.getCommentGoal()) / 2);
                commentItemViewHolder.commentList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("goodsId",goodsId);
                        AppUtils.toActivity(mContext, GoodsCommentListActivity.class,bundle);
                    }
                });
                if (comment.getCommentImages() != null && comment.getCommentImages().size() > 0) {
                    commentItemViewHolder.layoutCommentImages.setVisibility(View.VISIBLE);
                    commentItemViewHolder.layoutCommentImages.removeAllViews();

                    for (String image : comment.getCommentImages()) {
                        ImageView imageView = new ImageView(mContext);
                        imageView.setPadding(10,10,10,10);
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                (int) mContext.getResources().getDimension(R.dimen.goods_comment_height));
                        imageView.setLayoutParams(params);
                        Glide.with(mContext).load(image).into(imageView);
                        commentItemViewHolder.layoutCommentImages.addView(imageView);
                    }
                } else {
                    commentItemViewHolder.layoutCommentImages.setVisibility(View.GONE);
                }

                break;

            case TYPE_BANNER:
                if (mList.get(position).isFirst()) {
                    mList.get(position).setFirst(false);
                    ((GoodsBannerViewHolder) holder).bindView((Goods) mList.get(position).getData());
                }
                break;

        }
    }
}

class GoodsBannerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_index)
    TextView tvIndex;

    public GoodsBannerViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.goods_view_banner, parent, false));
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Goods goods) {
        List<ImageText> imageVideoText = new ArrayList<>();
        if (goods.getVideos() != null && goods.getVideos().size() != 0)
            imageVideoText.addAll(goods.getVideos());
        if (goods.getImages() != null && goods.getImages().size() != 0)
            imageVideoText.addAll(goods.getImages());

        banner.setImageTexts(imageVideoText);
        banner.getConvenientBanner().setCanLoop(false);
        banner.getConvenientBanner().setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvIndex.setText(position + 1 + "/" + imageVideoText.size());
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}


class GoodsInfoViewHolder extends RecyclerView.ViewHolder {

    Context mContext;
    GoodsFragment mGoodsFragment;
    Goods mGoods;

    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_value)
    TextView tvGoodsValue;
    @BindView(R.id.tv_tag_red_pager)
    TextView tvTagRedPager;
    @BindView(R.id.sv_spec)
    SpecView svSpec;
    @BindView(R.id.layout_spec)
    LinearLayout layoutSpec;
    @BindView(R.id.as_number)
    AddAndSubtractView asNumber;
    @BindView(R.id.iv_seller_icon)
    ImageView ivSellerIcon;
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.tv_seller_address)
    TextView tvSellerAddress;
    @BindView(R.id.iv_preferentials_1)
    ImageView ivPreferentials1;
    @BindView(R.id.iv_preferentials_2)
    ImageView ivPreferentials2;
    @BindView(R.id.iv_preferentials_3)
    ImageView ivPreferentials3;
    @BindView(R.id.gl_preferentials)
    GridLayout glPreferentials;
    @BindView(R.id.tv_cost_price)
    TextView tvCostPrice;
    @BindView(R.id.tv_express_tactics)
    TextView tvExpressTactics;
    @BindView(R.id.tv_sales_volume)
    TextView tvSalesVolume;

    public GoodsInfoViewHolder(Context context, GoodsFragment goodsFragment, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.goods_view_info, parent, false));
        ButterKnife.bind(this, itemView);
        mContext = context;
        mGoodsFragment = goodsFragment;
        Utils.strikethrough(tvCostPrice);
    }

    private void showGoodsInfo(Goods goods) {
        switch (goods.getType()) {
            case 1:
                //积分商品
                tvTagRedPager.setVisibility(View.GONE);
                tvGoodsValue.setText(goods.getValue());
                break;
            case 2:
                //现金商品
                tvTagRedPager.setVisibility(View.GONE);
                tvGoodsValue.setText(goods.getValue());
                break;
            case 3:
                //免单商品
                tvTagRedPager.setVisibility(View.GONE);
                tvGoodsValue.setText("免单");
                break;
            case 4:
                //现金红包商品
                tvTagRedPager.setVisibility(View.VISIBLE);
                tvTagRedPager.setText("红包可抵" + goods.getCashpoint() + "元");
                tvGoodsValue.setText(goods.getValue());
                break;
        }
    }

    public void bindView(Goods goods) {

        mGoods = goods;


        tvGoodsName.setText(mGoods.getName());

        showGoodsInfo(mGoods);

        tvCostPrice.setText("原价：￥" + mGoods.getCostPrice());
        tvExpressTactics.setText(mGoods.getExpressTactics());
        String salesVolume = mGoods.getSalesVolume();
//        String substring = salesVolume.substring(0,salesVolume.indexOf("."));
        tvSalesVolume.setText("销量:"+ salesVolume);
        Log.d("雨落无痕丶", "bindView: sales"+salesVolume);



        //设置规格等信息
        if (mGoods.getSpec() == null || mGoods.getSpec().size() == 0) {
            layoutSpec.setVisibility(View.GONE);
        } else {

            svSpec.setOnSpecItemViewSelectListener((specNote, selectKeys) -> {


                if (selectKeys != null && mGoods.getSpec().size() == selectKeys.length) {

                    Goods _goods = new Goods();
                    _goods.setPrice(specNote.getPrice());
                    _goods.setScore(specNote.getScore());
                    _goods.setCashpoint(specNote.getCashpoint());


                    GoodsInfoViewHolder.this.showGoodsInfo(_goods);

                    asNumber.setMaxValue(specNote.getStockNumber());

                    //规格变化时， 重新选中一下值
                    mGoodsFragment.specs = selectKeys;
                } else {
                    //规格变化时， 重新选中一下值
                    mGoodsFragment.specs = null;
                }


            });
            svSpec.show(mGoods.getSpec(), mGoods.getSpecNote());
        }

        //设置购买数量变化
        asNumber.setOnValueChangeListener(new AddAndSubtractView.OnValueChangeListener() {
            @Override
            public void change(int change) {
                mGoodsFragment.number = change;
            }
        });


        //设置商家信息
        Seller mSeller = goods.getSeller();

        tvSellerName.setText(mSeller.getSellerName());
        tvSellerAddress.setText(mSeller.getSellerAddress());
        Glide.with(mContext).load(mSeller.getSellerIcon()).into(ivSellerIcon);

        //商家优惠政策  为了性能，只能使用这种写法了
        if (mSeller.getPreferentials() == null || mSeller.getPreferentials().size() == 0) {
            glPreferentials.setVisibility(View.GONE);
        } else {
            if (mSeller.getPreferentials().size() > 0) {
                ivPreferentials1.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mSeller.getPreferentials().get(0).getImage()).into(ivPreferentials1);
            } else {
                ivPreferentials1.setVisibility(View.INVISIBLE);
                ivPreferentials2.setVisibility(View.INVISIBLE);
                ivPreferentials3.setVisibility(View.INVISIBLE);
            }

            if (mSeller.getPreferentials().size() > 1) {
                ivPreferentials2.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mSeller.getPreferentials().get(1).getImage()).into(ivPreferentials1);
            } else {
                ivPreferentials2.setVisibility(View.INVISIBLE);
                ivPreferentials3.setVisibility(View.INVISIBLE);
            }

            if (mSeller.getPreferentials().size() > 2) {
                ivPreferentials3.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mSeller.getPreferentials().get(2).getImage()).into(ivPreferentials1);
            } else {
                ivPreferentials3.setVisibility(View.INVISIBLE);
            }
        }


//        wvGoodsDetailed.getWebView().loadUrl(HTTP.URL_GOODS_DETAIL + "?goodsId" + goods.getGoodsId());

    }

    @OnClick(R.id.btn_seller)
    public void onClick() {
        AppUtils.toSeller(mContext, mGoods.getSeller().getSellerId());
    }
}
