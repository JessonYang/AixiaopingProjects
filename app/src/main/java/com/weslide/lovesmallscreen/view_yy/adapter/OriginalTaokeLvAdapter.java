package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.model_yy.javabean.SaveMoneyGoodModel;
import com.weslide.lovesmallscreen.view_yy.activity.SaveMoneyHomeActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YY on 2017/6/16.
 */
public class OriginalTaokeLvAdapter extends BaseAdapter {

    private Context context;
    private List<SaveMoneyGoodModel> list;
    private LayoutInflater inflater;

    public OriginalTaokeLvAdapter(Context context, List<SaveMoneyGoodModel> list) {
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.vp_lv_item, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.delet = (TextView) convertView.findViewById(R.id.delet);
            viewHolder.tv_sale = (TextView) convertView.findViewById(R.id.tv_sale);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.dispeice = (TextView) convertView.findViewById(R.id.dispeice);
            viewHolder.discount = (TextView) convertView.findViewById(R.id.discount);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_pic);
            viewHolder.bg = (ImageView) convertView.findViewById(R.id.iv_bg);
            viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        double quanprice = Double.parseDouble(list.get(position).getOrg_price()) - Double.parseDouble(list.get(position).getPrice());
        java.text.DecimalFormat df = new java.text.DecimalFormat("#");

        viewHolder.title.setText(list.get(position).getD_title());
        viewHolder.delet.setText("领券立减" + df.format(quanprice) + "元");
        viewHolder.tv_sale.setText(list.get(position).getPrice());
        viewHolder.price.setText("¥" + list.get(position).getOrg_price());
        viewHolder.price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.dispeice.setText(df.format(quanprice));
        // viewHolder.discount.setText(data.get(position).getD_title());
        int receive = Integer.parseInt(list.get(position).getQuan_receive());
        int surplus = Integer.parseInt(list.get(position).getQuan_surplus());
        int d = receive + surplus;
        double c = receive * 100 / d;
        viewHolder.discount.setText("已经抢" + df.format(c) + "%");
        if (c > 0 && c <= 15) {
            viewHolder.bg.setImageResource(R.drawable.icon_10);
        } else if (c > 15 && c <= 25) {
            viewHolder.bg.setImageResource(R.drawable.icon_20);
        } else if (c > 25 && c <= 35) {
            viewHolder.bg.setImageResource(R.drawable.icon_30);
        } else if (c > 35 && c <= 45) {
            viewHolder.bg.setImageResource(R.drawable.icon_40);
        } else if (c > 45 && c <= 55) {
            viewHolder.bg.setImageResource(R.drawable.icon_50);
        } else if (c > 55 && c <= 65) {
            viewHolder.bg.setImageResource(R.drawable.icon_60);
        } else if (c > 65 && c <= 75) {
            viewHolder.bg.setImageResource(R.drawable.icon_70);
        } else if (c > 70 && c <= 80) {
            viewHolder.bg.setImageResource(R.drawable.icon_80);
        } else if (c > 80 && c <= 90) {
            viewHolder.bg.setImageResource(R.drawable.icon_90);
        } else if (c > 90 && c <= 100) {
            viewHolder.bg.setImageResource(R.drawable.icon_100);
        } else {
            viewHolder.bg.setImageResource(R.drawable.icon_touming);
        }

        Glide.with(context).load(list.get(position).getPic()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.imageView);

        viewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTaobao(list.get(position).getLink());
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView title, delet, tv_sale, price, dispeice, discount;
        ImageView imageView, bg;
        LinearLayout ll;
    }

    private void toTaobao(String url) {
        Log.d("雨落无痕丶", "toTaobao: "+url);
        //提供给三方传递配置参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        AlibcBasePage alibcBasePage = new AlibcPage(url);
        //设置页面打开方式
        AlibcShowParams showParams = null;
        if (isPkgInstalled("com.taobao.taobao") == false) {
            showParams = new AlibcShowParams(OpenType.Auto, false);
        } else {
            showParams = new AlibcShowParams(OpenType.Native, false);
        }
        //使用百川sdk提供默认的Activity打开detail
        AlibcTrade.show(SaveMoneyHomeActivity.saveMoneyHomeActivity, alibcBasePage, showParams, null, exParams,
                new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(TradeResult tradeResult) {
                        //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                    }
                });
    }

    private boolean isPkgInstalled(String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
