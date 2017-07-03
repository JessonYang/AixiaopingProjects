package com.showmo.ipc360.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.showmo.ipc360.play.PlayActivity;
import com.showmo.ipc360.play.ReNameActivity;
import com.showmo.ipc360.play.ShareDeviceActivity;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.BaseFragment;
import com.weslide.lovesmallscreen.utils.L;
import com.weslide.lovesmallscreen.utils.T;
import com.xmcamera.core.model.XmDevice;
import com.xmcamera.core.model.XmErrInfo;
import com.xmcamera.core.sys.XmSystem;
import com.xmcamera.core.sysInterface.IXmSystem;
import com.xmcamera.core.sysInterface.OnXmListener;
import com.xmcamera.core.sysInterface.OnXmSimpleListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xu on 2016/7/29.
 * 设备列表Fragment
 */
public class DevicesListFragment extends BaseFragment {

    public static final int Owner = 0;
    public static final int Share = 1;
    public static final int Demo = 2;

    View mView;
    @BindView(R.id.list)
    SwipeMenuListView listView;
    IXmSystem xmSystem;

    MyAdapter adapter;
    List<XmDevice> mlist;

    private int mode;


    public static DevicesListFragment getInstance(int mode) {

        DevicesListFragment fragment = new DevicesListFragment();

        fragment.mode = mode;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_devices_list, container, false);

        ButterKnife.bind(this, mView);

        initview();
        initdata();

        return mView;
    }
    private void initview() {
        mlist = new ArrayList<XmDevice>();
        adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem openItem = new SwipeMenuItem(

                        getActivity().getApplicationContext());

                openItem.setBackground(new ColorDrawable(getResources().getColor(R.color.main_color_red)));

                openItem.setWidth(dp2px(90));

                openItem.setTitle("删除");

                openItem.setTitleSize(18);

                openItem.setTitleColor(Color.WHITE);

                menu.addMenuItem(openItem);

//                // create "open" item
//                SwipeMenuItem openItem1 = new SwipeMenuItem(
//                        getActivity().getApplicationContext());
//                // set item background
//                openItem1.setBackground(new ColorDrawable(getResources().getColor(R.color.background_color)));
//                // set item width
//                openItem1.setWidth(dp2px(90));
//                // set item title
//                openItem1.setTitle("分享");
//                // set item title fontsize
//                openItem1.setTitleSize(18);
//                // set item title font color
//                openItem1.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem1);

            }
        };
        // set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                XmDevice item = mlist.get(position);
                switch (index) {
                    case 0:
                        xmSystem.xmDeleteDevice(item.getmCameraId(), item.getmUuid(), new OnXmSimpleListener() {
                            @Override
                            public void onErr(XmErrInfo xmErrInfo) {
                                T.showShort(getActivity(), "失败了");
                                initdata();
                            }

                            @Override
                            public void onSuc() {
                               new Handler().post(new Runnable() {
                                   @Override
                                   public void run() {
                                       L.e("======执行删除==========");
                                       initdata();
                                   }
                               });
                            }
                        });

                       /* new Handler().postAtTime(new Runnable() {
                            @Override
                            public void run() {
                                initdata();
                            }
                        }, 2500);*/

                        break;
                }
                return false;
            }
        });

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private void initdata() {
        xmSystem = XmSystem.getInstance();
//        if(!getIntent().getExtras().getBoolean("isDemo")) {
//            account = (XmAccount) getIntent().getExtras().getSerializable("username");
//        }
        getDevices();
    }

    private void getDevices() {

        xmSystem.xmGetDeviceList(new OnXmListener<List<XmDevice>>() {
            @Override
            public void onErr(XmErrInfo info) {

            }

            @Override
            public void onSuc(List<XmDevice> info) {
                mlist.clear();
                List<XmDevice> infoTemp = new ArrayList<XmDevice>();

                for (int i = 0; i < info.size(); i++) {
                    if (mode == info.get(i).getmOwnerType()) {
                        infoTemp.add(info.get(i));
                    }
                }

                mlist = infoTemp;
                adapter.notifyDataSetChanged();
                if(Looper.myLooper() != Looper.getMainLooper()){
                    L.e("===子线程=====");
                }else{
                    L.e("===UI线程=====");
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 101) {
            getDevices();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        getDevices();
        super.onResume();
    }

    class MyAdapter extends BaseAdapter {

        Context context;

        public MyAdapter(Context context) {
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

            XmDevice device = mlist.get(position);
            holer.tvName.setText(device.getmName());
            holer.cbStatus.setChecked(true);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int cameraId = mlist.get(position).getmCameraId();
                    Intent in = new Intent(getActivity(), PlayActivity.class);
                    in.putExtra("cameraId", cameraId);
                    startActivityForResult(in, 100);
                }
            };

            holer.tvName.setOnClickListener(listener);
            holer.cbStatus.setOnClickListener(listener);
            holer.tvOnline.setOnClickListener(listener);
            holer.cbShare.setOnClickListener(listener);
            //分享
            holer.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mode == Owner) {
                        int cameraId = mlist.get(position).getmCameraId();
                        String uuid = mlist.get(position).getmUuid();
                        Intent in = new Intent(getActivity(), ShareDeviceActivity.class);
                        in.putExtra("cameraId", cameraId);
                        in.putExtra("uuid", uuid);
                        startActivity(in);
                    } else {
                        T.showShort(getActivity(), "暂时没有权限...");
                    }
                }
            });
            holer.rename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mode == Owner) {
                        int cameraId = mlist.get(position).getmCameraId();
                        Intent in = new Intent(getActivity(), ReNameActivity.class);
                        in.putExtra("cameraId", cameraId);
                        startActivity(in);
                    } else {
                        T.showShort(getActivity(), "暂时没有权限...");
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.cb_status)
            CheckBox cbStatus;
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_online)
            TextView tvOnline;
            @BindView(R.id.cb_share)
            CheckBox cbShare;
            @BindView(R.id.rl_share)
            RelativeLayout ivShare;
            @BindView(R.id.rl_rename)
            RelativeLayout rename;
            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
