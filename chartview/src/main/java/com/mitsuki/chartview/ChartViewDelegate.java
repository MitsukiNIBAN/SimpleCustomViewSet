package com.mitsuki.chartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;

public class ChartViewDelegate {

    /**
     * 统一使用该画笔
     */
    private Paint mPaint;

    /**
     * 统一默认颜色
     */
    private final int ALL_DEFAULT_COLOR = 0xFF7C7C7C;

    /**
     * 暂时写死
     * 坐标轴刻度到两端箭头的距离
     */
    private final int mGapWithTextAndArrow = 48;

    /**
     * 线的类型
     */
    private final int SOLID = 0; //实线

    private final int DASHED = 1; //虚线

    /**
     * 坐标轴线粗细
     */
    private int mAxisLineWidth;

    /**
     * 坐标轴线颜色
     */
    private int mAxisLineColor;

    /**
     * 坐标箭头大小
     */
    private int mArrowSize;

    /**
     * 是否绘制坐标箭头
     */
    private boolean isDrawArrow;

    /**
     * 坐标点的大小
     */
    private int mPointSize;

    /**
     * 坐标点的颜色
     */
    private int mPointColor;

    /**
     * 是否绘制坐标点
     */
    private boolean isDrawPoint;

    /**
     * 曲线的宽度
     */
    private int mLineWidth;

    /**
     * 曲线的颜色
     */
    private int mLineColor;

    /**
     * 留给横轴文字的宽度【横轴到view边缘的距离】
     */
    private int mHorizontalAxisTextWidth;

    /**
     * 是否绘制横轴文字
     */
    private boolean isDrawHorizontalAxisText;

    /**
     * 横轴文字颜色
     */
    private int mHizontalAxisTextColor;

    /**
     * 横轴文字大小
     */
    private int mHorizontalAxisTextSize;

    /**
     * 横轴预留间隙
     */
    private int mHorizontalReservedGap;

    /**
     * 留给纵轴文字的宽度【纵轴到view边缘的距离】
     */
    private int mVerticalAxisTextWidth;

    /**
     * 是否绘制纵轴文字
     */
    private boolean isDrawVerticalAxisText;

    /**
     * 纵轴文字颜色
     */
    private int mVerticalAxisTextColor;

    /**
     * 纵轴文字大小
     */
    private int mVerticalAxisTextSize;

    /**
     * 纵轴预留间隙
     */
    private int mVerticalReservedGap;

    /**
     * 文字和轴之间的距离
     */
    private int mGapWithAxisAndText;

    /**
     * 是否绘制坐标点与x轴对准的线
     */
    private boolean isDrawAlignLineForX;

    /**
     * 是否绘制坐标点与y轴对准的线
     */
    private boolean isDrawAlignLineForY;

    /**
     * 坐标点与x轴对准的线的颜色
     */
    private int mAlignXLineColor;

    /**
     * 坐标点与y轴对准的线的颜色
     */
    private int mAlignYLineColor;

    /**
     * 坐标点与x轴对准的线的粗细
     */
    private int mAlignXLineWidth;

    /**
     * 坐标点与y轴对准的线的粗细
     */
    private int mAlignYLineWidth;

    /**
     * 坐标点与x轴对准的线的类型
     */
    private int mAlignXType;

    /**
     * 坐标点与y轴对准的线的类型
     */
    private int mAlignYType;

    /**
     * 是否绘制网格线
     */
    private boolean isDrawGridLine;

    /**
     * 网格线类型，虚线实线等
     */
    private int mGridLineType;

    /**
     * 网格线颜色
     */
    private int mGridLineColor;

    /**
     * 网格线粗细
     */
    private int mGridLineWidth;


    public ChartViewDelegate(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ChartView);

        mAxisLineWidth = (int) array.getDimension(R.styleable.ChartView_axis_line_width, 2);
        mAxisLineColor = array.getColor(R.styleable.ChartView_axis_line_color, ALL_DEFAULT_COLOR);
        isDrawArrow = array.getBoolean(R.styleable.ChartView_draw_arrow, true);
        mArrowSize = (int) array.getDimension(R.styleable.ChartView_arrow_size, 12);

