package com.weslide.lovesmallscreen.activitys;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.models.Zone;
import com.weslide.lovesmallscreen.models.eventbus_message.UpdateMallMessage;
import com.weslide.lovesmallscreen.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;

public class CityDistrictActivity extends BaseActivity implements OnClickListener {
    private static final String TAG = CityDistrictActivity.class.getSimpleName();

    private GridView mGVCategory;

    private TextView mCurrentCity;//显示当前城市
    private RelativeLayout mChangeCity;//更换城市

    private List<Zone> zoneAOs = new ArrayList<Zone>();

    private Zone currentZone;//传递过来的选中的城市
    private String districtZoneId = null;//是否选中过县级

    private View currentView;
    private GridAdapter myAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_city_district);

        currentZone = ContextParameter.getCurrentZone();
        if(currentZone.getLevel().equals("2")){
            zoneAOs = getSupportApplication().getDaoSession().getZoneDao().loadDistrictList(currentZone.getZoneId());
        } else {
            zoneAOs = getSupportApplication().getDaoSession().getZoneDao().loadDistrictList(currentZone.getParentZoneId());
        }

        if(zoneAOs.size()>0)
            insertDefaultZone(zoneAOs);

        init();
        fetchZoneByCurrentCity();

    }

    protected void init() {
        currentView = findViewById(R.id.activty_category_root);
        mGVCategory = (GridView) findViewById(R.id.activity_category_mGvCategory);
        mChangeCity = (RelativeLayout) findViewById(R.id.rl_changecity);
        mCurrentCity = (TextView) findViewById(R.id.tv_location_currentcity);

        if(currentZone.getLevel().equals("3")){
            mCurrentCity.setText("当前城市:" + getSupportApplication().getDaoSession()
                    .getZoneDao().loadCityByZoneId(currentZone.getParentZoneId()).getName());
        } else {
            mCurrentCity.setText("当前城市:" + currentZone.getName());
        }


        myAdapter = new GridAdapter();
        mGVCategory.setAdapter(myAdapter);

        mChangeCity.setOnClickListener(this);

        currentView.setOnClickListener(this);
    }

    private void insertDefaultZone(List<Zone> list) {
        Zone zone = new Zone();
        zone.setZoneId(currentZone.getZoneId());
        zone.setName("全城");
        zone.setEnglishChar(null);
        zone.setLevel(null);
        list.add(0, zone);
    }


    private void fetchZoneByCurrentCity() {

        myAdapter.notifyDataSetChanged();
    }


    //重写BaseAdapter的getView方法，使输入的data转换为View
    public class GridAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //创建或获取ViewHolder对象holder，用于返回最终的convertView
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(
                        R.layout.city_item_category, null);
                holder.countyname = (Button) convertView.findViewById(R.id.item_btn_city_county);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Zone zoneao = zoneAOs.get(position);
            holder.countyname.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_gray));
            if (districtZoneId == null) {
                if ((zoneao.getEnglishChar() == null && zoneao.getLevel() == null
                        && zoneao.getName().equals("全城"))
                        ) {
//					holder.countyname.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_currentcity_county_background_check));
                    holder.countyname.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_gray));
                }
            } else {
                if (zoneao.getZoneId().equals(districtZoneId))
//					holder.countyname.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_currentcity_county_background_check));
                    holder.countyname.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_gray));
            }
            holder.countyname.setText(zoneao.getName());
            holder.countyname.setTag(zoneao);


            holder.countyname.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_MOVE:
//						v.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_currentcity_county_background_check));
                            v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_gray));
                            break;
                        default:
                            v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_gray));
                            break;
                    }
                    return false;
                }
            });

            holder.countyname.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Zone zone = (Zone) v.getTag();

                    if(zone.getZoneId().equals( currentZone.getZoneId())){
                        zone = currentZone;
                    }

                    ContextParameter.setCurrentZone(zone);
                    finish();

                    EventBus.getDefault().post(new UpdateMallMessage());
                }
            });

            return convertView;
            //return itemView[position];
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
            return zoneAOs.size();
        }
    }


    private class ViewHolder {
        //ImageView icon;
        Button countyname;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rl_changecity:
                AppUtils.toActivity(this, CityListActivity.class);
                finish();
                break;
            case R.id.activty_category_root:
                finish();
                break;
        }
    }

}
