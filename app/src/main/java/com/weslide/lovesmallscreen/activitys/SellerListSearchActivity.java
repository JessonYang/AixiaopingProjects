package com.weslide.lovesmallscreen.activitys;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.core.RecyclerViewSubscriber;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.mall.SellerListActivity_new;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.models.SellerList;
import com.weslide.lovesmallscreen.models.bean.GetSellerListBean;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.MyOpenHelper;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.adapters.SellerListAdapter;
import com.weslide.lovesmallscreen.views.adapters.SellerListSearchPwLvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YY on 2017/4/24.
 */
public class SellerListSearchActivity extends BaseActivity {
    private Toolbar toolbar;
    private Button reset_btn;
    private EditText search;
    private SuperRecyclerView lv;
    GetSellerListBean getSellerListBean = new GetSellerListBean();
    SellerList mSellerList = new SellerList();
    private SellerListAdapter mAdapter;
    private String edtMsg;
    private int tag = 0;
    private SQLiteDatabase db;
    private List<String> list = new ArrayList<>();
    private PopupWindow pw;
    private ListView pwLv;
    private View header;
    private ImageView headerDeleteIv;
    private int touchType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_list_search);
        initView();
        db = new MyOpenHelper(this).getReadableDatabase();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(layoutManager);
        mAdapter = new SellerListAdapter(this, mSellerList);
        lv.setAdapter(mAdapter);
        View pwView = LayoutInflater.from(this).inflate(R.layout.seller_list_search_pw, null, false);
        pwLv = ((ListView) pwView.findViewById(R.id.seller_list_search_pw_lv));
        pw = new PopupWindow(pwView, ViewGroup.LayoutParams.MATCH_PARENT, 520);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOutsideTouchable(true);
        pw.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        pw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (touchType == 0) {
                    searchDB();
                    if (list != null && list.size() > 0) {
                        SellerListSearchPwLvAdapter adapter = new SellerListSearchPwLvAdapter(SellerListSearchActivity.this, list);
                        pwLv.setAdapter(adapter);
                        if (header == null) {
                            header = LayoutInflater.from(SellerListSearchActivity.this).inflate(R.layout.seller_list_search_pw_lv_header, null, false);
                            headerDeleteIv = (ImageView) header.findViewById(R.id.header_delete_iv);
                            pwLv.addHeaderView(header);
                        }
                        headerDeleteIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (list.size() > 0) {
                                    list.clear();
                                    deleteAll();
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                        adapter.setOnDeleteListenner(new SellerListSearchPwLvAdapter.OnDeleteListenner() {
                            @Override
                            public void onDeleteClick(int position) {
                                delete(list.get(position));
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        pw.showAsDropDown(toolbar);
                        pwLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //                            Log.d("雨落无痕丶", "onItemClick: " + i + "size:" + list.size());
                                if (i != 0) {
                                    String s = list.get(i - 1);
                                    loadData(s);
                                    tag = 1;
                                    reset_btn.setText("重置");
                                    search.setText(s);
                                    pw.dismiss();
                                    searchDB();
                                    for (String str : list) {
                                        if (s.equals(str)) {
                                            return;
                                        }
                                    }
                                    addData(s);
                                }
                            }
                        });
                    }
                }
            }
        });
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    touchType = 0;
                }
                return false;
            }
        });
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 0 || charSequence.toString() == null) {
                    reset_btn.setText("搜索");
                    tag = 0;
                }
                edtMsg = charSequence.toString();
