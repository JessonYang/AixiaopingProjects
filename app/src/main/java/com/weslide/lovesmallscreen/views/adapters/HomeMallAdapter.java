package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.LiveActivity;
import com.weslide.lovesmallscreen.activitys.LiveDetails;
import com.weslide.lovesmallscreen.activitys.TaoKeActivity;
import com.weslide.lovesmallscreen.activitys.mall.SellerListActivity_old;
import com.weslide.lovesmallscreen.activitys.mall.Preferential99Activity;
import com.weslide.lovesmallscreen.activitys.ScoreExchangeActivity;
import com.weslide.lovesmallscreen.activitys.mall.SpecialLocalProductActivity;
import com.weslide.lovesmallscreen.activitys.mall.GoodsClassifiActivity;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.Home;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.Live;
import com.weslide.lovesmallscreen.models.SecondKill;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.TaoKe;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.views.Banner;
import com.weslide.lovesmallscreen.views.adapters.viewholder.ListSellerViewHolder;
import com.weslide.lovesmallscreen.views.home.HomeScoreExchangeView;
import com.weslide.lovesmallscreen.views.home.HomeSecondKillView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/3.
 * 商城首页适配器
 */
public class HomeMallAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {

    /**
     * 头部轮播图
     */
    public static final int TYPE_HEAD_BANNERS = 1;
    /**
     * 商品分类
     */
    public static final int TYPE_GOODS_CLASSIFYS = 2;
    /**
     * 精选商家
     */
    public static final int TYPE_CONCENTRATION_SELLERS = 3;
    /**
     * 积分商品分类
     */
    public static final int TYPE_SCORE_GOODS_CLASSIFYS = 4;
    /**
     * 限时秒杀
     */
    public static final int TYPE_SECOND_KILL = 6;
    /**
     * 附近商家
     */
    public static final int TYPE_SELLER = 7;
    /**
     * 图文操作，例如：99特惠、各地特产
     */
    public static final int TYPE_OPTION = 8;
    /**
     * 爱划算、特卖汇
     */
    public static final int TYPE_OPTION_BANNER = 9;
    /**
     * 附近商家标题
     */
    public static final int TYPE_SELLER_TITLE = 11;

    /**
     * 直播标题
     */
    public static final int TYPE_LIVE_TITLE = 10;

    /**
     * 直播列表
     */
    public static final int TYPE_LIVE_LIST = 12;

    /**
     * 淘客标题
     */
    public static final int TYPE_TAOKE_TITLE = 13;

    /**
     * 淘客商品分类
     */
    public static final int TYPE_TAOKE_TYPE = 14;

    SuperRecyclerView mRecyclerView;


