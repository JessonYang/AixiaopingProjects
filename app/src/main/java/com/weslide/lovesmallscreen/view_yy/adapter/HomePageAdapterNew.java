package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.ScoreExchangeActivity;
import com.weslide.lovesmallscreen.activitys.SecondKillActivity;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.HomePage;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.Live;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.views.adapters.HomePageAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YY on 2017/6/10.
 */
public class HomePageAdapterNew extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {

    private LayoutInflater inflater;

    /**
     * 头部轮播图
     */
    public static final int NEW_TYPE_HEAD_BANNERS = 1;
    /**
     * 周边店铺分类栏
     */
    public static final int NEW_TYPE_CLASSIFYS = 2;
    /**
     * 省钱购物
     */
    public static final int NEW_TYPE_SAVE_MONEY_BUY = 3;
    /**
     * 优惠券广告
     */
    public static final int NEW_TYPE_DISCOUNT_AD = 4;
    /**
     * 服装，母婴，居家...类
     */
    public static final int NEW_TYPE_OPTION = 5;
    /**
     * 一县一品商品
     */
    public static final int NEW_TYPE_COUNTY_PRODUCT_GOODS = 6;

    /**
     * 一县一品分类
     */
    public static final int NEW_TYPE_COUNTY_PRODUCT_CLASSIFY = 8;

    /**
     * 小屏直播
     */
    public static final int NEW_TYPE_SMALL_LIVE = 9;

    /**
     * 书瑶换货会
     */
    public static final int NEW_TYPE_EXCHANGE = 10;

    SuperRecyclerView mRecyclerView;
    public static Thread mThread;


    public HomePageAdapterNew(Context context, DataList<RecyclerViewModel> data, SuperRecyclerView recyclerView) {
        super(context, data);
        mRecyclerView = recyclerView;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case NEW_TYPE_HEAD_BANNERS:
                viewHolder = new HomePagerHeadBannerViewHolder(inflater.inflate(R.layout.new_home_page_top_banner,parent,false));
                break;
            case NEW_TYPE_CLASSIFYS:
                viewHolder = new HomePageClassifyHolder(inflater.inflate(R.layout.new_home_page_classify_item,parent,false));
                break;
            case NEW_TYPE_COUNTY_PRODUCT_CLASSIFY:
                viewHolder = new HomePageCountyClassifyHolder(inflater.inflate(R.layout.new_home_page_product_classify_item,parent,false));
                break;
            case NEW_TYPE_COUNTY_PRODUCT_GOODS:
                viewHolder = new HomePageCountyGoodHolder(inflater.inflate(R.layout.new_home_page_county_product_rclv_item,parent,false));
                break;
            case NEW_TYPE_DISCOUNT_AD:
                viewHolder = new HomePageDiscountAdHolder(inflater.inflate(R.layout.new_home_page_yhq_banner_item,parent,false));
                break;
            case NEW_TYPE_EXCHANGE:
                viewHolder = new HomePageExchangeHolder(inflater.inflate(R.layout.new_home_page_exchange_item,parent,false));
                break;
            case NEW_TYPE_OPTION:
                viewHolder = new HomePageOptionHolder(inflater.inflate(R.layout.new_home_page_goods_classify_item,parent,false));
                break;
            case NEW_TYPE_SAVE_MONEY_BUY:
                viewHolder = new HomePageSaveMoneyHolder(inflater.inflate(R.layout.new_home_page_save_money_buy_item,parent,false));
                break;
            case NEW_TYPE_SMALL_LIVE:
                viewHolder = new HomePageSmallLiveHolder(inflater.inflate(R.layout.new_home_page_small_live, parent,false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case NEW_TYPE_HEAD_BANNERS:

                break;
            case NEW_TYPE_CLASSIFYS:

                break;
            case NEW_TYPE_COUNTY_PRODUCT_CLASSIFY:

                break;
            case NEW_TYPE_COUNTY_PRODUCT_GOODS:

                break;
            case NEW_TYPE_DISCOUNT_AD:

                break;
            case NEW_TYPE_EXCHANGE:

                break;
            case NEW_TYPE_OPTION:

                break;
            case NEW_TYPE_SAVE_MONEY_BUY:

                break;
            case NEW_TYPE_SMALL_LIVE:

                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    /**
     * 首页头部BannerViewHolder
     */
    class HomePagerHeadBannerViewHolder extends RecyclerView.ViewHolder {

        public HomePagerHeadBannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    /**
     * 周边店铺分类栏
     */
    class HomePageClassifyHolder extends RecyclerView.ViewHolder {

        public HomePageClassifyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bindView(HomePage home) {
        }

        @OnClick({})
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }


    /**
     * 优惠券、一县一品广告
     */
    class HomePageDiscountAdHolder extends RecyclerView.ViewHolder {

        public HomePageDiscountAdHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bindView(HomePage home) {

        }

        @OnClick({})
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }


    /**
     * 小屏直播
     */
    class HomePageSmallLiveHolder extends RecyclerView.ViewHolder {

        public HomePageSmallLiveHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView() {

        }

        @OnClick({R.id.title})
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }

    /**
     * 书瑶换货会
     */
    class HomePageExchangeHolder extends RecyclerView.ViewHolder {

        public HomePageExchangeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bindView() {

        }

        @OnClick({})
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }

    /**
     * 一县一品分类
     */
    class HomePageCountyClassifyHolder extends RecyclerView.ViewHolder {

        public HomePageCountyClassifyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(List<ImageText> concentrationSellersImageTexts) {

        }
    }

    /**
     * 一县一品商品
     */
    class HomePageCountyGoodHolder extends RecyclerView.ViewHolder {

        public HomePageCountyGoodHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HomePageLiveListViewHolder extends RecyclerView.ViewHolder {

        public HomePageLiveListViewHolder(ViewGroup parent, Context context) {
            super(LayoutInflater.from(context).inflate(R.layout.home_live_list, parent, false));
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Live live, Context context) {

        }
    }

    /**
     * 服装，母婴，居家栏
     */
    class HomePageOptionHolder extends RecyclerView.ViewHolder {

        public HomePageOptionHolder(View itemView) {
            super(itemView);

        }

    }

    /**
     * 省钱购物
     */
    class HomePageSaveMoneyHolder extends RecyclerView.ViewHolder {

        public HomePageSaveMoneyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView() {

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
}

