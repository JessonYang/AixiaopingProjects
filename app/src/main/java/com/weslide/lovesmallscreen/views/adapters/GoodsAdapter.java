package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayout;
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
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.GoodsCommentListActivity;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.fragments.goods.GoodsFragment;
import com.weslide.lovesmallscreen.models.Comment;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.bean.TeamGoodEvModel;
import com.weslide.lovesmallscreen.models.bean.TeamOrderModel;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.CalcUtils;
import com.weslide.lovesmallscreen.utils.CustomDialogUtil;
import com.weslide.lovesmallscreen.utils.Utils;
import com.weslide.lovesmallscreen.view_yy.customview.UPMarqueeView;
import com.weslide.lovesmallscreen.views.Banner;
import com.weslide.lovesmallscreen.views.adapters.viewholder.GoodsCommentItemViewHolder;
import com.weslide.lovesmallscreen.views.adapters.viewholder.GoodsCommentTitleViewHolder;
import com.weslide.lovesmallscreen.views.customview.CircleImageView;
import com.weslide.lovesmallscreen.views.dialogs.CustomDialog;
import com.weslide.lovesmallscreen.views.goods.SpecView;
import com.weslide.lovesmallscreen.views.widget.AddAndSubtractView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
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
                        bundle.putString("goodsId", goods.getGoodsId());
                        AppUtils.toActivity(mContext, GoodsCommentListActivity.class, bundle);
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
                        bundle.putString("goodsId", goodsId);
                        AppUtils.toActivity(mContext, GoodsCommentListActivity.class, bundle);
                    }
                });
                if (comment.getCommentImages() != null && comment.getCommentImages().size() > 0) {
                    commentItemViewHolder.layoutCommentImages.setVisibility(View.VISIBLE);
                    commentItemViewHolder.layoutCommentImages.removeAllViews();

                    for (String image : comment.getCommentImages()) {
                        ImageView imageView = new ImageView(mContext);
                        imageView.setPadding(10, 10, 10, 10);
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
    @BindView(R.id.rights_ll)
    LinearLayout rightsLl;
    @BindView(R.id.real_good_ll)
    LinearLayout realGoodLl;
    @BindView(R.id.send_fast_ll)
    LinearLayout sendFastLl;
    @BindView(R.id.after_careless_ll)
    LinearLayout afterCarelessLl;
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
    @BindView(R.id.score_no_charge_back)
    TextView score_no_charge_back;
    @BindView(R.id.iv_express_tactics)
    ImageView ivExpressTactics;
    @BindView(R.id.tv_sales_volume)
    TextView tvSalesVolume;
    @BindView(R.id.team_line)
    View team_line;
    @BindView(R.id.team_line2)
    View team_line2;
    @BindView(R.id.look_other_team)
    RelativeLayout look_other_team;
    @BindView(R.id.upmarquee_view)
    UPMarqueeView upmarquee_view;
    private CustomDialog teamListDialog;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd天HH时mm分ss秒");

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
                tvGoodsValue.setText(goods.getScore()+"积分");
                break;
            case 2:
                //现金商品
                tvTagRedPager.setVisibility(View.GONE);
                String value = goods.getValue();
                tvGoodsValue.setText(value);
                if (goods.isTeam()) {//拼团商品
                    String oriMoney = value.substring(value.indexOf("￥") + 1);
                    TeamGoodEvModel teamGoodEvModel = new TeamGoodEvModel();
                    teamGoodEvModel.setGoodType(Constants.TYPE_OF_TEAM);
                    String discountPrice = goods.getDiscountPrice();
                    teamGoodEvModel.setMoney(CalcUtils.doubleSub(Double.parseDouble(oriMoney),Double.parseDouble(discountPrice)));
                    teamGoodEvModel.setOriMoney(value);
                    EventBus.getDefault().post(teamGoodEvModel);
                }
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

        List<Integer> rightsProtect = mGoods.getRightsProtect();
        if (rightsProtect != null && rightsProtect.size() > 0) {
            rightsLl.setVisibility(View.VISIBLE);
            for (Integer integer : rightsProtect) {
                switch (integer) {
                    case 1:
                        realGoodLl.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        sendFastLl.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        afterCarelessLl.setVisibility(View.VISIBLE);
                        break;
                }
            }
        } else {
            rightsLl.setVisibility(View.GONE);
        }
        if ("2".equals(mGoods.getMallType()) || "scm".equals(mGoods.getMallType())) {
            score_no_charge_back.setVisibility(View.VISIBLE);
        } else {
            score_no_charge_back.setVisibility(View.GONE);
        }
        tvCostPrice.setText("￥" + mGoods.getCostPrice());
        tvExpressTactics.setText(mGoods.getExpressTactics());
        String expressStatus = mGoods.getExpressStatus();
        if (expressStatus != null && expressStatus.length() > 0) {
            if (expressStatus.equals("1")) {
                ivExpressTactics.setImageResource(R.drawable.icon_sm_qgby);
            } else if (expressStatus.equals("2")) {
                ivExpressTactics.setImageResource(R.drawable.icon_sm_ddxf);
            }
        }
        int salesVolume = mGoods.getSalesVolume();
//        String substring = salesVolume.substring(0,salesVolume.indexOf("."));
        tvSalesVolume.setText("销量:" + salesVolume);

        //设置规格等信息
        if (mGoods.getSpec() == null || mGoods.getSpec().size() == 0) {
            layoutSpec.setVisibility(View.GONE);
        } else {

            svSpec.setOnSpecItemViewSelectListener((specNote, selectKeys) -> {

                if (selectKeys != null && mGoods.getSpec().size() == selectKeys.length) {

                    Goods _goods = new Goods();
                    _goods.setPrice(specNote.getPrice());
                    _goods.setScore(specNote.getScore());
                    _goods.setTeam(mGoods.isTeam());
                    _goods.setDiscountPrice(mGoods.getDiscountPrice());
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

        if (goods.isTeam()) {
            look_other_team.setVisibility(View.VISIBLE);
            team_line.setVisibility(View.VISIBLE);
            List<View> views = new ArrayList<>();
            List<TeamOrderModel> teamOrderList = goods.getTeamOrderList();
            for (int j = 0; j < teamOrderList.size(); j = j + 2) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.vf_item, null);
                RelativeLayout ptRll = (RelativeLayout) view.findViewById(R.id.yiqipin_rll);
                CircleImageView ptUserFace1 = (CircleImageView) view.findViewById(R.id.host_pic1);
                CircleImageView ptUserFace2 = (CircleImageView) view.findViewById(R.id.host_pic2);
                TextView limitTv1 = (TextView) view.findViewById(R.id.host_title1);
                TextView limitTv2 = (TextView) view.findViewById(R.id.host_title2);
                TextView leftTv1 = (TextView) view.findViewById(R.id.host_left_time1);
                TextView leftTv2 = (TextView) view.findViewById(R.id.host_left_time2);
                RelativeLayout yiqipinTv1 = (RelativeLayout) view.findViewById(R.id.yiqipin_tv);
                TextView yiqipinTv2 = (TextView) view.findViewById(R.id.yiqipin_tv2);
                final TeamOrderModel teamOrderModel = teamOrderList.get(j);
                Glide.with(mContext).load(teamOrderModel.getUserHead()).into(ptUserFace1);
                limitTv1.setText("至少" + teamOrderModel.getSurplusNum() + "人");
                long total_second = teamOrderModel.getSurplusTime() / 1000;
                long total_minute = total_second / 60;
                long total_hour = total_minute / 60;
                long second = total_second % 60;
                long minute = total_minute % 60;
                long hour = total_hour % 24;
                long day = total_hour / 24;
                leftTv1.setText(getTime(day, hour, minute, second));
                new CountDownTimer(teamOrderModel.getSurplusTime(), 1000) {
                    @Override
                    public void onTick(long l) {
                        long total_second = l / 1000;
                        long total_minute = total_second / 60;
                        long total_hour = total_minute / 60;
                        long second = total_second % 60;
                        long minute = total_minute % 60;
                        long hour = total_hour % 24;
                        long day = total_hour / 24;
                        leftTv1.setText(getTime(day, hour, minute, second));
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
                yiqipinTv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TeamGoodEvModel teamGoodEvModel = new TeamGoodEvModel();
                        teamGoodEvModel.setGoodType(Constants.TYPE_OF_TEAM_LIST);
                        teamGoodEvModel.setTeamOrderId(teamOrderModel.getTeamOrderId());
                        teamGoodEvModel.setOrderUserId(teamOrderModel.getOrderUserId());
                        EventBus.getDefault().post(teamGoodEvModel);
                    }
                });
                if (teamOrderList.size() > j + 1) {
                    ptRll.setVisibility(View.VISIBLE);
                    final TeamOrderModel teamOrderModel1 = teamOrderList.get(j + 1);
                    Glide.with(mContext).load(teamOrderModel1.getUserHead()).into(ptUserFace2);
                    limitTv2.setText(teamOrderModel1.getSurplusNum());
                    long total_second2 = teamOrderModel.getSurplusTime() / 1000;
                    long total_minute2 = total_second / 60;
                    long total_hour2 = total_minute / 60;
                    long second2 = total_second2 % 60;
                    long minute2 = total_minute2 % 60;
                    long hour2 = total_hour2 % 24;
                    long day2 = total_hour2 / 24;
                    leftTv2.setText(getTime(day2, hour2, minute2, second2));
                    new CountDownTimer(teamOrderModel1.getSurplusTime(), 1000) {
                        @Override
                        public void onTick(long l) {
                            long total_second = l / 1000;
                            long total_minute = total_second / 60;
                            long total_hour = total_minute / 60;
                            long second = total_second % 60;
                            long minute = total_minute % 60;
                            long hour = total_hour % 24;
                            long day = total_hour / 24;
                            leftTv2.setText(getTime(day, hour, minute, second));
                        }

                        @Override
                        public void onFinish() {

                        }
                    }.start();
                    ptRll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TeamGoodEvModel teamGoodEvModel = new TeamGoodEvModel();
                            teamGoodEvModel.setGoodType(Constants.TYPE_OF_TEAM_LIST);
                            teamGoodEvModel.setTeamOrderId(teamOrderModel1.getTeamOrderId());
                            teamGoodEvModel.setOrderUserId(teamOrderModel.getOrderUserId());
                            EventBus.getDefault().post(teamGoodEvModel);
                        }
                    });
                } else {
                    ptRll.setVisibility(View.GONE);
                }
                views.add(view);
            }
            upmarquee_view.setViews(views);

        } else {
            look_other_team.setVisibility(View.GONE);
            team_line.setVisibility(View.GONE);
            upmarquee_view.setVisibility(View.GONE);
            team_line2.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.btn_seller, R.id.look_other_team})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_seller:
                AppUtils.toSeller(mContext, mGoods.getSeller().getSellerId());
                break;
            case R.id.look_other_team:
                List<TeamOrderModel> list = mGoods.getTeamOrderList();
                if (list != null && list.size() > 0) {
                    TeamListAdapter teamListAdapter = new TeamListAdapter(mContext, list);
                    teamListDialog = CustomDialogUtil.showCustomDialog(mContext, R.style.customDialogStyle, R.layout.pt_list_dialog, 270, 350, teamListAdapter, R.id.pt_dialog_lv, mGoods.getTeamOrderList(), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            teamListDialog.dismiss();
                        }
                    }, R.id.btn_cal);
                } else {
                    CustomDialogUtil.showNoticDialog(mContext, "还没有其他小伙伴发起此商品的拼团哦!您可以单独购买或发起团购拼团活动。");
                }
                break;
        }
    }

    private String getTime(long day, long hour, long minute, long second) {
        return "剩余:" + String.format("%02d", day) + "天" + String.format("%02d", hour) + "时" + String.format("%02d", minute) + "分" + String.format("%02d", second) + "秒";
    }

}
