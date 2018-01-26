package com.weslide.lovesmallscreen.exchange.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.model_yy.javabean.ExchangeGoodDtModel;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.view_yy.customview.GlideImageLoader;
import com.weslide.lovesmallscreen.views.adapters.SimpleViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

/**
 * Created by YY on 2018/1/24.
 */
public class ExchangeGoodDtAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel,RecyclerView.ViewHolder> {

    public static final int BANNER_TYPE = 0;//轮播图
    public static final int GOOD_INFO_TYPE = 1;//商品信息
    public static final int NOTE_TYPE = 2;//想换标签
    public static final int GOOD_DESCRIPTION_TYPE = 3;//商品描述
    public static final int LOOK_MORE_TYPE = 4;//查看更多
    private final Context mContext;
    private LayoutInflater inflater;

    public ExchangeGoodDtAdapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            //轮播图
            case BANNER_TYPE:
                holder = new BannerHolder(inflater.inflate(R.layout.exchange_good_dt_banner_item,parent,false));
                break;
            //商品信息
            case GOOD_INFO_TYPE:
                holder = new GoodsInfoHolder(inflater.inflate(R.layout.exchange_good_dt_goods_info_item,parent,false));
                break;
            //想换标签
            case NOTE_TYPE:
                holder = new GoodsNoteHolder(inflater.inflate(R.layout.exchange_good_dt_good_note_item,parent,false));
                break;
            //商品描述
            case GOOD_DESCRIPTION_TYPE:
                holder = new GoodsDescHolder(inflater.inflate(R.layout.exchange_good_dt_good_desc_item,parent,false));
                break;
            //商品描述
            case LOOK_MORE_TYPE:
                holder = new SimpleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.goods_view_load_detials, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            //轮播图
            case BANNER_TYPE:
                ((BannerHolder) holder).startBanner((ExchangeGoodDtModel) mList.get(position).getData());
                break;
            //商品信息
            case GOOD_INFO_TYPE:
                ((GoodsInfoHolder) holder).bindView((ExchangeGoodDtModel) mList.get(position).getData());
                break;
            //想换标签
            case NOTE_TYPE:
                ((GoodsNoteHolder) holder).bindView((ExchangeGoodDtModel) mList.get(position).getData());
                break;
            //商品描述
            case GOOD_DESCRIPTION_TYPE:
                ((GoodsDescHolder) holder).bindView((ExchangeGoodDtModel) mList.get(position).getData());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }

    class BannerHolder extends RecyclerView.ViewHolder{

        private Banner banner;

        public BannerHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.good_dt_banner);
        }

        public void startBanner(ExchangeGoodDtModel goodDtModel) {
            banner.setImageLoader(new GlideImageLoader());
            banner.isAutoPlay(false);
            banner.setImages(goodDtModel.getCoverPic());
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.start();
            Log.d("雨落无痕丶", "bannerSize: "+goodDtModel.getCoverPic().size());
        }
    }

    class GoodsInfoHolder extends RecyclerView.ViewHolder{

        private TextView goodDtName,goodDtPrice,goodDtAddress,goodDtDealWay;

        public GoodsInfoHolder(View itemView) {
            super(itemView);
            goodDtName = (TextView) itemView.findViewById(R.id.good_dt_name);
            goodDtPrice = (TextView) itemView.findViewById(R.id.good_dt_price);
            goodDtAddress = (TextView) itemView.findViewById(R.id.good_dt_address);
            goodDtDealWay = (TextView) itemView.findViewById(R.id.good_dt_exchange_way);
        }

        public void bindView(ExchangeGoodDtModel goodDtModel) {
            goodDtName.setText(goodDtModel.getGoodsName());
            goodDtPrice.setText("市场价:  " + goodDtModel.getDisplayPrice());
            goodDtAddress.setText(goodDtModel.getSellerAddress());
            goodDtDealWay.setText(goodDtModel.getTransportationType());
        }
    }

    class GoodsNoteHolder extends RecyclerView.ViewHolder{

        private TextView wantNote1,wantNote2,resumeCount,wantTag;

        public GoodsNoteHolder(View itemView) {
            super(itemView);
            wantNote1 = (TextView) itemView.findViewById(R.id.want_note1);
            wantNote2 = (TextView) itemView.findViewById(R.id.want_note2);
            resumeCount = (TextView) itemView.findViewById(R.id.resume_count);
            wantTag = (TextView) itemView.findViewById(R.id.want_tag);
        }

        public void bindView(ExchangeGoodDtModel goodDtModel) {
            List<String> want = goodDtModel.getWant();
            if (want != null && want.size() > 0) {
                wantTag.setVisibility(View.VISIBLE);
                for (int i = 0; i < want.size(); i++) {
                    if (i == 0) {
                        wantNote1.setVisibility(View.VISIBLE);
                        wantNote1.setText(want.get(i));
                    } else if (i == 1) {
                        wantNote2.setVisibility(View.VISIBLE);
                        wantNote2.setText(want.get(i));
                    }
                }
            }else {
                wantTag.setVisibility(View.GONE);
            }
            resumeCount.setText(goodDtModel.getPageView()+"");
        }
    }

    class GoodsDescHolder extends RecyclerView.ViewHolder{

        private TextView goodDtDesc,goodDtType,goodDtNum,goodDtIntroduce;

        public GoodsDescHolder(View itemView) {
            super(itemView);
            goodDtDesc = (TextView) itemView.findViewById(R.id.good_dt_desc);
            goodDtType = (TextView) itemView.findViewById(R.id.good_dt_type);
            goodDtNum = (TextView) itemView.findViewById(R.id.good_dt_num);
            goodDtIntroduce = (TextView) itemView.findViewById(R.id.good_dt_introduce);
        }

        public void bindView(ExchangeGoodDtModel goodDtModel){
            goodDtDesc.setText(goodDtModel.getChangeDesc());
        }
    }
}
