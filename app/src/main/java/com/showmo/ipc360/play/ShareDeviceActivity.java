package com.showmo.ipc360.play;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.utils.StringUtils;
import com.weslide.lovesmallscreen.utils.T;
import com.xmcamera.core.model.XmErrInfo;
import com.xmcamera.core.model.XmSharedUserInfo;
import com.xmcamera.core.sys.XmSystem;
import com.xmcamera.core.sysInterface.IXmSystem;
import com.xmcamera.core.sysInterface.OnXmListener;
import com.xmcamera.core.sysInterface.OnXmSimpleListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dong on 2016/10/11.
 * 分享设备
 */
public class ShareDeviceActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.btn_add_devices)
    ImageView btnAddDevices;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.list)
    SwipeMenuListView list;
    IXmSystem xmSystem;
    Intent in;
    int cameraId;
    String uuid;
    List<XmSharedUserInfo> mlist;
    private MyUserAdapter adapter;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_list);
        ButterKnife.bind(this);
        init();
        initData();
    }

    private void init() {
        xmSystem = XmSystem.getInstance();
        in = getIntent();
        cameraId = in.getIntExtra("cameraId", 0);
        uuid = in.getStringExtra("uuid");
        mlist = new ArrayList<>();
        adapter = new MyUserAdapter(ShareDeviceActivity.this);
        list.setAdapter(adapter);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareDeviceActivity.this.finish();
            }
        });
        btnAddDevices.setOnClickListener(this);

        //   getDevicesUser();
        // deletDeviceUser(cameraId,1034355);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deletItem = new SwipeMenuItem(ShareDeviceActivity.this);

                // set item background
                deletItem.setBackground(new ColorDrawable(getResources().getColor(R.color.main_color_red)));
                // set item width
                deletItem.setWidth(dp2px(90));
                // set item title
                deletItem.setTitle("删除");
                // set item title fontsize
                deletItem.setTitleSize(18);
                // set item title font color
                deletItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deletItem);
            }
        };
        list.setMenuCreator(creator);
        list.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                XmSharedUserInfo userInfo = mlist.get(position);
                deletDeviceUser(cameraId, userInfo.getUserid());

                return false;
            }
        });
    }

    private void initData() {
        getDevicesUser();
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_devices:
                //点击出现对话框，添加设备账号
                //  shareDevices(cameraId,uuid,"15515515511");
                showTwoDimensionCode();
                break;


        }
    }

    /**
     * 分享设备功能
     */
    private void shareDevices(int mCameraId, String mUuid, String name) {
        xmSystem.xmShareDevice(mCameraId, mUuid, name, new OnXmListener<Integer>() {
            @Override
            public void onErr(XmErrInfo xmErrInfo) {
                handler.sendEmptyMessage(0x101);
            }

            @Override
            public void onSuc(Integer integer) {
                handler.sendEmptyMessage(0x100);
                getDevicesUser();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x100) {
                T.showShort(ShareDeviceActivity.this, "分享成功");
            } else if (msg.what == 0x101) {
                T.showShort(ShareDeviceActivity.this, "分享失败");
            }
        }
    };


    /**
     * 获取分享列表
     */
    private void getDevicesUser() {

        xmSystem.xmGetDeviceSharedUsers(cameraId, new OnXmListener<List<XmSharedUserInfo>>() {
            @Override
            public void onErr(XmErrInfo xmErrInfo) {
            }

            @Override
            public void onSuc(List<XmSharedUserInfo> xmSharedUserInfos) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mlist.clear();
                        mlist.addAll(xmSharedUserInfos);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /**
     * 删除分享列表
     */
    private void deletDeviceUser(int cameraId, int userId) {
        xmSystem.xmDeleteShareDevice(cameraId, userId, new OnXmSimpleListener() {
            @Override
            public void onErr(XmErrInfo xmErrInfo) {
            }

            @Override
            public void onSuc() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getDevicesUser();
                    }
                });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    class MyUserAdapter extends BaseAdapter {

        Context context;

        public MyUserAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holer = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.devicers_list_view_item, parent, false);
                holer = new ViewHolder(convertView);
                convertView.setTag(holer);
            } else {
                holer = (ViewHolder) convertView.getTag();
            }

            XmSharedUserInfo device = mlist.get(position);
            holer.tvName.setText(device.getUsername());
            //分享
            holer.ivShare.setVisibility(View.GONE);
            holer.ivRename.setVisibility(View.GONE);
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.iv_share)
            ImageView ivShare;
            @BindView(R.id.iv_rename)
            ImageView ivRename;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * 点击出现分享输入框
     */
    private void showTwoDimensionCode() {
        LayoutInflater inflater = ShareDeviceActivity.this.getLayoutInflater();
        LayoutInflater inflater2 = ShareDeviceActivity.this.getLayoutInflater();
        //得到界面视图
        View currean_View = inflater.inflate(R.layout.activity_share_list, null);
        //得到要弹出的界面视图
        View view = inflater2.inflate(R.layout.view_share_devices_show, null);
        WindowManager windowManager = ShareDeviceActivity.this.getWindowManager();
        int width = windowManager.getDefaultDisplay().getWidth();
        int heigth = windowManager.getDefaultDisplay().getHeight();
        Log.i("width", width + "");
        Log.i("height", heigth + "");
        popupWindow = new PopupWindow(view, (int) (width * 0.8), LinearLayout.LayoutParams.WRAP_CONTENT/*(int) (heigth * 0.4)*/);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //显示在屏幕中央
        popupWindow.showAtLocation(currean_View, Gravity.CENTER, 0, 40);
        //popupWindow弹出后屏幕半透明
        BackgroudAlpha((float) 0.5);
        //弹出窗口关闭事件
        popupWindow.setOnDismissListener(new popupwindowdismisslistener());
        EditText name = (EditText)view.findViewById(R.id.tv_name);
        android.widget.Button buttonTrue = (android.widget.Button) view.findViewById(R.id.btn_share_true);
        buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareName = name.getText().toString();
                if(!StringUtils.isBlank(shareName)){
                    shareDevices(cameraId,uuid,shareName);
                }else{
                    T.showShort(ShareDeviceActivity.this,"请输入分享账号");
                }
            }
        });
        android.widget.Button buttonFalse = (android.widget.Button) view.findViewById(R.id.btn_share_false);
        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    //设置屏幕背景透明度
    private void BackgroudAlpha(float alpha) {
        // TODO Auto-generated method stub
        WindowManager.LayoutParams l = ShareDeviceActivity.this.getWindow().getAttributes();
        l.alpha = alpha;
        ShareDeviceActivity.this.getWindow().setAttributes(l);
    }

    //点击其他部分popwindow消失时，屏幕恢复透明度
    class popupwindowdismisslistener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            BackgroudAlpha((float) 1);
        }
    }
}
