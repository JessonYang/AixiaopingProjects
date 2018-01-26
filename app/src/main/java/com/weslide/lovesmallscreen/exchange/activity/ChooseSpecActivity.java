package com.weslide.lovesmallscreen.exchange.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.exchange.adapter.ExchangeExtensionSpecGvAdapter;
import com.weslide.lovesmallscreen.exchange.widget.ExchangeDealView;
import com.weslide.lovesmallscreen.model_yy.javabean.ExchangeGoodDtModel;
import com.weslide.lovesmallscreen.model_yy.javabean.SpecificationModel;
import com.weslide.lovesmallscreen.view_yy.customview.MyGridView;
import com.weslide.lovesmallscreen.views.custom.CustomToolbar;
import com.weslide.lovesmallscreen.views.widget.AddAndSubtractView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseSpecActivity extends AppCompatActivity implements AddAndSubtractView.OnValueChangeListener, AdapterView.OnItemClickListener {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.good_view)
    ExchangeDealView goodView;
    @BindView(R.id.spec_gv)
    MyGridView specGv;
    @BindView(R.id.as_number)
    AddAndSubtractView asNumber;
    private ArrayList<SpecificationModel> specificationList;
    private ExchangeGoodDtModel goods;
    private int goodNum = 1;
    private ExchangeExtensionSpecGvAdapter mAdapter;
    private int curPos;
    private Intent intent;
    private String presentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_spec);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        intent = getIntent();
        goods = ((ExchangeGoodDtModel) intent.getSerializableExtra("goods"));
        specificationList = goods.getSpecifications();
        presentType = getIntent().getStringExtra("presentType");
        goodView.bindView(this,goods, presentType);
        goodView.hiddenChooseSpec();
        asNumber.setMaxValue(specificationList.get(0).getStock());
        asNumber.setOnValueChangeListener(this);
        mAdapter = new ExchangeExtensionSpecGvAdapter(this, specificationList);
        specGv.setAdapter(mAdapter);
        specGv.setOnItemClickListener(this);
    }

    @OnClick(R.id.sure_btn)
    public void onClick() {
        intent.putExtra("spec",specificationList.get(curPos));
        intent.putExtra("goodNum",goodNum);
        intent.putExtra("presentType",presentType);
        setResult(DealDetailActivity.CHOOSE_SPEC_RESULT,intent);
        finish();
    }

    @Override
    public void change(int change) {
        goodNum = change;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        curPos = i;
        mAdapter.changeSelect(curPos);
        asNumber.setMaxValue(specificationList.get(curPos).getStock());
    }
}
