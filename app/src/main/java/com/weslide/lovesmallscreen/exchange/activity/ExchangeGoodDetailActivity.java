package com.weslide.lovesmallscreen.exchange.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.lzy.widget.VerticalSlide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.exchange.fragment.GoodDtBottomFragment;
import com.weslide.lovesmallscreen.exchange.fragment.GoodDtTopFragment;
import com.weslide.lovesmallscreen.model_yy.javabean.ExchangeGoodDtModel;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class ExchangeGoodDetailActivity extends AppCompatActivity implements VerticalSlide.OnShowNextPageListener {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.change_rll)
    RelativeLayout changeRll;
    @BindView(R.id.first)
    FrameLayout topFragment;
    @BindView(R.id.second)
    FrameLayout bottomFragment;
    @BindView(R.id.vertical_layout)
    VerticalSlide verticalLayout;
    private GoodDtTopFragment goodDtTopFragment;
    private GoodDtBottomFragment goodDtBottomFragment;
    private ExchangeGoodDtModel goods;
    private String goodsUserId;
    private String goodsUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_good_detail);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        verticalLayout.setOnShowNextPageListener(this);
        goodDtTopFragment = GoodDtTopFragment.newInstance(this);
        goodDtBottomFragment = new GoodDtBottomFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.first, goodDtTopFragment);
        transaction.replace(R.id.second, goodDtBottomFragment);
        transaction.commit();
    }

    public void setExchangeGood(ExchangeGoodDtModel good) {
        goods = good;
        goodsUserId = good.getGoodsUserId() + "";
        goodsUserName = good.getGoodsUserName();
    }

    @OnClick({R.id.connect_ll, R.id.score_exchange, R.id.good_exchange})
    public void onClick(View view) {
        switch (view.getId()) {
            //联系他
            case R.id.connect_ll:
                RongIM.getInstance().startConversation(this, Conversation.ConversationType.PRIVATE, goodsUserId, goodsUserName);
                break;
            //积分换
            case R.id.score_exchange:

                break;
            //物物换
            case R.id.good_exchange:
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods",goods);
                AppUtils.toActivity(this,DealDetailActivity.class,bundle);
                break;
        }
    }

    @Override
    public void onShowNextPage() {
        //新图文详情
        goodDtBottomFragment.setDetailGood(goods.getGoodsDetail());
    }
}
