package com.weslide.lovesmallscreen.views.adapters.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.models.bean.PtGoodModel;
import com.weslide.lovesmallscreen.views.home.HomePtItemView;

/**
 * Created by YY on 2017/12/20.
 */
public class HomePtViewHoder extends RecyclerView.ViewHolder {

    private final Context context;
    private final HomePtItemView ptGoodView;

    public HomePtViewHoder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        ptGoodView = ((HomePtItemView) itemView.findViewById(R.id.pt_good_item));
    }

    public void oprateView(PtGoodModel model){
        ptGoodView.bindView(model);
    }
}
