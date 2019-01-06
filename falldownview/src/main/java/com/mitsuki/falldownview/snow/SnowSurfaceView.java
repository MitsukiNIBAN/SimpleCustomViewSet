package com.mitsuki.falldownview.snow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mitsuki.falldownview.R;

import java.util.ArrayList;
import java.util.List;

public class SnowSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private int width; // 宽度
    private int height;  //高度

    private final int delay = 3;

    private Paint mPaint;  // 画笔
    private SurfaceHolder mSurfaceHolder;    // SurfaceHolder
    private Canvas mCanvas;

    private List<Snow> mSnow;
    private SnowPath snowPath;

    private final int count = 32;

    public SnowSurfaceView(Context context) {
        this(context, null);
    }

    public SnowSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        mSnow = new ArrayList<>();
        snowPath = new SnowSample();
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
    }


    /**
     * 初始化
     */
    private void init() {
        //初始化 SurfaceHolder mSurfaceHolder
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        //画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setStyle(Paint.Style.FILL);

        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

    /**********************************************************************************************/

    @Override
    public void surfaceCreated(SurfaceHolder holder) {//创建
        //添加雪
        for (int i = 0; i < count; i++) {
            mSnow.add(new Snow.Builder(snowPath, width, height)
                    .setFastest(8)
                    .setBiggest(30)
                    .setSway(8, 5)
                    .build());
        }

        //绘制线程
        getHandler().postDelayed(this, delay);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {//改变

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {//销毁
        getHandler().removeCallbacks(this);
    }

    /**********************************************************************************************/


    @Override
    public void run() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            for (Snow snow : mSnow) {
                snow.drawSnow(mCanvas, mPaint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mCanvas) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
            getHandler().postDelayed(this, delay);
        }
    }

}
