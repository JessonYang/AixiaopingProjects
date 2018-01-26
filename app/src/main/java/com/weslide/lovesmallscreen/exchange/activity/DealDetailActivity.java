package com.weslide.lovesmallscreen.exchange.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.weslide.lovesmallscreen.Constants;
import com.weslide.lovesmallscreen.ContextParameter;
import com.weslide.lovesmallscreen.R;
import com.weslide.lovesmallscreen.core.SupportSubscriber;
import com.weslide.lovesmallscreen.exchange.widget.ExchangeDealView;
import com.weslide.lovesmallscreen.model_yy.javabean.ExchangeGoodDtModel;
import com.weslide.lovesmallscreen.model_yy.javabean.PostExchangeModel;
import com.weslide.lovesmallscreen.model_yy.javabean.SpecificationModel;
import com.weslide.lovesmallscreen.network.Request;
import com.weslide.lovesmallscreen.network.Response;
import com.weslide.lovesmallscreen.utils.CustomDialogUtil;
import com.weslide.lovesmallscreen.utils.RXUtils;
import com.weslide.lovesmallscreen.views.dialogs.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DealDetailActivity extends AppCompatActivity {

    @BindView(R.id.change_ll)
    LinearLayout changeLl;
    @BindView(R.id.edit_btn)
    Button editBtn;
    @BindView(R.id.opposite_good_view)
    ExchangeDealView oppositeGoodView;
    @BindView(R.id.add_good_rll)
    RelativeLayout addGoodRll;
    @BindView(R.id.my_good_view)
    ExchangeDealView myGoodView;
    private boolean isReplaceVisible = false;
    private ExchangeGoodDtModel oppositeGoods;
    private ExchangeGoodDtModel myGoods;
    public static int CHOOSE_SPEC_REQUEST = 0;
    public static int ADD_GOOD_REQUEST = 1;
    public static int ADD_GOOD_RESULT = 2;
    public static int CHOOSE_SPEC_RESULT = 3;
    private int oppositeGoodNum;
    private int myGoodNum;
    private SpecificationModel oppesiteSpec;
    private SpecificationModel mySpec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_detail);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            oppositeGoods = ((ExchangeGoodDtModel) bundle.getSerializable("goods"));
        }
        oppositeGoodView.bindView(this, oppositeGoods, "甲方");
    }

    @OnClick({R.id.cancel_exchange_btn, R.id.exchange_btn, R.id.back_iv, R.id.edit_btn, R.id.add_good_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            //发起换货
            case R.id.exchange_btn:
                postExchange();
                break;
            //取消换货(返回)
            case R.id.cancel_exchange_btn:
            case R.id.back_iv:
                finish();
                break;
            //编辑
            case R.id.edit_btn:
                if (isReplaceVisible) {
                    myGoodView.hiddenReplace();
                    isReplaceVisible = false;
                } else {
                    myGoodView.showReplace();
                    isReplaceVisible = true;
                }
                break;
            //添加商品
            case R.id.add_good_iv:
                Intent intent = new Intent(this, ChooseGoodsActivity.class);
                startActivityForResult(intent, ADD_GOOD_REQUEST);
                break;
        }
    }

    private void postExchange() {
        Request<PostExchangeModel> request = new Request<>();
        PostExchangeModel model = new PostExchangeModel();
        model.setChangeType(Constants.GOOD_EXCHANGE_TYPE);
        model.setInviteUserId(ContextParameter.getUserInfo().getUserId());
        model.setInviteGoodsId(myGoods.getGoodsId() + "");
        model.setInviteGoodsNum(myGoodNum);
        model.setInviteGoodStandardId(mySpec.getSpecId());

        model.setAcceptUserId(oppositeGoods.getGoodsUserHead());
        model.setAcceptGoodsId(oppositeGoods.getGoodsId() + "");
        model.setAcceptGoodsNum(oppositeGoodNum);
        model.setAcceptGoodStandardId(oppesiteSpec.getSpecId());
        request.setData(model);
        RXUtils.request(this, request, "putChangeOrder", new SupportSubscriber<Response>() {
            private LoadingDialog loadingDialog;

            @Override
            public void onStart() {
                loadingDialog = new LoadingDialog(DealDetailActivity.this);
                loadingDialog.show();
            }

            @Override
            public void onNext(Response response) {
                loadingDialog.dismiss();
                if (response.getStatus() == 1) {
                    CustomDialogUtil.showNoticDialog(DealDetailActivity.this, response.getMessage(), DealDetailActivity.this);
                } else {
                    CustomDialogUtil.showNoticDialog(DealDetailActivity.this, response.getMessage());
                }
            }

            @Override
            public void onResponseError(Response response) {
                loadingDialog.dismiss();
                CustomDialogUtil.showNoticDialog(DealDetailActivity.this, response.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                loadingDialog.dismiss();
                CustomDialogUtil.showNoticDialog(DealDetailActivity.this, "发起换货出错啦!");
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == CHOOSE_SPEC_REQUEST && resultCode == CHOOSE_SPEC_RESULT) {//选择规格
                String presentType = data.getStringExtra("presentType");
                if (presentType.equals("甲方")) {
                    oppesiteSpec = ((SpecificationModel) data.getSerializableExtra("spec"));
                    oppositeGoodNum = data.getIntExtra("goodNum", -1);
                    oppositeGoodView.setSpec(oppesiteSpec.getSpecStr());
                } else {
                    mySpec = ((SpecificationModel) data.getSerializableExtra("spec"));
                    myGoodNum = data.getIntExtra("goodNum", -1);
                    myGoodView.setSpec(mySpec.getSpecStr());
                }
            } else {//添加商品
                myGoods = ((ExchangeGoodDtModel) data.getSerializableExtra("goods"));
                if (myGoods != null) {
                    addGoodRll.setVisibility(View.GONE);
                    myGoodView.bindView(this, myGoods, "乙方");
                    editBtn.setVisibility(View.VISIBLE);
                    myGoodView.setVisibility(View.VISIBLE);
                }
            }
        }else super.onActivityResult(requestCode, resultCode, data);
    }

}
