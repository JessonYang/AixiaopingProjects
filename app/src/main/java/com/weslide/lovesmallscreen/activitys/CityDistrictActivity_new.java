package com.weslide.lovesmallscreen.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.DownloadImageService;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.managers.LocationManager;
import com.weslide.lovesmallscreen.models.Location;
import com.weslide.lovesmallscreen.models.Zone;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateMallMessage;
import com.weslide.lovesmallscreen.view_yy.adapter.AreaListGvAdapter;
import com.weslide.lovesmallscreen.view_yy.adapter.CityDistrictAdapter;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;
import com.weslide.lovesmallscreen.views.custom.LetterIndexView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YY on 2017/9/25.
 */
public class CityDistrictActivity_new extends BaseActivity implements View.OnClickListener {

    private CustomToolbar customToolbar;
    private ListView mLv;
    private List<Zone> cityList;
    private CityDistrictAdapter adapter;
    private LetterIndexView indexView;
    private Map<String, Integer> mAlphaPosition = new HashMap<>();
    private TextView showLetterTv;
    private List<Zone> hotCityList;
    private View lvHeader;
    private TextView current_city_tv, location_city_tv, latest_city_tv1, latest_city_tv2, latest_city_tv3;
    private LinearLayout select_area_ll;
    private GridView hot_city_gv, area_gv;
    private ImageView select_area_iv;
    private Zone currentZone;
    private List<Zone> areaList = new ArrayList<Zone>();
    private AreaListGvAdapter areaListGvAdapter,hotCityGvAdapter;
    private FrameLayout alpha_bg;
    private EditText search_value_edt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citydistrict_new);
        initView();
        initData();
    }

    private void initData() {
        customToolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        select_area_ll.setOnClickListener(this);
        search_value_edt.setOnClickListener(this);
        cityList = getSupportApplication().getDaoSession().getZoneDao().loadAllCity();
        hotCityList = ContextParameter.getHotCityList();
        currentZone = ContextParameter.getCurrentZone();
        if (currentZone.getLevel().equals("2")) {
            areaList = getSupportApplication().getDaoSession().getZoneDao().loadDistrictList(currentZone.getZoneId());
        } else {
            areaList = getSupportApplication().getDaoSession().getZoneDao().loadDistrictList(currentZone.getParentZoneId());
        }
        if (areaList.size() > 0) {
            insertDefaultZone(areaList);
        }
        for (int i = 0; i < cityList.size(); i++) {
            String alpha = cityList.get(i).getEnglishChar().toUpperCase();
            if (!mAlphaPosition.containsKey(alpha))
                mAlphaPosition.put(alpha, i);
        }
        if (currentZone.getLevel().equals("3")) {
            current_city_tv.setText("当前:"+getSupportApplication().getDaoSession().getZoneDao().loadCityByZoneId(currentZone.getParentZoneId()).getName());
        }else {
            current_city_tv.setText("当前:"+currentZone.getName());
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Location location = LocationManager.syncGetLocation();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        location_city_tv.setText(location.getCity());
                    }
                });
            }
        }).start();
        adapter = new CityDistrictAdapter(this, cityList);
        areaListGvAdapter = new AreaListGvAdapter(this,areaList);
        hotCityGvAdapter = new AreaListGvAdapter(this,hotCityList);
        mLv.setAdapter(adapter);
        area_gv.setAdapter(areaListGvAdapter);
        hot_city_gv.setAdapter(hotCityGvAdapter);
        mLv.addHeaderView(lvHeader);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view instanceof LinearLayout) {
                    TextView city_name_tv = (TextView) view.findViewById(R.id.city_name_tv);
                    Zone zone = (Zone) city_name_tv.getTag();
                    ContextParameter.setCurrentZone(zone);
                    EventBus.getDefault().post(new UpdateMallMessage());
                    Intent intent = new Intent(CityDistrictActivity_new.this, DownloadImageService.class);
                    intent.putExtra(DownloadImageService.KEY_FRESHEN, true);
                    CityDistrictActivity_new.this.startService(intent);
                    CityDistrictActivity_new.this.finish();
                }
            }
        });
        area_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Zone zone = areaList.get(i);
                if (currentZone.getZoneId().equals(zone.getZoneId())) {
                    zone = currentZone;
                }
                ContextParameter.setCurrentZone(zone);
                EventBus.getDefault().post(new UpdateMallMessage());
                finish();
            }
        });
        hot_city_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Zone zone = hotCityList.get(i);
                if (currentZone.getZoneId().equals(zone.getZoneId())) {
                    zone = currentZone;
                }
                ContextParameter.setCurrentZone(zone);
                EventBus.getDefault().post(new UpdateMallMessage());
                finish();
            }
        });
        indexView.setShowLetterTv(showLetterTv);
        indexView.setUpdateListView(new LetterIndexView.UpdateListView() {
            @Override
            public void updateListView(String firstLetter) {
                Integer position = mAlphaPosition.get(firstLetter);
                mLv.setSelection(position+1);
            }
        });
        mLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                String englishChar = cityList.get(i).getEnglishChar();
                indexView.updateLetterIndexView(englishChar);
            }
        });
    }

    private void initView() {
        customToolbar = ((CustomToolbar) findViewById(R.id.custom_tool_bar));
        mLv = ((ListView) findViewById(R.id.city_list));
        indexView = ((LetterIndexView) findViewById(R.id.letter_index_view));
        showLetterTv = ((TextView) findViewById(R.id.showLetterTv));
        alpha_bg = ((FrameLayout) findViewById(R.id.alpha_bg));
        lvHeader = getLayoutInflater().inflate(R.layout.city_district_list_header, null);
        current_city_tv = ((TextView) lvHeader.findViewById(R.id.current_city_tv));
        location_city_tv = ((TextView) lvHeader.findViewById(R.id.location_city_tv));
        latest_city_tv1 = ((TextView) lvHeader.findViewById(R.id.latest_city_tv1));
        latest_city_tv2 = ((TextView) lvHeader.findViewById(R.id.latest_city_tv2));
        latest_city_tv3 = ((TextView) lvHeader.findViewById(R.id.latest_city_tv3));
        select_area_ll = ((LinearLayout) lvHeader.findViewById(R.id.select_area_ll));
        hot_city_gv = ((GridView) lvHeader.findViewById(R.id.hot_city_gv));
        area_gv = ((GridView) lvHeader.findViewById(R.id.area_gv));
        select_area_iv = ((ImageView) lvHeader.findViewById(R.id.select_area_iv));
        search_value_edt = ((EditText) lvHeader.findViewById(R.id.search_value_edt));
    }

    private void insertDefaultZone(List<Zone> list) {
        Zone zone = new Zone();
        zone.setZoneId(currentZone.getZoneId());
        zone.setName("全城");
        zone.setEnglishChar(null);
        zone.setLevel(null);
        list.add(0, zone);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_area_ll:
                if (area_gv.getVisibility() == View.GONE) {
                    area_gv.setVisibility(View.VISIBLE);
                    select_area_iv.setImageResource(R.drawable.icon_xialashang);
                } else if (area_gv.getVisibility() == View.VISIBLE) {
                    area_gv.setVisibility(View.GONE);
                    select_area_iv.setImageResource(R.drawable.icon_xialaxia);
                }
                break;
            case R.id.search_value_edt:

                break;
        }
    }
}
