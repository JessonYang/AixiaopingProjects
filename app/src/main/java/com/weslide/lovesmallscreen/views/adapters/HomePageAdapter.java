package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.ChangeGoodsWebActivity;
import com.weslide.lovesmallscreen.activitys.HomeActivity;
import com.weslide.lovesmallscreen.activitys.LiveDetails;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.activitys.ScoreExchangeActivity;
import com.weslide.lovesmallscreen.activitys.SecondKillActivity;
import com.weslide.lovesmallscreen.activitys.TaoKeActivity;
import com.weslide.lovesmallscreen.activitys.mall.Preferential99Activity;
import com.weslide.lovesmallscreen.activitys.mall.SellerListActivity_new;
import com.weslide.lovesmallscreen.activitys.mall.SpecialLocalProductActivity;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.HomePage;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.Live;
import com.weslide.lovesmallscreen.models.SecondKill;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.TaoKe;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.views.Banner;
import com.weslide.lovesmallscreen.views.adapters.viewholder.ListSellerViewHolder;
import com.weslide.lovesmallscreen.views.home.HomeScoreExchangeView;
import com.weslide.lovesmallscreen.views.home.HomeSecondKillView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2017/3/7.
 */
public class HomePageAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {

    /**
     * 头部轮播图
     */
    public static final int TYPE_HEAD_BANNERS = 1;
    /**
     * 积分商品分类
     */
    public static final int TYPE_SCORE_GOODS_CLASSIFYS = 4;
    /**
     * 限时秒杀
     */
    public static final int TYPE_SECOND_KILL = 6;
    /**
     * 周边店铺
     */
    public static final int TYPE_SELLER = 7;
    /**
     * 图文操作，例如：99特惠、各地特产
     */
    public static final int TYPE_OPTION = 8;
    /**
     * 本地商城
     */
    public static final int TYPE_SHOPPINGMALL = 2;

