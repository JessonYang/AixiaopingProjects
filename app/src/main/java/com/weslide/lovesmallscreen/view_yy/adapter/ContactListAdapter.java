package com.weslide.lovesmallscreen.view_yy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.model_yy.javabean.ContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/7/21.
 */
public class ContactListAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ContactBean> list;
    private LayoutInflater inflater;
    private SparseBooleanArray checkSataus = new SparseBooleanArray();

    public void setiPhoneNumListenner(IPhoneNumListenner iPhoneNumListenner) {
        this.iPhoneNumListenner = iPhoneNumListenner;
    }

    private IBtnAlphaListenner iBtnAlphaListenner;
    private IPhoneNumListenner iPhoneNumListenner;

    public void setiBtnAlphaListenner(IBtnAlphaListenner iBtnAlphaListenner) {
        this.iBtnAlphaListenner = iBtnAlphaListenner;
    }

    public ContactListAdapter(Context context, List<ContactBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contact_list_rclv_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ContactBean contactBean = list.get(position);
        myHolder.userName.setText(contactBean.getTitle());
        myHolder.phone.setText(contactBean.getPhoneNum());
        myHolder.cb.setTag(position);
        myHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int pos = (int) compoundButton.getTag();
                if (b) {
                    checkSataus.put(pos, true);
                    contactBean.setSelect(true);
                    if (iBtnAlphaListenner != null) {
                        iBtnAlphaListenner.changeBtnAlpha(1f);
                    }
                    if (iPhoneNumListenner != null) {
                        iPhoneNumListenner.changeBtnNum(returnPhoneNum().size());
                    }
                } else {
                    checkSataus.delete(pos);
                    contactBean.setSelect(false);
                    if (iPhoneNumListenner != null) {
                        iPhoneNumListenner.changeBtnNum(returnPhoneNum().size());
                    }
                    for (ContactBean bean : list) {
                        if (bean.isSelect()) {
                            iBtnAlphaListenner.changeBtnAlpha(1f);
                            return;
                        }
                    }
                    iBtnAlphaListenner.changeBtnAlpha(0.5f);

                }
            }
        });
        myHolder.cb.setChecked(checkSataus.get(position, false));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView userName, phone;
        private CheckBox cb;

        public MyHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.username_tv);
            phone = (TextView) itemView.findViewById(R.id.userphone_tv);
            cb = (CheckBox) itemView.findViewById(R.id.cb);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Bundle bundle = new Bundle();
                    bundle.putString("name",list.get(getLayoutPosition()).getTitle());
                    bundle.putString("phone",list.get(getLayoutPosition()).getPhoneNum());
                    AppUtils.toActivity(context, SendMsgActivity.class,bundle);*/

                    int layoutPosition = getLayoutPosition();
                    if (!checkSataus.get(layoutPosition, false)) {
                        /*checkSataus.put(layoutPosition,true);
                        list.get(layoutPosition).setSelect(true);
                        if (iBtnAlphaListenner != null) {
                            iBtnAlphaListenner.changeBtnAlpha(1f);
                        }*/
                        cb.setChecked(true);
                    } else {
                        /*checkSataus.delete(layoutPosition);
                        list.get(layoutPosition).setSelect(false);
                        for (ContactBean bean : list) {
                            if (bean.isSelect()) {
                                iBtnAlphaListenner.changeBtnAlpha(1f);
                                return;
                            }
                        }
                        iBtnAlphaListenner.changeBtnAlpha(0.5f);*/
                        cb.setChecked(false);
                    }
                }
            });
        }
    }

    public void selectAll() {
        for (int i = 0; i < list.size(); i++) {
            checkSataus.put(i, true);
        }
        notifyDataSetChanged();
        if (iBtnAlphaListenner != null) {
            iBtnAlphaListenner.changeBtnAlpha(1f);
        }
    }

    public void cancleAll() {
        for (int i = 0; i < list.size(); i++) {
            checkSataus.put(i, false);
        }
        notifyDataSetChanged();
        if (iBtnAlphaListenner != null) {
            iBtnAlphaListenner.changeBtnAlpha(0.5f);
        }
    }

    public List<ContactBean> returnPhoneNum() {
        List<ContactBean> phoneNum = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (checkSataus.get(i)) {
                phoneNum.add(list.get(i));
            }
        }
        return phoneNum;
    }

    public interface IBtnAlphaListenner {
        void changeBtnAlpha(float value);
    }

    public interface IPhoneNumListenner {
        void changeBtnNum(int num);
    }
}
