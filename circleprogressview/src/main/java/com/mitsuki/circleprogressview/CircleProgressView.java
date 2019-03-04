package com.mitsuki.circleprogressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

public class CircleProgressView extends View {

    //view 的大小
    private int width;
    private int height;
    private int mCenterX;
    private int mCenterY;
    //xml参数代理
    private final CircleProgressDelegate mCircleProgressDelegate;

    private float mCircleRegion; //可进行绘制图形的正方形区域的边长


    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCircleProgressDelegate = new CircleProgressDelegate(context, attrs);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();

        mCenterX = width / 2;
        mCenterY = height / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(44, 44);
            return;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(w, w);
        }

        if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(h, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mCircleRegion = Math.min(width, height) - (2 * mCircleProgressDelegate.getCirclePadding());

        onDrawBoardCircle(canvas);
        onDrawProgressCircle(canvas);
        onDrawArcLineEndPoint(canvas);
    }


    //绘制底部细圆圈
    private void onDrawBoardCircle(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY,
                mCircleProgressDelegate.getBoardCircleRadius(mCircleRegion)
                , mCircleProgressDelegate.getBoardCirclePaint());
    }

    //绘制完成的圈
    private void onDrawProgressCircle(Canvas canvas) {
        float top = (height - mCircleRegion) / 2 + mCircleProgressDelegate.getProgressCircleWidth() / 2;
        float bottom = (height + mCircleRegion) / 2 - mCircleProgressDelegate.getProgressCircleWidth() / 2;
        float left = (width - mCircleRegion) / 2 + mCircleProgressDelegate.getProgressCircleWidth() / 2;
        float right = (width + mCircleRegion) / 2 - mCircleProgressDelegate.getProgressCircleWidth() / 2;
        RectF oval = new RectF(left, top, right, bottom);
        canvas.drawArc(oval, -90, 360 * mCircleProgressDelegate.getPercentProp(), false,
                mCircleProgressDelegate.getProgressCirclePaint(mCenterX, mCenterY, mCircleProgressDelegate.getPercentProp()));
    }

    //绘制圆端点
    private void onDrawArcLineEndPoint(Canvas canvas) {
        float mEndPointOneX = width / 2;
        float mEndPointOneY = (height - mCircleRegion) / 2 + mCircleProgressDelegate.getProgressCircleWidth() / 2;
        canvas.drawCircle(mEndPointOneX, mEndPointOneY,
                mCircleProgressDelegate.getProgressCircleWidth() / 2,
                mCircleProgressDelegate.getProgressEndPointPaint(true));
        if (mCircleProgressDelegate.getEndPointHollowSize() > 0) {
            canvas.drawCircle(mEndPointOneX, mEndPointOneY,
                    mCircleProgressDelegate.getEndPointHollowSize() / 2,
                    mCircleProgressDelegate.getEndPointHollowPaint());
        }


        double angle = 360 * mCircleProgressDelegate.getPercentProp();
        double x = width / 2 + Math.cos(Math.toRadians(90 - angle)) * (mCircleRegion - mCircleProgressDelegate.getProgressCircleWidth()) / 2;
        double y = height / 2 - Math.sin(Math.toRadians(90 - angle)) * (mCircleRegion - mCircleProgressDelegate.getProgressCircleWidth()) / 2;
        canvas.drawCircle((float) x, (float) y, mCircleProgressDelegate.getProgressCircleWidth() / 2,
                mCircleProgressDelegate.getProgressEndPointPaint(false));

        if (mCircleProgressDelegate.getEndPointHollowSize() > 0) {
            canvas.drawCircle((float) x, (float) y,
                    mCircleProgressDelegate.getEndPointHollowSize() / 2,
                    mCircleProgressDelegate.getEndPointHollowPaint());
        }
    }


    //获取文字的宽度
    private float getTextWidth(Paint paint, String str) {
        return paint.measureText(str);
    }

    //获取文字的高度
    private float getTextHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        //要考虑baseline到文字底部的距离，具体参考https://www.jianshu.com/p/814858d85a74
        //此处rect.bottom * 2是方便外部/2的计算
        return rect.height() - 2 * rect.bottom;
    }

    public void setPercent(int value) {
        if (value >= 0
                && value <= 100) {
            mCircleProgressDelegate.setPercent(value);
            invalidate();
        }
    }
}