    /**
     * 换货会
     */
    public static final int TYPE_EXCHANGE = 3;
    /**
     * /**
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

    public static final int TYPE_ACTIVITIES_TYPE = 15;

    SuperRecyclerView mRecyclerView;
    public static Thread mThread;


    public HomePageAdapter(Context context, DataList<RecyclerViewModel> data, SuperRecyclerView recyclerView) {
        super(context, data);
        mRecyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEAD_BANNERS:
                viewHolder = new HomePagerHeadBannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_view_head_banner, parent, false));

                break;
            case TYPE_TAOKE_TITLE:


                break;

            case TYPE_TAOKE_TYPE:

                viewHolder = new HomePageTaokeTypeViewHolder(parent, mContext);

                break;
            case TYPE_OPTION:
                viewHolder = new HomePageOptionViewHolder(mContext, parent);
                break;
            case TYPE_SHOPPINGMALL:

                viewHolder = new HomeShoppingMallViewHolder(mContext, parent);

                break;
            case TYPE_EXCHANGE:
                viewHolder = new HomeExchangeViewHolder(mContext, parent);
                break;
            case TYPE_SECOND_KILL:
                viewHolder = new SimpleViewHolder(new HomeSecondKillView(mContext));
                break;
            case TYPE_LIVE_TITLE:
                viewHolder = new HomeLiveTitleViewHolder(mContext, parent);
                break;
            case TYPE_SCORE_GOODS_CLASSIFYS:
                viewHolder = new HomePageScoreExchangeViewHolder(new HomeScoreExchangeView(mContext));
                break;
            case TYPE_SELLER_TITLE:
                viewHolder = new SimpleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_view_seller_title, parent, false));
                break;
            case TYPE_SELLER:
                viewHolder = new ListSellerViewHolder(mContext, parent);
                break;
            case TYPE_LIVE_LIST:
                viewHolder = new HomePageLiveListViewHolder(parent, mContext);
                break;
            case TYPE_ACTIVITIES_TYPE:
                viewHolder = new ActivitiesComeViewHolder(parent, mContext);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_HEAD_BANNERS:     //头部轮播图
                mList.get(position).setFirst(false);
                if (mList.get(position).getData() != null) {
                    ((HomePagerHeadBannerViewHolder) holder).banner.setImageTexts((List<ImageText>) mList.get(position).getData());
                }
                break;

            case TYPE_TAOKE_TITLE:

                break;

            case TYPE_TAOKE_TYPE:
                TaoKe taoKe1 = (TaoKe) mList.get(position).getData();

                if (taoKe1 != null) {
                    ((HomePageTaokeTypeViewHolder) holder).bindView(taoKe1, mContext);
                }

                break;
            case TYPE_OPTION:
                if (mList.get(position).getData() != null) {
                    if (mList.get(position).isFirst()) {
                        mList.get(position).setFirst(false);
                        HomePageOptionViewHolder optionViewHolder = (HomePageOptionViewHolder) holder;

                        optionViewHolder.bindView((HomePage) mList.get(position).getData());

                    }
                }
                break;
            case TYPE_SHOPPINGMALL:
                if (((HomePage) mList.get(position).getData()).getShopping() != null) {
                    if (mList.get(position).isFirst()) {
                        mList.get(position).setFirst(false);
                        HomeShoppingMallViewHolder optionViewHolder = (HomeShoppingMallViewHolder) holder;

                        HomePage homePage = (HomePage) mList.get(position).getData();
                        if (homePage != null) {
                            optionViewHolder.bindView(homePage);
                        }
                    }
                }
                break;
            case TYPE_EXCHANGE:
                if (((HomePage) mList.get(position).getData()).getExchangeAll() != null) {
                    if (mList.get(position).isFirst()) {
                        mList.get(position).setFirst(false);
                        HomeExchangeViewHolder optionViewHolder = (HomeExchangeViewHolder) holder;

                        optionViewHolder.bindView((HomePage) mList.get(position).getData());

                    }
                }
                break;
            case TYPE_SECOND_KILL:
                if (mList.get(position).isFirst()) {
                    mList.get(position).setFirst(false);
                    List<SecondKill> secondKills = (List<SecondKill>) mList.get(position).getData();
                    ((HomeSecondKillView) holder.itemView).bindData(secondKills);

                }
                break;
            case TYPE_SCORE_GOODS_CLASSIFYS:
                if (mList.get(position).isFirst()) {
                    mList.get(position).setFirst(false);

                    ((HomePageScoreExchangeViewHolder) holder).mView.bindData((List<ImageText>) mList.get(position).getData());
                }
                break;
            case TYPE_LIVE_LIST:
                Live liveList = (Live) mList.get(position).getData();
                ((HomePageLiveListViewHolder) holder).bindView(liveList, mContext);

                break;
            case TYPE_LIVE_TITLE:
                if (mList.get(position).isFirst()) {
                    mList.get(position).setFirst(false);
                    HomeLiveTitleViewHolder optionViewHolder = (HomeLiveTitleViewHolder) holder;

                    optionViewHolder.bindView((HomePage) mList.get(position).getData());

                }

                break;
            case TYPE_SELLER_TITLE:
                holder.itemView.findViewById(R.id.tv_more).setOnClickListener(view -> AppUtils.toActivity(mContext, SellerListActivity_new.class));
                break;

            case TYPE_SELLER:
                ((ListSellerViewHolder) holder).bindView((Seller) mList.get(position).getData());
//                holder.itemView.findViewById(R.id.tv_more).setOnClickListener(view -> AppUtils.toActivity(mContext, LiveActivity.class));
                break;

            case TYPE_ACTIVITIES_TYPE:
                if (((HomePage) mList.get(position).getData()).getActivites() != null) {
                    ((ActivitiesComeViewHolder) holder).bindView(((HomePage) mList.get(position).getData()), mContext);
                }
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
class HomePagerHeadBannerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.banner_home_banner)
    Banner banner;

    public HomePagerHeadBannerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}

/**
 * 省钱购物
 * 例如：99特惠、各地特产
 */
class HomePageOptionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_preference_99)
    ImageView ivPreference;
    @BindView(R.id.iv_right_top)
    ImageView ivRightTop;
    @BindView(R.id.iv_right_bottom)
    ImageView ivRightBottom;
    @BindView(R.id.iv_title)
    ImageView title;
    @BindView(R.id.tv_title)
    TextView today_add_num;
    Context mContext;
    String URLMALL;
    String URL99;
    String URLSPECIAL;
    String URLSQGW;

    public HomePageOptionViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.home_view_sqgw, parent, false));
        ButterKnife.bind(this, itemView);

        mContext = context;
    }

    public void bindView(HomePage home) {
        TaoKe twenty = home.getSaveMoney().getTwenty();
        if (twenty != null) {
            URLMALL = twenty.getUri();
        }
        TaoKe superPreferential = home.getSaveMoney().getSuperPreferential();
        if (superPreferential != null) {
            URL99 = superPreferential.getUri();
        }
        TaoKe nine = home.getSaveMoney().getNine();
        if (nine != null) {
            URLSPECIAL = nine.getUri();
        }
        TaoKe save = home.getSaveMoney().getSave();
        if (save != null) {
            URLSQGW = save.getUri();
            today_add_num.setText(save.getTitle());
        }
        Glide.with(mContext).load(home.getSaveMoney().getSuperPreferential().getImage()).placeholder(R.drawable.xianjinquan).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivPreference);
        Glide.with(mContext).load(home.getSaveMoney().getNine().getImage()).placeholder(R.drawable.jiukuaijiu).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivRightTop);
        Glide.with(mContext).load(home.getSaveMoney().getTwenty().getImage()).placeholder(R.drawable.ershiyuan).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivRightBottom);
        Glide.with(mContext).load(home.getSaveMoney().getSave().getImage()).placeholder(R.drawable.sqgw_title).diskCacheStrategy(DiskCacheStrategy.ALL).into(title);
    }

    @OnClick({R.id.iv_preference_99, R.id.iv_right_top, R.id.iv_right_bottom, R.id.rl_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_preference_99:

                if (StringUtils.isBlank(URL99)) {
                    AppUtils.toActivity(mContext, Preferential99Activity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(TaoKeActivity.KEY_LOAD_URL, URL99);
                    AppUtils.toActivity(mContext, TaoKeActivity.class, bundle);
                }
                break;
            case R.id.iv_right_top:
                if (StringUtils.isBlank(URLSPECIAL)) {
                    AppUtils.toActivity(mContext, SpecialLocalProductActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(TaoKeActivity.KEY_LOAD_URL, URLSPECIAL);
                    AppUtils.toActivity(mContext, TaoKeActivity.class, bundle);
                }
                break;
            case R.id.iv_right_bottom:
                if (StringUtils.isBlank(URLMALL)) {
                    AppUtils.toActivity(mContext, ScoreExchangeActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(TaoKeActivity.KEY_LOAD_URL, URLMALL);
                    AppUtils.toActivity(mContext, TaoKeActivity.class, bundle);
                }
                break;
            case R.id.rl_title:
                Bundle bundle = new Bundle();
                bundle.putString(TaoKeActivity.KEY_LOAD_URL, URLSQGW);
                AppUtils.toActivity(mContext, TaoKeActivity.class, bundle);
                break;
        }
    }
}


/**
 * 买买集中营
 */
class HomeShoppingMallViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_mall)
    ImageView mall;
    @BindView(R.id.iv_fruit)
    ImageView fruit;
    @BindView(R.id.iv_share)
    ImageView share;
    @BindView(R.id.title)
    ImageView title;
    @BindView(R.id.iv_lovelife)
    ImageView lovelife;
    Context mContext;

    public HomeShoppingMallViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.home_view_shopingmall, parent, false));
        ButterKnife.bind(this, itemView);

        mContext = context;
    }

    public void bindView(HomePage home) {
        if (home != null) {
            TaoKe xiaopingCentre = home.getShopping().getXiaopingCentre();
            TaoKe fruit = home.getShopping().getFruit();
            TaoKe hot = home.getShopping().getHot();
            TaoKe shopping = home.getShopping().getShopping();
            TaoKe life = home.getShopping().getLife();
            if (xiaopingCentre != null) {
                Glide.with(mContext).load(xiaopingCentre.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(mall);
            }
            if (fruit != null) {
                Glide.with(mContext).load(fruit.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(this.fruit);
            }
            if (hot != null) {
                Glide.with(mContext).load(hot.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(share);
            }
            if (shopping != null) {
                Glide.with(mContext).load(shopping.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(title);
            }
            if (life != null) {
                Glide.with(mContext).load(life.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(lovelife);
            }
        }
    }

    @OnClick({R.id.iv_mall, R.id.iv_fruit, R.id.iv_share, R.id.title, R.id.iv_lovelife})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_mall:
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class);
                break;
            case R.id.iv_fruit:
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class);
                break;
            case R.id.iv_share:
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class);
                break;
            case R.id.title:
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class);
                break;
            case R.id.iv_lovelife:
                AppUtils.toActivity(mContext, SpecialLocalProductActivity.class);
                break;
        }
    }
}