//                loadData(edtMsg);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    touchType = 1;
//                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    loadData(edtMsg);
                    reset_btn.setText("重置");
                    tag = 1;
                    if (pw != null && pw.isShowing()) {
                        pw.dismiss();
                    }
                    if (edtMsg != null && edtMsg.length() > 0) {
                        searchDB();
                        for (String str : list) {
                            if (edtMsg.equals(str)) {
                                return false;
                            }
                        }
                        addData(edtMsg);
                    }
                }
                return false;
            }
        });
        /*search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER){
                    loadData(edtMsg);
                    reset_btn.setText("重置");
                    tag = 1;
                    if (pw != null && pw.isShowing()) {
                        pw.dismiss();
                    }
                    if (edtMsg != null && edtMsg.length() > 0) {
                        searchDB();
                        for (String str : list) {
                            if (edtMsg.equals(str)) {
                                return true;
                            }
                        }
                        addData(edtMsg);
                    }
                    Toast.makeText(SellerListSearchActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });*/
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag == 0) {
                    loadData(edtMsg);
                    reset_btn.setText("重置");
                    tag = 1;
                    if (pw != null && pw.isShowing()) {
                        pw.dismiss();
                    }
                    if (edtMsg != null && edtMsg.length() > 0) {
                        searchDB();
                        for (String str : list) {
                            if (edtMsg.equals(str)) {
                                return;
                            }
                        }
                        addData(edtMsg);
                    }
                } else if (tag == 1) {
                    loadData(null);
                    ((Button) view).setText("搜索");
                    search.setText("");
                    tag = 0;
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        toolbar = ((Toolbar) findViewById(R.id.toolbar));
        reset_btn = ((Button) findViewById(R.id.btn_search_reset));
        search = ((EditText) findViewById(R.id.et_search));
        lv = ((SuperRecyclerView) findViewById(R.id.seller_list_lv));

    }

    public void loadData(String search) {
        Request request = new Request();
        getSellerListBean.setPageIndex(1);
        getSellerListBean.setType("LIST");//列表类型
        getSellerListBean.setSearch(search);
        request.setData(getSellerListBean);
        RXUtils.request(this, request, "getSellerList", new RecyclerViewSubscriber<Response<SellerList>>(mAdapter, mSellerList) {
            @Override
            public void onSuccess(Response<SellerList> sellerListResponse) {
                SellerListActivity_new.sellerList = (ArrayList) sellerListResponse.getData().getDataList();
                mAdapter.addDataListNotifyDataSetChanged(sellerListResponse.getData());
            }
        });
    }

    public void searchDB() {
        list.clear();
        //推荐使用这种查询方式
        Cursor cursor = db.query(true, MyOpenHelper.USERTABLE, null, null, null, null, null, null, null);
        //如果cursor一直可以向下移动，则一直遍历数据库
        while (cursor.moveToNext()) {
            //获取username字段的下标,区分大小写
            int usernameIndex = cursor.getColumnIndex("NAME");
            String username = cursor.getString(usernameIndex);
            list.add(username);
        }
        //cursor使用完成之后一定要关闭
        cursor.close();
    }

    public void delete(String s) {
        //第一种删除方式，直接使用Sql语句进行删除，缺点是没有返回值
//        db.execSQL("delete from " + DBHelper.USERTABLE + " where username=?", new String[]{"zhangsan"});
        //第二种删除方式（推荐）：
        //1.要删除数据的表名
        //2.删除条件
        //3.删除条件的值
        //返回值表示删除的数据条数
        int delete = db.delete(MyOpenHelper.USERTABLE, "name=? ", new String[]{s});
//        Toast.makeText(SellerListSearchActivity.this, "删了:"+delete, Toast.LENGTH_SHORT).show();
    }

    public void deleteAll() {
        //第一种删除方式，直接使用Sql语句进行删除，缺点是没有返回值
//        db.execSQL("delete from " + DBHelper.USERTABLE + " where username=?", new String[]{"zhangsan"});
        //第二种删除方式（推荐）：
        //1.要删除数据的表名
        //2.删除条件
        //3.删除条件的值
        //返回值表示删除的数据条数
        int delete = db.delete(MyOpenHelper.USERTABLE, null, null);
//        Toast.makeText(SellerListSearchActivity.this, "删除了" + delete + "条数据", Toast.LENGTH_SHORT).show();
    }

    public void addData(String s) {
        ContentValues values = new ContentValues();
        values.put("name", s);
        db.insertWithOnConflict(MyOpenHelper.USERTABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
