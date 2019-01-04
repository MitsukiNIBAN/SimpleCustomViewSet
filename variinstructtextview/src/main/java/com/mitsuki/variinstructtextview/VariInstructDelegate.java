package com.mitsuki.variinstructtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

public class VariInstructDelegate {

    private int DEFAULT_COLOR = 0XFF000000;

    private Paint mPaint;

    private float mArrowSize; //箭头大小[高度]。默认文字的高度
    private int isUpOrDown; //0 : up  1 : down
    private int mArrowColor;

    private float mArrowPadding;

    //扩展文字内容
    private CharSequence mExtendText;
    //扩展文字内容颜色
    private int mExtendTextColor;
    //扩展文字大小
    private float mExtendTextSize;

    private boolean isEnable;


    public VariInstructDelegate(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VariInstructTextView);
        mArrowSize = array.getDimension(R.styleable.VariInstructTextView_arrow_size, -1);
        isUpOrDown = array.getInt(R.styleable.VariInstructTextView_up_or_down, 0);

        mArrowColor = array.getColor(R.styleable.VariInstructTextView_arrow_color, DEFAULT_COLOR);

        mExtendText = array.getText(R.styleable.VariInstructTextView_extend_text);
        mExtendTextColor = array.getColor(R.styleable.VariInstructTextView_extend_text_color, -1);
        mExtendTextSize = array.getDimension(R.styleable.VariInstructTextView_extend_text_size, 7);

        mArrowPadding = array.getDimension(R.styleable.VariInstructTextView_arrow_padding, 8);

        isEnable = array.getBoolean(R.styleable.VariInstructTextView_instruct_enable, true);
    }

    public Paint getArrowPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mArrowColor);
        return mPaint;
    }

    public Paint getExtendTextPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.FILL);
        if (mExtendTextColor != -1) {
            mPaint.setColor(mExtendTextColor);
        }
        mPaint.setTextSize(mExtendTextSize);
        return mPaint;
    }

    public Path getArrow(int x, int y) {
        switch (isUpOrDown) {
            case 0:
                return getUpArrow(x, y);
            case 1:
                return getDownArrow(x, y);
            default:
                return new Path();
        }
    }

    //数值增加，箭头向上的路径
    private Path getUpArrow(int x, int y) {
        Path path = new Path();
        float width = mArrowSize / 3;
        float height = mArrowSize;
        //整个箭头高宽比为 3：1
        //从上的箭头的尖尖开始绘制
        path.moveTo(x + width / 2, y);
        //画线到左侧的点 (顶部的箭头在正方形中)
        path.lineTo(x, y + width);
        //往里画
        path.lineTo(x + width / 4, y + width);
        //往下画
        path.lineTo(x + width / 4, y + width + height / 2);
        //画弧
        float left1 = x + width / 4 - pointSpacing(x + width / 4, y + width + height / 2, x, y + height) * 2;
        float top1 = y + width + height / 2 - pointSpacing(x + width / 4, y + width + height / 2, x, y + height);
        float right1 = x + width / 4;
        float bottom1 = y + width + height / 2 + pointSpacing(x + width / 4, y + width + height / 2, x, y + height);
        RectF _acr1 = new RectF(left1, top1, right1, bottom1);
        path.arcTo(_acr1, 0, 60, false);
        //画弧
        float left2 = x - width / 4 * 3;
        float top2 = y + height - width;
        float right2 = x + width / 4 * 3;
        float bottom2 = y + height;
        RectF _acr2 = new RectF(left2, top2, right2, bottom2);
        path.arcTo(_acr2, 90, -90, false);

        //往上画
        path.lineTo(x + width / 4 * 3, y + width);
        //往外画
        path.lineTo(x + width, y + width);

        path.lineTo(x + width / 2, y);
        //结束
        path.setLastPoint(x + width / 2, y);
        return path;
    }

    //数值减少， 箭头向下的路径
    private Path getDownArrow(int x, int y) {
        Path path = new Path();
        float width = mArrowSize / 3;
        float height = mArrowSize;
        //从底部开始画

        path.moveTo(x + width / 2, y + height);
        path.lineTo(x, y + height - width);
        path.lineTo(x + width / 4, y + height - width);
        path.lineTo(x + width / 4, y + height / 2 - width);

        float left1 = x + width / 4 - pointSpacing(x + width / 4, y + height / 2 - width, x, y) * 2;
        float top1 = y + height / 2 - width - pointSpacing(x + width / 4, y + height / 2 - width, x, y);
        float right1 = x + width / 4;
        float bottom1 = y + height / 2 - width + pointSpacing(x + width / 4, y + height / 2 - width, x, y);
        RectF _acr1 = new RectF(left1, top1, right1, bottom1);
        path.arcTo(_acr1, 0, -60, false);

        float left2 = x - width / 4 * 3;
        float top2 = y;
        float right2 = x + width / 4 * 3;
        float bottom2 = y + width;
        RectF _acr2 = new RectF(left2, top2, right2, bottom2);
        path.arcTo(_acr2, -90, 90, false);

        path.lineTo(x + width / 4 * 3, y + height - width);
        path.lineTo(x + width, y + height - width);
        path.lineTo(x + width / 2, y + height);
        path.setLastPoint(x + width / 2, y + height);

        return path;
    }

    private float pointSpacing(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public float getArrowSize() {
        return mArrowSize;
    }

    public void setArrowSize(float mArrowSize) {
        this.mArrowSize = mArrowSize;
    }

    public CharSequence getExtendText() {
        return mExtendText;
    }

    public void setExtendText(CharSequence mExtendText) {
        this.mExtendText = mExtendText;
    }

    public float getArrowPadding() {
        return mArrowPadding;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void setIsUpOrDown(int isUpOrDown) {
        this.isUpOrDown = isUpOrDown;
    }
}
