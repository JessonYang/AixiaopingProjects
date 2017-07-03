package com.weslide.lovesmallscreen.activitys.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.activitys.TaoKeActivity;
import com.weslide.lovesmallscreen.core.BaseActivity;
import com.weslide.lovesmallscreen.models.bean.GetGoodsListBean;
import com.weslide.lovesmallscreen.utils.AppUtils;
import com.weslide.lovesmallscreen.utils.SerializableUtils;
import com.weslide.lovesmallscreen.view_yy.activity.SaveMoneySearchActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by xu on 2016/7/19.
 * 商品搜索界面
 */
public class GoodsSearchActivity_New extends BaseActivity {

    public static final String KEY_MALL_TYPE = "KEY_MALL_TYPE";
    String mallType = Constants.MALL_STAND_ALONE;

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tg_hot_tags)
    TagGroup tgHotTags;
    @BindView(R.id.search_tv)
    TextView searchTv;
    private ArrayList<String> tags = null;
    private ArrayList<String> newTags = new ArrayList<>();
    GetGoodsListBean mGoodsListReqeust = new GetGoodsListBean();
    private String titleType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_goods_search);
        ButterKnife.bind(this);

        loadBundle();

        mGoodsListReqeust.setMallTyle(mallType);

        /*toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        tags = new ArrayList<>();

        tgHotTags.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                search("", tag);
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    String value = etSearch.getText().toString();
                    search("", value);

                }
                return false;
            }
        });
    }

    private void loadBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mallType = bundle.getString(KEY_MALL_TYPE, Constants.MALL_STAND_ALONE);
            titleType = bundle.getString("titleType","");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        etSearch.setText("");
        tags.clear();
        ArrayList<String> data = (ArrayList<String>) SerializableUtils.getObjectByCacheFile(this, "searchName");
        if (data != null) {
            int size;
            if (data.size() > 12) {
                size = 12;
            } else {
                size = data.size();
            }
            for (int i = 0; i < size; i++) {
                tags.add(data.get(i));
            }
            tgHotTags.setTags(tags);
        }
    }

    public void search(String mallType, String value) {

        //原生版搜索
        /*mGoodsListReqeust.setSearch(value);
        Bundle bundle = new Bundle();
        bundle.putSerializable(GoodsClassifiActivity.KEY_REQUEST, mGoodsListReqeust);
        AppUtils.toActivity(this, GoodsClassifiActivity.class, bundle);*/

        //网页版搜索
        Bundle bundle = new Bundle();
//        bundle.putString(TaoKeActivity.KEY_LOAD_URL, "http://www.518wtk.com/index.php?s=/Index/seek.html");
        bundle.putString(TaoKeActivity.KEY_LOAD_URL, getIntent().getStringExtra("searchUrl"));
        bundle.putString("search", value);
        bundle.putString("cid", "-1");
        bundle.putString("title", getIntent().getExtras().getString("title"));
        AppUtils.toActivity(this, SaveMoneySearchActivity.class, bundle);
        newTags.clear();
        newTags.add(value);
        for (String mTag : tags) {
            newTags.add(mTag);
        }
        SerializableUtils.serializableToCacheFile(this, newTags, "searchName");
    }

    @OnClick({R.id.tv_must_see_strategy,R.id.search_tv,R.id.back_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_must_see_strategy:
                tags.clear();
                tgHotTags.setTags(tags);
                SerializableUtils.serializableToCacheFile(this, tags, "searchName");
                break;
            case R.id.search_tv:
                String value = etSearch.getText().toString();
                search("", value);
                break;
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
