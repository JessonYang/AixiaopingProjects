package com.weslide.lovesmallscreen.views.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/22.
 *
 * 商品评论标题
 */
public class GoodsCommentTitleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_goods_comment_count)
    public TextView tvCommentCount;
    @BindView(R.id.tv_goods_comment_praise)
    public TextView tvCommentPraise;
    @BindView(R.id.rl_come_comment_list)
    public RelativeLayout comeList;

    public GoodsCommentTitleViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

    }
}
