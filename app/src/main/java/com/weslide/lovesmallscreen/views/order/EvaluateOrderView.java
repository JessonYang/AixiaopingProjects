package com.weslide.lovesmallscreen.views.order;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.Order;
import com.weslide.lovesmallscreen.views.widget.TextUploadImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/16.
 * 订单评价
 */
public class EvaluateOrderView extends FrameLayout {
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.giv_goods)
    GoodsItemView givGoods;
    @BindView(R.id.rb_score)
    RatingBar rbScore;
    @BindView(R.id.tuliv)
    TextUploadImageView tuliv;

    public EvaluateOrderView(Context context) {
        super(context);
        initView();
    }

    public EvaluateOrderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EvaluateOrderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.order_view_evaluate, this, true);
        ButterKnife.bind(this);

        tuliv.setContentHint("请输入评价内容");
    }

    public String getContent() {
        return tuliv.getContent();
    }

    public List<String> getSelectPath() {
        return tuliv.getSelectPath();
    }

    public String getScore() {
        return String.valueOf(rbScore.getRating() * 2);
    }

    /**
     * fragment 或 activity中的onActivityResult必须调用该方法才能生效
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        tuliv.onActivityResult(requestCode, resultCode, data);
    }

    public void bindView(Order order) {
        tvSellerName.setText(order.getSeller().getSellerName());
        givGoods.bindView(order.getOrderItems().get(0));
    }
}
