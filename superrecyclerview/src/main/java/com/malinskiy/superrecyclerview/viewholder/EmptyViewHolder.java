package com.malinskiy.superrecyclerview.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.R;
import com.rey.material.widget.Button;

/**
 * Created by xu on 2016/7/2.
 * 数据为空时显示
 */
public class EmptyViewHolder extends DifferentSituationViewHolder {

    private final Button btn;
    public View view;
    public ImageView ivImage;
    public TextView tvText;
    private View.OnClickListener onClickListener;

    public EmptyViewHolder(final Context context, ViewGroup parent, int type) {
        switch (type){
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.layout_empty, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.layout_shopping_car_empty, parent, false);
                break;
        }

        ivImage = (ImageView) view.findViewById(R.id.iv_empty_image);
        tvText = (TextView) view.findViewById(R.id.tv_empty_text);
        btn = ((Button) view.findViewById(R.id.btn_empty_reload));
        if (type == 1) {
            btn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            btn.setBackground(context.getResources().getDrawable(R.drawable.btn_bg_yes_click));
                            btn.setTextColor(Color.parseColor("#ffffff"));
                            break;
                        case MotionEvent.ACTION_UP:
                            btn.setBackground(context.getResources().getDrawable(R.drawable.btn_bg_no_click));
                            btn.setTextColor(Color.parseColor("#ff2d47"));
                            break;
                    }
                    return false;
                }
            });
        }
    }


    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

        view.findViewById(R.id.btn_empty_reload).setOnClickListener(getOnClickListener());
    }


    @Override
    public View getView() {
        return view;
    }
}