/**
 * 直播标题
 */
class HomeLiveTitleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_live_name)
    TextView tv_live_name;

    @BindView(R.id.tv_live_name2)
    TextView tv_live_name2;

    @BindView(R.id.title)
    ImageView title;

    @BindView(R.id.iv_live_item)
    ImageView iv_live_item;

    @BindView(R.id.iv_live_item2)
    ImageView iv_live_item2;
    Context mContext;

    public HomeLiveTitleViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.layout_view_mylive_title, parent, false));
        ButterKnife.bind(this, itemView);

        mContext = context;
    }

    public void bindView(HomePage home) {
        Glide.with(mContext).load(home.getLive().getHeader().getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(title);
        if (home.getLive().getLives().size() >= 2) {
            Glide.with(mContext).load(home.getLive().getLives().get(0).getImgae()).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_live_item);
            Glide.with(mContext).load(home.getLive().getLives().get(1).getImgae()).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_live_item2);
            tv_live_name.setText(home.getLive().getLives().get(0).getLivename());
            tv_live_name2.setText(home.getLive().getLives().get(1).getLivename());
            iv_live_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("liveId", home.getLive().getLives().get(0).getLiveid());
                    AppUtils.toActivity(mContext, LiveDetails.class, bundle);
                }
            });

            iv_live_item2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("liveId", home.getLive().getLives().get(1).getLiveid());
                    AppUtils.toActivity(mContext, LiveDetails.class, bundle);
                }
            });
        }
    }

    @OnClick({R.id.title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title:

                break;
        }
    }
}

