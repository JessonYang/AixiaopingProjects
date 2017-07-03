package com.weslide.lovesmallscreen.fragments.goods;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.widget.VerticalSlide;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.bean.CreateTempOrderListBean;
import com.weslide.lovesmallscreen.models.bean.ShoppingCarBean;
import com.weslide.lovesmallscreen.models.config.ShareContent;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateShoppingCarMessage;
import com.weslide.lovesmallscreen.network.HTTP;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.APIUtils;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.OrderUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.ShareUtils;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.weslide.lovesmallscreen.utils.UserUtils;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/15.
 * 商品信息Fragment
 */
public class GoodsFragment extends BaseFragment {

    /**
     * 秒杀已开始
     */
    public static final String KEY_SECOND_KILL_START = "KEY_SECOND_KILL_NO_START";
    boolean secondKillStart = true;

    View mView;
    @BindView(R.id.tv_option_concern)
    TextView tvOptionConcern;
    @BindView(R.id.layout_option_concern)
    RelativeLayout rlOptionConcern;
    @BindView(R.id.layout_option_add_shopping_car)
    RelativeLayout layoutOptionAddShoppingCar;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.layout_goods_bottom)
    LinearLayout layoutOption;
    private Goods goods;

    @BindView(R.id.tool_bar)
    Toolbar toolBar;


    /**
     * 购买数量，值在GoodsAdapter中传递
     */
    public int number = 1;
    /**
     * 用户购买的规格，值在GoodsAdapter中传递
     */
    public String[] specs;
    @BindView(R.id.dragLayout)
    VerticalSlide dragLayout;

    GoodsInfoFragment firstFragment;
    GoodsDetailFragment secondFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_goods, container, false);

        ButterKnife.bind(this, mView);

        loadBundle();

        layoutOption.setVisibility(View.GONE);
        toolBar.setNavigationOnClickListener(v -> getActivity().finish());

        dragLayout.setOnShowNextPageListener(new VerticalSlide.OnShowNextPageListener() {
            @Override
            public void onShowNextPage() {

                Map<String, String> values = new HashMap<>();
                values.put("goodsId", goods.getGoodsId());
                String json = HTTP.formatJSONData(new Request().setData(values));

                secondFragment.setUrl(HTTP.URL_GOODS_DETAIL + json);
            }
        });

        firstFragment = GoodsInfoFragment.newInstance(this);
        secondFragment = new GoodsDetailFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.first, firstFragment);
        transaction.replace(R.id.second, secondFragment);
        transaction.commit();

        if (!secondKillStart) {
            tvBuy.setText("敬请期待");
        }

        return mView;
    }

    private void loadBundle() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            secondKillStart = bundle.getBoolean(KEY_SECOND_KILL_START, true);
        }
    }

    @OnClick({R.id.layout_option_seller, R.id.layout_option_share, R.id.layout_option_concern, R.id.layout_option_add_shopping_car,
            R.id.layout_option_buy, R.id.iv_to_shopping_car})
    public void onClick(View view) {
        if (getGoods() == null) return;
        switch (view.getId()) {
            case R.id.layout_option_seller:
                AppUtils.toSeller(getActivity(), getGoods().getSeller().getSellerId());
                break;
            case R.id.layout_option_share:
                ShareContent home = ContextParameter.getClientConfig().getGoodsShareContent();
                String username = ContextParameter.getUserInfo().getUsername();
                String userId = ContextParameter.getUserInfo().getUserId();
                String sellerId = ContextParameter.getUserInfo().getSellerId();
                String headimage = ContextParameter.getUserInfo().getHeadimage();
                String phone = ContextParameter.getUserInfo().getPhone();
                String inviteCode = ContextParameter.getUserInfo().getInviteCode();
                if (username == null){
                    username = "";
                }
                if (userId == null){
                    userId = "";
                }
                if (sellerId == null){
                    sellerId = "";
                }
                if (headimage == null){
                    headimage = "";
                }
                if (phone == null){
                    phone = "";
                }
                if (inviteCode == null){
                    inviteCode = "";
                }
                ShareUtils.share(getActivity(), "商品详情",
                        goods.getCoverPic(),
                        home.getTargetUrl() + "?userId=" + userId + "&appVersion=" + AppUtils.getVersionCode(getActivity()) + "&zoneId=" + ContextParameter.getCurrentZone().getZoneId() + "&sellerId=" + sellerId
                                + "&goodsId=" + goods.getGoodsId()+ "&img=" + headimage + "&name=" + username + "&phone=" + phone + "&code=" + inviteCode,
                        goods.getName());
                break;
            case R.id.layout_option_concern:
                if (UserUtils.handlerLogin(getActivity())) {
                    APIUtils.concernGoods(getActivity(), goods.getGoodsId(), !goods.getConcern(), new SupportSubscriber<Response>() {

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            T.showShort(getActivity(), "关注失败");
                        }

                        @Override
                        public void onResponseError(Response response) {
                            super.onResponseError(response);
                            T.showShort(getActivity(), "关注失败");
                        }

                        @Override
                        public void onNext(Response response) {


                            goods.setConcern(!goods.getConcern());
                            if (goods.getConcern()) {

                                T.showShort(getActivity(), "恭喜，商品关注成功！");

                                Drawable drawable = getResources().getDrawable(R.drawable.icon_concern_select);
                                // 这一步必须要做,否则不会显示.
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                tvOptionConcern.setCompoundDrawables(null, drawable, null, null);
                            } else {

                                T.showShort(getActivity(), "商品已取消关注");

                                Drawable drawable = getResources().getDrawable(R.drawable.icon_concern);
                                // 这一步必须要做,否则不会显示.
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                tvOptionConcern.setCompoundDrawables(null, drawable, null, null);
                            }


                        }
                    });

                }
                break;
            case R.id.layout_option_add_shopping_car:

                if (UserUtils.handlerLogin(getActivity()))
                    addShoppingCar();


                break;
            case R.id.layout_option_buy:

                if (secondKillStart) {
                    if (UserUtils.handlerLogin(getActivity())) {

                        buy();
                    }
                } else {
                    //秒杀未开始
                    T.showShort(getActivity(), "敬请期待!");
                }


                break;
            case R.id.iv_to_shopping_car:
                AppUtils.toShoppingCar(getActivity());
                getActivity().finish();
                break;
        }
    }


    private void buy() {

        //判断用户是否选中规格，这种情况在该商品有规格的情况下
        if (getGoods().getSpec() != null && getGoods().getSpec().size() != 0) {
            if (specs == null || specs.length == 0 || specs.length != getGoods().getSpec().size()) {
                T.showShort(getActivity(), "还没有选商品属性哦~");
                return;
            }
        }

        //如果是免单商品，判断免单券
        if (goods.getMallType().equals(Constants.MALL_TYPE_FREE_SINGLE)) {
            int count = Integer.parseInt(ContextParameter.getUserInfo().getFreeCount());
            if (count < number) {
                T.showShort(getActivity(), "您的免单券不足~");
                return;
            }
        }

        CreateTempOrderListBean bean = new CreateTempOrderListBean();
        if (specs != null) bean.setSpecs(Arrays.asList(specs));
        bean.setGoodsId(getGoods().getGoodsId());
        bean.setNumber(number);
        bean.setType("2");

        OrderUtils.createTempOrderList(getActivity(), bean);
    }

    private void addShoppingCar() {

        //判断用户是否选中规格，这种情况在该商品有规格的情况下
        if (getGoods().getSpec() != null && getGoods().getSpec().size() != 0) {
            if (specs == null || specs.length == 0 || specs.length != getGoods().getSpec().size()) {
                T.showShort(getActivity(), "还没有选商品属性哦~");
                return;
            }
        }

        Request<ShoppingCarBean> request = new Request<>();
        ShoppingCarBean shoppingCarBean = new ShoppingCarBean();

        shoppingCarBean.setNumber(number);
        shoppingCarBean.setGoodsId(getGoods().getGoodsId());
        shoppingCarBean.setSpecs(specs);

        request.setData(shoppingCarBean);

        RXUtils.request(getActivity(), request, "putShoppingCar", new SupportSubscriber() {

            LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.show();
            }

            @Override
            public void onCompleted() {
                loadingDialog.dismiss();
            }

            @Override
            public void onNext(Object o) {
                Response response = (Response) o;
                T.showShort(getActivity(), response.getMessage());

                //发送购物车改变事件
                EventBus.getDefault().post(new UpdateShoppingCarMessage());
            }
        });
    }


    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
        if (goods.getMallType().equals(Constants.MALL_SECOND_KILL)) {
            rlOptionConcern.setVisibility(View.GONE);
        }
        if (goods.getConcern()) {
            Drawable drawable = getResources().getDrawable(R.drawable.icon_concern_select);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvOptionConcern.setCompoundDrawables(null, drawable, null, null);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.icon_concern);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvOptionConcern.setCompoundDrawables(null, drawable, null, null);
        }

        //判断是否能加入购物车
        if (!StringUtils.isEmpty(goods.getMallType())) {
            switch (goods.getMallType()) {
                case Constants.MALL_FREE_SINGLE:
                case Constants.MALL_SECOND_KILL:
                    layoutOptionAddShoppingCar.setVisibility(View.GONE);
                    break;
                default:
                    layoutOptionAddShoppingCar.setVisibility(View.VISIBLE);
                    break;
            }
        }

        layoutOption.setVisibility(View.VISIBLE);
    }
}
