package com.mitsuki.falldownview.sakura;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mitsuki.falldownview.rain.Rain;

import java.util.ArrayList;
import java.util.List;

public class SakuraSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private int width; // 宽度
    private int height;  //高度

    private SurfaceHolder mSurfaceHolder;    // SurfaceHolder
    private Canvas mCanvas;
    private Paint mPaint;  // 画笔

    private List<Sakura> mSakura;

    private SakuraPath sakuraPath;

    private final int delay = 3;
    private final int count = 8;

    public SakuraSurfaceView(Context context) {
        this(context, null);
    }

    public SakuraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        mSakura = new ArrayList<>();
        sakuraPath = new SakuraSample();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    private void init() {
        //初始化
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        //画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setStyle(Paint.Style.FILL);

        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        for (int i = 0; i < count; i++) {
            mSakura.add(new Sakura.Builder(sakuraPath, width, height)
                    .setSpeed(4, 8)
                    .setWind(4, 8)
                    .setTransparent(20, 60)
                    .setSize(40, 200)
                    .build());
        }
        //绘制线程
        getHandler().postDelayed(this, delay);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        getHandler().removeCallbacks(this);
    }

    @Override
    public void run() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            for (Sakura sakura : mSakura) {
                sakura.drawSakura(mCanvas, mPaint);
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
