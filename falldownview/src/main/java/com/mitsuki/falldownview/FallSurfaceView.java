package com.mitsuki.falldownview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class FallSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private int width; // 宽度
    private int height;  //高度

    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
//    private RenderingRunnable mRenderingRunnable;


    private Paint mPaint;  // 画笔


    public FallSurfaceView(Context context) {
        this(context, null);
    }

    public FallSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
//        mRenderingRunnable.onCreateFallObject(width, height);
//        FrameThreadQueueManager.getInstance().onPostRenderingTask();

    }


    /**
     * 初始化
     */
    private void init() {
        //初始化
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        //画笔
        mPaint = new Paint();

        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

//        mRenderingRunnable = RenderingRunnableFactory.newRenderingRunnable(ComponentType.SNOW);
//        FrameThreadQueueManager.getInstance().onBindRenderingTask(mRenderingRunnable);
//        FrameThreadQueueManager.getInstance().onBindDrawThread(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //绑定绘制线程
        FrameThreadQueueManager.getInstance().onPostDrawTask();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        FrameThreadQueueManager.getInstance().onDrawThreadCancel();
    }

    @Override
    public void run() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mCanvas) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
            FrameThreadQueueManager.getInstance().onPostDrawTask();
        }
    }

}
