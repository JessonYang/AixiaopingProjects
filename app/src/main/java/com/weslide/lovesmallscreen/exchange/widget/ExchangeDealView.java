package com.weslide.lovesmallscreen.exchange.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.exchange.activity.ChooseGoodsActivity;
import com.weslide.lovesmallscreen.exchange.activity.ChooseSpecActivity;
import com.weslide.lovesmallscreen.exchange.activity.DealDetailActivity;
import com.weslide.lovesmallscreen.model_yy.javabean.ExchangeGoodDtModel;
import com.weslide.lovesmallscreen.views.customview.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YY on 2018/1/24.
 */
public class ExchangeDealView extends FrameLayout {
    @BindView(R.id.user_head)
    CircleImageView userHead;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_presenter)
    TextView userPresenter;
    @BindView(R.id.user_address)
    TextView userAddress;
    @BindView(R.id.good_pic)
    ImageView goodPic;
    @BindView(R.id.good_name)
    TextView goodName;
    @BindView(R.id.good_price)
    TextView goodPrice;
    @BindView(R.id.note_tv1)
    TextView noteTv1;
    @BindView(R.id.note_tv2)
    TextView noteTv2;
    @BindView(R.id.spec_tv)
    TextView specTv;
    @BindView(R.id.good_num)
    TextView goodNum;
    @BindView(R.id.replace_ll)
    LinearLayout replaceLl;
    @BindView(R.id.stock_ll)
    LinearLayout stockLl;
    @BindView(R.id.want_ll)
    LinearLayout wantLl;
    @BindView(R.id.choose_spec_rll)
    RelativeLayout chooseSpecRll;
    private Context mContext;
    private ExchangeGoodDtModel mGoodDtModel;
    private TranslateAnimation mShowAnimation, mHiddenAnimation;
    private View goodView;
    private String presentType;

    public ExchangeDealView(Context context) {
        this(context, null);
    }

    public ExchangeDealView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExchangeDealView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        goodView = LayoutInflater.from(context).inflate(R.layout.exchange_deal_view_item, this, true);
        ButterKnife.bind(this);
        mShowAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAnimation.setDuration(200);
        mHiddenAnimation.setDuration(200);
    }

    public void bindView(Context context, ExchangeGoodDtModel goodDtModel, String presentType) {
        mContext = context;
        mGoodDtModel = goodDtModel;
        this.presentType = presentType;
        Glide.with(mContext).load(goodDtModel.getGoodsUserHead()).into(userHead);
        Glide.with(mContext).load(goodDtModel.getCoverPicOne()).into(goodPic);
        userPresenter.setText(presentType);
        userName.setText(goodDtModel.getGoodsUserName());
        userAddress.setText(goodDtModel.getSellerAddress());
        goodName.setText(goodDtModel.getGoodsName());
        goodPrice.setText(goodDtModel.getDisplayPrice() + "");
        List<String> want = goodDtModel.getWant();
        if (want != null && want.size() > 0) {
            wantLl.setVisibility(VISIBLE);
            for (int i = 0; i < want.size(); i++) {
                if (i == 0) {
                    noteTv1.setVisibility(View.VISIBLE);
                    noteTv1.setText(want.get(i));
                } else if (i == 1) {
                    noteTv2.setVisibility(View.VISIBLE);
                    noteTv2.setText(want.get(i));
                }
            }
        } else {
            wantLl.setVisibility(GONE);
        }
        if (goodDtModel.isHasSpecStr()) {
            stockLl.setVisibility(GONE);
            chooseSpecRll.setVisibility(VISIBLE);
        } else {
            stockLl.setVisibility(VISIBLE);
            goodNum.setText(goodDtModel.getStock() + "");
            chooseSpecRll.setVisibility(GONE);
        }
    }

    public void showReplace() {
        replaceLl.startAnimation(mShowAnimation);
        replaceLl.setVisibility(VISIBLE);
    }

    public void hiddenReplace() {
        replaceLl.startAnimation(mHiddenAnimation);
        replaceLl.setVisibility(GONE);
    }

    public void hiddenChooseSpec() {
        chooseSpecRll.setVisibility(GONE);
    }

    public void setSpec(String spec) {
        specTv.setText("已选: " + spec);
    }

    @OnClick({R.id.choose_spec_rll,R.id.replace_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            //选择规格
            case R.id.choose_spec_rll:
                Intent intent = new Intent(mContext, ChooseSpecActivity.class);
                intent.putExtra("goods", mGoodDtModel);
                intent.putExtra("presentType", presentType);
                ((DealDetailActivity) mContext).startActivityForResult(intent, DealDetailActivity.CHOOSE_SPEC_REQUEST);
                break;
            //替换商品
            case R.id.replace_ll:
                Intent intent1 = new Intent(mContext, ChooseGoodsActivity.class);
                ((DealDetailActivity) mContext).startActivityForResult(intent1, DealDetailActivity.ADD_GOOD_REQUEST);
                break;
        }
    }
}