    public HomeMallAdapter(Context context, DataList<RecyclerViewModel> data, SuperRecyclerView recyclerView) {
        super(context, data);
        mRecyclerView = recyclerView;
    }


    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEAD_BANNERS:
                viewHolder = new HomeHeadBannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_view_head_banner, parent, false));

                break;
            case TYPE_GOODS_CLASSIFYS:
                viewHolder = new HomeGoodsClassifyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_view_goods_classify, parent, false));

                break;
            case TYPE_TAOKE_TITLE:

                viewHolder = new HomeTaokeTitleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_taoke_title,parent,false));

                break;

            case TYPE_TAOKE_TYPE:

                viewHolder = new HomeTaokeTypeViewHolder(parent,mContext);

                break;
            case TYPE_OPTION:
                viewHolder = new HomeOptionViewHolder(mContext, parent);
                break;
            case TYPE_CONCENTRATION_SELLERS:
                viewHolder = new HomeConcentrationSellersViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_view_concentration_seller, parent, false));

                break;
            case TYPE_OPTION_BANNER:
                viewHolder = new HomeOptionBannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_view_option_banner, parent, false));
                break;
            case TYPE_SECOND_KILL:
                viewHolder = new SimpleViewHolder(new HomeSecondKillView(mContext));
                break;
            case TYPE_LIVE_TITLE:
                viewHolder = new SimpleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_live_title, parent, false));
                break;
            case TYPE_SCORE_GOODS_CLASSIFYS:
                viewHolder = new HomeScoreExchangeViewHolder(new HomeScoreExchangeView(mContext));
                break;
            case TYPE_SELLER_TITLE:
                viewHolder = new SimpleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_view_seller_title, parent, false));
                break;
            case TYPE_SELLER:
                viewHolder = new ListSellerViewHolder(mContext, parent);
                break;
            case TYPE_LIVE_LIST:
                viewHolder = new HomeLiveListViewHolder(parent,mContext);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_HEAD_BANNERS:     //头部轮播图
                mList.get(position).setFirst(false);
                ((HomeHeadBannerViewHolder) holder).banner.setImageTexts((List<ImageText>) mList.get(position).getData());
                break;
            case TYPE_GOODS_CLASSIFYS:  //商品分类
                if (mList.get(position).isFirst()) {
                    mList.get(position).setFirst(false);
                    List<ImageText> imageTexts = (List<ImageText>) mList.get(position).getData();
                    ((HomeGoodsClassifyViewHolder) holder).bindView(imageTexts);
                }
                break;

            case TYPE_TAOKE_TITLE:

                TaoKe taoKe = (TaoKe) mList.get(position).getData();
                ((HomeTaokeTitleViewHolder)holder).title.setText(taoKe.getTitle());
                ((HomeTaokeTitleViewHolder)holder).total.setText(taoKe.getTotal());
                ((HomeTaokeTitleViewHolder)holder).item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!StringUtils.isBlank(taoKe.getUri())) {
                            Bundle bundle = new Bundle();
                            bundle.putString(TaoKeActivity.KEY_LOAD_URL, taoKe.getUri());
                            AppUtils.toActivity(mContext, TaoKeActivity.class, bundle);
                        }
                    }
                });
                break;

            case TYPE_TAOKE_TYPE:
                TaoKe taoKe1 = (TaoKe) mList.get(position).getData();

                ((HomeTaokeTypeViewHolder)holder).bindView(taoKe1,mContext);

                break;
            case TYPE_OPTION:
                if (mList.get(position).isFirst()) {
                    mList.get(position).setFirst(false);
                    HomeOptionViewHolder optionViewHolder = (HomeOptionViewHolder) holder;

                    optionViewHolder.bindView((Home) mList.get(position).getData());

                }
                break;
            case TYPE_CONCENTRATION_SELLERS:
                if (mList.get(position).isFirst()) {
                    mList.get(position).setFirst(false);
                    List<ImageText> concentrationSellersImageTexts = (List<ImageText>) mList.get(position).getData();
                    ((HomeConcentrationSellersViewHolder) holder).bindView(concentrationSellersImageTexts);
                }
                break;
            case TYPE_OPTION_BANNER:
                List<ImageText> optionBannerImageTexts = (List<ImageText>) mList.get(position).getData();
                ((HomeOptionBannerViewHolder) holder).mOptionBanner.setImageTexts(optionBannerImageTexts);
                ((HomeOptionBannerViewHolder) holder).mName.setText(optionBannerImageTexts.get(0).getName());

                break;
            case TYPE_SECOND_KILL:
                if (mList.get(position).isFirst()) {
                    mList.get(position).setFirst(false);
                    List<SecondKill> secondKills = (List<SecondKill>) mList.get(position).getData();
//                    ((HomeSecondKillView) holder.itemView).bindData(secondKills);
                    ((HomeSecondKillView) holder.itemView).bindData(secondKills);
                    //实现滑动时不加载
//                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                        @Override
//                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                            switch (newState) {
//                                case 2:
//                                    L.i("Main", "用户在手指离开屏幕之前，由于滑了一下，视图仍然依靠惯性继续滑动");
//                                    //刷新
//                                    break;
//                                case 0:
//                                    L.i("Main", "视图已经停止滑动");
//                                    ((HomeSecondKillView) holder.itemView).bindData(secondKills);
//                                    //加载完后将事件移除
//                                    mRecyclerView.removeOnScrollListener(this);
//                                    break;
//                                case 1:
//                                    L.i("Main", "手指没有离开屏幕，视图正在滑动");
//                                    break;
//                            }
//                        }
//                    });
                }
                break;
            case TYPE_SCORE_GOODS_CLASSIFYS:
                if (mList.get(position).isFirst()) {
                    mList.get(position).setFirst(false);

                    ((HomeScoreExchangeViewHolder) holder).mView.bindData((List<ImageText>) mList.get(position).getData());
                    //实现滑动时不加载
//                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                        @Override
//                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                            switch (newState) {
//                                case 2:
//                                    L.i("Main", "用户在手指离开屏幕之前，由于滑了一下，视图仍然依靠惯性继续滑动");
//                                    //刷新
//                                    break;
//                                case 0:
//                                    L.i("Main", "视图已经停止滑动");
//                                    ((HomeScoreExchangeViewHolder) holder).mView.bindData((List<ImageText>) mList.get(position).getData());
//                                    //加载完后将事件移除
//                                    mRecyclerView.removeOnScrollListener(this);
//                                    break;
//                                case 1:
//                                    L.i("Main", "手指没有离开屏幕，视图正在滑动");
//                                    break;
//                            }
//                        }
//                    });
                }
                break;
            case TYPE_LIVE_LIST:
                    Live liveList = (Live) mList.get(position).getData();
                    ((HomeLiveListViewHolder) holder).bindView(liveList, mContext);

                break;
            case TYPE_LIVE_TITLE:
                holder.itemView.findViewById(R.id.tv_more).setOnClickListener(view -> AppUtils.toActivity(mContext, LiveActivity.class));
                break;
            case TYPE_SELLER_TITLE:
                holder.itemView.findViewById(R.id.tv_more).setOnClickListener(view -> AppUtils.toActivity(mContext, SellerListActivity_old.class));
                break;

            case TYPE_SELLER:
                ((ListSellerViewHolder) holder).bindView((Seller) mList.get(position).getData());