/**
 * 书瑶换货会
 */
class HomeExchangeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_exchange)
    ImageView exchange;
    @BindView(R.id.iv_sign)
    ImageView sign;
    @BindView(R.id.iv_signUp)
    ImageView signUp;
    Context mContext;
    HomePage homePage;

    public HomeExchangeViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(R.layout.layout_home_exchange, parent, false));
        ButterKnife.bind(this, itemView);

        mContext = context;
    }

    public void bindView(HomePage home) {
        homePage = home;
        Glide.with(mContext).load(home.getExchangeAll().getExchange().getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(exchange);
        Glide.with(mContext).load(home.getExchangeAll().getSign().getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(sign);
        Glide.with(mContext).load(home.getExchangeAll().getSignUp().getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(signUp);
    }

    @OnClick({R.id.iv_exchange, R.id.iv_signUp, R.id.iv_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_exchange:
                String exchangeUri = homePage.getExchangeAll().getExchange().getUri();
                if (exchangeUri != null && exchangeUri != "") {
                    if (ContextParameter.isLogin()) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ChangeGoodsWebActivity.KEY_LOAD_URL, exchangeUri);
                        AppUtils.toActivity(mContext, ChangeGoodsWebActivity.class, bundle);
//                        Log.d("雨落无痕丶", "onClick====: " + exchangeUri);
                    } else {
                        AppUtils.toActivity(mContext, LoginOptionActivity.class);
                        if (HomeActivity.activity != null) {
                            HomeActivity.activity.finish();
                        }
                    }
                }
                break;
            case R.id.iv_sign:
                String signUri = homePage.getExchangeAll().getSign().getUri();
                if (signUri != null && signUri != "" && ContextParameter.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ChangeGoodsWebActivity.KEY_LOAD_URL, signUri);
                    AppUtils.toActivity(mContext, ChangeGoodsWebActivity.class, bundle);
                } else AppUtils.toActivity(mContext, LoginOptionActivity.class);
                break;
            case R.id.iv_signUp:
                String signUpUri = homePage.getExchangeAll().getSignUp().getUri();
                if (signUpUri != null && signUpUri != "" && ContextParameter.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ChangeGoodsWebActivity.KEY_LOAD_URL, signUpUri);
                    AppUtils.toActivity(mContext, ChangeGoodsWebActivity.class, bundle);
                } else {
                    AppUtils.toActivity(mContext, LoginOptionActivity.class);
                }
                break;
        }
    }
}

/**
 * 精选商家
 */
class HomePageConcentrationSellersViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.gv_grid)
    GridView mGridView;
    @BindView(R.id.tv_more)
    TextView mMore;

    public HomePageConcentrationSellersViewHolder(View itemView) {
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
                Glide.with(itemView.getContext()).load(concentrationSellersImageTexts.get(position).getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into((ImageView) view.findViewById(R.id.iv_icon));
                return view;
            }
        });
    }
}

/**
 * 精选商家
 */
class HomePageOptionBannerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.banner_option_banner)
    Banner mOptionBanner;

    public HomePageOptionBannerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

class HomePageLiveListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_live_item)
    ImageView ivItem;
    @BindView(R.id.tv_live_name)
    TextView name;

    public HomePageLiveListViewHolder(ViewGroup parent, Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.home_live_list, parent, false));
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Live live, Context context) {
        Glide.with(context).load(live.getImgae()).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivItem);
        name.setText(live.getName());
        ivItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("liveId", live.getLiveid());
                AppUtils.toActivity(context, LiveDetails.class, bundle);
            }
        });
    }
}

/**
 * 积分兑换
 */
class HomePageScoreExchangeViewHolder extends RecyclerView.ViewHolder {

    public HomeScoreExchangeView mView;

    public HomePageScoreExchangeViewHolder(View itemView) {
        super(itemView);
        mView = (HomeScoreExchangeView) itemView;
    }

}

class HomePageTaokeTypeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_good)
    ImageView good;

    public HomePageTaokeTypeViewHolder(ViewGroup parent, Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.layout_taoke_type1, parent, false));
        ButterKnife.bind(this, itemView);
    }

    public void bindView(TaoKe taoKe, Context context) {
        Glide.with(context).load(taoKe.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(good);
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isBlank(taoKe.getUri())) {
                    Bundle bundle = new Bundle();
                    bundle.putString(TaoKeActivity.KEY_LOAD_URL, taoKe.getUri());
                    Log.d("雨落无痕丶", "onClick: 淘客");
                    AppUtils.toActivity(context, TaoKeActivity.class, bundle);
                }
            }
        });

    }
}

/**
 * 活动来袭
 */
class ActivitiesComeViewHolder extends RecyclerView.ViewHolder {

    Handler mHandler = new Handler();

    private boolean isTrue = true;

    @BindView(R.id.activities_start_time)
    TextView startTime;
    @BindView(R.id.to_startTime_hour)
    TextView to_startTime_hour;
    @BindView(R.id.to_startTime_min)
    TextView to_startTime_min;
    @BindView(R.id.to_startTime_second)
    TextView to_startTime_second;
    @BindView(R.id.activities_come_limit_skill)
    ImageView limitImg;
    @BindView(R.id.activities_come_score_exchange)
    ImageView scoreExchange;
    @BindView(R.id.activites_come_title)
    ImageView activites_come_title;


    public ActivitiesComeViewHolder(ViewGroup parent, Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.activites_come_item, parent, false));
        ButterKnife.bind(this, itemView);
    }

    public void bindView(HomePage homePage, Context context) {
        Glide.with(context).load(homePage.getActivites().getHeader().getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(activites_come_title);
        Glide.with(context).load(homePage.getActivites().getSecondKills_new().getSeckGoods().getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(limitImg);
        Glide.with(context).load(homePage.getActivites().getScore().getScoreGoods().getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(scoreExchange);
        startTime.setText(homePage.getActivites().getSecondKills_new().getCurrentTime().getStartDate());
        HomePageAdapter.mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isTrue) {
                    long currentTimeMillis = System.currentTimeMillis();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentTime = format.format(new Date(currentTimeMillis));
                    String currentDate = currentTime.substring(0, currentTime.indexOf(" "));
//                    Log.d("雨落无痕丶", "run: "+currentDate);
                    try {
                        Date parse = format.parse(currentDate + " " + startTime.getText().toString());
                        long time = parse.getTime();
                        if (time <= currentTimeMillis) {
                            to_startTime_hour.setText("00");
                            to_startTime_min.setText("00");
                            to_startTime_second.setText("00");
                            isTrue = false;
                        } else {
                            long leftTime = time - currentTimeMillis;
                            long days = leftTime / (1000 * 60 * 60 * 24);
                            long hours = (leftTime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                            long minutes = (leftTime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                            long seconds = (leftTime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (hours < 10) {
                                        to_startTime_hour.setText("0" + hours + ":");
                                    } else {
                                        to_startTime_hour.setText("" + hours + ":");
                                    }
                                    if (minutes < 10) {
                                        to_startTime_min.setText("0" + minutes + ":");
                                    } else {
                                        to_startTime_min.setText("" + minutes + ":");
                                    }
                                    if (seconds < 10) {
                                        to_startTime_second.setText("0" + seconds);
                                    } else {
                                        to_startTime_second.setText("" + seconds);
                                    }
                                }
                            });
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SystemClock.sleep(1000);
                }
            }
        });
        HomePageAdapter.mThread.start();

        limitImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toActivity(context, SecondKillActivity.class);
            }
        });

        scoreExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.toActivity(context, ScoreExchangeActivity.class);
            }
        });

    }
}
