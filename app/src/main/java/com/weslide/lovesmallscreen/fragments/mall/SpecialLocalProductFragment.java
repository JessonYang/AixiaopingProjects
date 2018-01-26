package com.weslide.lovesmallscreen.fragments.mall;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.core.RecyclerViewModel;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.models.CityItems;
import com.weslide.lovesmallscreen.models.CityType;
import com.weslide.lovesmallscreen.models.Goods;
import com.weslide.lovesmallscreen.models.GoodsList;
import com.weslide.lovesmallscreen.models.GoodsType;
import com.weslide.lovesmallscreen.models.HeadquartersType;
import com.weslide.lovesmallscreen.models.SpecialLocalProduct;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateMallMessage;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.DensityUtils;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.Preferential99Adapter;
import com.weslide.lovesmallscreen.views.adapters.SpecialLocalProductAdapter;
import com.weslide.lovesmallscreen.views.dialogs.SecondaryCityDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/7/19.
 * 特产速递
 */
public class SpecialLocalProductFragment extends BaseFragment {
    private PopupWindow pw;
    private List<GoodsType> typeList;
    View mView;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.list)
    SuperRecyclerView list;
    SpecialLocalProductAdapter mAdapter;
    DataList<RecyclerViewModel> mDataList = new DataList<>();
    List<CityType> mDataType = new ArrayList<>();
    public static GetGoodsListBean mGoodsListReqeust = new GetGoodsListBean();
    public static GetGoodsListBean mGoodsTypeReqeust = new GetGoodsListBean();
    @BindView(R.id.sort_bar)
    LinearLayout layoutClassification;
    //    @BindView(R.id.btn_search)
