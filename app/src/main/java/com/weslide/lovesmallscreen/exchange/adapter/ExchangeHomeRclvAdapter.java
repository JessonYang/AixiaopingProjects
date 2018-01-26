package com.weslide.lovesmallscreen.exchange.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.squareup.picasso.Picasso;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.URIResolve;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.exchange.helper.DateFormatUtil;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.bean.ExchangeGoodModel;
import com.weslide.lovesmallscreen.models.bean.ExchangeReplyModel;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.view_yy.customview.GlideImageLoader;
import com.weslide.lovesmallscreen.view_yy.customview.RecyclerViewDivider;
import com.weslide.lovesmallscreen.views.customview.CircleImageView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by YY on 2018/1/20.
 */
public class ExchangeHomeRclvAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {

    private final Context mContext;
    private LayoutInflater inflater;
    private final int BANNER_TYPE = 0;
    private final int GOOD_TYPE = 1;
    private final int REPLY_TYPE = 2;
    private static final int MORE_TYPE = 3;
    private static final int REPLY_TITLE_TYPE = 4;

    public ExchangeHomeRclvAdapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            //轮播图
            case BANNER_TYPE:
                viewHolder = new BannerHolder(inflater.inflate(R.layout.exchange_banner_item, parent, false));
                break;
            //商品
            case GOOD_TYPE:
                viewHolder = new GoodsHolder(inflater.inflate(R.layout.exchange_goods_item, parent, false));
                break;
            //查看更多
            case MORE_TYPE:
                viewHolder = new GoodsMoreHolder(inflater.inflate(R.layout.more_goods_item, parent, false));
                break;
            //帖子标题
            case REPLY_TITLE_TYPE:
                viewHolder = new ReplyTitleHolder(inflater.inflate(R.layout.reply_title_item, parent, false));
                break;
            //帖子
            case REPLY_TYPE:
                viewHolder = new ReplyHolder(inflater.inflate(R.layout.reply_item, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            //轮播图
            case BANNER_TYPE:
                ((BannerHolder) holder).bindView((List<ImageText>) mList.get(position).getData());
                break;
            //商品
            case GOOD_TYPE:
                ((GoodsHolder) holder).bindView((ExchangeGoodModel) mList.get(position).getData());
                break;
            //查看更多
            case MORE_TYPE:

                break;
            //帖子标题
            case REPLY_TITLE_TYPE:
                ((ReplyTitleHolder) holder).bindView((String) mList.get(position).getData());
                break;
            //帖子
            case REPLY_TYPE:
                ((ReplyHolder) holder).bindView((ExchangeReplyModel) mList.get(position).getData());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = super.getItemViewType(position);
        return itemViewType == 0 ? mList.get(position).getItemType() : itemViewType;
    }

    public boolean isGood(int pos) {
        return getItemViewType(pos) == GOOD_TYPE;
    }

    class BannerHolder extends RecyclerView.ViewHolder {

        private Banner banner;

        public BannerHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.exchange_banner);
        }

