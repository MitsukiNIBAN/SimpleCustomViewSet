package com.mitsuki.circleprogressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;

/**
 * xml代理
 */
public class CircleExponentDelegate {

    private final int DEFAULT_COLOR = 0xffDEECFA;

    private Paint mPaint;

    private int mBackGroundColor;


    private int percent;


    public CircleExponentDelegate(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
    }

//    /**
//     * 获取底部线的画笔
//     *
//     * @return
//     */
//    public Paint getBoardCirclePaint() {
//        mPaint.reset();
//        mPaint.setAntiAlias(true);
//        mPaint.setColor(mBoardCircleColor);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(mBoardCircleWidth);
//        return mPaint;
//    }
//
//    /**
//     * 获取进度线画笔
//     * 纯色方案+渐变色方案
//     *
//     * @param centerX 圈的中心x坐标
//     * @param centerY 圈的中心y坐标
//     * @param percent 圈的进度
//     * @return
//     */
//    public Paint getProgressCirclePaint(float centerX, float centerY, float percent) {
//        mPaint.reset();
//        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(mProgressCircleWidth);
//
//        if (mProgressCircleColorType == FILL) {
//            mPaint.setColor(mProgressCircleColor);
//        } else if (mProgressCircleColorType == GRADIENT) {
//            Matrix matrix = new Matrix();
//            matrix.setRotate(-90, centerX, centerY);
//            SweepGradient mSweepGradient = new SweepGradient(centerX, centerY, //以圆弧中心作为扫描渲染的中心以便实现需要的效果
//                    new int[]{mProgressCircleStartColor, mProgressCircleEndColor}, new float[]{0f, percent});
//            mSweepGradient.setLocalMatrix(matrix);
//            mPaint.setShader(mSweepGradient);
//        }
//        return mPaint;
//    }
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
//    /**********************************************************************************************/
//
//    public float getBoardCircleRadius(float range) {
//        return (range - mProgressCircleWidth) / 2;
//    }
//
//    public float getProgressCircleWidth() {
//        return mProgressCircleWidth;
//    }
//
//    public float getCirclePadding() {
//        return mCirclePadding;
//    }
//
//    public int getPercent() {
//        return percent;
//    }
//
//    public float getPercentProp() {
//        return (float) percent / 100;
//    }
//
//    public float getEndPointHollowSize() {
//        if (mProgressCircleWidth < mEndPointHollowSize) {
//            return mProgressCircleWidth;
//        }
//        return mEndPointHollowSize;
//    }
//
//    /**********************************************************************************************/

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
