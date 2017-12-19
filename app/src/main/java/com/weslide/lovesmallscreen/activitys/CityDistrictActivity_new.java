package com.weslide.lovesmallscreen.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.weslide.lovesmallscreen.view_yy.util.ChineseToPinyinHelper;
import com.weslide.lovesmallscreen.view_yy.util.SpUtil;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;
import com.weslide.lovesmallscreen.views.custom.LetterIndexView;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

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
    private List<Zone> allcityList = new ArrayList<>();
    private List<Zone> cityList;
    private CityDistrictAdapter adapter;
    private LetterIndexView indexView;
    private Map<String, Integer> mAlphaPosition = new HashMap<>();
    private TextView showLetterTv;
    private List<Zone> hotCityList, totalCityList;
    private View lvHeader;
    private TextView current_city_tv, location_city_tv, latest_city_tv1, latest_city_tv2, latest_city_tv3;
    private RelativeLayout select_area_rll;
    private GridView hot_city_gv, area_gv;
    private ImageView select_area_iv;
    private Zone currentZone;
    private List<Zone> areaList = new ArrayList<Zone>();
    private AreaListGvAdapter areaListGvAdapter, hotCityGvAdapter;
    private FrameLayout alpha_bg;
    private EditText search_value_edt;
    private String LATEST_CITY = "LATEST_CITY";
    private List<Zone> latest_city_list;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 8:
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    break;
            }
        }
    };
    private LoadingDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citydistrict_new);
        initView();
        initData();
    }

    private void initData() {
        latest_city_list = SpUtil.getList(this, LATEST_CITY);
        if (latest_city_list == null) {
            latest_city_list = new ArrayList<>();
            latest_city_list.add(new Zone("2", "广州市", "GuangZhouShi", "G", "1961", "1960"));
            latest_city_list.add(new Zone("2", "上海市", "ShangHaiShi", "S", "800", "799"));
            latest_city_list.add(new Zone("2", "北京市", "BeiJingShi", "B", "2", "1"));
        }
        latest_city_tv1.setText(latest_city_list.get(0).getName());
        latest_city_tv2.setText(latest_city_list.get(1).getName());
        latest_city_tv3.setText(latest_city_list.get(2).getName());
        latest_city_tv1.setOnClickListener(this);
        latest_city_tv2.setOnClickListener(this);
        latest_city_tv3.setOnClickListener(this);
        customToolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        select_area_rll.setOnClickListener(this);
        search_value_edt.setOnClickListener(this);
        search_value_edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    dialog = new LoadingDialog(CityDistrictActivity_new.this);
                    dialog.show();
                    String searchValue = textView.getText().toString();
                    allcityList.clear();
                    if (searchValue != null && searchValue.length() > 0) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (Zone zone : totalCityList) {
                                    if (zone.getName().contains(searchValue) || new ChineseToPinyinHelper().getPinyin(zone.getName()).contains(searchValue)) {
                                        allcityList.add(zone);
                                    }
                                }
                                mHandler.sendEmptyMessage(8);
                            }
                        }).start();
                    } else {
                        allcityList.addAll(cityList);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        cityList = getSupportApplication().getDaoSession().getZoneDao().loadAllCity();
        allcityList.clear();
        allcityList.addAll(cityList);
        hotCityList = ContextParameter.getHotCityList();
        totalCityList = ContextParameter.getAllCityList();
        currentZone = ContextParameter.getCurrentZone();
        if (currentZone.getLevel().equals("2")) {
            List<Zone> zones = getSupportApplication().getDaoSession().getZoneDao().loadDistrictList(currentZone.getZoneId());
            if (zones != null) {
                areaList.addAll(zones);
            }
        } else {
            List<Zone> districtList = getSupportApplication().getDaoSession().getZoneDao().loadDistrictList(currentZone.getParentZoneId());
            if (districtList != null) {
                areaList.addAll(districtList);
            }
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
            current_city_tv.setText("当前:" + getSupportApplication().getDaoSession().getZoneDao().loadCityByZoneId(currentZone.getParentZoneId()).getName());
        } else {
            current_city_tv.setText("当前:" + currentZone.getName());
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Location location = LocationManager.syncGetLocation();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (location != null && location.getCity() != null) {
                            location_city_tv.setEnabled(true);
                            location_city_tv.setText(location.getCity());
                            location_city_tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Zone zone = getSupportApplication().getDaoSession().getZoneDao()
                                            .loadDistrictByZoneName(location.getCity(), location.getDistrict());
                                    ContextParameter.setCurrentZone(zone);
                                    EventBus.getDefault().post(new UpdateMallMessage());
                                    Intent intent = new Intent(CityDistrictActivity_new.this, DownloadImageService.class);
                                    intent.putExtra(DownloadImageService.KEY_FRESHEN, true);
                                    CityDistrictActivity_new.this.startService(intent);
                                    getSharedPreferences("ZONEFILE", MODE_PRIVATE).edit().putString("LEVEL", zone.getLevel()).putString("NAME", zone.getName()).putString("PINYIN", zone.getPinYin()).putString("ENGLISHCHAR", zone.getEnglishChar()).putString("ZONEID", zone.getZoneId()).putString("PARENTZONEID", zone.getParentZoneId()).commit();
                                    CityDistrictActivity_new.this.finish();
                                    updateLatestCityList(zone);
                                }
                            });
                        } else {
                            location_city_tv.setEnabled(false);
                            location_city_tv.setText("未开启定位");
                        }
                    }
                });
            }
        }).start();
        adapter = new CityDistrictAdapter(this, allcityList);
        areaListGvAdapter = new AreaListGvAdapter(this, areaList);
        hotCityGvAdapter = new AreaListGvAdapter(this, hotCityList);
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
                    getSharedPreferences("ZONEFILE", MODE_PRIVATE).edit().putString("LEVEL", zone.getLevel()).putString("NAME", zone.getName()).putString("PINYIN", zone.getPinYin()).putString("ENGLISHCHAR", zone.getEnglishChar()).putString("ZONEID", zone.getZoneId()).putString("PARENTZONEID", zone.getParentZoneId()).commit();
                    CityDistrictActivity_new.this.finish();
                    updateLatestCityList(zone);
                    putZoneId(zone);
                }
            }
        });
        area_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*Zone zone = areaList.get(i);
                if (currentZone.getZoneId().equals(zone.getZoneId())) {
                    zone = currentZone;
                }
                ContextParameter.setCurrentZone(zone);
                getSharedPreferences("ZONEFILE", MODE_PRIVATE).edit().putString("LEVEL", zone.getLevel()).putString("NAME", zone.getName()).putString("PINYIN",zone.getPinYin()).putString("ENGLISHCHAR",zone.getEnglishChar()).putString("ZONEID",zone.getZoneId()).putString("PARENTZONEID",zone.getParentZoneId()).commit();
                EventBus.getDefault().post(new UpdateMallMessage());
                finish();
                updateLatestCityList(zone);*/

                Zone zone = areaList.get(i);
                ContextParameter.setCurrentZone(zone);
                EventBus.getDefault().post(new UpdateMallMessage());
                Intent intent = new Intent(CityDistrictActivity_new.this, DownloadImageService.class);
                intent.putExtra(DownloadImageService.KEY_FRESHEN, true);
                CityDistrictActivity_new.this.startService(intent);
                getSharedPreferences("ZONEFILE", MODE_PRIVATE).edit().putString("LEVEL", zone.getLevel()).putString("NAME", zone.getName()).putString("PINYIN", zone.getPinYin()).putString("ENGLISHCHAR", zone.getEnglishChar()).putString("ZONEID", zone.getZoneId()).putString("PARENTZONEID", zone.getParentZoneId()).commit();
                CityDistrictActivity_new.this.finish();
                updateLatestCityList(zone);
                putZoneId(zone);
            }
        });
        hot_city_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Zone zone = hotCityList.get(i);
                ContextParameter.setCurrentZone(zone);
                EventBus.getDefault().post(new UpdateMallMessage());
                Intent intent = new Intent(CityDistrictActivity_new.this, DownloadImageService.class);
                intent.putExtra(DownloadImageService.KEY_FRESHEN, true);
                CityDistrictActivity_new.this.startService(intent);
                getSharedPreferences("ZONEFILE", MODE_PRIVATE).edit().putString("LEVEL", zone.getLevel()).putString("NAME", zone.getName()).putString("PINYIN", zone.getPinYin()).putString("ENGLISHCHAR", zone.getEnglishChar()).putString("ZONEID", zone.getZoneId()).putString("PARENTZONEID", zone.getParentZoneId()).commit();
                CityDistrictActivity_new.this.finish();
                updateLatestCityList(zone);
                putZoneId(zone);
            }
        });
        indexView.setShowLetterTv(showLetterTv);
        indexView.setUpdateListView(new LetterIndexView.UpdateListView() {
            @Override
            public void updateListView(String firstLetter) {
                Integer position = mAlphaPosition.get(firstLetter);
                mLv.setSelection(position + 1);
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

    private void updateLatestCityList(Zone zone) {
        int pos = latest_city_list.size() - 1;
        for (int i = 0; i < latest_city_list.size(); i++) {
            if (zone.getName().equals(latest_city_list.get(i).getName())) {
                pos = i;
            }
        }
        latest_city_list.remove(pos);
        latest_city_list.add(0, zone);
        SpUtil.putList(CityDistrictActivity_new.this, LATEST_CITY, latest_city_list);
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
        select_area_rll = ((RelativeLayout) lvHeader.findViewById(R.id.select_area_rll));
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
            case R.id.select_area_rll:
                if (area_gv.getVisibility() == View.GONE) {
                    area_gv.setVisibility(View.VISIBLE);
                    select_area_iv.setImageResource(R.drawable.icon_xialashang);
                } else if (area_gv.getVisibility() == View.VISIBLE) {
                    area_gv.setVisibility(View.GONE);
                    select_area_iv.setImageResource(R.drawable.icon_xialaxia);
                }
                break;
            case R.id.search_value_edt:
//                mLv.smoothScrollToPosition(0);
                break;
            case R.id.latest_city_tv1:
                Zone zone = latest_city_list.get(0);
                ContextParameter.setCurrentZone(zone);
                EventBus.getDefault().post(new UpdateMallMessage());
                Intent intent = new Intent(CityDistrictActivity_new.this, DownloadImageService.class);
                intent.putExtra(DownloadImageService.KEY_FRESHEN, true);
                CityDistrictActivity_new.this.startService(intent);
                getSharedPreferences("ZONEFILE", MODE_PRIVATE).edit().putString("LEVEL", zone.getLevel()).putString("NAME", zone.getName()).putString("PINYIN", zone.getPinYin()).putString("ENGLISHCHAR", zone.getEnglishChar()).putString("ZONEID", zone.getZoneId()).putString("PARENTZONEID", zone.getParentZoneId()).commit();
                CityDistrictActivity_new.this.finish();
                updateLatestCityList(zone);
                putZoneId(zone);
                break;
            case R.id.latest_city_tv2:
                Zone zone1 = latest_city_list.get(1);
                if (currentZone.getZoneId().equals(zone1.getZoneId())) {
                    zone1 = currentZone;
                }
                ContextParameter.setCurrentZone(zone1);
                getSharedPreferences("ZONEFILE", MODE_PRIVATE).edit().putString("LEVEL", zone1.getLevel()).putString("NAME", zone1.getName()).putString("PINYIN", zone1.getPinYin()).putString("ENGLISHCHAR", zone1.getEnglishChar()).putString("ZONEID", zone1.getZoneId()).putString("PARENTZONEID", zone1.getParentZoneId()).commit();
                EventBus.getDefault().post(new UpdateMallMessage());
                finish();
                updateLatestCityList(zone1);
                putZoneId(zone1);
                break;
            case R.id.latest_city_tv3:
                Zone zone2 = latest_city_list.get(2);
                if (currentZone.getZoneId().equals(zone2.getZoneId())) {
                    zone2 = currentZone;
                }
                ContextParameter.setCurrentZone(zone2);
                getSharedPreferences("ZONEFILE", MODE_PRIVATE).edit().putString("LEVEL", zone2.getLevel()).putString("NAME", zone2.getName()).putString("PINYIN", zone2.getPinYin()).putString("ENGLISHCHAR", zone2.getEnglishChar()).putString("ZONEID", zone2.getZoneId()).putString("PARENTZONEID", zone2.getParentZoneId()).commit();
                EventBus.getDefault().post(new UpdateMallMessage());
                finish();
                updateLatestCityList(zone2);
                putZoneId(zone2);
                break;
        }
    }

    private void putZoneId(Zone zone2) {
        SharedPreferences mSharedPreferences = getSharedPreferences("Location_ZONG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("LOCATION_ZONG_ID",zone2.getZoneId());
        editor.commit();
    }
}