        mPointSize = (int) array.getDimension(R.styleable.ChartView_point_size, 6);
        mPointColor = array.getColor(R.styleable.ChartView_point_color, ALL_DEFAULT_COLOR);
        isDrawPoint = array.getBoolean(R.styleable.ChartView_draw_point, true);

        mLineWidth = (int) array.getDimension(R.styleable.ChartView_line_width, 4);
        mLineColor = array.getColor(R.styleable.ChartView_line_color, ALL_DEFAULT_COLOR);

        isDrawGridLine = array.getBoolean(R.styleable.ChartView_draw_grid_line, false);
        mGridLineType = array.getInt(R.styleable.ChartView_grid_line_type, DASHED);
        mGridLineColor = array.getColor(R.styleable.ChartView_grid_line_color, ALL_DEFAULT_COLOR);
        mGridLineWidth = (int) array.getDimension(R.styleable.ChartView_grid_line_width, 2);

        isDrawAlignLineForX = array.getBoolean(R.styleable.ChartView_draw_align_line_for_x, false);
        mAlignXLineColor = array.getColor(R.styleable.ChartView_align_x_line_color, ALL_DEFAULT_COLOR);
        mAlignXLineWidth = (int) array.getDimension(R.styleable.ChartView_align_x_line_width, 2);
        mAlignXType = array.getInt(R.styleable.ChartView_align_x_line_type, DASHED);

        isDrawAlignLineForY = array.getBoolean(R.styleable.ChartView_draw_align_line_for_y, false);
        mAlignYLineColor = array.getColor(R.styleable.ChartView_align_y_line_color, ALL_DEFAULT_COLOR);
        mAlignYLineWidth = (int) array.getDimension(R.styleable.ChartView_align_y_line_width, 2);
        mAlignYType = array.getInt(R.styleable.ChartView_align_y_line_type, DASHED);

        mGapWithAxisAndText = (int) array.getDimension(R.styleable.ChartView_gap_with_axis_and_text, 8);

        isDrawHorizontalAxisText = array.getBoolean(R.styleable.ChartView_draw_horizontal_axis_text, true);
        mHizontalAxisTextColor = array.getColor(R.styleable.ChartView_horizontal_axis_text_color, ALL_DEFAULT_COLOR);
        mHorizontalAxisTextSize = (int) array.getDimension(R.styleable.ChartView_horizontal_axis_text_size, 12);
        mHorizontalAxisTextWidth = (int) array.getDimension(R.styleable.ChartView_horizontal_axis_text_width, 48);
        mHorizontalReservedGap = (int) array.getDimension(R.styleable.ChartView_horizontal_reserved_gap, 24);