//    ImageView btnSearch;
    @BindView(R.id.tv_all_classifi)
    TextView tvAllClassifi;
    @BindView(R.id.tv_sales_volume)
    TextView tvSalesVolume;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.special_local_tablayout)
    TabLayout mTabLayout;
    String typeId;
    String cityId;
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private int totalDy = 0;
    //    private FlexboxLayout flexBox;
    private int count = 1;
    private int typeListCount = 1;
    private GridView gridview;
    private GridAdapter mGridAdapter;
    private int position;
    private String intentTypeId;
    private int where = 0;
    private int truePosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_special_local_product, container, false);
        ButterKnife.bind(this, mView);
        EventBus.getDefault().register(this);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            typeId = extras.getString("typeId");
            where = extras.getInt("where", 0);
        }
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果是商品占用一格，如果不是占用一行
                return mAdapter.isGoods(position) ? 1 : layoutManager.getSpanCount();
            }
        });
        list.setLayoutManager(layoutManager);

        mAdapter = new SpecialLocalProductAdapter(getActivity(), mDataList);

        list.setAdapter(mAdapter);

        //添加分割
        list.addItemDecoration(new RecyclerView.ItemDecoration() {

            int goodsIndex = 0;

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


                int position = parent.getChildLayoutPosition(view);
                int itemType = mAdapter.getItemViewType(position);

                if (itemType == SpecialLocalProductAdapter.VIEW_TYPE_FILTER_TYPE) {
                    int space = DensityUtils.dp2px(getActivity(), 4);
                    outRect.top = space;
                    outRect.bottom = space;
                } else if (itemType == SpecialLocalProductAdapter.VIEW_TYPE_GOODS_ITEM) {
                    int space = DensityUtils.dp2px(getActivity(), 4);

                    outRect.top = space;
                    //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
                    if (goodsIndex % 2 == 0) {
                        outRect.left = space;
                        outRect.right = space / 2;
                    } else {
                        outRect.right = space;
                        outRect.left = space / 2;
                    }

                    goodsIndex++;
                }


            }
        });

        list.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> loadGoodsList(), 2);
        //数据请求失败后的数据重新载入
        list.setDifferentSituationOptionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });

        load();
        layoutClassification.setVisibility(View.VISIBLE);

        list.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                layoutClassification.setVisibility(View.GONE);
                totalDy += dy;
                if (totalDy > mAdapter.getHigh()) {
                    layoutClassification.setVisibility(View.VISIBLE);
                } else {
                    layoutClassification.setVisibility(View.GONE);
                }
            }
        });
        return mView;
    }

    private void load() {
        mDataList.getDataList().clear();
        loadSpecialLocalProduct();
    }

    private void loadSpecialLocalProduct() {
        Request request = new Request();
        RXUtils.request(getActivity(), request, "specialLocalProduct", new RecyclerViewSubscriber<Response<SpecialLocalProduct>>(mAdapter, mDataList) {

            @Override
            public void onSuccess(Response<SpecialLocalProduct> preferential99Response) {
                handlerSpecialLocalProduct(preferential99Response);
                loadType();

            }
        });
    }

    private void loadGoodsList() {
        Request<GetGoodsListBean> request = new Request<>();
        if (where == 0) {
            mGoodsListReqeust.setPageIndex(1);
            where = 1;
        } else {
            mGoodsListReqeust.setPageIndex(mGoodsListReqeust.getPageIndex() + 1);
        }
        mGoodsListReqeust.setMallTyle(Constants.MALL_SPECIAL_LOCAL_PRODUCT);
        mGoodsListReqeust.setCityId(cityId);
//        mGoodsListReqeust.setTypeId(typeId);
        if (typeId != null && !typeId.equals("184")) {
            mGoodsListReqeust.setTypeId(typeId);
        } else if (typeId == null || typeId.equals("184")) {
            mGoodsListReqeust.setTypeId(null);
        }
        request.setData(mGoodsListReqeust);

        RXUtils.request(getActivity(), request, "getGoodsList", new RecyclerViewSubscriber<Response<GoodsList>>(mAdapter, mDataList) {

            @Override
            public void onSuccess(Response<GoodsList> goodsListResponse) {
                handlerGoodsItem(goodsListResponse);
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @OnClick({R.id.layout_all_classifi, R.id.layout_sales_volume, R.id.layout_value})
    public void onClick(View view) {

        if (mDataType == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.layout_all_classifi:

                if (mDataType == null || mDataType.size() == 0) {
                    return;
                }

                int top = layoutClassification.getMeasuredHeight() + toolBar.getMeasuredHeight();

                SecondaryCityDialog dialog = new SecondaryCityDialog(getActivity(), mDataType, top);
                dialog.setOnClassificationSelectListener(new ClassificationSelectListener());
                dialog.show();
                break;
            case R.id.layout_sales_volume:
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                    lastClickTime = currentTime;
                    mGoodsListReqeust.setPageIndex(0);
                    mGoodsTypeReqeust.setTypeId(typeId);
                    mGoodsListReqeust.setValue("0");
                    tvValue.setText("价格");

                    if (mGoodsListReqeust.getSalesVolume().equals("0") || mGoodsListReqeust.getSalesVolume().equals("1")) {
                        mGoodsListReqeust.setSalesVolume("2");
                        tvSalesVolume.setText("从低到高");
                    } else if (mGoodsListReqeust.getSalesVolume().equals("2")) {
                        mGoodsListReqeust.setSalesVolume("1");
                        tvSalesVolume.setText("从高到低");
                    }
                    count++;
                    load();
                }
                break;
            case R.id.layout_value:
                long currentTime1 = Calendar.getInstance().getTimeInMillis();
                if (currentTime1 - lastClickTime > MIN_CLICK_DELAY_TIME) {
                    lastClickTime = currentTime1;
                    mGoodsListReqeust.setPageIndex(0);
                    mGoodsTypeReqeust.setTypeId(typeId);
                    mGoodsListReqeust.setSalesVolume("0");
                    tvSalesVolume.setText("销量");

                    if (mGoodsListReqeust.getValue().equals("1")) {
                        mGoodsListReqeust.setValue("2");
                        tvValue.setText("从低到高");
                    } else if (mGoodsListReqeust.getValue().equals("0") || mGoodsListReqeust.getValue().equals("2")) {
                        mGoodsListReqeust.setValue("1");
                        tvValue.setText("从高到低");
                    }
                    count++;
                    load();
                }
                break;
        }
    }

    /**
     * 处理商城数据
     *
     * @param response
     * @return
     */
    public void handlerSpecialLocalProduct(Response<SpecialLocalProduct> response) {


        RecyclerViewModel VIEW_TYPE_BANNER = new RecyclerViewModel();
        VIEW_TYPE_BANNER.setData(response.getData());
        VIEW_TYPE_BANNER.setItemType(Preferential99Adapter.VIEW_TYPE_BANNER);

//        RecyclerViewModel VIEW_TYPE_FILTER_TYPE = new RecyclerViewModel();
//        VIEW_TYPE_FILTER_TYPE.setData(response.getData());
//        VIEW_TYPE_FILTER_TYPE.setItemType(Preferential99Adapter.VIEW_TYPE_FILTER_TYPE);

        mDataList.getDataList().add(VIEW_TYPE_BANNER);
//        mDataList.getDataList().add(VIEW_TYPE_FILTER_TYPE);

    }

    /**
     * 处理商品列表
     *
     * @param response
     * @return
     */
    public void handlerGoodsItem(Response<GoodsList> response) {
        setResponseData(mDataList, response.getData());

        for (Goods goods : response.getData().getDataList()) {
            RecyclerViewModel recyclerViewModel = new RecyclerViewModel();
            recyclerViewModel.setData(goods);
            recyclerViewModel.setItemType(Preferential99Adapter.VIEW_TYPE_GOODS_ITEM);

            mDataList.getDataList().add(recyclerViewModel);
        }

    }

    /**
     * 设置通用的响应数据
     *
     * @param oldData
     * @param newData
     */
    private void setResponseData(DataList oldData, DataList newData) {
        oldData.setPageIndex(newData.getPageIndex());
        oldData.setPageItemCount(newData.getPageItemCount());
        oldData.setPageSize(newData.getPageSize());
    }

    /*@OnClick(R.id.btn_search)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putString(GoodsSearchActivity.KEY_MALL_TYPE, Constants.MALL_SPECIAL_LOCAL_PRODUCT);
        AppUtils.toActivity(getActivity(), GoodsSearchActivity.class, bundle);

    }*/

    private void loadType() {
        mGoodsTypeReqeust.setType("91");
        mGoodsTypeReqeust.setPageIndex(1);
        mGoodsTypeReqeust.setMallTyle(Constants.MALL_SPECIAL_LOCAL_PRODUCT);
        Request<GetGoodsListBean> request = new Request();
        request.setData(mGoodsTypeReqeust);
        RXUtils.request(getActivity(), request, "getGoodsType", new SupportSubscriber<Response<HeadquartersType>>() {

            @Override
            public void onNext(Response<HeadquartersType> listResponse) {
                if (typeListCount == 1) {
                    mDataType = listResponse.getData().getCityList();
                }
//                handlerCityItem(listResponse);
                loadGoodsList();
                if (typeListCount == 1) {
                    typeList = listResponse.getData().getTypeList();
                }
                if (count == 1) {
                    initTablayout();
                }
                typeListCount++;
                count++;
            }
        });
    }

    private void initTablayout() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View pwView = inflater.inflate(R.layout.special_local_popupwindow, null, false);
        initPwView(pwView);
        pw = new PopupWindow(pwView, ViewGroup.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 145, getResources().getDisplayMetrics()), true);
        for (int i = 0; i < typeList.size() + 1; i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            if (i != 0) {
                tab.setText(typeList.get(i - 1).getTypeName());
                tab.setTag(new TabBean(typeList.get(i - 1).getTypeItems(), typeList.get(i - 1).getTypeId()));
            } else {
                tab.setText("全部");
                tab.setTag(null);
            }
            mTabLayout.addTab(tab);
            if (i != 0 && typeId != null && typeId.equals(typeList.get(i - 1).getTypeId())) {
                truePosition = i;
                mTabLayout.getTabAt(truePosition).select();
            }
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                showPw(tab);
                if (tab.getPosition() != 0) {
                    typeId = typeList.get(tab.getPosition() - 1).getTypeId();
                } else {
                    typeId = null;
                }
                mGoodsListReqeust.setPageIndex(0);
                count++;
                typeListCount++;
                load();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                showPw(tab);
            }
        });
    }

    private void showPw(TabLayout.Tab tab) {
        position = tab.getPosition();
//        List<GoodsType> goods = typeList.get(position).getTypeItems();
        TabBean tabBean = (TabBean) tab.getTag();
        List<GoodsType> goods = tabBean.list;
            /*if (!goods.get(0).getTypeId().equals(typeList.get(position).getTypeId())) {
                goods.add(0, new GoodsType(typeList.get(position).getTypeId(), "全部", null, false));
            }*/
        if (!goods.get(0).getTypeId().equals(tabBean.typeId)) {
            goods.add(0, new GoodsType(tabBean.typeId, "全部", null, false));
        }
        mGridAdapter = new GridAdapter(goods);
        gridview.setAdapter(mGridAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                typeId = goods.get(i).getTypeId();
                goods.get(i).setSelect(true);
                updateGridItem(goods, i);
                mGoodsListReqeust.setPageIndex(0);
                count++;
                typeListCount++;
                load();
                pw.dismiss();
                mGridAdapter.notifyDataSetChanged();
            }
        });
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.showAsDropDown(mTabLayout, 0, 0);
    }

    private void updateGridItem(List<GoodsType> goods, int i) {
        for (int j = 0; j < goods.size(); j++) {
            if (j != i) {
                goods.get(j).setSelect(false);
            }
        }

        for (int m = 0; m < typeList.size(); m++) {
            if (m != position) {
                List<GoodsType> items = typeList.get(m).getTypeItems();
                for (GoodsType item : items) {
                    item.setSelect(false);
                }
            }

        }
    }

    /**
     * 处理商品列表
     *
     * @param response
     * @return
     */
    public void handlerCityItem(Response<HeadquartersType> response) {

        RecyclerViewModel recyclerViewModel = new RecyclerViewModel();
        recyclerViewModel.setData(response.getData().getCityList());
        recyclerViewModel.setItemType(SpecialLocalProductAdapter.VIEW_TYPE_CITY);

        mDataList.getDataList().add(recyclerViewModel);


    }

    class ClassificationSelectListener implements SecondaryCityDialog.OnClassificationSelectListener {
        @Override
        public void select(CityItems type) {
            mGoodsListReqeust.setPageIndex(0);
            mGoodsListReqeust.setValue("0");
            tvValue.setText("价格");
            mGoodsListReqeust.setSalesVolume("0");
            tvSalesVolume.setText("销量");

//            mGoodsListReqeust.setTypeId(type.getCityId());
            cityId = type.getCityId();
            //    toolBar.setTitle(type.getTypeName());
            typeListCount++;
            count++;
            load();
        }
    }

    class TabBean {
        private List<GoodsType> list;
        private String typeId;

        public TabBean(List<GoodsType> list, String typeId) {
            this.list = list;
            this.typeId = typeId;
        }
    }

    private void initPwView(View pwView) {
//        flexBox = (FlexboxLayout) pwView.findViewById(R.id.flex_box);
        gridview = (GridView) pwView.findViewById(R.id.gridview);
    }

    @Subscribe
    public void getCityChangeMsg(CityItems type) {
        mGoodsListReqeust.setPageIndex(0);
        mGoodsListReqeust.setValue("0");
        tvValue.setText("价格");
        mGoodsListReqeust.setSalesVolume("0");
        tvSalesVolume.setText("销量");

        mGoodsListReqeust.setTypeId(type.getCityId());
        typeId = type.getCityId();
        //    toolBar.setTitle(type.getTypeName());
        load();
        Toast.makeText(SpecialLocalProductFragment.this.getActivity(), typeId, Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void getNumChangeMsg(UpdateMallMessage message) {
        mGoodsListReqeust.setPageIndex(0);
        mGoodsTypeReqeust.setTypeId(typeId);
        mGoodsListReqeust.setValue("0");
        load();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    class GridAdapter extends BaseAdapter {

        public GridAdapter(List<GoodsType> list) {
            this.list = list;
        }

        private List<GoodsType> list;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            MyHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.gridview_item, null, false);
                holder = new MyHolder();
                holder.typeName = (TextView) convertView.findViewById(R.id.type_name_tv);
                convertView.setTag(holder);
            } else holder = (MyHolder) convertView.getTag();
            GoodsType type = list.get(i);
            holder.typeName.setText(type.getTypeName());
            holder.typeName.setBackground(getResources().getDrawable(R.drawable.tv_bg));
            if (type.getSelect()) {
                holder.typeName.setTextColor(Color.parseColor("#ff2b49"));
            } else holder.typeName.setTextColor(Color.parseColor("#666666"));
            return convertView;
        }

        class MyHolder {
            TextView typeName;
        }
    }
}
