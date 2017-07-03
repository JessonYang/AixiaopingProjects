package com.weslide.lovesmallscreen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;

/**
 * Created by YY on 2017/3/23.
 */
public class OriginalCityAgencyVpBaseFg extends BaseFragment {

    private View vpFg;
    private ListView lv;
    private String typeId;


    public static OriginalCityAgencyVpBaseFg getInstance(Bundle bundle) {
        OriginalCityAgencyVpBaseFg cityAgencyVpBaseFg = new OriginalCityAgencyVpBaseFg();
        cityAgencyVpBaseFg.setArguments(bundle);
        return cityAgencyVpBaseFg;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int isAgency = bundle.getInt("isAgency");
        typeId = bundle.getString("typeId");
        vpFg = inflater.inflate(R.layout.fragment_original_city_agency_vp, container, false);
        /*initView();
        OriginalAgenceVpAdapter adapter = new OriginalAgenceVpAdapter(getSupportActivity());
        lv.setAdapter(adapter);
        int count = adapter.getCount();
        int totalHeight = 0;
        for (int i = 0; i < count; i++) {
            View itemView = adapter.getView(i, null, lv);
            itemView.measure(0, 0);
            totalHeight += itemView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (count - 1) * lv.getDividerHeight();
        lv.requestLayout();
        adapter.notifyDataSetChanged();*/
        return vpFg;
    }

    private void initView() {
        lv = ((ListView) vpFg.findViewById(R.id.lv));
    }
}
