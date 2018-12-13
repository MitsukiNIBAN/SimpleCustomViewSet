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
public class CircleProgressDelegate {

    private final int DEFAULT_COLOR = 0xffDEECFA;

    private Paint mPaint;

    private float mBoardCircleWidth; //细圈线宽度
    private int mBoardCircleColor; //细圈线颜色

    private float mProgressCircleWidth; // 外圈线宽度
    private int mProgressCircleColor; // 外圈线颜色
    private int mProgressCircleStartColor; // 如果是渐变的话，开始颜色
    private int mProgressCircleEndColor; //如果是渐变的话，结束颜色
    private int mProgressCircleColorType; //颜色类型，渐变还是纯色
    private final int GRADIENT = 1;
    private final int FILL = 0;

    private float mCirclePadding; //圈的填充

    private String mText;
    private int mTextColor;
    private float mTextSize;
    private boolean isDrawPercent;
    private int percent;
    private int mPercentTextColor;
    private float mPercentTextSize;
    private float mTextPadding;


    public CircleProgressDelegate(Context context,AttributeSet attrs) {
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);

        mBoardCircleWidth = array.getDimension(R.styleable.CircleProgressView_board_circle_width, 4);
        mBoardCircleColor = array.getColor(R.styleable.CircleProgressView_board_circle_color, DEFAULT_COLOR);

        mProgressCircleWidth = array.getDimension(R.styleable.CircleProgressView_progress_circle_width, 20);
        mProgressCircleColor = array.getColor(R.styleable.CircleProgressView_progress_circle_color, DEFAULT_COLOR);
        mProgressCircleColorType = array.getInt(R.styleable.CircleProgressView_progress_circle_color_type, FILL);
        mProgressCircleStartColor = array.getColor(R.styleable.CircleProgressView_progress_circle_gradient_start_color, DEFAULT_COLOR);
        mProgressCircleEndColor = array.getColor(R.styleable.CircleProgressView_progress_circle_gradient_end_color, DEFAULT_COLOR);

        mCirclePadding = array.getDimension(R.styleable.CircleProgressView_circle_padding, 0);

        mText = array.getString(R.styleable.CircleProgressView_text);
        mTextColor = array.getColor(R.styleable.CircleProgressView_text_color, DEFAULT_COLOR);
        mTextSize = array.getDimension(R.styleable.CircleProgressView_text_size, 14);

        percent = array.getInteger(R.styleable.CircleProgressView_percent, 0);
        isDrawPercent = array.getBoolean(R.styleable.CircleProgressView_draw_percent, true);
        mPercentTextColor = array.getColor(R.styleable.CircleProgressView_percent_text_color, DEFAULT_COLOR);
        mPercentTextSize = array.getDimension(R.styleable.CircleProgressView_percent_text_size, 80);
        mTextPadding = array.getDimension(R.styleable.CircleProgressView_text_padding, 0);
    }

    /**
     * 获取底部线的画笔
     *
     * @return
     */
    public Paint getBoardCirclePaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBoardCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBoardCircleWidth);
        return mPaint;
    }

    /**
     * 获取进度线画笔
     * 纯色方案+渐变色方案
     *
     * @param centerX 圈的中心x坐标
     * @param centerY 圈的中心y坐标
     * @param percent 圈的进度
     * @return
     */
    public Paint getProgressCirclePaint(float centerX, float centerY, float percent) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mProgressCircleWidth);

        if (mProgressCircleColorType == FILL) {
            mPaint.setColor(mProgressCircleColor);
        } else if (mProgressCircleColorType == GRADIENT) {
            Matrix matrix = new Matrix();
            matrix.setRotate(-90, centerX, centerY);
            SweepGradient mSweepGradient = new SweepGradient(centerX, centerY, //以圆弧中心作为扫描渲染的中心以便实现需要的效果
                    new int[]{mProgressCircleStartColor, mProgressCircleEndColor}, new float[]{0f, percent});
            mSweepGradient.setLocalMatrix(matrix);
            mPaint.setShader(mSweepGradient);
        }
        return mPaint;
    }

    /**
     * 获取进度线画笔
     * 纯色方案
     *
     * @return
     */
    public Paint getProgressCirclePaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mProgressCircleWidth);
        mPaint.setColor(mProgressCircleColor);
        return mPaint;
    }

    /**
     * 圈俩端点的画笔
     *
     * @param isStarter 是否是线开始的端点
     * @return
     */
    public Paint getProgressEndPointPaint(boolean isStarter) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mProgressCircleWidth);
        if (mProgressCircleColorType == FILL) {
            mPaint.setColor(mProgressCircleColor);
        } else if (mProgressCircleColorType == GRADIENT) {
            mPaint.setColor(isStarter
                    ? mProgressCircleStartColor
                    : mProgressCircleEndColor);
        }
        return mPaint;
    }

    /**
     * 获取百分数值画笔
     *
     * @param isSymbol 是不是百分号
     * @return
     */
    public Paint getPersentTextPaint(boolean isSymbol) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mPercentTextColor);
        if (isSymbol) {
            mPaint.setTextSize(mPercentTextSize / 3);
            mPaint.setTextAlign(Paint.Align.LEFT);
        } else {
            mPaint.setTextSize(mPercentTextSize);
            mPaint.setTextAlign(Paint.Align.CENTER);
        }
        return mPaint;
    }

    public Paint getTextPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.FILL);
        return mPaint;
    }

    /**********************************************************************************************/

    public float getBoardCircleRadius(float range) {
        return (range - mProgressCircleWidth) / 2;
    }

    public float getProgressCircleWidth() {
        return mProgressCircleWidth;
    }

    public float getCirclePadding() {
        return mCirclePadding;
    }

    public String getText() {
        return mText;
    }

    public boolean isDrawPercent() {
        return isDrawPercent;
    }

    public String getPercentText() {
        return String.valueOf(percent);
    }

    public int getPercent() {
        return percent;
    }

    public float getPercentProp() {
        return (float) percent / 100;
    }

    public float getTextPadding() {
        return mTextPadding;
    }
}
