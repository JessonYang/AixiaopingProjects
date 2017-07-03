package com.malinskiy.superrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.L;

import java.util.List;

/**
 * Created by xu on 2016/6/7.
 * 配合加载更多
 */
public abstract class SuperRecyclerViewAdapter<V, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected DataList mDataList;
    protected List<V> mList;
    protected Context mContext;

    /**
     * 是否显示底部加载布局
     */
    private boolean showBottemLoadingLayout = false;
    private LoadMoreViewHolder mLoadingLayout;

    public SuperRecyclerViewAdapter(Context context, DataList<V> dataList) {
        mContext = context;
        mDataList = dataList;
        mList = dataList.getDataList();
    }

    public SuperRecyclerViewAdapter(Context context, List<V> list) {
        mContext = context;
        DataList<V> dataList = new DataList<>();
        dataList.setDataList(list);

        mDataList = dataList;
        mList = dataList.getDataList();


    }

    /**
     * 向RecyclerView中添加数据
     *
     * @param dataList
     */
    public void addDataList(DataList<V> dataList) {

        if (dataList == null || dataList.getDataList() == null) {
            return;
        }

        //如果是第一页的情况下清空，考虑到布局类型分布很多种， 不能将所有清空，但是固定数据开始加载时pageIndex是0,
        //可以根据这个来判断             还有一种更好的办法，清空指定类型的项，没遇到什么问题暂时先这样用着
        if (dataList.getPageIndex() == 1 && !(mDataList.getPageIndex() == 0)) {
            mDataList.getDataList().clear();
        }

        mDataList.setRecyclerViewStatus(SuperRecyclerView.RECYCLER_VIEW_STATUS_SUCCESS);
        mDataList.setPageIndex(dataList.getPageIndex());
        mDataList.setPageItemCount(dataList.getPageItemCount());
        mDataList.setPageSize(dataList.getPageSize());


        mDataList.getDataList().addAll(dataList.getDataList());
    }

    public void addDataListNotifyDataSetChanged(DataList<V> dataList) {
        addDataList(dataList);
        notifyDataSetChanged();
    }

    /**
     * footerView
     */
    public static final int TYPE_LOAD_MORE_VIEW = 805861;

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_LOAD_MORE_VIEW:
                if (isShowBottemLoadingLayout()) {
                    viewHolder = mLoadingLayout = (new LoadMoreViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_load_more, parent, false)));
                    break;
                }

            default:
                viewHolder = onSuperCreateViewHolder(parent, viewType);
                break;
        }

        return (VH) viewHolder;
    }

    public DataList<V> getData() {
        return mDataList;
    }

    public abstract VH onSuperCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_LOAD_MORE_VIEW:
                if (isShowBottemLoadingLayout()) {
                    break;
                }

            default:
                onSuperBindViewHolder(holder, position);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.getDataList().size() == position && isShowBottemLoadingLayout()) {
            return TYPE_LOAD_MORE_VIEW;
        }
        return 0;
    }

    public abstract void onSuperBindViewHolder(VH holder, int position);

    @Override
    public int getItemCount() {
        if (mDataList.getDataList() == null) {
            return 0;
        }
        return isShowBottemLoadingLayout() ? mDataList.getDataList().size() + 1 : mDataList.getDataList().size();
    }

    public boolean isShowBottemLoadingLayout() {
        return showBottemLoadingLayout;
    }

    /**
     * 返回错误页
     *
     * @return
     */
    public int errorView() {

        return R.layout.layout_error;
    }

    /**
     * 空数据
     *
     * @return
     */
    public int emptyView() {

        return R.layout.layout_empty;
    }

    /**
     * 无网络
     *
     * @return
     */
    public int noNetworkView() {

        return R.layout.layout_no_network;
    }


    /**
     * 判断是否是底部布局
     *
     * @param position
     * @return
     */
    public boolean isButtonLayout(int position) {
        if (getItemViewType(position) == TYPE_LOAD_MORE_VIEW) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置是否显示底部加载
     *
     * @param showBottemLoadingLayout
     */
    public void setShowBottemLoadingLayout(boolean showBottemLoadingLayout) {
        this.showBottemLoadingLayout = showBottemLoadingLayout;
    }


    public LoadMoreViewHolder getLoadingLayout() {
        return mLoadingLayout;
    }

    public void setLoadingLayout(LoadMoreViewHolder loadingLayout) {
        this.mLoadingLayout = loadingLayout;
    }
}

class LoadMoreViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout mLoadMoreLayout;
    RelativeLayout mLoadEndLayout;

    public LoadMoreViewHolder(View itemView) {
        super(itemView);

        mLoadEndLayout = (RelativeLayout) itemView.findViewById(R.id.layout_load_end);
        mLoadMoreLayout = (RelativeLayout) itemView.findViewById(R.id.layout_load_more);
    }

    public void showLoadMore() {
        mLoadMoreLayout.setVisibility(View.VISIBLE);
        mLoadEndLayout.setVisibility(View.GONE);
    }

    public void showLoadEnd() {
        mLoadMoreLayout.setVisibility(View.GONE);
        mLoadEndLayout.setVisibility(View.VISIBLE);
    }


    public void hideLoad() {
        mLoadMoreLayout.setVisibility(View.GONE);
        mLoadEndLayout.setVisibility(View.GONE);
    }
}