        isDrawVerticalAxisText = array.getBoolean(R.styleable.ChartView_draw_vertical_axis_text, true);
        mVerticalAxisTextColor = array.getColor(R.styleable.ChartView_vertical_axis_text_color, ALL_DEFAULT_COLOR);
        mVerticalAxisTextSize = (int) array.getDimension(R.styleable.ChartView_vertical_axis_text_size, 12);
        mVerticalAxisTextWidth = (int) array.getDimension(R.styleable.ChartView_vertical_axis_text_width, 48);
        mVerticalReservedGap = (int) array.getDimension(R.styleable.ChartView_vertical_reserved_gap, 0);
    }

    /**
     * 获取横纵轴文字画笔
     *
     * @return
     */
    public Paint getVerticalAxisTextPaint() {
        mPaint.reset();
        if (isDrawVerticalAxisText()) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(mVerticalAxisTextColor);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(mVerticalAxisTextSize);
            mPaint.setTextAlign(Paint.Align.RIGHT);
        }
        return mPaint;
    }

    /**
     * 获取横轴文字画笔
     *
     * @return
     */
    public Paint getHorizontalAxisTextPaint() {
        mPaint.reset();
        if (isDrawHorizontalAxisText()) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(mHizontalAxisTextColor);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(mHorizontalAxisTextSize);
            mPaint.setTextAlign(Paint.Align.CENTER);
        }
        return mPaint;
    }

    /**
     * 获取与Y轴对齐线的画笔
     *
     * @return
     */
    public Paint getAlignYLinePaint() {
        mPaint.reset();
        if (isDrawAlignLineForY()) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(mAlignYLineColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mAlignYLineWidth);
            switch (mAlignYType) {
                case DASHED:
                    mPaint.setPathEffect(new DashPathEffect(new float[]{8, 8}, 0));
                    break;
                case SOLID:
                    break;
            }
        }
        return mPaint;
    }

    /**
     * 获取与x轴对齐线的画笔
     *
     * @return
     */
    public Paint getAlignXLinePaint() {
        mPaint.reset();
        if (isDrawAlignLineForX()) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(mAlignXLineColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mAlignXLineWidth);
            switch (mAlignXType) {
                case DASHED:
                    mPaint.setPathEffect(new DashPathEffect(new float[]{8, 8}, 0));
                    break;
                case SOLID:
                    break;
            }
        }
        return mPaint;
    }

    /**
     * 获取网格线画笔
     *
     * @return
     */
    public Paint getGridLinePaint() {
        mPaint.reset();
        if (isDrawGridLine()) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(mGridLineColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mGridLineWidth);
            switch (mGridLineType) {
                case DASHED:
                    mPaint.setPathEffect(new DashPathEffect(new float[]{8, 8}, 0));
                    break;
                case SOLID:
                    break;
            }
        }
        return mPaint;
    }

    /**
     * 获取曲线的画笔
     *
     * @return
     */
    public Paint getLinePaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mLineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLineWidth);
        return mPaint;
    }

    /**
     * 获取坐标轴的画笔
     *
     * @return Paint
     */
    public Paint getAxisLinePaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mAxisLineColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mAxisLineWidth);
        return mPaint;
    }

    /**
     * 获取坐标点画笔
     *
     * @return Paint
     */
    public Paint getPointPaint() {
        mPaint.reset();
        if (isDrawPoint()) {
            mPaint.setAntiAlias(true);
            mPaint.setColor(mPointColor);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextAlign(Paint.Align.CENTER);
        }
        return mPaint;
    }

    /**********************************************************************************************/
    public int getGapWithTextAndArrow() {
        return mGapWithTextAndArrow;
    }

    public int getHorizontalReservedGap() {
        return mHorizontalReservedGap;
    }

    public int getVerticalReservedGap() {
        return mVerticalReservedGap;
    }

    public int getHorizontalAxisTextWidth() {
        return mHorizontalAxisTextWidth;
    }

    public int getVerticalAxisTextWidth() {
        return mVerticalAxisTextWidth;
    }

    public int getGapWithAxisAndText() {
        return mGapWithAxisAndText;
    }

    public boolean isDrawVerticalAxisText() {
        return isDrawVerticalAxisText;
    }

    public int getHorizontalAxisTextSize() {
        return mHorizontalAxisTextSize;
    }

    public boolean isDrawHorizontalAxisText() {
        return isDrawHorizontalAxisText;
    }

    public boolean isDrawAlignLineForY() {
        return isDrawAlignLineForY;
    }

    public boolean isDrawAlignLineForX() {
        return isDrawAlignLineForX;
    }

    public boolean isDrawGridLine() {
        return isDrawGridLine;
    }

    public int getArrowSize() {
        return mArrowSize;
    }

    public boolean isDrawArrow() {
        return isDrawArrow;
    }

    public boolean isDrawPoint() {
        return isDrawPoint;
    }

    public int getPointSize() {
        return mPointSize;
    }

    /**********************************************************************************************/

    public void setDrawGridLine(boolean drawGridLine) {
        isDrawGridLine = drawGridLine;
    }
}
