package com.mitsuki.circleprogressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;

/**
 * xml代理
 */
public class CircleExponentDelegate {

    private final int[] colors = new int[]{
            0XFF45C481, 0XFF45C481, 0XFFF2E11E, 0XFFF2831E, 0XFF9433C3, 0XFF8C254B, 0XFF8C254B};
    private final float[] positions = new float[]{
            0f, 0.2f, 0.4f, 0.5f, 0.65f, 0.80f, 0.9f
    };

    private Paint mPaint;

    private int mBackGroundColor;
    private float mInternalPadding;
    private float mProgressWidth;

    private int percent;


    public CircleExponentDelegate(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleExponentView);

        mBackGroundColor = array.getColor(R.styleable.CircleExponentView_background_color, 0xffffffff);
        mInternalPadding = array.getDimension(R.styleable.CircleExponentView_internal_padding, 0);
        mProgressWidth = array.getDimension(R.styleable.CircleExponentView_progress_width, 60);
        percent = array.getInteger(R.styleable.CircleExponentView_exponent_percent, 0);
    }

    /**
     * 获取底部背景画笔
     *
     * @return
     */
    public Paint getBackGroundPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBackGroundColor);
        mPaint.setStyle(Paint.Style.FILL);
        return mPaint;
    }

    /**
     * 获取进度线画笔
     * 渐变色方案
     *
     * @param centerX 圈的中心x坐标
     * @param centerY 圈的中心y坐标
     * @return
     */
    public Paint getProgressCirclePaint(float centerX, float centerY) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mProgressWidth);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);

        SweepGradient sweepGradient = new SweepGradient(centerX, centerY, colors, positions);
        Matrix matrix = new Matrix();
        matrix.setRotate(90, centerX, centerY);//加上旋转还是很有必要的，每次最右边总是有一部分多余了,不太美观,也可以不加
        sweepGradient.setLocalMatrix(matrix);
        mPaint.setShader(sweepGradient);
        return mPaint;
    }

    public Paint getProgressDirectivePaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GRAY);
        return mPaint;
    }
//
//    /**
//     * 圈俩端点的画笔
//     *
//     * @param isStarter 是否是线开始的端点
//     * @return
//     */
//    public Paint getProgressEndPointPaint(boolean isStarter) {
//        mPaint.reset();
//        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStrokeWidth(mProgressCircleWidth);
//        if (mProgressCircleColorType == FILL) {
//            mPaint.setColor(mProgressCircleColor);
//        } else if (mProgressCircleColorType == GRADIENT) {
//            mPaint.setColor(isStarter
//                    ? mProgressCircleStartColor
//                    : mProgressCircleEndColor);
//        }
//        return mPaint;
//    }
//
//    public Paint getEndPointHollowPaint() {
//        mPaint.reset();
//        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStrokeWidth(getEndPointHollowSize());
//        mPaint.setColor(mBoardCircleColor);
//        return mPaint;
//    }
//
//

    /**********************************************************************************************/
    public float getInternalPadding() {
        return mInternalPadding;
    }

    public float getProgressWidth() {
        return mProgressWidth;
    }

    public float getPercent() {
        return percent;
    }

    /**********************************************************************************************/

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
