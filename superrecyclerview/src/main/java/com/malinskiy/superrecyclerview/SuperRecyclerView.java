package com.malinskiy.superrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.malinskiy.superrecyclerview.core.DefaultRecyclerDifferentSituationAdapter;
import com.malinskiy.superrecyclerview.core.RecyclerDifferentSituationAdapter;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;
import com.malinskiy.superrecyclerview.util.FloatUtil;
import com.malinskiy.superrecyclerview.viewholder.EmptyViewHolder;
import com.malinskiy.superrecyclerview.viewholder.ErrorViewHolder;
import com.malinskiy.superrecyclerview.viewholder.NoNetworkViewHolder;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.utils.L;

import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class SuperRecyclerView extends FrameLayout {

    protected int ITEM_LEFT_TO_LOAD_MORE = 10;

    /** 数据响应错误 */
    public static final String RECYCLER_VIEW_STATUS_ERROR = "RECYCLER_VIEW_STATUS_ERROR";
    /** 无网络 */
    public static final String RECYCLER_VIEW_STATUS_NO_NETWORK = "RECYCLER_VIEW_STATUS_NO_NETWORK";
    /** 无数据 */
    public static final String RECYCLER_VIEW_STATUS_EMPTY = "RECYCLER_VIEW_STATUS_EMPTY";
    /** 正在加载 */
    public static final String RECYCLER_VIEW_STATUS_LOADING = "RECYCLER_VIEW_STATUS_LOADING";
    /** 加载完成 */
    public static final String RECYCLER_VIEW_STATUS_SUCCESS = "RECYCLER_VIEW_STATUS_SUCCESS";

    private RecyclerDifferentSituationAdapter differentSituationAdapter = new DefaultRecyclerDifferentSituationAdapter();

    private OnClickListener differentSituationOptionListener;

    protected RecyclerView mRecycler;
    protected FrameLayout mDifferentSituationContainer;
    protected ViewStub mProgress;
    protected ViewStub mEmpty;
    protected View mProgressView;
    protected View mEmptyView;

    protected boolean mClipToPadding;
    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected int mScrollbarStyle;
    protected int mEmptyId;

    protected LAYOUT_MANAGER_TYPE layoutManagerType;

    protected RecyclerView.OnScrollListener mInternalOnScrollListener;
    private RecyclerView.OnScrollListener mSwipeDismissScrollListener;
    protected RecyclerView.OnScrollListener mExternalOnScrollListener;
    protected List<RecyclerView.OnScrollListener> mExternalOnScrollListenerList = new LinkedList<>();
    protected List<RecyclerView.OnScrollListener> mRemoveOnScrollListenerList = new LinkedList<>();

    protected OnMoreListener mOnMoreListener;
    protected boolean isLoadingMore;
    protected PtrClassicFrameLayout mPtrLayout;

    protected int mSuperRecyclerViewMainLayout;
    private int mProgressId;

    public int getSupeRecyclerView_parent_type() {
        return supeRecyclerView_parent_type;
    }

    public void setSupeRecyclerView_parent_type(int supeRecyclerView_parent_type) {
        this.supeRecyclerView_parent_type = supeRecyclerView_parent_type;
    }

    private int supeRecyclerView_parent_type = 0;

    private int[] lastScrollPositions;

    public PtrClassicFrameLayout getSwipeToRefresh() {
        return mPtrLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecycler;
    }

    public SuperRecyclerView(Context context) {
        super(context);
        initView();
    }

    public SuperRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
    }

    public SuperRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
        initView();
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.superrecyclerview);
        try {
            mSuperRecyclerViewMainLayout = a.getResourceId(R.styleable.superrecyclerview_mainLayoutId, R.layout.layout_progress_recyclerview);
            mClipToPadding = a.getBoolean(R.styleable.superrecyclerview_recyclerClipToPadding, false);
            mPadding = (int) a.getDimension(R.styleable.superrecyclerview_recyclerPadding, -1.0f);
            mPaddingTop = (int) a.getDimension(R.styleable.superrecyclerview_recyclerPaddingTop, 0.0f);
            mPaddingBottom = (int) a.getDimension(R.styleable.superrecyclerview_recyclerPaddingBottom, 0.0f);
            mPaddingLeft = (int) a.getDimension(R.styleable.superrecyclerview_recyclerPaddingLeft, 0.0f);
            mPaddingRight = (int) a.getDimension(R.styleable.superrecyclerview_recyclerPaddingRight, 0.0f);
            mScrollbarStyle = a.getInt(R.styleable.superrecyclerview_scrollbarStyle, -1);
            mEmptyId = a.getResourceId(R.styleable.superrecyclerview_layout_empty, R.layout.layout_empty);
//            mMoreProgressId = a.getResourceId(R.styleable.superrecyclerview_layout_moreProgress, R.layout.layout_more_progress);
            mProgressId = a.getResourceId(R.styleable.superrecyclerview_layout_progress, R.layout.layout_progress);
        } finally {
            a.recycle();
        }
    }

    private void initView() {
        if (isInEditMode()) {
            return;
        }
        View v = LayoutInflater.from(getContext()).inflate(mSuperRecyclerViewMainLayout, this);
        mPtrLayout = (PtrClassicFrameLayout) v.findViewById(R.id.rotate_header_grid_view_frame);
        mPtrLayout.setEnabled(false);

        mDifferentSituationContainer = (FrameLayout) v.findViewById(R.id.layout_different_situation_container);

        mProgress = (ViewStub) v.findViewById(android.R.id.progress);

        mProgress.setLayoutResource(mProgressId);
        mProgressView = mProgress.inflate();

        mEmpty = (ViewStub) v.findViewById(R.id.empty);
        mEmpty.setLayoutResource(mEmptyId);
        if (mEmptyId != 0)
            mEmptyView = mEmpty.inflate();
        mEmpty.setVisibility(View.GONE);

        initRecyclerView(v);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener){
        mExternalOnScrollListenerList.add(listener);
    }

    public void removeOnScrollListener(RecyclerView.OnScrollListener listener){
        mRemoveOnScrollListenerList.add(listener);

    }

    /**
     * Implement this method to customize the AbsListView
     */
    protected void initRecyclerView(View view) {
        View recyclerView = view.findViewById(android.R.id.list);

        if (recyclerView instanceof RecyclerView)
            mRecycler = (RecyclerView) recyclerView;
        else
            throw new IllegalArgumentException("SuperRecyclerView works with a RecyclerView!");


        mRecycler.setClipToPadding(mClipToPadding);
        mInternalOnScrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                processOnMore();

                if (mExternalOnScrollListener != null) {
                    mExternalOnScrollListener.onScrolled(recyclerView, dx, dy);
                    if(mExternalOnScrollListenerList != null){
                        for (RecyclerView.OnScrollListener listener: mRemoveOnScrollListenerList) {
                            mExternalOnScrollListenerList.remove(listener);
                        }
                        for (RecyclerView.OnScrollListener listener: mExternalOnScrollListenerList) {
                            listener.onScrolled(recyclerView, dx, dy);
                        }
                    }

                }
                if (mSwipeDismissScrollListener != null)
                    mSwipeDismissScrollListener.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mExternalOnScrollListener != null) {
                    mExternalOnScrollListener.onScrollStateChanged(recyclerView, newState);
                    if(mExternalOnScrollListenerList != null){
                        for (RecyclerView.OnScrollListener listener: mExternalOnScrollListenerList) {
                            listener.onScrollStateChanged(recyclerView, newState);
                        }
                    }

                }

                if (mSwipeDismissScrollListener != null)
                    mSwipeDismissScrollListener.onScrollStateChanged(recyclerView, newState);
            }
        };
        mRecycler.addOnScrollListener(mInternalOnScrollListener);

        if (!FloatUtil.compareFloats(mPadding, -1.0f)) {
            mRecycler.setPadding(mPadding, mPadding, mPadding, mPadding);
        } else {
            mRecycler.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        }

        if (mScrollbarStyle != -1) {
            mRecycler.setScrollBarStyle(mScrollbarStyle);
        }
    }


    private void processOnMore() {
        RecyclerView.LayoutManager layoutManager = mRecycler.getLayoutManager();
        int lastVisibleItemPosition = getLastVisibleItemPosition(layoutManager);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

        //判断是加载更多还是已经加载完成
        if (getAdapter().getData() != null) {
            if (getAdapter().getData().getPageSize() == 0 || getAdapter().getData().getPageIndex() >= getAdapter().getData().getPageSize()) {
                showLoadEnd();
                return;
            } else {
                showLoadingMore();
            }

        }

        if (((totalItemCount - lastVisibleItemPosition) <= ITEM_LEFT_TO_LOAD_MORE ||
                (totalItemCount - lastVisibleItemPosition) == 0 && totalItemCount > visibleItemCount)
                && !isLoadingMore) {

            isLoadingMore = true;
            if (mOnMoreListener != null) {
                mOnMoreListener.onMoreAsked(mRecycler.getAdapter().getItemCount(), ITEM_LEFT_TO_LOAD_MORE, lastVisibleItemPosition);
            }
        }
    }

    private int getLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int lastVisibleItemPosition = -1;
        if (layoutManagerType == null) {
            if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            } else if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                lastVisibleItemPosition = caseStaggeredGrid(layoutManager);
                break;
        }
        return lastVisibleItemPosition;
    }

    private int caseStaggeredGrid(RecyclerView.LayoutManager layoutManager) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        if (lastScrollPositions == null)
            lastScrollPositions = new int[staggeredGridLayoutManager.getSpanCount()];

        staggeredGridLayoutManager.findLastVisibleItemPositions(lastScrollPositions);
        return findMax(lastScrollPositions);
    }


    private int findMax(int[] lastPositions) {
        int max = Integer.MIN_VALUE;
        for (int value : lastPositions) {
            if (value > max)
                max = value;
        }
        return max;
    }

    /**
     * @param adapter                       The new adapter to set, or null to set no adapter
     * @param compatibleWithPrevious        Should be set to true if new adapter uses the same {@android.support.v7.widget.RecyclerView.ViewHolder}
     *                                      as previous one
     * @param removeAndRecycleExistingViews If set to true, RecyclerView will recycle all existing Views. If adapters
     *                                      have stable ids and/or you want to animate the disappearing views, you may
     *                                      prefer to set this to false
     */
    private void setAdapterInternal(SuperRecyclerViewAdapter adapter, boolean compatibleWithPrevious,
                                    boolean removeAndRecycleExistingViews) {
        if (compatibleWithPrevious)
            mRecycler.swapAdapter(adapter, removeAndRecycleExistingViews);
        else
            mRecycler.setAdapter(adapter);

        if (mOnMoreListener != null) {
            getAdapter().setShowBottemLoadingLayout(true); //显示底部正在加载
        }

        mProgress.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
        if (null != adapter) {
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    super.onItemRangeChanged(positionStart, itemCount);
                    L.e("onItemRangeChanged");
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    L.e("onItemRangeChanged");
                    update();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    L.e("onItemRangeChanged");
                    update();
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                    L.e("onItemRangeChanged");
                    update();
                }

                @Override
                public void onChanged() {
                    super.onChanged();
                    L.e("onItemRangeChanged");
                    update();
                }

                private void update() {
                    mProgress.setVisibility(View.GONE);
                    mPtrLayout.refreshComplete();
                    isLoadingMore = false;
                    DataList data = ((SuperRecyclerViewAdapter) mRecycler.getAdapter()).getData();

                    //第一页正加载的情况下，pageSize有可能为空
                    if (data.getPageSize() == 0 && data.getDataList().size() == 0 && data.getRecyclerViewStatus() != RECYCLER_VIEW_STATUS_LOADING) {
                        data.setRecyclerViewStatus(RECYCLER_VIEW_STATUS_EMPTY);
                        hideLoad();
                    } else if (mEmptyId != 0) {
                        //判断是否已经加载完
                        if (data.getPageSize() <= data.getPageIndex()) {
                            showLoadEnd();
                        } else {
                            showLoadingMore();
                        }
                    }

                    //对不同情况进行处理
                    switch (data.getRecyclerViewStatus()){
                        case RECYCLER_VIEW_STATUS_EMPTY:

                            mDifferentSituationContainer.setVisibility(View.VISIBLE);
                            mDifferentSituationContainer.removeAllViews();
                            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) differentSituationAdapter.getEmpty(getContext(),mDifferentSituationContainer, null,supeRecyclerView_parent_type);

                            if(getDifferentSituationOptionListener() != null)
                                emptyViewHolder.setOnClickListener(getDifferentSituationOptionListener());

                            mDifferentSituationContainer.addView(emptyViewHolder.getView());
                            mRecycler.setVisibility(View.GONE);
                            mPtrLayout.setVisibility(View.GONE);

                            break;
                        case RECYCLER_VIEW_STATUS_ERROR:

                            mDifferentSituationContainer.setVisibility(View.VISIBLE);
                            mDifferentSituationContainer.removeAllViews();

                            ErrorViewHolder errorViewHolder = (ErrorViewHolder) differentSituationAdapter.getError(getContext(),
                                    mDifferentSituationContainer, null);
                            if(getDifferentSituationOptionListener() != null)
                                errorViewHolder.setOnClickListener(getDifferentSituationOptionListener());

                            mDifferentSituationContainer.addView(errorViewHolder.getView());
                            mRecycler.setVisibility(View.GONE);
                            mPtrLayout.setVisibility(View.GONE);

                            break;
                        case RECYCLER_VIEW_STATUS_LOADING:

                            mDifferentSituationContainer.setVisibility(View.VISIBLE);
                            mDifferentSituationContainer.removeAllViews();
                            mDifferentSituationContainer.addView(differentSituationAdapter.getLoading(getContext(),
                                    mDifferentSituationContainer, null).getView());
                            mRecycler.setVisibility(View.GONE);
                            mPtrLayout.setVisibility(View.GONE);

                            break;
                        case RECYCLER_VIEW_STATUS_NO_NETWORK:

                            mDifferentSituationContainer.setVisibility(View.VISIBLE);
                            mDifferentSituationContainer.removeAllViews();
                            NoNetworkViewHolder noNetworkViewHolder = (NoNetworkViewHolder) differentSituationAdapter.getNoNetwork(getContext(),
                                    mDifferentSituationContainer, null);

                            if(getDifferentSituationOptionListener() != null)
                                noNetworkViewHolder.setOnClickListener(getDifferentSituationOptionListener());

                            mDifferentSituationContainer.addView(noNetworkViewHolder.getView());
                            mRecycler.setVisibility(View.GONE);
                            mPtrLayout.setVisibility(View.GONE);

                            break;
                        case RECYCLER_VIEW_STATUS_SUCCESS:
                            mRecycler.setVisibility(View.VISIBLE);
                            mPtrLayout.setVisibility(View.VISIBLE);
                            mDifferentSituationContainer.setVisibility(View.GONE);
                            break;
                    }


                }
            });
        }
    }

    /**
     * Set the layout manager to the recycler
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecycler.setLayoutManager(manager);
    }

    /**
     * Set the adapter to the recycler
     * Automatically hide the progressbar
     * Set the refresh to false
     * If adapter is empty, then the emptyview is shown
     */
    public void setAdapter(SuperRecyclerViewAdapter adapter) {
        setAdapterInternal(adapter, false, true);
    }

    /**
     * @param adapter                       The new adapter to , or null to set no adapter.
     * @param removeAndRecycleExistingViews If set to true, RecyclerView will recycle all existing Views. If adapters
     *                                      have stable ids and/or you want to animate the disappearing views, you may
     *                                      prefer to set this to false.
     */
    public void swapAdapter(SuperRecyclerViewAdapter adapter, boolean removeAndRecycleExistingViews) {
        setAdapterInternal(adapter, true, removeAndRecycleExistingViews);
    }

    public void setupSwipeToDismiss(final SwipeDismissRecyclerViewTouchListener.DismissCallbacks listener) {
        SwipeDismissRecyclerViewTouchListener touchListener =
                new SwipeDismissRecyclerViewTouchListener(mRecycler, new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return listener.canDismiss(position);
                    }

                    @Override
                    public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        listener.onDismiss(recyclerView, reverseSortedPositions);
                    }
                });
        mSwipeDismissScrollListener = touchListener.makeScrollListener();
        mRecycler.setOnTouchListener(touchListener);
    }

    /**
     * Remove the adapter from the recycler
     */
    public void clear() {
        mRecycler.setAdapter(null);
    }

    /**
     * Show the progressbar
     */
    public void showProgress() {
        hideRecycler();
        if (mEmptyId != 0) mEmpty.setVisibility(View.INVISIBLE);
        mProgress.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the progressbar and show the recycler
     */
    public void showRecycler() {
        hideProgress();
        if (mRecycler.getAdapter().getItemCount() == 0 && mEmptyId != 0) {
            mEmpty.setVisibility(View.VISIBLE);
        } else if (mEmptyId != 0) {
            mEmpty.setVisibility(View.GONE);
        }
        mRecycler.setVisibility(View.VISIBLE);
    }

    /**
     * Set the listener when refresh is triggered and enable the SwipeRefreshLayout
     */
    public void setRefreshListener(final SwipeRefreshLayout.OnRefreshListener listener) {
        mPtrLayout.setEnabled(true);

        if(listener != null){
            mPtrLayout.setPtrHandler(new PtrHandler() {
                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }

                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    listener.onRefresh();
                }
            });
        }

    }


    /**
     * Hide the progressbar
     */
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    /**
     * 显示正在加载更多
     */
    public void showLoadingMore() {
        LoadMoreViewHolder viewHolder = ((SuperRecyclerViewAdapter) getAdapter()).getLoadingLayout();
        if (viewHolder != null) {
            viewHolder.showLoadMore();
        }
    }

    /**
     * 显示已加载完成
     */
    public void showLoadEnd() {
        LoadMoreViewHolder viewHolder = ((SuperRecyclerViewAdapter) getAdapter()).getLoadingLayout();
        if (viewHolder != null) {
            viewHolder.showLoadEnd();
        }
    }

    /**
     * 隐藏加载
     */
    public void hideLoad() {
        LoadMoreViewHolder viewHolder = ((SuperRecyclerViewAdapter) getAdapter()).getLoadingLayout();
        if (viewHolder != null) {
            viewHolder.hideLoad();
        }
    }

    /**
     * Hide the recycler
     */
    public void hideRecycler() {
        mRecycler.setVisibility(View.GONE);
    }

    /**
     * Set the scroll listener for the recycler
     */
    public void setOnScrollListener(RecyclerView.OnScrollListener listener) {
        mExternalOnScrollListener = listener;
    }

    /**
     * Add the onItemTouchListener for the recycler
     */
    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecycler.addOnItemTouchListener(listener);
    }

    /**
     * Remove the onItemTouchListener for the recycler
     */
    public void removeOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecycler.removeOnItemTouchListener(listener);
    }

    /**
     * @return the recycler adapter
     */
    public SuperRecyclerViewAdapter getAdapter() {
        return (SuperRecyclerViewAdapter) mRecycler.getAdapter();
    }

    /**
     * Sets the More listener
     * 当其他地方实现了GridLayoutManager SpanSizeLookup对象是， 一定要做底部加载布局判断
     *
     * @param max Number of items before loading more
     */
    public void setupMoreListener(OnMoreListener onMoreListener, int max) {
        mOnMoreListener = onMoreListener;
        if (getAdapter() != null) getAdapter().setShowBottemLoadingLayout(true); //显示底部正在加载
        if (mRecycler.getLayoutManager() instanceof GridLayoutManager) {
            if (((GridLayoutManager) mRecycler.getLayoutManager()).getSpanSizeLookup() instanceof GridLayoutManager.DefaultSpanSizeLookup) {
                ((GridLayoutManager) mRecycler.getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        //如果是底部正在加载布局， 则占整行
                        return getAdapter().isButtonLayout(position) ? ((GridLayoutManager) mRecycler.getLayoutManager()).getSpanCount() : 1;
                    }
                });
            }

        }
        ITEM_LEFT_TO_LOAD_MORE = max;
    }

    public void setOnMoreListener(OnMoreListener onMoreListener) {
        mOnMoreListener = onMoreListener;
    }

    public void setNumberBeforeMoreIsCalled(int max) {
        ITEM_LEFT_TO_LOAD_MORE = max;
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    /**
     * Enable/Disable the More event
     */
    public void setLoadingMore(boolean isLoadingMore) {
        this.isLoadingMore = isLoadingMore;
    }

    /**
     * Remove the moreListener
     */
    public void removeMoreListener() {
        mOnMoreListener = null;
    }


    public void setOnTouchListener(OnTouchListener listener) {
        mRecycler.setOnTouchListener(listener);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecycler.addItemDecoration(itemDecoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
        mRecycler.addItemDecoration(itemDecoration, index);
    }

    public void removeItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecycler.removeItemDecoration(itemDecoration);
    }

    /**
     * @return inflated progress view or null
     */
    public View getProgressView() {
        return mProgressView;
    }

    /**
     * @return inflated empty view or null
     */
    public View getEmptyView() {
        return mEmptyView;
    }

    /**
     * Animate a scroll by the given amount of pixels along either axis.
     *
     * @param dx Pixels to scroll horizontally
     * @param dy Pixels to scroll vertically
     */
    public void smoothScrollBy(int dx, int dy) {
        mRecycler.smoothScrollBy(dx, dy);
    }

    public void setupMoreListener(OnMoreListener onMoreListener) {
        setupMoreListener(onMoreListener, 2);
    }

    public RecyclerDifferentSituationAdapter getDifferentSituationAdapter() {
        return differentSituationAdapter;
    }

    public void setDifferentSituationAdapter(RecyclerDifferentSituationAdapter differentSituationAdapter) {
        this.differentSituationAdapter = differentSituationAdapter;
    }

    public OnClickListener getDifferentSituationOptionListener() {
        return differentSituationOptionListener;
    }

    public void setDifferentSituationOptionListener(OnClickListener differentSituationOptionListener) {
        this.differentSituationOptionListener = differentSituationOptionListener;
    }


    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    public RecyclerView.LayoutManager getRlLayoutManager(){
        return mRecycler.getLayoutManager();
    }

}


