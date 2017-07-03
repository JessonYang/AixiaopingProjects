package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.GoodsClassifiActivity;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.fragments.mall.SecondMallFragment;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsType;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.Utils;
import com.weslide.lovesmallscreen.views.dialogs.SecondaryClassificationDialog;
import com.weslide.lovesmallscreen.views.widget.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/10/21.
 */
public class SecondMallAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {
     SecondMallFragment fragment;
    GetGoodsListBean request = new GetGoodsListBean();;
    ClassifiGoodsTypeAdapterViewHolder mViewHolder ;
    String typeTd;
    /**
     * 商品分类
     */
    public static final int TYPE_GOODS_CLASSIFYS = 1;

    /**
     * 分类
     */

    public static final int TYPE_GOODS_TYPE = 2;

    /**
     * 商品
     */
    public static final int TYPE_GOODS_LIST = 3;

    /**
     *商品列表
     */
    public SecondMallAdapter(Context context, DataList<RecyclerViewModel> dataList,SecondMallFragment fragment) {
        super(context, dataList);
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case TYPE_GOODS_CLASSIFYS:
                viewHolder = new SecondGoodsClassifyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_view_goods_classify, parent, false));
                break;
            case TYPE_GOODS_TYPE:
                viewHolder = new ClassifiGoodsTypeAdapterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_dressing_by_screening, parent, false));
                break;
            case TYPE_GOODS_LIST:
                viewHolder = new ClassifiGoodsAdapterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_goods_grid, parent, false));
                break;

        }
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)){
            case TYPE_GOODS_CLASSIFYS:  //商品分类

                if (mList.get(position).isFirst()) {
                    mList.get(position).setFirst(false);
                    List<ImageText> imageTexts = (List<ImageText>) mList.get(position).getData();
                    ((SecondGoodsClassifyViewHolder) holder).bindView(imageTexts);
                }

                break;

            case TYPE_GOODS_TYPE:
                int top = 204;
                List<GoodsType> types = (List<GoodsType>) mList.get(position).getData();
                mViewHolder = ((ClassifiGoodsTypeAdapterViewHolder)holder);
                if(request.getValue().equals("0")){
                    ((ClassifiGoodsTypeAdapterViewHolder)holder).tvValue.setText("价格");
                }
                if(request.getSalesVolume().equals("0")){
                    ((ClassifiGoodsTypeAdapterViewHolder)holder).tvVolume.setText("销量");
                }
                ((ClassifiGoodsTypeAdapterViewHolder)holder).flClassifi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SecondaryClassificationDialog dialog = new SecondaryClassificationDialog(mContext, types, top);
                        dialog.setOnClassificationSelectListener(new ClassificationSelectListener());
                        dialog.show();
                    }});
                ((ClassifiGoodsTypeAdapterViewHolder)holder).flVolume.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        request.setTypeId(typeTd);
                        request.setValue("0");
                        ((ClassifiGoodsTypeAdapterViewHolder)holder).tvValue.setText("价格");

                        if (request.getSalesVolume().equals("0") || request.getSalesVolume().equals("1")) {
                            request.setSalesVolume("2");
                            ((ClassifiGoodsTypeAdapterViewHolder)holder).tvVolume.setText("从低到高");
                        } else if (request.getSalesVolume().equals("2")) {
                            request.setSalesVolume("1");
                            ((ClassifiGoodsTypeAdapterViewHolder)holder).tvVolume.setText("从高到低");
                        }
                        fragment.regetGoodsList(request);
                    }
                });
                ((ClassifiGoodsTypeAdapterViewHolder)holder).flValue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        request.setTypeId(typeTd);
                        request.setSalesVolume("0");
                        ((ClassifiGoodsTypeAdapterViewHolder)holder).tvVolume.setText("销量");

                        if (request.getValue().equals("0") || request.getValue().equals("1")) {
                            request.setValue("2");
                            ((ClassifiGoodsTypeAdapterViewHolder)holder).tvValue.setText("从低到高");
                        } else if (request.getValue().equals("2")) {
                            request.setValue("1");
                            ((ClassifiGoodsTypeAdapterViewHolder)holder).tvValue.setText("从高到低");
                        }
                        fragment.regetGoodsList(request);
                    }
                });

                break;
            case TYPE_GOODS_LIST:
                Goods goods =(Goods)mList.get(position).getData();

                Glide.with(mContext).load(goods.getCoverPic()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(((ClassifiGoodsAdapterViewHolder)holder).ivGoodsImage);
                ((ClassifiGoodsAdapterViewHolder)holder).tvGoodsCostPrice.setText("￥" + goods.getCostPrice());
                ((ClassifiGoodsAdapterViewHolder)holder).tvGoodsName.setText(goods.getName());
                ((ClassifiGoodsAdapterViewHolder)holder).tvGoodsPrice.setText(goods.getValue());

                //分类界面用到的是距离， 销量只有商品详情界面用到
                ((ClassifiGoodsAdapterViewHolder)holder).tvLocaiont.setVisibility(View.VISIBLE);
                ((ClassifiGoodsAdapterViewHolder)holder).tvSalesVolume.setVisibility(View.GONE);

                ((ClassifiGoodsAdapterViewHolder)holder).tvLocaiont.setText(ContextParameter.getDistanceForCurrentLocationAddUnit(goods.getSeller().getLat(), goods.getSeller().getLng()));
                ((ClassifiGoodsAdapterViewHolder)holder).expressTactics.setText(goods.getExpressTactics());

                holder.itemView.setOnClickListener(v -> AppUtils.toGoods(mContext, goods.getGoodsId()));
                break;
        }
    }
    @Override
    public int getItemViewType(int position) {
        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;
    }

    /**
     * 商品分类
     */
    class SecondGoodsClassifyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.gv_gride)
        GridView mGrideView;
        Context mContext;

        public SecondGoodsClassifyViewHolder(View itemView) {
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
                    view.setFocusable(false);
                    ImageView imageView = (ImageView) view.findViewById(R.id.iv_select);
                    if(position == 0){
                        imageView.setVisibility(View.VISIBLE);
                    }
              /*              view.setOnClickListener(v -> {
                        //跳转至商品列表
                      //  Intent intent = new Intent(mContext, GoodsClassifiActivity.class);
                        GetGoodsListBean request = new GetGoodsListBean();
                        request.setTypeId(imageTexts.get(position).getTypeId());
                        typeTd = imageTexts.get(position).getTypeId();
                        request.setMallTyle(Constants.MALL_STAND_ALONE);
                        fragment.regetGoodsList(request);

                    });*/
                    TextView tv = (TextView) view.findViewById(R.id.tv_name);
                    ImageView iv = (ImageView) view.findViewById(R.id.iv_icon);
                    tv.setFocusable(false);
                    iv.setFocusable(false);
                    imageView.setFocusable(false);
                    ((TextView) view.findViewById(R.id.tv_name)).setText(imageTexts.get(position).getName());
                    Glide.with(mContext).load(imageTexts.get(position).getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into((ImageView) view.findViewById(R.id.iv_icon));
                    return view;
                }

            });
            mGrideView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    GetGoodsListBean requests = new GetGoodsListBean();
                    requests.setTypeId(imageTexts.get(position).getTypeId());
                    typeTd = imageTexts.get(position).getTypeId();
                    requests.setMallTyle(Constants.MALL_STAND_ALONE);
                    fragment.regetGoodsList(requests);
                    request.setSalesVolume("0");
                    request.setValue("0");
                    for(int i=0;i<adapterView.getCount();i++){
                        ImageView item = (ImageView) adapterView.getChildAt(i).findViewById(R.id.iv_select);
                        if (position == i) {//当前选中的Item改变背景颜色
                            item.setVisibility(View.VISIBLE);
                        } else {
                            item.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
    }

    class ClassifiGoodsAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_goods_image)
        SquareImageView ivGoodsImage;
        @BindView(R.id.expressTactics)
        TextView expressTactics;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_costPrice)
        TextView tvGoodsCostPrice;
        @BindView(R.id.tv_sales_volume)
        TextView tvSalesVolume;
        @BindView(R.id.tv_locaiont)
        TextView tvLocaiont;

        public ClassifiGoodsAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            Utils.strikethrough(tvGoodsCostPrice);
        }
    }
    class ClassifiGoodsTypeAdapterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.layout_all_classifi)
        FrameLayout flClassifi;
        @BindView(R.id.layout_sales_volume)
        FrameLayout flVolume;
        @BindView(R.id.layout_value)
        FrameLayout flValue;
        @BindView(R.id.tv_all_classifi)
        TextView tvClassifi;
        @BindView(R.id.tv_sales_volume)
        TextView tvVolume;
        @BindView(R.id.tv_value)
        TextView tvValue;
        @BindView(R.id.layout_classification)
        LinearLayout layoutClassification;
        public ClassifiGoodsTypeAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
    class ClassificationSelectListener implements SecondaryClassificationDialog.OnClassificationSelectListener {
        @Override
        public void select(GoodsType type) {
            mViewHolder.tvValue.setText("价格");
            mViewHolder.tvVolume.setText("销量");
            typeTd = type.getTypeId();
            request.setTypeId(type.getTypeId());
            request.setMallTyle(Constants.MALL_STAND_ALONE);
            fragment.regetGoodsList(request);

        }
    }
}