//                holder.itemView.findViewById(R.id.tv_more).setOnClickListener(view -> AppUtils.toActivity(mContext, LiveActivity.class));
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;
    }
}

/**
 * 首页头部BannerViewHolder
 */
class HomeHeadBannerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.banner_home_banner)
    Banner banner;

    public HomeHeadBannerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}

/**
 * 商品分类
 */
class HomeGoodsClassifyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.gv_gride)
    GridView mGrideView;
    Context mContext;

    public HomeGoodsClassifyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
    }

    public void bindView(List<ImageText> imageTexts) {
        mGrideView.setAdapter(new SimpleAdpater() {
            @Override
            public int getCount() {
                return imageTexts.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView != null) {
                    return convertView;
                }
                View view = LayoutInflater.from(mContext).inflate(R.layout.home_view_goods_classify_item, parent, false);
                view.setOnClickListener(v -> {
                    //跳转至商品列表
                    Intent intent = new Intent(mContext, GoodsClassifiActivity.class);
                    GetGoodsListBean request = new GetGoodsListBean();
                    request.setTypeId(imageTexts.get(position).getTypeId());
                    request.setMallTyle(Constants.MALL_STAND_ALONE);
                    intent.putExtra(GoodsClassifiActivity.KEY_REQUEST, request);
                    intent.putExtra(GoodsClassifiActivity.KEY_TITLE, imageTexts.get(position).getName());
                    mContext.startActivity(intent);

                });
                ((TextView) view.findViewById(R.id.tv_name)).setText(imageTexts.get(position).getName());
                Glide.with(mContext).load(imageTexts.get(position).getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) view.findViewById(R.id.iv_icon));
                return view;
            }
        });
    }
}

/**
 * 图文操作
 * 例如：99特惠、各地特产
 */
class HomeOptionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_preference_99)
    ImageView ivPreference;
    @BindView(R.id.iv_right_top)
    ImageView ivRightTop;
    @BindView(R.id.iv_right_bottom)
    ImageView ivRightBottom;
    Context mContext;
    String URLMALL;
    String URL99;
    String URLSPECIAL;
    public HomeOptionViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.home_view_option, parent, false));
        ButterKnife.bind(this, itemView);

        mContext = context;
    }

    public void bindView(Home home) {
      /*  URLMALL = home.getScoreMall().getUri();
        URL99 = home.getPreferential99().getUri();
        URLSPECIAL = home.getSpecialLocalProduct().getUri();
        Glide.with(mContext).load(home.getPreferential99().getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivPreference);
        Glide.with(mContext).load(home.getSpecialLocalProduct().getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivRightTop);
        Glide.with(mContext).load(home.getScoreMall().getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivRightBottom);*/
        URLMALL = home.getTwentyImage().getUri();
        URL99 = home.getSqgwImage().getUri();
        URLSPECIAL = home.getNineImage().getUri();
        Glide.with(mContext).load(home.getSqgwImage().getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivPreference);
        Glide.with(mContext).load(home.getNineImage().getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivRightTop);
        Glide.with(mContext).load(home.getTwentyImage().getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivRightBottom);
    }

    @OnClick({R.id.iv_preference_99, R.id.iv_right_top, R.id.iv_right_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_preference_99:

                if(StringUtils.isBlank(URL99)) {
                    AppUtils.toActivity(mContext, Preferential99Activity.class);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString(TaoKeActivity.KEY_LOAD_URL,URL99);
                    AppUtils.toActivity(mContext, TaoKeActivity.class,bundle);
                   // URIResolve.resolve(mContext,URL99);
                }
                break;
            case R.id.iv_right_top:
                if(StringUtils.isBlank(URLSPECIAL)) {
                    AppUtils.toActivity(mContext, SpecialLocalProductActivity.class);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString(TaoKeActivity.KEY_LOAD_URL,URLSPECIAL);
                    AppUtils.toActivity(mContext, TaoKeActivity.class,bundle);
                //    URIResolve.resolve(mContext,URLSPECIAL);
                }
                break;
            case R.id.iv_right_bottom:
                if(StringUtils.isBlank(URLMALL)) {
                    AppUtils.toActivity(mContext, ScoreExchangeActivity.class);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString(TaoKeActivity.KEY_LOAD_URL,URLMALL);
                    AppUtils.toActivity(mContext, TaoKeActivity.class,bundle);
                //    URIResolve.resolve(mContext,URLMALL);
                }
                break;
        }
    }
}

