package com.mitsuki.circleprogressview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class CircleExponentView extends View {

    private final int DEFAULT_SIZE = 60;


    //view 的大小
    private int width;

    private int mDrawCenterX;
    private int mDrawCenterY;

    //xml参数代理
    private final CircleExponentDelegate mCircleExponentDelegate;

    private Bitmap slider;

    /**********************************************************************************************/
    public CircleExponentView(Context context) {
        this(context, null);
    }

    public CircleExponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCircleExponentDelegate = new CircleExponentDelegate(context, attrs);
        init(context);
    }

    private void init(Context context) {
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            mDrawCenterX = DEFAULT_SIZE / 2;
            mDrawCenterY = DEFAULT_SIZE / 2;
            setMeasuredDimension(DEFAULT_SIZE, DEFAULT_SIZE * 3 / 4);
        } else {
            mDrawCenterX = w / 2;
            mDrawCenterY = w / 2;
            setMeasuredDimension(w, w * 3 / 4);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawBackGround(canvas);
        onDrawColorfulProgress(canvas);
        onDrawProgressDirective(canvas);
    }

    //绘制白色底板
    private void onDrawBackGround(Canvas canvas) {
        canvas.drawCircle(mDrawCenterX, mDrawCenterY, width / 2, mCircleExponentDelegate.getBackGroundPaint());
    }

    //绘制色轮
    private void onDrawColorfulProgress(Canvas canvas) {
        RectF oval = new RectF(mCircleExponentDelegate.getInternalPadding() + mCircleExponentDelegate.getProgressWidth() / 2,
                mCircleExponentDelegate.getInternalPadding() * 3 / 2 + mCircleExponentDelegate.getProgressWidth() / 2,
                width - mCircleExponentDelegate.getInternalPadding() - mCircleExponentDelegate.getProgressWidth() / 2,
                width - mCircleExponentDelegate.getInternalPadding() / 2 - mCircleExponentDelegate.getProgressWidth() / 2);
        //当前进度
        canvas.drawArc(oval, 148, 242, false, mCircleExponentDelegate.getProgressCirclePaint(mDrawCenterX, mDrawCenterY));
    }

    //绘制指示圆点
    private void onDrawProgressDirective(Canvas canvas) {
        float mCircleRegion = width - mCircleExponentDelegate.getInternalPadding() * 2 - mCircleExponentDelegate.getProgressWidth();

        double angle = 240 * mCircleExponentDelegate.getPercent() / 100 + 240;
        if (angle > 360) angle = angle - 360;
        double x = (width / 2) + Math.cos(Math.toRadians(90 - angle)) * (mCircleRegion) / 2;
        double y = (width / 2 + mCircleExponentDelegate.getInternalPadding() / 2) - Math.sin(Math.toRadians(90 - angle)) * (mCircleRegion) / 2;
//        canvas.drawCircle((float) x, (float) y, mCircleExponentDelegate.getProgressWidth(), mCircleExponentDelegate.getProgressDirectivePaint());

        createSlider();
        canvas.drawBitmap(slider,
                new Rect(0, 0, slider.getWidth(), slider.getHeight()),
                new Rect((int) (x - mCircleExponentDelegate.getSliderSize() / 2), (int) (y - mCircleExponentDelegate.getSliderSize() / 2),
                        (int) (x + mCircleExponentDelegate.getSliderSize() / 2), (int) (y + mCircleExponentDelegate.getSliderSize() / 2)),
                mCircleExponentDelegate.getProgressDirectivePaint());
    }

    public void setPercent(int value) {
        if (value >= 0
                && value <= 100) {
            mCircleExponentDelegate.setPercent(value);
            invalidate();
        }
    }

    private void createSlider() {
        if (slider == null)
            slider = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.progress_slider);
    }
}