        public void bindView(List<ImageText> bannerImgs) {
            banner.setImageLoader(new GlideImageLoader());
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setIndicatorGravity(BannerConfig.CENTER);
            banner.setImages(bannerImgs);
            banner.setDelayTime(2500);
            banner.startAutoPlay();
            banner.start();
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    URIResolve.resolve(mContext, bannerImgs.get(position).getUri());
                }
            });
        }
    }

    class GoodsHolder extends RecyclerView.ViewHolder {

        private final ImageView goodIv;
        private final LinearLayout noteLl, goodViewLl;
        private final TextView goodName, goodPrice, noteTv1, noteTv2;

        public GoodsHolder(View itemView) {
            super(itemView);
            goodIv = (ImageView) itemView.findViewById(R.id.iv_goods_image);
            goodName = (TextView) itemView.findViewById(R.id.tv_goods_name);
            goodPrice = (TextView) itemView.findViewById(R.id.tv_goods_price);
            noteTv1 = (TextView) itemView.findViewById(R.id.note_tv1);
            noteTv2 = (TextView) itemView.findViewById(R.id.note_tv2);
            noteLl = (LinearLayout) itemView.findViewById(R.id.note_ll);
            goodViewLl = (LinearLayout) itemView.findViewById(R.id.good_view_ll);
        }

        public void bindView(ExchangeGoodModel goodModel) {
            Glide.with(mContext).load(goodModel.getCoverPic()).into(goodIv);
            goodName.setText(goodModel.getName());
            goodPrice.setText("市场价:￥" + goodModel.getPrice());
            List<String> wantlable = goodModel.getWantlable();
            if (wantlable != null && wantlable.size() > 0) {
                noteTv1.setVisibility(View.VISIBLE);
                noteTv1.setText(wantlable.get(0));
                if (wantlable.size() > 1) {
                    noteTv2.setVisibility(View.VISIBLE);
                    noteTv2.setText(wantlable.get(1));
                }
            }
            goodViewLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.toExchangeGoods(mContext, goodModel.getGoodsOrder());
                }
            });
        }

    }

    class GoodsMoreHolder extends RecyclerView.ViewHolder {

        public GoodsMoreHolder(View itemView) {
            super(itemView);
            ((RelativeLayout) itemView.findViewById(R.id.more_rll)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

    }

    class ReplyTitleHolder extends RecyclerView.ViewHolder {

        private final ImageView replyTitleIv;

        public ReplyTitleHolder(View itemView) {
            super(itemView);
            replyTitleIv = ((ImageView) itemView.findViewById(R.id.reply_title_iv));
        }

        public void bindView(String replyTitleUrl) {
//            Glide.with(mContext).load(replyTitleUrl).error(R.drawable.sy_yhqbanner).into(replyTitleIv);
            Picasso.with(mContext).load(replyTitleUrl).into(replyTitleIv);
        }

    }

    class ReplyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final CircleImageView userFaceIv;
        private final RecyclerView goodPicRclv;
        private final TextView userNameTv, addressTv, dateTv, goodDescTv;
        private final LinearLayout goodExchange, scoreExchange, connectLl;
        private ReplyGoodImgRclvAdapter mReplyGoodAdapter;

        public ReplyHolder(View itemView) {
            super(itemView);
            userFaceIv = ((CircleImageView) itemView.findViewById(R.id.user_face_iv));
            userNameTv = ((TextView) itemView.findViewById(R.id.user_name_tv));
            addressTv = ((TextView) itemView.findViewById(R.id.address_tv));
            dateTv = ((TextView) itemView.findViewById(R.id.date_tv));
            goodDescTv = ((TextView) itemView.findViewById(R.id.good_desc_tv));
            connectLl = ((LinearLayout) itemView.findViewById(R.id.connect_ll));
            goodExchange = ((LinearLayout) itemView.findViewById(R.id.good_exchange));
            scoreExchange = ((LinearLayout) itemView.findViewById(R.id.score_exchange));
            goodPicRclv = ((RecyclerView) itemView.findViewById(R.id.good_pic_rclv));
        }

        public void bindView(ExchangeReplyModel model) {
            Glide.with(mContext).load(model.getHeadImg()).into(userFaceIv);
            userNameTv.setText(model.getUserName());
            addressTv.setText(model.getAddress());
            dateTv.setText(DateFormatUtil.formatDateShow(model.getTopTime(), System.currentTimeMillis()));
            goodDescTv.setText(model.getContent());
            List<ImageText> goodsImgs = model.getGoodsImgs();
            if (goodsImgs != null && goodsImgs.size() > 0) {
//                goodExchange.setVisibility(View.VISIBLE);
//                scoreExchange.setVisibility(View.VISIBLE);
                goodPicRclv.setVisibility(View.VISIBLE);
                mReplyGoodAdapter = new ReplyGoodImgRclvAdapter(mContext, goodsImgs);
                goodPicRclv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                goodPicRclv.setAdapter(mReplyGoodAdapter);
                goodPicRclv.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL, DensityUtils.dp2px(mContext, 10), Color.parseColor("#ffffff")));

            } else {
//                goodExchange.setVisibility(View.GONE);
//                scoreExchange.setVisibility(View.GONE);
                goodPicRclv.setVisibility(View.GONE);
            }
            connectLl.setTag(model);
            connectLl.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //联系他
                case R.id.connect_ll:
                    RongIM.getInstance().startConversation(mContext, Conversation.ConversationType.PRIVATE, ((ExchangeReplyModel) view.getTag()).getReplyUserId(), ((ExchangeReplyModel) view.getTag()).getUserName());
                    break;
            }
        }
    }
}
