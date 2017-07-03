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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weslide.lovesmallscreen.models.GoodsType;

import net.aixiaoping.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2016/6/23.
 * 级联类型选择视图
 */
public class SecondaryTypeDialog extends android.app.Dialog {

    List<GoodsType> mTypes = new ArrayList<>();
    List<GoodsType> mTowTypes = new ArrayList<>();
    ListView lvOneLevel;
    BaseAdapter oneLevelAdapter;
    BaseAdapter towLevelAdapter;
    int top;
    int width;
    OnClassificationSelectListener mOnClassificationSelectListener;
    private WindowManager wm;
    private GridView gvTowLevel;

    public SecondaryTypeDialog(Context context, List<GoodsType> types, int top) {
        super(context, R.style.secondaryClassificationDialog);
        mTypes = types;
        this.top = top;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();

        mTowTypes.addAll(getSelectGoodsTypeItems());
    }

    public SecondaryTypeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SecondaryTypeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setOnClassificationSelectListener(OnClassificationSelectListener onClassificationSelectListener) {
        mOnClassificationSelectListener = onClassificationSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_secondary_type);
        lvOneLevel = (ListView) findViewById(R.id.lv_one_level);
        gvTowLevel = (GridView) findViewById(R.id.gv_tow_level);
        oneLevelAdapter = new OneLevelAdapter(getContext(), mTypes);
        towLevelAdapter = new TowLevelAdapter(getContext(), mTowTypes);

        lvOneLevel.setAdapter(oneLevelAdapter);
        gvTowLevel.setAdapter(towLevelAdapter);

        lvOneLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTowTypes.clear();
                resetGoodsTypeSelect();
                mTypes.get(position).setSelect(true);
                mTowTypes.addAll(mTypes.get(position).getTypeItems());

                oneLevelAdapter.notifyDataSetChanged();
                towLevelAdapter.notifyDataSetChanged();
            }
        });

        gvTowLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    public List<GoodsType> getSelectGoodsTypeItems() {
        for (GoodsType goodType : mTypes) {
            if (goodType.getSelect()) {
                return goodType.getTypeItems();
            }
        }

        return mTypes.get(0).getTypeItems();
    }

    public void resetGoodsTypeSelect() {
        for (GoodsType typeType : mTypes) {
            typeType.setSelect(false);

        }
    }

    public void resetGoodsTypeItemSelect() {
        for (GoodsType goodType : mTypes) {
            if (goodType.getTypeItems() != null) {
                for (GoodsType item : goodType.getTypeItems()) {
                    item.setSelect(false);
                }
            }

        }
    }

    public GoodsType getParentGoodsType(GoodsType type) {
        for (GoodsType goodsType : mTypes) {
            if (goodsType.getTypeItems() != null) {
                for (GoodsType item : goodsType.getTypeItems()) {
                    if (item.getTypeId().equals(type.getTypeId())) {
                        return goodsType;
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
        void select(GoodsType type);
    }

    class OneLevelAdapter extends BaseAdapter {

        Context mContext;
        List<GoodsType> mTypes;

        public OneLevelAdapter(Context context, List<GoodsType> types) {
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

            tvName.setText(mTypes.get(position).getTypeName());

            return convertView;
        }
    }


    class TowLevelAdapter extends BaseAdapter {

        Context mContext;
        List<GoodsType> mTypes;

        public TowLevelAdapter(Context context, List<GoodsType> types) {
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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.view_secondary_type_tow_item, parent, false);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.type_tv);
            RelativeLayout rll = (RelativeLayout) convertView.findViewById(R.id.type_rll);
            tvName.setText(mTypes.get(position).getTypeName());

            if (mTypes.get(position).getSelect()) {
                tvName.setTextColor(Color.parseColor("#ffffff"));
                rll.setBackground(mContext.getResources().getDrawable(R.drawable.secondary_type_bg));
            } else {
                tvName.setTextColor(Color.parseColor("#333333"));
                rll.setBackground(mContext.getResources().getDrawable(R.drawable.secondary_type_bg_default));
            }

            return convertView;
        }
    }
}
