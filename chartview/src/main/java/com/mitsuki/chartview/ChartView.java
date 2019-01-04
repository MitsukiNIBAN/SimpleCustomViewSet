package com.mitsuki.chartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ChartView extends View {

    //xml参数代理
    private final ChartViewDelegate mChartViewDelegate;
    //View
    private int width; //view的宽度  预先作为横轴的长度 要根据其他数据再进行计算
    private int height;  //view的高度  预先作为纵轴的长度 要根据其他数据再进行计算0

    //计算
    private int mHorizontalAxisTextInterval;//横轴单位长度所占的像素
    private int mVerticalAxisTextInterval; //纵轴单位长度所占的像素
    private int mHorizontalAxisLength; //横轴长度
    private int mVerticalAxisLength;  //纵轴长度
    private int mVerticalAxisMaxPosition; //纵轴最高刻度位置
    private int mVerticalAxisMinPosition; //纵轴最低刻度位置
    private int mHorizontalAxisMaxPosition; //横轴最右刻度位置
    private int mHorizontalAxisMinPosition; //横轴最左刻度位置

    //曲线平滑 0为折线图
    //方式一 最好不要超过0.5
    //方式二 1为最佳 最好不要超过2
    private final double SMOOTHNESS = 0.5;

    private ChartBean mChart;
    private List<Point> mControlPointList; //曲线控制点
    private List<Point> mPointList; //坐标点

    public ChartView(Context context) {
        this(context, null);
        init(context);
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mChartViewDelegate = new ChartViewDelegate(context, attrs);
        init(context);
    }

    //初始化画笔等数据
    private void init(Context context) {
//        mChart = FalseDataProvider.getChartBean();
        if (null == mControlPointList) mControlPointList = new ArrayList<>();
        if (null == mPointList) mPointList = new ArrayList<>();
    }

    /**
     * 部分参数计算
     */
    public void onPartialParameterCalculation() {
        if (null == mChart) return;
        //计算两轴长度
        mVerticalAxisLength = height - mChartViewDelegate.getHorizontalAxisTextWidth();
        mHorizontalAxisLength = width - mChartViewDelegate.getVerticalAxisTextWidth();
        //计算两轴单位长度
        mVerticalAxisTextInterval = (mVerticalAxisLength - mChartViewDelegate.getVerticalReservedGap()
                - mChartViewDelegate.getGapWithTextAndArrow()) / (mChart.getVeriCount() - 1);
        mHorizontalAxisTextInterval = (mHorizontalAxisLength - mChartViewDelegate.getHorizontalReservedGap()
                - mChartViewDelegate.getGapWithTextAndArrow()) / (mChart.getPointCount() - 1);
        //计算两轴端点刻度
        mVerticalAxisMinPosition = mVerticalAxisLength - mChartViewDelegate.getVerticalReservedGap();
        mVerticalAxisMaxPosition = mVerticalAxisMinPosition - mVerticalAxisTextInterval * (mChart.getVeriCount() - 1);
        mHorizontalAxisMinPosition = mChartViewDelegate.getVerticalAxisTextWidth() + mChartViewDelegate.getHorizontalReservedGap();
        mHorizontalAxisMaxPosition = mHorizontalAxisMinPosition + (mChart.getPointCount() - 1) * mHorizontalAxisTextInterval;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        height = getHeight();
        width = getWidth();
        onPartialParameterCalculation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mChart) return;
        //清除列表数据
        mControlPointList.clear();
        mPointList.clear();
        //绘制纵轴坐标刻度文字
        //位置 x:纵轴文字宽度 - 轴离文字的距离 y:纵轴高度 - 单位长度
        for (int i = 0; i < mChart.getVeriCount(); i++) {
            if (mChartViewDelegate.isDrawVerticalAxisText()) {
                canvas.drawText(((int) Math.floor(mChart.getVerticalUnitValue() * i)) + mChart.getUnit(),
                        mChartViewDelegate.getVerticalAxisTextWidth() - mChartViewDelegate.getGapWithAxisAndText(),
                        mVerticalAxisMinPosition - (i * mVerticalAxisTextInterval),
                        mChartViewDelegate.getVerticalAxisTextPaint());
            }
            onDrawCoordinateLine(canvas, true, mVerticalAxisMinPosition - (i * mVerticalAxisTextInterval));
        }
        //绘制横轴坐标刻度 以及计算坐标点的位置
        //位置 x: mChartViewDelegate.getHorizontalReservedGap() + 纵轴文字宽度 + 单元长度  y：横轴高度+轴与文字间的距离 + 文字高度
        int j = 0;

        for (LinkedHashMap.Entry<String, Double> entry : mChart.getPointMap().entrySet()) {
//            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            double tempX = mChartViewDelegate.getHorizontalReservedGap() + mChartViewDelegate.getVerticalAxisTextWidth() + mHorizontalAxisTextInterval * j;
            double tempY = mVerticalAxisLength - mChart.getVeriPosition(entry.getValue(), mVerticalAxisTextInterval) - mChartViewDelegate.getVerticalReservedGap();
            mPointList.add(j, new Point(tempX, tempY));
            if (mChartViewDelegate.isDrawHorizontalAxisText()) {
                canvas.drawText(entry.getKey(),
                        mHorizontalAxisMinPosition + mHorizontalAxisTextInterval * j,
                        mVerticalAxisLength + mChartViewDelegate.getGapWithAxisAndText() + mChartViewDelegate.getHorizontalAxisTextSize(),
                        mChartViewDelegate.getHorizontalAxisTextPaint());
            }
            onDrawCoordinateLine(canvas, false, mHorizontalAxisMinPosition + mHorizontalAxisTextInterval * j);
            onDrawAlignLine(canvas, (float) tempX, (float) tempY);
            j++;
        }

        //绘制纵轴
        //起点 x:距离侧面‘留给纵轴文字的宽度’的位置 y：0
        //终点 x:距离侧面‘留给纵轴文字的宽度’的位置 y：轴高度的位置
        canvas.drawLine(mChartViewDelegate.getVerticalAxisTextWidth(), mVerticalAxisLength,
                mChartViewDelegate.getVerticalAxisTextWidth(), 0,
                mChartViewDelegate.getAxisLinePaint());
        //绘制横轴
        //起点 x：距离侧面‘留给纵轴文字的宽度’的位置 y：距离底部‘横轴单位长度所占的像素’的位置
        //终点 x：view的最后面 y：距离底部‘横轴单位长度所占的像素’的位置
        canvas.drawLine(mChartViewDelegate.getVerticalAxisTextWidth(), mVerticalAxisLength,
                width, mVerticalAxisLength,
                mChartViewDelegate.getAxisLinePaint());
        //绘制坐标轴箭头
        onDrawAxisArrow(canvas);

        //计算曲线控制点
        onCubicBezierCurveControlPointCalculationForThree();
        onDrawCubicBezierCurve(canvas);

    }

    /**********************************************************************************************/

    /**
     * 绘制坐标轴箭头
     *
     * @param canvas
     */
    private void onDrawAxisArrow(Canvas canvas) {
        if (!mChartViewDelegate.isDrawArrow()) return;
        //绘制x轴箭头
        canvas.drawLine(width, mVerticalAxisLength, width - mChartViewDelegate.getArrowSize(),
                mVerticalAxisLength - mChartViewDelegate.getArrowSize() / 2,
                mChartViewDelegate.getAxisLinePaint());
        canvas.drawLine(width, mVerticalAxisLength, width - mChartViewDelegate.getArrowSize(),
                mVerticalAxisLength + mChartViewDelegate.getArrowSize() / 2,
                mChartViewDelegate.getAxisLinePaint());
        //绘制y轴箭头
        canvas.drawLine(mChartViewDelegate.getVerticalAxisTextWidth(), 0,
                mChartViewDelegate.getVerticalAxisTextWidth() - mChartViewDelegate.getArrowSize() / 2, mChartViewDelegate.getArrowSize(),
                mChartViewDelegate.getAxisLinePaint());
        canvas.drawLine(mChartViewDelegate.getVerticalAxisTextWidth(), 0,
                mChartViewDelegate.getVerticalAxisTextWidth() + mChartViewDelegate.getArrowSize() / 2, mChartViewDelegate.getArrowSize(),
                mChartViewDelegate.getAxisLinePaint());
    }

    /**
     * 坐标网格线绘制
     * 注：使用drawPath保证在硬件加速开启的情况下也能绘制虚线
     */
    private void onDrawCoordinateLine(Canvas canvas, boolean isX, float value) {
        if (isX && mChartViewDelegate.isDrawGridLine()) {
            //在绘制纵轴时绘制横轴网格线
            Path temp = new Path();
            temp.reset();
            temp.moveTo(mChartViewDelegate.getVerticalAxisTextWidth(), value);
            temp.lineTo(mHorizontalAxisMaxPosition, value);
            canvas.drawPath(temp, mChartViewDelegate.getGridLinePaint());
        } else if (!isX && mChartViewDelegate.isDrawGridLine()) {
            //在绘制横轴时绘制纵轴网格线
            Path temp = new Path();
            temp.reset();
            temp.moveTo(value, mVerticalAxisLength);
            temp.lineTo(value, mVerticalAxisMaxPosition);
            canvas.drawPath(temp, mChartViewDelegate.getGridLinePaint());
        }
    }

    /**
     * 坐标点与坐标轴对齐线
     */

    private void onDrawAlignLine(Canvas canvas, float x, float y) {
        if (mChartViewDelegate.isDrawAlignLineForY()) {
            //与纵轴对齐的线
            Path temp = new Path();
            temp.reset();
            temp.moveTo(mChartViewDelegate.getVerticalAxisTextWidth(), y);
            temp.lineTo(x, y);
            canvas.drawPath(temp, mChartViewDelegate.getAlignYLinePaint());
        }
        if (mChartViewDelegate.isDrawAlignLineForX()) {
            //与横轴对齐的线
            Path temp = new Path();
            temp.reset();
            temp.moveTo(x, mVerticalAxisLength);
            temp.lineTo(x, y);
            canvas.drawPath(temp, mChartViewDelegate.getAlignXLinePaint());
        }
    }

    /**********************************************************************************************/
    //绘制三次贝塞尔曲线以及坐标点
    private void onDrawCubicBezierCurve(Canvas canvas) {
        if (mPointList.size() < 1) return;
        Path mPath = new Path();
        mPath.reset();
        mPath.moveTo((float) mPointList.get(0).x, (float) mPointList.get(0).y);
        //绘制坐标点以及曲线
        for (int i = 0; i < mPointList.size(); i++) {
            if (mChartViewDelegate.isDrawPoint())
                canvas.drawCircle((float) mPointList.get(i).x,
                        (float) mPointList.get(i).y,
                        mChartViewDelegate.getPointSize(), mChartViewDelegate.getPointPaint());

            if (i != mPointList.size() - 1) {
                Point leftControlPoint = mControlPointList.get(i * 2);
                Point rightControlPoint = mControlPointList.get(i * 2 + 1);
                Point nextPoint = mPointList.get(i + 1);
                mPath.cubicTo((float) leftControlPoint.x, (float) leftControlPoint.y,
                        (float) rightControlPoint.x, (float) rightControlPoint.y,
                        (float) nextPoint.x, (float) nextPoint.y);
            } else {
                mPath.setLastPoint((float) mPointList.get(i).x, (float) mPointList.get(i).y);
            }
        }

        canvas.drawPath(mPath, mChartViewDelegate.getLinePaint());
    }

    //三次贝塞尔曲线控制点计算方式一
    private void onCubicBezierCurveControlPointCalculationForOne() {
        for (int i = 0; i < mPointList.size(); i++) {
            Log.e("ndex", "=====>" +
                    i + "<======");
            if (i == 0) {
                //第一个点,没有左侧控制点
                Point point = mPointList.get(i);
                Point nextPoint = mPointList.get(i + 1);
                double controlX = point.x + (nextPoint.x - point.x) * SMOOTHNESS;
                double controlY = point.y;
                mControlPointList.add(new Point(controlX, controlY));
            } else if (i == (mChart.getPointCount() - 1)) {
                //最后一个点，没有右侧控制点
                Point point = mPointList.get(i);
                Point lastPoint = mPointList.get(i - 1);
                double controlX = point.x - (point.x - lastPoint.x) * SMOOTHNESS;
                double controlY = point.y;
                mControlPointList.add(new Point(controlX, controlY));
            } else {
                //当中的点，双侧都有控制点
                Point point = mPointList.get(i);
                Point lastPoint = mPointList.get(i - 1);
                Point nextPoint = mPointList.get(i + 1);
                double k = (nextPoint.y - lastPoint.y) / (nextPoint.x - lastPoint.x);
                double b = point.y - k * point.x;
                //添加前控制点
                double lastControlX = point.x - (point.x - lastPoint.x) * SMOOTHNESS;
                double lastControlY = k * lastControlX + b;
                mControlPointList.add(new Point(lastControlX, lastControlY));
                //添加后控制点
                double nextControlX = point.x + (nextPoint.x - point.x) * SMOOTHNESS;
                double nextControlY = k * nextControlX + b;
                mControlPointList.add(new Point(nextControlX, nextControlY));
            }
        }
    }

    //三次贝塞尔曲线控制点计算方式二
    private void onCubicBezierCurveControlPointCalculationForTwo() {
        for (int i = 0; i < mPointList.size(); i++) {

            if (i == 0) {
                //第一个点,没有左侧控制点
                Point point = mPointList.get(i);
                Point nextPoint = mPointList.get(i + 1);
                double controlX = point.x + (nextPoint.x - point.x) / 2 * SMOOTHNESS;
                double controlY = point.y;
                mControlPointList.add(new Point(controlX, controlY));
            } else if (i == (mChart.getPointCount() - 1)) {
                //最后一个点，没有右侧控制点
                Point point = mPointList.get(i);
                Point lastPoint = mPointList.get(i - 1);
                double controlX = point.x - (point.x - lastPoint.x) / 2 * SMOOTHNESS;
                double controlY = point.y;
                mControlPointList.add(new Point(controlX, controlY));
            } else {
                //当中的点，双侧都有控制点
                Point point = mPointList.get(i);
                Point lastPoint = mPointList.get(i - 1);
                Point nextPoint = mPointList.get(i + 1);
                //添加前控制点
                double lastControlX = point.x - (point.x - lastPoint.x) / 2 * SMOOTHNESS;
                double lastControlY = point.y;
                mControlPointList.add(new Point(lastControlX, lastControlY));
                //添加后控制点
                double nextControlX = point.x + (nextPoint.x - point.x) / 2 * SMOOTHNESS;
                double nextControlY = point.y;
                mControlPointList.add(new Point(nextControlX, nextControlY));
            }
        }
    }

    //三次贝塞尔曲线控制点计算方式三
    private void onCubicBezierCurveControlPointCalculationForThree() {
        for (int j = 0; j < mPointList.size() - 1; j++) {
            Point sp = mPointList.get(j);
            Point ep = mPointList.get(j + 1);
            double wt = (sp.x + ep.x) / 2;
            mControlPointList.add(new Point(wt, sp.y));
            mControlPointList.add(new Point(wt, ep.y));
        }
    }


    /**********************************************************************************************/


    public void setGridLineEnable(boolean isEnable) {
        mChartViewDelegate.setDrawGridLine(isEnable);
        invalidate();
    }

    public void onChangeGridLineEnable() {
        mChartViewDelegate.setDrawGridLine(!mChartViewDelegate.isDrawGridLine());
        invalidate();
    }

    public void setChart(ChartBean mChart) {
        this.mChart = mChart;
        requestLayout();
        invalidate();
    }
}
