package com.mitsuki.variinstructtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class VariInstructTextView extends TextView {

    //view 的大小
    private int width;
    private int height;

    private VariInstructDelegate mVariInstructDelegate;

    public VariInstructTextView(Context context) {
        this(context, null);
    }

    public VariInstructTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mVariInstructDelegate = new VariInstructDelegate(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //不需要时直接return
        if (!mVariInstructDelegate.isEnable()) return;

        //根据父类计算的高度和宽度，重新加算上增加内容的高宽
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        float tempTextHeight = getTextHeight(getPaint(), getText().toString());
        if (mVariInstructDelegate.getArrowSize() == -1) {
            //若没有指定箭头大小，则跟随view的大小走
            mVariInstructDelegate.setArrowSize(tempTextHeight);
        } else {
            if (mVariInstructDelegate.getArrowSize() > tempTextHeight) {
                h = (int) Math.ceil(h - tempTextHeight + mVariInstructDelegate.getArrowSize());
            }
        }
        w += mVariInstructDelegate.getArrowPadding();
        w += mVariInstructDelegate.getArrowSize() / 3;
        if (!TextUtils.isEmpty(mVariInstructDelegate.getExtendText())) {
            w += mVariInstructDelegate.getArrowPadding();
            w += getTextWidth(mVariInstructDelegate.getExtendTextPaint(), mVariInstructDelegate.getExtendText().toString());
        }

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //不需要时直接return
        if (!mVariInstructDelegate.isEnable()) return;

        Rect bounds = new Rect();
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), bounds);

        float textHeight = bounds.height();
        float tempPadding = (height - textHeight) / 2;
        float tempBaseLine = tempPadding + (bounds.bottom - 1) / 2;

        float extendTextWidth = 0;

        //绘制文字
        if (!TextUtils.isEmpty(mVariInstructDelegate.getExtendText())) {
            Paint paint = mVariInstructDelegate.getExtendTextPaint();
            extendTextWidth = getTextWidth(paint, mVariInstructDelegate.getExtendText().toString());

            canvas.drawText(mVariInstructDelegate.getExtendText(),
                    0, mVariInstructDelegate.getExtendText().length(),
                    width - getPaddingRight() - extendTextWidth,
                    getPaddingTop() + getTextHeight(paint, mVariInstructDelegate.getExtendText().toString()) + tempPadding,
                    paint);
        }

        //绘制箭头标志
        canvas.drawPath(mVariInstructDelegate.getArrow(
                (int) (width - getPaddingRight() - extendTextWidth
                        - mVariInstructDelegate.getArrowPadding() / 2
                        - mVariInstructDelegate.getArrowSize() / 3),
                (int) (height - tempBaseLine - mVariInstructDelegate.getArrowSize())
                ),
                mVariInstructDelegate.getArrowPaint());
    }


    private int getTextHeight(Paint paint, String str) {
        //计算的宽度 与字体的大小和长度有关 用画笔来测量
        Rect bounds = new Rect();
        //获取文本的Rect [区域]
        //参数一：要测量的文字、参数二：从位置0开始、参数三：到文字的长度、参数四：
        paint.getTextBounds(str, 0, str.length(), bounds);
        //文字的宽度
        return bounds.height();
    }

    private int getTextWidth(Paint paint, String str) {
        //计算的宽度 与字体的大小和长度有关 用画笔来测量
        Rect bounds = new Rect();
        //获取文本的Rect [区域]
        //参数一：要测量的文字、参数二：从位置0开始、参数三：到文字的长度、参数四：
        paint.getTextBounds(str, 0, str.length(), bounds);
        //文字的宽度
        return bounds.width();
    }

    public void setExtendText(CharSequence text) {
        mVariInstructDelegate.setExtendText(text);
        requestLayout();
        invalidate();
    }

    public void setInstructEnable(boolean enable) {
        mVariInstructDelegate.setEnable(enable);
        requestLayout();
        invalidate();
    }

    public void setArrowDirection(boolean isUp) {
        if (isUp) {
            mVariInstructDelegate.setIsUpOrDown(0);
        } else {
            mVariInstructDelegate.setIsUpOrDown(1);
        }
    }

}
