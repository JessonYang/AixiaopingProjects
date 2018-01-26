package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.bean.TeamOrderModel;
import com.weslide.lovesmallscreen.views.customview.CircleImageView;

import java.util.List;

/**
 * Created by YY on 2017/12/25.
 */
public class TeamListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<TeamOrderModel> list;

    public TeamListAdapter(Context context, List<TeamOrderModel> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyHolder holder;
        if (view == null) {
            holder = new MyHolder();
            view = inflater.inflate(R.layout.pt_dialog_lv_item, viewGroup, false);
            holder.pt_user_face = (CircleImageView) view.findViewById(R.id.pt_user_face);
            holder.pt_limit_tv = (TextView) view.findViewById(R.id.pt_title_tv);
            holder.pt_left_time_tv = (TextView) view.findViewById(R.id.pt_left_time_tv);
            holder.pt_together = (TextView) view.findViewById(R.id.pt_together);
            view.setTag(holder);
        } else {
            holder = (MyHolder) view.getTag();
        }
        if (i < list.size()) {
            Glide.with(context).load(list.get(i).getUserHead()).into(holder.pt_user_face);
            holder.pt_limit_tv.setText(list.get(i).getSurplusNum());
            final long surplusTime = list.get(i).getSurplusTime();
            long total_second = surplusTime / 1000;
            long total_minute = total_second / 60;
            long total_hour = total_minute / 60;
            long second = total_second % 60;
            long minute = total_minute % 60;
            long hour = total_hour % 24;
            long day = total_hour / 24;
            holder.pt_left_time_tv.setText(getTime(day,hour,minute,second));
            new CountDownTimer(surplusTime, 1000) {
                @Override
                public void onTick(long l) {
                    Log.d("雨落无痕丶", "onTick: position==" + i);
                    long total_second = l / 1000;
                    long total_minute = total_second / 60;
                    long total_hour = total_minute / 60;
                    long second = total_second % 60;
                    long minute = total_minute % 60;
                    long hour = total_hour % 24;
                    long day = total_hour / 24;
                    holder.pt_left_time_tv.setText(getTime(day,hour,minute,second));
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }
        return view;
    }

    class MyHolder {
        CircleImageView pt_user_face;
        TextView pt_limit_tv, pt_left_time_tv, pt_together;
    }

    private String getTime(long day,long hour,long minute,long second){
        return  "剩余:" + String.format("%02d",day)+"天"+String.format("%02d",hour)+"时"+String.format("%02d",minute)+"分"+String.format("%02d",second)+"秒";
    }

}
