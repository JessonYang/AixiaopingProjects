package com.weslide.lovesmallscreen.views.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.demo.IpInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/5/5.
 * Demo中使用的数据适配器
 */
public class DemoListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    List<IpInfo> mInfoList = null;
    Activity mActivity;

    public DemoListAdapter(Activity activity, List<IpInfo> infoList){
        mActivity = activity;
        mInfoList = infoList;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListViewHolder holder = new ListViewHolder(LayoutInflater.from(mActivity)
                .inflate(R.layout.item_demo_listview, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        IpInfo info = mInfoList.get(position);
        holder.tvIp.setText(info.getIp());
        holder.tvIpLocation.setText(info.getArea());
    }

    @Override
    public int getItemCount() {
        return mInfoList.size();
    }
}

class ListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_listview_ip)
    TextView tvIp;
    @BindView(R.id.tv_listview_ip_location)
    TextView tvIpLocation;

    public ListViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}