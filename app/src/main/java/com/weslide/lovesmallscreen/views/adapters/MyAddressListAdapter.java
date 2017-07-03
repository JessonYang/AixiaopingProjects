package com.weslide.lovesmallscreen.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerViewAdapter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.user.MyAddressEditActivity;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.fragments.user.MyAddressFragment;
import com.weslide.lovesmallscreen.models.Address;
import com.weslide.lovesmallscreen.network.DataList;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/7/1.
 * 地址列表
 */
public class MyAddressListAdapter extends SuperRecyclerViewAdapter<Address,MyAddressListAdapter.MyAddressListViewHolder> {
    MyAddressFragment fragment;

    public MyAddressListAdapter(Context context,MyAddressFragment fragment, DataList<Address> dataList) {
        super(context, dataList);
        this.fragment = fragment;
    }

    @Override
    public MyAddressListViewHolder onSuperCreateViewHolder(ViewGroup parent, int viewType) {

        MyAddressListViewHolder viewHolder = new MyAddressListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_address_list,parent,false));

        return viewHolder;
    }

    @Override
    public void onSuperBindViewHolder(MyAddressListViewHolder holder, int position) {
        String addressId = mList.get(0).getAddressId();
        Address address = mList.get(position);
        holder.name.setText(address.getName());
        holder.phone.setText(address.getPhone());
        holder.defaultaddress.setText(address.getAddress());
        holder.booleanDefault.setChecked(address.getDefaultAddress());
        holder.delte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.deleteAddress(address.getAddressId());
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("address",address);
                bundle.putInt("type",2);
                AppUtils.toActivity(mContext, MyAddressEditActivity.class,bundle);

            }
        });
        holder.booleanDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                      fragment.setDefaultAddress(address.getAddressId());

            }
        });
    }

    class MyAddressListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.tv_phone)
        TextView phone;
        @BindView(R.id.tv_defaultaddress)
        TextView defaultaddress;
        @BindView(R.id.cb_boolean_defaultaddress)
        CheckBox booleanDefault;
        @BindView(R.id.iv_delet)
        ImageView delte;
        @BindView(R.id.iv_edit)
        ImageView edit;


        public MyAddressListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }


}