/**
 * 精选商家
 */
class HomeConcentrationSellersViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.gv_grid)
    GridView mGridView;
    @BindView(R.id.tv_more)
    TextView mMore;

    public HomeConcentrationSellersViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(List<ImageText> concentrationSellersImageTexts) {
        mGridView.setAdapter(new SimpleAdpater() {
            @Override
            public int getCount() {
                return concentrationSellersImageTexts.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView != null) {
                    return convertView;
                }
                View view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.home_view_concentration_seller_item, parent, false);
                view.setOnClickListener(v -> {
                    //跳转至商家详情
                    AppUtils.toSeller(itemView.getContext(), concentrationSellersImageTexts.get(position).getSellerId());
                });
                ((TextView) view.findViewById(R.id.tv_name)).setText(concentrationSellersImageTexts.get(position).getName());
                L.e("加载的商家路径：" + concentrationSellersImageTexts.get(position).getImage());
                Glide.with(itemView.getContext()).load(concentrationSellersImageTexts.get(position).getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) view.findViewById(R.id.iv_icon));
                return view;
            }
        });
    }
}

/**
 * 精选商家
 */
class HomeOptionBannerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.banner_option_banner)
    Banner mOptionBanner;

    public HomeOptionBannerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
class HomeLiveListViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.iv_live_item)
    ImageView ivItem;
    @BindView(R.id.tv_live_name)
    TextView name;
    public HomeLiveListViewHolder(ViewGroup parent,Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.home_live_list,parent,false));
        ButterKnife.bind(this, itemView);
    }
    public void bindView(Live live,Context context){
            Glide.with(context).load(live.getImgae()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivItem);
            name.setText(live.getName());
            ivItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("liveId",live.getLiveid());
                AppUtils.toActivity(context, LiveDetails.class,bundle);
            }
        });

      /*     if(!StringUtils.isBlank(live.getUri())){

            ivItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    URIResolve.resolve(context, live.getUri());
                }
            });
        }*/
    }
}

/**
 * 积分兑换
 */
class HomeScoreExchangeViewHolder extends RecyclerView.ViewHolder {

    public HomeScoreExchangeView mView;

    public HomeScoreExchangeViewHolder(View itemView) {
        super(itemView);
        mView = (HomeScoreExchangeView) itemView;
    }


}

/**
 * 淘客标题
 */
class HomeTaokeTitleViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tv_total)
    TextView total;
    @BindView(R.id.ll_taoke_item)
    LinearLayout item;
    public HomeTaokeTitleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

class HomeTaokeTypeViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.iv_good)
    ImageView good;
    @BindView(R.id.tv_price)
    TextView price;
    @BindView(R.id.tv_orgprice)
    TextView orgprice;
    public HomeTaokeTypeViewHolder(ViewGroup parent,Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.layout_taoke_type,parent,false));
        ButterKnife.bind(this, itemView);
    }
    public void bindView(TaoKe taoKe,Context context){
        Glide.with(context).load(taoKe.getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(good);
        price.setText("券后：¥"+taoKe.getPrice());
        orgprice.setText("原价："+taoKe.getOrg_price());
        orgprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!StringUtils.isBlank(taoKe.getUri())) {
                    Bundle bundle = new Bundle();
                    bundle.putString(TaoKeActivity.KEY_LOAD_URL, taoKe.getUri());
                    AppUtils.toActivity(context, TaoKeActivity.class, bundle);
                }
            }
        });

    }
}
