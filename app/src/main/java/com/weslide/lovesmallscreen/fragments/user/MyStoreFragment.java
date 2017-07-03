package com.weslide.lovesmallscreen.fragments.user;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.SellerAddressActivity;
import com.weslide.lovesmallscreen.activitys.sellerinfo.SellerHandlerBackOrderDetailActivity;
import com.weslide.lovesmallscreen.activitys.sellerinfo.SellerHandlerBackOrderListActivity;
import com.weslide.lovesmallscreen.activitys.sellerinfo.SellerOrderActivity;
import com.weslide.lovesmallscreen.activitys.sellerinfo.StayExchangeActivity;
import com.weslide.lovesmallscreen.activitys.sellerinfo.StayTakeOrderActivity;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Seller;
import com.weslide.lovesmallscreen.models.SellerInfo;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.QRCodeUtil;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.views.custom.SuperGridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/6/15.
 * 我的店铺
 */
public class MyStoreFragment extends BaseFragment {
    View mView;
    @BindView(R.id.iv_head_portrait)
    ImageView ivHeadPortrait;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.tv_store_address)
    TextView tvStoreAddress;
    @BindView(R.id.tv_today_order)
    TextView tvTodayOrder;
    @BindView(R.id.tv_store_cash)
    TextView tvStoreCash;
    @BindView(R.id.tv_look_at_order)
    TextView tvLookAtOrder;
    @BindView(R.id.tv_to_evaluate)
    TextView tvToEvaluate;
    @BindView(R.id.tv_acknowledged)
    TextView tvAcknowledged;
    @BindView(R.id.tv_have_evaluation)
    TextView tvHaveEvaluation;
    @BindView(R.id.tv_off_the_stocks)
    TextView tvOffTheStocks;
    @BindView(R.id.gv_store_layout)
    SuperGridView gvStoreLayout;
    @BindView(R.id.iv_store_two_dimension_code)
    ImageView ivStoreTwoDimensionCode;
    @BindView(R.id.tv_store_invitation_code)
    TextView tvStoreInvitationCode;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.iv_two_dimension_code)
    ImageView ivTwoDimensionCode;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    private String mUrl;
    private int[] drawint = new int[]{/*R.drawable.icon_store_redpac,
            R.drawable.icon_jifen_wode, R.drawable.icon_tixian,
            R.drawable.icon_store_dianpuzhiliao,*/ R.drawable.icon_store_address,
    };

    private String[] storeString = new String[]{
           /* "店铺红包", "商品管理", "店铺提现", "店铺资料",*/
            "店铺位置"
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_my_store, container, false);

        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        gvStoreLayout.setAdapter(storeBaseAdapter);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }

    /**
     * 适配器
     */
    private BaseAdapter storeBaseAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return drawint.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final int Localposition = position;
            if (convertView == null) {
                convertView = getActivity()
                        .getLayoutInflater()
                        .inflate(R.layout.item_user_personal_center, parent, false);
            }
            ImageView imageview = (ImageView) convertView
                    .findViewById(R.id.imageview_item_user_center);
            TextView textView = (TextView) convertView
                    .findViewById(R.id.textview_item_grid_user_center);
            imageview.setImageResource(drawint[position]); // 图片
            textView.setText(storeString[position]);

            RelativeLayout lItem = (RelativeLayout) convertView
                    .findViewById(R.id.ll_item);// 线性布局

            lItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    switch (Localposition) {
                        case 0:
                     AppUtils.toActivity(getActivity(),SellerAddressActivity.class);
                            break;
                     /*   case 1:

                            break;
                        case 2:
                            break;
                        case 3:

                            break;
                        case 4:

                            break;*/

                    }
                }
            });
            return convertView;
        }

    };

    @Override
    public void onResume() {
        super.onResume();
        getSellerInfo();

    }

    /**
     * 获取商家信息
     */
    private void getSellerInfo() {
        Request<SellerInfo> request = new Request<SellerInfo>();
        SellerInfo userInfo = new SellerInfo();
        userInfo.setSellerId(ContextParameter.getUserInfo().getSellerId());
        request.setData(userInfo);
        RXUtils.request(getActivity(), request, "getSellerInfo", new SupportSubscriber<Response<SellerInfo>>() {
            @Override
            public void onNext(Response<SellerInfo> response) {
                setData(response);

            }
        });
    }

    /**
     * 给各控件设值
     *
     * @param response
     */
    private void setData(Response<SellerInfo> response) {


        setSellerInfo(response.getData());
    }

    private void setSellerInfo(SellerInfo sellerInfo) {
        if(StringUtils.isBlank(sellerInfo.getInviteCode())){
            mUrl = ContextParameter.getClientConfig().getDownload()+"?invitecode="+"";
        }else {
            mUrl = ContextParameter.getClientConfig().getDownload() + "?invitecode=" + sellerInfo.getInviteCode();
        }
        if (!StringUtils.isBlank(sellerInfo.getSeller().getSellerName())) {
            tvStoreName.setText(sellerInfo.getSeller().getSellerName());
        } else {
            tvStoreName.setText("未设置");
        }
        if (!StringUtils.isBlank(sellerInfo.getSeller().getSellerAddress())) {
            tvStoreAddress.setText(sellerInfo.getSeller().getSellerAddress());
        } else {
            tvStoreAddress.setText("请添加地址");
        }
        if(!StringUtils.isBlank(sellerInfo.getUnavailableMoney())){//未收益
            tvTodayOrder.setText(sellerInfo.getUnavailableMoney());
        }
        if(!StringUtils.isBlank(sellerInfo.getAvailableMoney())){//已收益
            tvStoreCash.setText(sellerInfo.getAvailableMoney());
        }
        if(!StringUtils.isBlank(sellerInfo.getFansNumber())){//粉丝数量
            tvFans.setText(sellerInfo.getFansNumber()+"个粉丝");
        }
        if(!StringUtils.isBlank(sellerInfo.getInviteCode())){//邀请码
            tvStoreInvitationCode.setText(sellerInfo.getInviteCode());
        }
    }

    @OnClick({R.id.tv_to_evaluate, R.id.tv_acknowledged, R.id.tv_have_evaluation, R.id.tv_off_the_stocks,R.id.iv_two_dimension_code,R.id.iv_store_two_dimension_code,R.id.tv_look_at_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_to_evaluate:
                Bundle bundle = new Bundle();
                bundle.putString(SellerOrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_CONFIRM);
                AppUtils.toActivity(getActivity(),SellerOrderActivity.class,bundle);
                break;
            case R.id.tv_acknowledged:
                Bundle bundle1 = new Bundle();
                bundle1.putString(SellerOrderActivity.KEY_ORDER_STATUS, Constants.ORDER_STATUS_WAIT_SEND_OUT_GOODS);
                AppUtils.toActivity(getActivity(),SellerOrderActivity.class,bundle1);
                break;
            case R.id.tv_have_evaluation:
                Bundle bundle2 = new Bundle();
                bundle2.putString(SellerOrderActivity.KEY_ORDER_STATUS,Constants.ORDER_STATUS_WAIT_EXCHANGE);
                AppUtils.toActivity(getActivity(),SellerOrderActivity.class,bundle2);

                break;
            case R.id.tv_off_the_stocks:
                AppUtils.toActivity(getActivity(), SellerHandlerBackOrderListActivity.class);
                break;
            case R.id.iv_two_dimension_code:
                QRCodeUtil.scan(getActivity());
                break;
            case R.id.iv_store_two_dimension_code:
                showTwoDimensionCode(mUrl);
                break;
            case R.id.tv_look_at_order:
                AppUtils.toActivity(getActivity(),SellerOrderActivity.class);
                break;
        }
    }



    /**
     * 点击出现二维码
     */
    private void showTwoDimensionCode(String content) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LayoutInflater inflater2 = getActivity().getLayoutInflater();
        //得到界面视图
        View currean_View = inflater.inflate(R.layout.fragment_personal_center, null);
        //得到要弹出的界面视图
        View view = inflater2.inflate(R.layout.view_two_dimension_code_show, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_two_dimension_code);
        Bitmap bitmap = QRCodeUtil.createQRImage(content,300,300);
        imageView.setImageBitmap(bitmap);
        WindowManager windowManager = getActivity().getWindowManager();
        int width = windowManager.getDefaultDisplay().getWidth();
        int heigth = windowManager.getDefaultDisplay().getHeight();
        Log.i("width", width+"");
        Log.i("height", heigth+"");
        PopupWindow popupWindow = new PopupWindow(view,(int)(width*0.8),(int)(heigth*0.5));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //显示在屏幕中央
        popupWindow.showAtLocation(currean_View, Gravity.CENTER, 0, 40);
        //popupWindow弹出后屏幕半透明
        BackgroudAlpha((float)0.5);
        //弹出窗口关闭事件
        popupWindow.setOnDismissListener(new popupwindowdismisslistener());
    }
    //设置屏幕背景透明度
    private void BackgroudAlpha(float alpha) {
        // TODO Auto-generated method stub
        WindowManager.LayoutParams l = getActivity().getWindow().getAttributes();
        l.alpha = alpha;
        getActivity().getWindow().setAttributes(l);
    }
    //点击其他部分popwindow消失时，屏幕恢复透明度
    class popupwindowdismisslistener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            BackgroudAlpha((float)1);
        }
    }
}
