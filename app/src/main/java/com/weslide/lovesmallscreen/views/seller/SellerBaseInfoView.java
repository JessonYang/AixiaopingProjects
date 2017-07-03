package com.weslide.lovesmallscreen.views.seller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.ImageText;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.APIUtils;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.MapUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.views.FixedGridView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/12.
 * 商家主要信息展示
 */
public class SellerBaseInfoView extends FrameLayout {
    @BindView(R.id.iv_seller_icon)
    ImageView ivSellerIcon;
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.tv_shopHours)
    TextView tvShopHours;
    @BindView(R.id.cb_concern)
    CheckBox cbConcern;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_seller_address)
    TextView tvSellerAddress;
    @BindView(R.id.layout_address_and_phone)
    LinearLayout layoutAddressAndPhone;
    @BindView(R.id.tv_branchs_text)
    TextView tvBranchsText;
    @BindView(R.id.gv_preferentials)
    FixedGridView gvPreferentials;
    @BindView(R.id.layout_branchs)
    RelativeLayout layoutBranchs;

    @BindString(R.string.seller_address)
    String addressText;
    @BindString(R.string.seller_shopHours)
    String shopHoursText;

    Seller mSeller;
    @BindView(R.id.tv_seller_introduce)
    TextView tvSellerIntroduce;
    @BindView(R.id.iv_seller_phone)
    ImageView ivSellerPhone;
    @BindView(R.id.ll_seller_introduce)
    LinearLayout llSellerIntroduce;

    public SellerBaseInfoView(Context context) {
        super(context);
        initView();
    }

    public SellerBaseInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SellerBaseInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.seller_view_base_info, this, true);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_seller_phone, R.id.layout_branchs, R.id.tv_distance, R.id.tv_seller_address})
    public void onClick(View view) {
        if (mSeller == null) return;

        switch (view.getId()) {
            case R.id.iv_seller_phone:
                if (StringUtils.isEmpty(mSeller.getSellerPhone())) {
                    T.showShort(getContext(), "该商家还没有填写手机号码");
                    return;
                }
                AppUtils.toCallPhone(getContext(), mSeller.getSellerPhone());
                break;
            case R.id.layout_branchs:
                T.showShort(getContext(), "分店功能待完善");
                break;
            case R.id.tv_distance:
            case R.id.tv_seller_address:
                MapUtils.showNav((BaseActivity) getContext(), new LatLng(Double.parseDouble(mSeller.getLat()), Double.parseDouble(mSeller.getLng())));
                break;
        }
    }

    private boolean bind = false;

    @OnCheckedChanged(R.id.cb_concern)
    public void onCheckedChanged() {

        if (bind) {
            return;
        }

        APIUtils.concernSeller(getContext(), mSeller.getSellerId(), cbConcern.isChecked(), new SupportSubscriber<Response>() {

            private void setCheckValue() {
                bind = true;
                cbConcern.setChecked(!cbConcern.isChecked());
                bind = false;
            }

            @Override
            public void onNoNetwork() {
                super.onNoNetwork();
                T.showShort(getContext(), "请链接网络~");
                setCheckValue();
            }

            @Override
            public void onResponseError(Response response) {
                super.onResponseError(response);
                T.showShort(getContext(), "未知错误~");
                setCheckValue();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                T.showShort(getContext(), "未知错误~");
                setCheckValue();
            }

            @Override
            public void onNext(Response response) {
            }
        });

    }

    public void show(Seller seller) {
        mSeller = seller;
        Glide.with(getContext()).load(seller.getSellerIcon()).into(ivSellerIcon);
        tvSellerName.setText(seller.getSellerName());
        tvShopHours.setText(shopHoursText + seller.getShopHours());
        bind = true;
        cbConcern.setChecked(seller.getConcern());
        bind = false;
        if (StringUtils.isBlank(seller.getSellerAddress())){
            tvSellerAddress.setText(addressText + "");
        }else {
            tvSellerAddress.setText(addressText + seller.getSellerAddress());
        }
        if (StringUtils.isBlank(seller.getSellerIntroduce())) {
            llSellerIntroduce.setVisibility(View.GONE);
        } else {
            llSellerIntroduce.setVisibility(View.VISIBLE);
            tvSellerIntroduce.setText(seller.getSellerIntroduce());
        }
        if (seller.getBranchs() == null || seller.getBranchs().size() == 0) {
            layoutBranchs.setVisibility(View.GONE);
        } else {
            tvBranchsText.setText("查看全部" + seller.getBranchs().size() + "家分店");
        }

        //百度地图距离计算
        tvDistance.setText(ContextParameter.getDistanceForCurrentLocationAddUnit(mSeller));
        gvPreferentials.setAdapter(new PerentialsAdapter(getContext(), seller.getPreferentials()));

    }


    /**
     * 展示优惠政策
     */
    class PerentialsAdapter extends BaseAdapter {

        Context mContext;
        List<ImageText> mList;

        public PerentialsAdapter(Context context, List<ImageText> list) {
            mContext = context;
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.seller_view_perentials_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ImageText imageText = mList.get(position);

            Glide.with(mContext).load(imageText.getImage()).into(holder.ivPerentiasImage);
            holder.tvPerentiasName.setText(imageText.getName());

            return view;
        }

        class ViewHolder {
            @BindView(R.id.iv_perentias_image)
            ImageView ivPerentiasImage;
            @BindView(R.id.tv_perentias_name)
            TextView tvPerentiasName;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
