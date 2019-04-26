package com.mitsuki.chowder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

public class ScanEffectDelegate {

    private Paint mPaint;

    private int mColor;
    private int mSpeed;
    private int mDirection;
    private float mGridSize;
    private float mScanWidth;

    private final int delay = 8;


    public ScanEffectDelegate(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScanEffect);
        mColor = array.getColor(R.styleable.ScanEffect_effect_color, 0xff00ffff);
        mSpeed = array.getInt(R.styleable.ScanEffect_speed, 16);
        mDirection = array.getInt(R.styleable.ScanEffect_effect_direction, 1);
        mGridSize = array.getDimension(R.styleable.ScanEffect_grid_size, 20);
        mScanWidth = array.getDimension(R.styleable.ScanEffect_scan_width, 50);


    }

    public Paint getGridLinePaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(mColor);
        mPaint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));

        return mPaint;
    }

    public Paint getScanPaint(float s, float e) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        if (mDirection == 0 || mDirection == 1) {
            Shader mShader = new LinearGradient(0, s, 0, e,
                    mColor, 0x00ffffff, Shader.TileMode.MIRROR);
            mPaint.setShader(mShader);
        } else {
            Shader mShader = new LinearGradient(s, 0, e, 0,
                    mColor, 0x00ffffff, Shader.TileMode.MIRROR);
            mPaint.setShader(mShader);
        }


        return mPaint;
    }

    public float getGridSize() {
        return mGridSize;
    }

    public int getDirection() {
        return mDirection;
    }

    public float getScanWidth() {
        return mScanWidth;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public int getDelay() {
        return delay;
    }
}
