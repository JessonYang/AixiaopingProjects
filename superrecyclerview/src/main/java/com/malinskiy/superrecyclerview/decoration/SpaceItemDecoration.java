package com.malinskiy.superrecyclerview.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xu on 2016/7/7.
 * RecyclerView空间间隔
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;
    private int mViewType;

    public SpaceItemDecoration(int space, int viewType) {
        mSpace = space;
        mViewType = viewType;
    }

    public SpaceItemDecoration(int space) {
        this(space, 0);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int viewType = parent.getAdapter().getItemViewType(itemPosition);


        if(parent.getChildPosition(view) != 0){
            if(mViewType == viewType){
                outRect.top = mSpace;
            }
        }

    }

}
