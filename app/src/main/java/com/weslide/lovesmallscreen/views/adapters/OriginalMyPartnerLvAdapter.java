package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.model_yy.javabean.PartnerDtEventBusBean;
import com.weslide.lovesmallscreen.model_yy.javabean.RemarkModel;
import com.weslide.lovesmallscreen.models.PartnersOb;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.RXUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by YY on 2017/3/27.
 */
public class OriginalMyPartnerLvAdapter extends BaseAdapter {

    private Context context;
    private List<PartnersOb> list;
    private LayoutInflater inflater;

    public OriginalMyPartnerLvAdapter(Context context, List<PartnersOb> list) {
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
            view = inflater.inflate(R.layout.original_my_partner_lv_item, viewGroup, false);
            holder = new MyHolder();
            holder.partners_tv = (TextView) view.findViewById(R.id.partners_name);
            holder.order_tv = (TextView) view.findViewById(R.id.order_tv);
            holder.predict_tv = (TextView) view.findViewById(R.id.predict_tv);
            holder.partner_edit_tv = (TextView) view.findViewById(R.id.partner_edit_tv);
            holder.partners_phone = (TextView) view.findViewById(R.id.partners_phone);
            holder.delete_partner = (TextView) view.findViewById(R.id.delete_partner);
            holder.edit_partner = (TextView) view.findViewById(R.id.edit_partner);
            holder.partner_icon = (ImageView) view.findViewById(R.id.partner_icon);
            view.setTag(holder);
        } else holder = (MyHolder) view.getTag();

        PartnersOb partnersOb = list.get(i);
        holder.partners_tv.setText(partnersOb.getPartnerName());
        String partnerPhone = partnersOb.getPartnerPhone();
        holder.partners_phone.setText(partnerPhone);
        holder.order_tv.setText(partnersOb.getOrdersToday() + "笔");
        holder.predict_tv.setText("￥" + partnersOb.getPredictToday());
        holder.partner_edit_tv.setText("备注:" + partnersOb.getRemarks());
        Glide.with(context).load(partnersOb.getPartnerIcon()).asBitmap().placeholder(R.drawable.icon_defult).error(R.drawable.icon_defult).centerCrop().into(new BitmapImageViewTarget(holder.partner_icon) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.partner_icon.setImageDrawable(circularBitmapDrawable);
//                logo = resource;
            }
        });
        holder.delete_partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View delView = inflater.inflate(R.layout.partner_dt_del_dialog_view, null);
                AlertDialog delDialog = new AlertDialog.Builder(context).setView(delView).create();
                EditText del_phone_edt = (EditText) delView.findViewById(R.id.del_phone_edt);
                Button del_phone_cancel_btn = (Button) delView.findViewById(R.id.del_phone_cancel_btn);
                Button del_phone_confirm_btn = (Button) delView.findViewById(R.id.del_phone_confirm_btn);
                del_phone_edt.setText(partnerPhone);
                del_phone_edt.setFocusable(false);
                del_phone_edt.setFocusableInTouchMode(false);
                del_phone_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        delDialog.dismiss();
                    }
                });
                del_phone_confirm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Request<RemarkModel> request = new Request<>();
                        RemarkModel remarkModel = new RemarkModel();
                        remarkModel.setPartnerId(partnersOb.getPartnerId());
                        request.setData(remarkModel);
                        RXUtils.request(context, request, "getPartnerDelete", new SupportSubscriber<Response>() {
                            @Override
                            public void onNext(Response response) {
                                delDialog.dismiss();
                                list.remove(i);
                                notifyDataSetChanged();
                                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                delDialog.show();
            }
        });
        holder.edit_partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View edtView = inflater.inflate(R.layout.partner_dt_edit_dialog_view, null);
                AlertDialog edtDialog = new AlertDialog.Builder(context).setView(edtView).create();
                EditText edit_partner_edt = (EditText) edtView.findViewById(R.id.edit_partner_edt);
                Button edit_partner_cancel_btn = (Button) edtView.findViewById(R.id.edit_partner_cancel_btn);
                Button edit_partner_confirm_btn = (Button) edtView.findViewById(R.id.edit_partner_confirm_btn);
                edit_partner_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edtDialog.dismiss();
                    }
                });
                edit_partner_confirm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Request<RemarkModel> request = new Request<>();
                        RemarkModel remarkModel = new RemarkModel();
                        remarkModel.setPartnerId(partnersOb.getPartnerId());
                        remarkModel.setRemark(edit_partner_edt.getText().toString());
                        request.setData(remarkModel);
                        RXUtils.request(context, request, "getPartnerRemark", new SupportSubscriber<Response>() {
                            @Override
                            public void onNext(Response response) {
                                edtDialog.dismiss();
                                EventBus.getDefault().post(new PartnerDtEventBusBean());
                                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        });
                    }
                });
                edtDialog.show();
            }
        });
        return view;
    }

    class MyHolder {
        TextView partners_tv;
        TextView order_tv;
        TextView predict_tv;
        TextView partner_edit_tv;
        TextView partners_phone;
        TextView delete_partner;
        TextView edit_partner;
        ImageView partner_icon;
    }
}
