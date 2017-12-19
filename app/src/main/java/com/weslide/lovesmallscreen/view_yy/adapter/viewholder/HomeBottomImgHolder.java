package com.weslide.lovesmallscreen.view_yy.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.HomeActivity;
import com.weslide.lovesmallscreen.activitys.LoginOptionActivity;
import com.weslide.lovesmallscreen.activitys.mall.SellerListActivity_new;
import com.weslide.lovesmallscreen.models.NfcpModel;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.view_yy.activity.TaoBaoActivity;

/**
 * Created by YY on 2017/11/27.
 */
public class HomeBottomImgHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageView exchange_iv;
    private final RelativeLayout zbdp_rll;
    private Context mContext;
    private String exchangeUri;

    public HomeBottomImgHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        exchange_iv = (ImageView) itemView.findViewById(R.id.exchange_iv);
        zbdp_rll = (RelativeLayout) itemView.findViewById(R.id.zbdp_rll);
    }

    public void oprateView(NfcpModel nfcpModel){
        Glide.with(mContext).load(nfcpModel.getImage()).into(exchange_iv);
        exchangeUri = nfcpModel.getUri();
        exchange_iv.setOnClickListener(this);
        zbdp_rll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exchange_iv:
                if (exchangeUri != null && exchangeUri != "") {
                    if (ContextParameter.isLogin()) {
                        Intent intent = new Intent(mContext, TaoBaoActivity.class);
                        intent.putExtra("URL", exchangeUri);
                        mContext.startActivity(intent);
                    } else {
                        AppUtils.toActivity(mContext, LoginOptionActivity.class);
                        if (HomeActivity.activity != null) {
                            HomeActivity.activity.finish();
                        }
                    }
                }
                break;
            case R.id.zbdp_rll:
                AppUtils.toActivity(mContext, SellerListActivity_new.class);
                break;
        }
    }
}
