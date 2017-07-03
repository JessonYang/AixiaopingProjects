package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.ImageShowActivity;
import com.weslide.lovesmallscreen.models.Comment;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.AppUtils;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/8/16.
 */
public class CommentListAdapter extends SuperRecyclerViewAdapter<Comment,CommentListAdapter.CommnentListViewHolder>{


    public CommentListAdapter(Context context, DataList<Comment> dataList) {
        super(context, dataList);
    }

    @Override
    public CommnentListViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {
        CommnentListViewHolder viewHolder = new CommnentListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.goods_view_comment_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(CommnentListViewHolder holder, int position) {
        Comment comment = mList.get(position);
        holder.tvGoodsCommentName.setText(comment.getUserInfo().getUsername());
        holder.tvGoodsCommentDate.setText(comment.getCommentDate());
        holder.tvGoodsCommentContent.setText(comment.getCommentContent());
        holder.ratingBar.setRating(Float.parseFloat(comment.getCommentGoal())/2);
        if (comment.getCommentImages() != null && comment.getCommentImages().size() > 0) {
            holder.layoutCommentImages.setVisibility(View.VISIBLE);
            holder.layoutCommentImages.removeAllViews();

            for (String image : comment.getCommentImages()) {
                ImageView imageView = new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        (int) mContext.getResources().getDimension(R.dimen.goods_comment_height));
                imageView.setLayoutParams(params);
                Glide.with(mContext).load(image).into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ImageShowActivity.KEY_IMAGE_LIST_URI, (Serializable) comment.getCommentImages());
                        AppUtils.toActivity(mContext, ImageShowActivity.class, bundle);
                    }
                });
                holder.layoutCommentImages.addView(imageView);
            }
        } else {
            holder.layoutCommentImages.setVisibility(View.GONE);
        }

    }

    class CommnentListViewHolder  extends RecyclerView.ViewHolder{
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
        public CommnentListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
