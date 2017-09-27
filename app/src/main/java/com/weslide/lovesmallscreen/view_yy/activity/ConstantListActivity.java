package com.weslide.lovesmallscreen.view_yy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.model_yy.javabean.ContactBean;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.view_yy.adapter.ContactListAdapter;
import com.weslide.lovesmallscreen.view_yy.customview.IndexBar;
import com.weslide.lovesmallscreen.view_yy.util.ContactInfoService;
import com.weslide.lovesmallscreen.view_yy.util.TitleItemDecoration;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by YY on 2017/7/20.
 */
public class ConstantListActivity extends AppCompatActivity implements Comparator<ContactBean>, View.OnClickListener, ContactListAdapter.IBtnAlphaListenner, ContactListAdapter.IPhoneNumListenner {

    private List<ContactBean> mContactList = new ArrayList<>();
    private List<ContactBean> rclvList = new ArrayList<>();
    private RecyclerView rclv;
    private EditText search_edt;
    private LinearLayout search_hint;
    private ContactListAdapter mAdapter;
    private CustomToolbar toolbar;
    private IndexBar indexBar;
    private TextView hintText;
    private Button is_select_all, sure_btn;
    protected static ConstantListActivity constantListActivity;
    private int mNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constant_list);
        initView();
        initData();
    }

    private void initData() {
        constantListActivity = this;
        toolbar.setOnImgClick(new CustomToolbar.OnImgClick() {
            @Override
            public void onLeftImgClick() {
                finish();
            }

            @Override
            public void onRightImgClick() {

            }
        });
        mContactList.addAll(new ContactInfoService(this).getContactList());
        if (mContactList != null && mContactList.size() > 0) {
            Collections.sort(mContactList, this);
            for (int i = 0; i < mContactList.size(); i++) {
                String phoneNum = mContactList.get(i).getPhoneNum();
                phoneNum = phoneNum.replace(" ", "");
                mContactList.get(i).setPhoneNum(phoneNum);
            }
            mAdapter = new ContactListAdapter(this, mContactList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rclv.setLayoutManager(layoutManager);
            rclv.setAdapter(mAdapter);
            rclv.addItemDecoration(new TitleItemDecoration(this, mContactList));
            indexBar.setNeedRealIndex(true);
            indexBar.setmPressedShowTextView(hintText);
            indexBar.setmSourceDatas(mContactList);
            indexBar.setmLayoutManager(layoutManager);
            is_select_all.setOnClickListener(this);
            sure_btn.setOnClickListener(this);
            mAdapter.setiBtnAlphaListenner(this);
            mAdapter.setiPhoneNumListenner(this);
        }else {
            rclv.setVisibility(View.GONE);
            Toast.makeText(ConstantListActivity.this, "您的通讯录还没有联系人哦!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        rclv = ((RecyclerView) findViewById(R.id.contact_rclv));
        search_edt = ((EditText) findViewById(R.id.contact_search_edt));
        search_hint = ((LinearLayout) findViewById(R.id.search_hint_ll));
        toolbar = ((CustomToolbar) findViewById(R.id.contact_toolbar));
        indexBar = ((IndexBar) findViewById(R.id.index_bar));
        hintText = ((TextView) findViewById(R.id.tvSideBarHint));
        is_select_all = ((Button) findViewById(R.id.is_select_all));
        sure_btn = ((Button) findViewById(R.id.sure_btn));
    }

    @Override
    public int compare(ContactBean t1, ContactBean t2) {
        if (t1.getFirstHeadLetter().equals("#")) {
            return 1;
        } else if (t2.getFirstHeadLetter().equals("#")) {
            return -1;
        } else {
            return t1.getFirstHeadLetter().compareTo(t2.getFirstHeadLetter());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.is_select_all:
                if (is_select_all.getText().toString().equals("全选")) {
                    mAdapter.selectAll();
                    is_select_all.setText("取消");
                } else if (is_select_all.getText().toString().equals("取消")) {
                    mAdapter.cancleAll();
                    is_select_all.setText("全选");
                }
                break;
            case R.id.sure_btn:
                if (mNum <= 200) {
                    List<ContactBean> phoneNum = mAdapter.returnPhoneNum();
                    if (phoneNum != null && phoneNum.size() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("phoneList", (Serializable) phoneNum);
                        AppUtils.toActivity(this, SendMsgActivity.class, bundle);
                    }
                }
                break;
        }
    }

    @Override
    public void changeBtnAlpha(float value) {
        sure_btn.setAlpha(value);
    }

    @Override
    public void changeBtnNum(int num) {
        mNum = num;
        if (num != 0) {
            sure_btn.setText("确定(" + num + ")");
        } else {
            sure_btn.setText("确定");
        }
    }
}
