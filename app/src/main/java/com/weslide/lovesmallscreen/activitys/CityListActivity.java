package com.weslide.lovesmallscreen.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.DownloadImageService;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.models.Zone;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateMallMessage;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.views.custom.LetterIndexView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/12.
 * 城市列表
 */
public class CityListActivity extends BaseActivity {
    public final static String TAG = "CityListActivity";
    private final int MAX_HOTCITY = 6;
    private final int NUM_ALPHA = 26;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    private ListView mCityListView;
    private List<Zone> mCityList = new ArrayList<Zone>();
    private Zone mCurrentCity;
    private Map<String, String> mAlphaPosition = new HashMap<String, String>();
    //private ZoneListAO mZoneList;
    private LinearLayout header;
    private GridView hotcityGridView;
    public TextView locationTextView;
    private LetterIndexView mLetterView;
    private SharedPreferences sp;
//    public static String zoneName;
//    public static String zoneId;
//    public static String zoneEnglishChar;
//    public static String zoneLevel;


    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_city_list);
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //初始化定位信息
        findViews();
        init();
        mLetterView.setUpdateListView(new LetterIndexView.UpdateListView() {
            @Override
            public void updateListView(String firstLetter) {
                int position = Integer.parseInt(mAlphaPosition.get(firstLetter));
                //View item = mCityListView.getChildAt(position);
                mCityListView.setSelection(position-1);
            }
        });
        mCityListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                String englishChar = mCityList.get(firstVisibleItem).getEnglishChar();
                mLetterView.updateLetterIndexView(englishChar);
            }
        });
    }


    private void findViews() {
        header = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.city_lv_header_citylist, null, false);
        hotcityGridView = (GridView) header.findViewById(R.id.lv_header_citylist_mGridViewHotCity);
        mLetterView = (LetterIndexView) findViewById(R.id.letter_index_view);
        mCityListView = (ListView) findViewById(R.id.activity_citylist_mListView);
//        mCityListView.addHeaderView(header, null, false);


        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        hotcityGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.item_hotcity_mTvCity);
                Zone ao = (Zone) tv.getTag();
                ContextParameter.setCurrentZone(ao);
                EventBus.getDefault().post(new UpdateMallMessage());
                finish();
            }

        });


        mCityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (view instanceof LinearLayout) {
                    TextView tv = (TextView) view.findViewById(R.id.item_city_mTvCity);
                    Zone ao = (Zone) tv.getTag();
                    ContextParameter.setCurrentZone(ao);

                    /*zoneEnglishChar = ao.getEnglishChar();
                    zoneId = ao.getZoneId();
                    zoneLevel = ao.getLevel();
                    zoneName = ao.getName();*/

                    EventBus.getDefault().post(new UpdateMallMessage());
                    Intent intent = new Intent(CityListActivity.this, DownloadImageService.class);
                    intent.putExtra(DownloadImageService.KEY_FRESHEN, true);
                    CityListActivity.this.startService(intent);
                    finish();
                }
            }
        });

    }


    private void init() {
        mCityList = getSupportApplication().getDaoSession().getZoneDao().loadAllCity();

        for (int i = 0; i < mCityList.size(); i++) {
            String alpha = mCityList.get(i).getEnglishChar().toUpperCase();
            if (!mAlphaPosition.containsKey(alpha))
                mAlphaPosition.put(mCityList.get(i).getEnglishChar().toString(), i + 1 + "");
        }

        initAlphaButtons();

        //设置ListView适配器
        hotcityGridView.setAdapter(hotcityAdapter);
        mCityListView.setAdapter(cityAdapter);

        cityAdapter.notifyDataSetChanged();

    }


    /**
     * 初始化首字母按键
     **/
    private void initAlphaButtons() {

        LinearLayout layout1 = (LinearLayout) header.findViewById(R.id.lv_header_citylist_container1);
        LinearLayout layout2 = (LinearLayout) header.findViewById(R.id.lv_header_citylist_container2);
        LinearLayout layout3 = (LinearLayout) header.findViewById(R.id.lv_header_citylist_container3);
        LinearLayout layout4 = (LinearLayout) header.findViewById(R.id.lv_header_citylist_container4);
        List<LinearLayout> layoutList = new ArrayList<LinearLayout>();
        layoutList.add(layout1);
        layoutList.add(layout2);
        layoutList.add(layout3);
        layoutList.add(layout4);

        //所有按键设置统一监听器，并且根据mAlphaPostiton启用按键，mAlphaPosition是首字母与listview item的对应位置
        for (int i = 0; i < 4; i++) {
            LinearLayout layout = layoutList.get(i);
            for (int index = 0; index < layout.getChildCount(); index++) {
                View child = layout.getChildAt(index);
                if (child instanceof Button) {
                    child.setOnClickListener(alphaBtnListener);
                    if (mAlphaPosition.containsKey(((Button) child).getText().toString()))
                        child.setEnabled(true);
                }
            }
        }

    }


    /**
     * 首字母按键统一监听器
     **/
    private View.OnClickListener alphaBtnListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            int position = Integer.parseInt(mAlphaPosition.get(btn.getText()));
            //View item = mCityListView.getChildAt(position);
            mCityListView.setSelection(position);
        }
    };


    /**
     * 热门城市GridView适配器
     **/
    private BaseAdapter hotcityAdapter = new BaseAdapter() {

        @Override
        public int getCount() {

            return MAX_HOTCITY < mCityList.size() ? MAX_HOTCITY : mCityList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder_HotCity holder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.city_item_hotcity,
                        null);
                holder = new ViewHolder_HotCity();
                holder.mTvCity = (TextView) convertView
                        .findViewById(R.id.item_hotcity_mTvCity);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder_HotCity) convertView.getTag();
            }

            holder.mTvCity.setText(mCityList.get(position).getName());
            holder.mTvCity.setTag(mCityList.get(position));
            return convertView;
        }

    };


    /**
     * 城市列表适配器
     **/
    private BaseAdapter cityAdapter = new BaseAdapter() {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder_City holder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.city_item_city,
                        null);
                holder = new ViewHolder_City();
                holder.mTvCatalog = (TextView) convertView
                        .findViewById(R.id.item_city_mTvCatalog);
                holder.mTvCity = (TextView) convertView
                        .findViewById(R.id.item_city_mTvCity);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder_City) convertView.getTag();
            }

            Zone zoneAO = mCityList.get(position);
            String catalog = zoneAO.getEnglishChar();
            holder.mTvCatalog.setText(catalog);
            holder.mTvCity.setText(zoneAO.getName());
            holder.mTvCity.setTag(zoneAO);
            if (position == 0) {
                L.e("position:" + position);
            }

            if (position > 0) {
                String lastCatalog = mCityList.get(position - 1)
                        .getEnglishChar();
                if (catalog.equals(lastCatalog)) {
                    holder.mTvCatalog.setVisibility(View.GONE);
                } else {
                    holder.mTvCatalog.setVisibility(View.VISIBLE);
                }
            } else {
                holder.mTvCatalog.setVisibility(View.VISIBLE);
            }
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return mCityList.size();
        }
    };


    class ViewHolder_HotCity {
        TextView mTvCity;
    }


    class ViewHolder_City {
        TextView mTvCatalog;
        TextView mTvCity;
    }

}
