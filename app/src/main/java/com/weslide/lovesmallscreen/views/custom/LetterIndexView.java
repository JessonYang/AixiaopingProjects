package com.weslide.lovesmallscreen.views.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by YY on 2016/9/27.
 */

public class LetterIndexView extends View {
    private String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint paint;
    //用来记录手指当前所在的位置
    private int currentPosition = -1;
    private TextView showLetterTv;

    public void setShowLetterTv(TextView showLetterTv) {
        this.showLetterTv = showLetterTv;
    }

    public LetterIndexView(Context context) {
        this(context, null);
    }

    public LetterIndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
//        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(24);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取每一个字母所占高度
        int perLetterHeight = getMeasuredHeight() / letters.length;
        for (int i = 0; i < letters.length; i++) {
            if (i == currentPosition) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.BLACK);
            }
            canvas.drawText(letters[i], (getMeasuredWidth() - paint.measureText(letters[i])) / 2, (i + 1) * perLetterHeight, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        int perLetterHeight = getMeasuredHeight() / letters.length;
        currentPosition = (int) (y / perLetterHeight);
        switch (event.getAction()) {
            //处理按下和手指移动时的操作
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setBackgroundColor(Color.parseColor("#11000000"));
//                if (showLetterTv != null) {
                if (currentPosition > -1 && currentPosition < letters.length) {
//                        showLetterTv.setVisibility(View.VISIBLE);
//                        showLetterTv.setText(letters[currentPosition]);
                    if (updateListView != null) {
                        String firstLetter = letters[currentPosition];
                        if (!firstLetter.equals("I") && !firstLetter.equals("O") && !firstLetter.equals("U") && !firstLetter.equals("V")) {
                            updateListView.updateListView(firstLetter);
                        }
                    }
                }
//        }
                break;
            //处理手指抬起时的操作
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.TRANSPARENT);
//                if (showLetterTv != null) {
//                    showLetterTv.setVisibility(View.GONE);
//                }
                break;
        }
        invalidate();
        return true;
    }

    public interface UpdateListView {
        public void updateListView(String firstLetter);
    }

    private UpdateListView updateListView;

    public void setUpdateListView(UpdateListView updateListView) {
        this.updateListView = updateListView;
    }

    public void updateLetterIndexView(String letter) {
        for (int i = 0; i < letters.length; i++) {
            if (letters[i].equals(letter)) {
                currentPosition = i;
                invalidate();
            }
        }
    }
}
