package com.weslide.lovesmallscreen.view_yy.adapter.viewholder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.LiveDetails;
import com.weslide.lovesmallscreen.models.LivemoduleModel;
import com.weslide.lovesmallscreen.models.LivesModel;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.view_yy.adapter.LiveGvAdapter;

import java.util.List;

/**
 * Created by YY on 2017/11/27.
 */
public class HomeLiveHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final GridView small_live_gv;
    private Context mContext;
    private LiveGvAdapter liveGvAdapter;

    public HomeLiveHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        small_live_gv = (GridView) itemView.findViewById(R.id.new_home_page_small_live_gv);
    }

    public void oprateView(LivemoduleModel liveModul){
        List<LivesModel> lives = liveModul.getLives();
        liveGvAdapter = new LiveGvAdapter(mContext, lives);
        small_live_gv.setAdapter(liveGvAdapter);
        small_live_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("liveId", lives.get(i).getLiveid());
                AppUtils.toActivity(mContext, LiveDetails.class, bundle);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
