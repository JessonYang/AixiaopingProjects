package com.weslide.lovesmallscreen.views.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/6/22.
 * 评论项
 */
public class GoodsCommentItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_goods_comment_name)
    public TextView tvGoodsCommentName;
    @BindView(R.id.tv_goods_comment_date)
    public TextView tvGoodsCommentDate;
    @BindView(R.id.ratingBar)
    public RatingBar ratingBar;
    @BindView(R.id.tv_goods_comment_content)
    public TextView tvGoodsCommentContent;
    @BindView(R.id.layout_comment_images)
    public LinearLayout layoutCommentImages;
    @BindView(R.id.ll_come_coment_list)
    public LinearLayout commentList;

    public GoodsCommentItemViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
