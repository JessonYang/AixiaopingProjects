package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.models.FeatureTypeModel;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.LivemoduleModel;
import com.weslide.lovesmallscreen.models.NfcpModel;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.SqgwModel;
import com.weslide.lovesmallscreen.models.TopClassifyModel;
import com.weslide.lovesmallscreen.models.TopLocalProductModel;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.view_yy.adapter.viewholder.HomeBinnerHolder;
import com.weslide.lovesmallscreen.view_yy.adapter.viewholder.HomeBottomImgHolder;
import com.weslide.lovesmallscreen.view_yy.adapter.viewholder.HomeLiveHolder;
import com.weslide.lovesmallscreen.view_yy.adapter.viewholder.HomeNearStoreHolder;
import com.weslide.lovesmallscreen.view_yy.adapter.viewholder.HomeSaveMoneyHolder;
import com.weslide.lovesmallscreen.view_yy.adapter.viewholder.HomeSellerListHolder;
import com.weslide.lovesmallscreen.view_yy.adapter.viewholder.HomeSpecialStoreHolder;
import com.weslide.lovesmallscreen.view_yy.adapter.viewholder.HomeTopLocalHolder;

import java.util.List;

/**
 * Created by YY on 2017/11/27.
 */
public class HomeMainRcAdapter extends SuperRecyclerViewAdapter<RecyclerViewModel, RecyclerView.ViewHolder> {

    //首页adapter的item类型
    public static final int HOME_BINNER_TYPE = 0;
    public static final int HOME_NEAR_STORE_TYPE = 1;
    public static final int HOME_SAVE_MONEY_SHOPPING_TYPE = 2;
    public static final int HOME_TOP_LOCAL_TYPE = 3;
    public static final int HOME_SPECIAL_MALL_TYPE = 4;
    public static final int HOME_LIVE_TYPE = 5;
    public static final int HOME_BOTTOM_IMG_TYPE = 6;
    public static final int HOME_NEAR_STORE_LIST_TITLE_TYPE = 7;
    public static final int HOME_NEAR_STORE_LIST_TYPE = 8;
    private Context mContext;
    private LayoutInflater inflater;

    public HomeMainRcAdapter(Context context, DataList<RecyclerViewModel> dataList) {
        super(context, dataList);
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case HOME_BINNER_TYPE:
                viewHolder = new HomeBinnerHolder(mContext, inflater.inflate(R.layout.home_fragment_binner_item, parent, false));
                break;
            case HOME_NEAR_STORE_TYPE:
                viewHolder = new HomeNearStoreHolder(mContext, inflater.inflate(R.layout.home_fragment_near_store_item, parent, false));
                break;
            case HOME_SAVE_MONEY_SHOPPING_TYPE:
                viewHolder = new HomeSaveMoneyHolder(mContext, inflater.inflate(R.layout.home_fragment_save_money_item, parent, false));
                break;
            case HOME_TOP_LOCAL_TYPE:
                viewHolder = new HomeTopLocalHolder(mContext, inflater.inflate(R.layout.home_fragment_toplocal_item, parent, false));
                break;
            case HOME_SPECIAL_MALL_TYPE:
                viewHolder = new HomeSpecialStoreHolder(mContext, inflater.inflate(R.layout.home_fragment_special_mall_item, parent, false));
                break;
            case HOME_LIVE_TYPE:
                viewHolder = new HomeLiveHolder(mContext, inflater.inflate(R.layout.home_fragment_live_item, parent, false));
                break;
            case HOME_BOTTOM_IMG_TYPE:
                viewHolder = new HomeBottomImgHolder(mContext, inflater.inflate(R.layout.home_fragment_bottom_img_item, parent, false));
                break;
            case HOME_NEAR_STORE_LIST_TITLE_TYPE:

                break;
            case HOME_NEAR_STORE_LIST_TYPE:
                viewHolder = new HomeSellerListHolder(mContext, inflater.inflate(R.layout.home_view_seller_item, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            //轮播图
            case HOME_BINNER_TYPE:
                List<ImageText> data = (List<ImageText>) mList.get(position).getData();
                ((HomeBinnerHolder) holder).oprateView(data);
                break;
            //周边店铺状态栏
            case HOME_NEAR_STORE_TYPE:
                ((HomeNearStoreHolder) holder).oprateView((TopClassifyModel) mList.get(position).getData());
                break;
            //省钱购物
            case HOME_SAVE_MONEY_SHOPPING_TYPE:
                ((HomeSaveMoneyHolder) holder).oprateView((SqgwModel) mList.get(position).getData());
                break;
            //一县一品
            case HOME_TOP_LOCAL_TYPE:
                ((HomeTopLocalHolder) holder).oprateView((TopLocalProductModel) mList.get(position).getData());
                break;
            //特产商城
            case HOME_SPECIAL_MALL_TYPE:
                ((HomeSpecialStoreHolder) holder).oprateView((FeatureTypeModel) mList.get(position).getData());
                break;
            //直播
            case HOME_LIVE_TYPE:
                ((HomeLiveHolder) holder).oprateView((LivemoduleModel) mList.get(position).getData());
                break;
            //底部展示图
            case HOME_BOTTOM_IMG_TYPE:
                ((HomeBottomImgHolder) holder).oprateView((NfcpModel) mList.get(position).getData());
                break;
            case HOME_NEAR_STORE_LIST_TITLE_TYPE:

                break;
            //商家店铺列表
            case HOME_NEAR_STORE_LIST_TYPE:
                ((HomeSellerListHolder) holder).oprateView((Seller) mList.get(position).getData());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int superType = super.getItemViewType(position);
        return superType == 0 ? mList.get(position).getItemType() : superType;
    }

}
