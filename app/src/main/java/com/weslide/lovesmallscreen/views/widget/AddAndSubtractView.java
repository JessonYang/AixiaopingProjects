package com.weslide.lovesmallscreen.views.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.weslide.lovesmallscreen.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xu on 2016/6/15.
 * 加减的View
 */
public class AddAndSubtractView extends FrameLayout {
    @BindView(R.id.tv_aas_value)
    EditText tvValue;
    private OnValueChangeListener onValueChangeListener;
    private int maxValue = 99;
    private int minValue = 1;
    private int currentValue = 1;


    public AddAndSubtractView(Context context) {
        super(context);
        initView();
    }

    public AddAndSubtractView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AddAndSubtractView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_add_and_subtract, this, true);

        ButterKnife.bind(this);

        tvValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString() != null && editable.toString().length() > 0) {
                    int value = Integer.parseInt(editable.toString());
                    if (value > 0) {
                        currentValue = value;
                        if (onValueChangeListener != null) {
                            onValueChangeListener.change(value);
                        }
                    } else {
                        Toast.makeText(AddAndSubtractView.this.getContext(), "亲!购买数量必须大于0", Toast.LENGTH_SHORT).show();
                        tvValue.setText("1");
                        currentValue = 1;
                        if (onValueChangeListener != null) {
                            onValueChangeListener.change(value);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @OnClick({R.id.tv_aas_add, R.id.tv_aas_subtract})
    public void onClick(View view) {
        int value = Integer.parseInt(tvValue.getText().toString());
        switch (view.getId()) {
            case R.id.tv_aas_add:
                if (maxValue == value) {
                    return;
                }
                value++;
                currentValue = value;
                tvValue.setText(value + "");
                if (onValueChangeListener != null) {
                    onValueChangeListener.change(value);
                }

                break;
            case R.id.tv_aas_subtract:
                if (minValue == value) {
                    return;
                }
                value--;
                currentValue = value;
                tvValue.setText(value + "");
                if (onValueChangeListener != null) {
                    onValueChangeListener.change(value);
                }
                break;
        }
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setValue(int number) {
        tvValue.setText(number + "");
    }

    public void setMaxValue(int maxValue) {
        if (this.currentValue > maxValue) {
            tvValue.setText(maxValue + "");
            if (onValueChangeListener != null) {
                onValueChangeListener.change(maxValue);
            }
        }
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        if (this.currentValue < minValue) {
            tvValue.setText(minValue);
            if (onValueChangeListener != null) {
                onValueChangeListener.change(minValue);
            }
        }
        this.minValue = minValue;
    }

    public OnValueChangeListener getOnValueChangeListener() {
        return onValueChangeListener;
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    /**
     * 值改变事件
     */
    public interface OnValueChangeListener {
        void change(int change);
    }
}
