package com.mitsuki.circleprogressview;

import android.content.Context;
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
    private final int[] colors = new int[]{
            0XFF45C481, 0XFF45C481, 0XFFF2E11E, 0XFFF2831E, 0XFF9433C3, 0XFF8C254B, 0XFF8C254B};
    private final float[] positions = new float[]{
            0f, 0.2f, 0.4f, 0.5f, 0.65f, 0.80f, 0.9f
    };

    //view 的大小
    private int width;

    private int mDrawCenterX;
    private int mDrawCenterY;

    private Paint paint;

    /**********************************************************************************************/
    private int internalPadding = 32;
    private int progressWidth = 40;

    /**********************************************************************************************/


    public CircleExponentView(Context context) {
        this(context, null);
    }

    public CircleExponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
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
        paint.reset();
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setColor(0xffffffff);
        canvas.drawCircle(mDrawCenterX, mDrawCenterY, width / 2, paint);
    }

    //绘制色轮
    private void onDrawColorfulProgress(Canvas canvas) {
        paint.reset();
        paint.setStyle(Paint.Style.STROKE);//设置填充样式
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setStrokeWidth(40);//设置画笔宽度
        //设置笔刷的样式 Paint.Cap.Round ,Cap.SQUARE等分别为圆形、方形
        paint.setStrokeCap(Paint.Cap.SQUARE);


        SweepGradient sweepGradient = new SweepGradient(mDrawCenterX, mDrawCenterY, colors, positions);
        Matrix matrix = new Matrix();
        matrix.setRotate(90, mDrawCenterX, mDrawCenterY);//加上旋转还是很有必要的，每次最右边总是有一部分多余了,不太美观,也可以不加
        sweepGradient.setLocalMatrix(matrix);
        paint.setShader(sweepGradient);
        RectF oval1 = new RectF(internalPadding + progressWidth / 2,
                internalPadding * 3 / 2 + progressWidth / 2,
                width - internalPadding - progressWidth / 2,
                width - internalPadding / 2 - progressWidth / 2);
        //        canvas.drawArc(oval1, 180, currentAngle, false, paintCurrent);//小弧形
        //当前进度
        canvas.drawArc(oval1, 148, 242, false, paint);
    }

    //绘制指示圆点
    private void onDrawProgressDirective(Canvas canvas) {
        paint.reset();
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setColor(Color.GRAY);

        int mCircleRegion = width - internalPadding;

        double angle = 240;
        double x = (width / 2) + Math.cos(Math.toRadians(90 - angle)) * (mCircleRegion - 2 * progressWidth) / 2;
        double y = (width / 2 + internalPadding / 2) - Math.sin(Math.toRadians(90 - angle)) * (mCircleRegion - 2 * progressWidth) / 2;
        canvas.drawCircle((float) x, (float) y, progressWidth, paint);
    }
}
