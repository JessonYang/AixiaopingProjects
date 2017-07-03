package com.weslide.lovesmallscreen.views.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.weslide.lovesmallscreen.models.CityItems;
import com.weslide.lovesmallscreen.models.CityType;

import net.aixiaoping.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2016/6/23.
 * 级联类型选择视图
 */
public class SecondaryCityDialog_new extends android.app.Dialog {

    List<CityType> mTypes = new ArrayList<>();
    List<CityItems> mTowTypes = new ArrayList<>();
    ListView lvOneLevel;
    BaseAdapter oneLevelAdapter;
    ListView lvTowLevel;
    BaseAdapter towLevelAdapter;
    int top;
    int width;
    OnClassificationSelectListener mOnClassificationSelectListener;
    private WindowManager wm;

    public SecondaryCityDialog_new(Context context, List<CityType> types, int top) {
        super(context, R.style.secondaryClassificationDialog);
        mTypes = types;
        this.top = top;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();

        mTowTypes.addAll(getSelectGoodsTypeItems());
    }

    public SecondaryCityDialog_new(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SecondaryCityDialog_new(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setOnClassificationSelectListener(OnClassificationSelectListener onClassificationSelectListener) {
        mOnClassificationSelectListener = onClassificationSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_secondary_classification_new);
        lvOneLevel = (ListView) findViewById(R.id.lv_one_level);
        lvTowLevel = (ListView) findViewById(R.id.lv_tow_level);
        oneLevelAdapter = new OneLevelAdapter(getContext(), mTypes);
        towLevelAdapter = new TowLevelAdapter(getContext(), mTowTypes);

        lvOneLevel.setAdapter(oneLevelAdapter);
        lvTowLevel.setAdapter(towLevelAdapter);

        lvOneLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTowTypes.clear();
                resetGoodsTypeSelect();
                mTypes.get(position).setSelect(true);
                mTowTypes.addAll(mTypes.get(position).getCityItemses());

                oneLevelAdapter.notifyDataSetChanged();
                towLevelAdapter.notifyDataSetChanged();
            }
        });

        lvTowLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetGoodsTypeItemSelect();
                mTowTypes.get(position).setSelect(true);
                if (mOnClassificationSelectListener != null)
                    mOnClassificationSelectListener.select(mTowTypes.get(position));
                dismiss();
            }
        });

    }

    @Override
    public void show() {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        getWindow().setGravity(Gravity.LEFT | Gravity.TOP);
        layoutParams.x = 0;
        layoutParams.y = top;
        getWindow().setAttributes(layoutParams);
        super.show();
    }

    public List<CityItems> getSelectGoodsTypeItems(){
        for (CityType cityType : mTypes) {
            if(cityType.getSelect()){
                return cityType.getCityItemses();
            }
        }

        return mTypes.get(0).getCityItemses();
    }

    public void resetGoodsTypeSelect() {
        for (CityType cityType : mTypes) {
            cityType.setSelect(false);

        }
    }

    public void resetGoodsTypeItemSelect(){
        for (CityType cityType : mTypes) {
            if (cityType.getCityItemses() != null) {
                for (CityItems item : cityType.getCityItemses()) {
                    item.setSelect(false);
                }
            }

        }
    }

    public CityType getParentGoodsType(CityType type) {
        for (CityType cityType : mTypes) {
            if (cityType.getCityItemses() != null) {
                for (CityItems item : cityType.getCityItemses()) {
                    if (item.getCityId().equals(type.getProvinceId())) {
                        return cityType;
                    }
                }
            }

        }
        return null;
    }

    /**
     * 分类项选中事件
     */
    public interface OnClassificationSelectListener {
        void select(CityItems type);
    }

    class OneLevelAdapter extends BaseAdapter {

        Context mContext;
        List<CityType> mTypes;

        public OneLevelAdapter(Context context, List<CityType> types) {
            mContext = context;
            mTypes = types;
        }

        @Override
        public int getCount() {
            return mTypes.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.view_secondary_classification_one_item_new, parent, false);
            }

            TextView tvName = (TextView) convertView.findViewById(R.id.province_tv);


            if (mTypes.get(position).getSelect()) {
                convertView.findViewById(R.id.province_rll).setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvName.setTextColor(Color.parseColor("#ff2d47"));
            } else {
                convertView.findViewById(R.id.province_rll).setBackgroundColor(Color.parseColor("#ffffff"));
                tvName.setTextColor(Color.parseColor("#333333"));
            }

            tvName.setText(mTypes.get(position).getProvinceName());

            return convertView;
        }
    }


    class TowLevelAdapter extends BaseAdapter {

        Context mContext;
        List<CityItems> mTypes;

        public TowLevelAdapter(Context context, List<CityItems> types) {
            mContext = context;
            mTypes = types;
        }

        @Override
        public int getCount() {
            return mTypes.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.view_secondary_classification_tow_item_new, parent, false);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.city_tv);
            tvName.setText(mTypes.get(position).getCityName());

            if (mTypes.get(position).getSelect()) {
                tvName.setTextColor(Color.parseColor("#ff2d47"));
            } else {
                tvName.setTextColor(Color.parseColor("#333333"));
            }

            return convertView;
        }
    }
}
