package com.mitsuki.falldownview;

import android.graphics.Path;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 用于管理帧队列、path计算线程、绘制线程
 */
class FrameThreadQueueManager {
    private static volatile FrameThreadQueueManager singleton = null;

    private FrameThreadQueueManager() {
        mFrameQueue = new LinkedBlockingQueue(FallDownConfig.FRAME_CACHE_SIZE);
        mWorkThread = Executors.newScheduledThreadPool(2);//核心线程数为2
    }

    public static FrameThreadQueueManager getInstance() {
        if (singleton == null) {
            synchronized (FrameThreadQueueManager.class) {
                if (singleton == null) {
                    singleton = new FrameThreadQueueManager();
                }
            }
        }
        return singleton;
    }

    //帧队列，用于存放计算和合成完毕的path
    private LinkedBlockingQueue mFrameQueue;
    //工作线程池，其中包含path计算线程和path绘制线程
    private ScheduledExecutorService mWorkThread;
    //存下来存下来
    private Runnable mDrawThread;
    private Runnable mRenderingThread;
    //用于暂停线程
    private Future<?> mDrawFuture;
    private Future<?> mRenderingFuture;

    /**
     * 提前保存Runnable
     *
     * @param task
     */
    public void onBindDrawThread(Runnable task) {
        if (null != mDrawThread) {
            mDrawThread = null;
        }
        this.mDrawThread = task;
    }

    /**
     * 开启绘制线程
     */
    public void onStartDrawTask() {
        //开启线程任务
        mDrawFuture = mWorkThread.scheduleAtFixedRate(mDrawThread, 0, FallDownConfig.DRAW_DELAY, TimeUnit.MILLISECONDS);
    }

    /**
     * 开启path计算线程
     * 该线程会因队列塞满而阻塞
     */
    public void onStartRenderingTask() {
        if (null != mRenderingThread) {
            mRenderingThread = null;
        }
        mRenderingThread = new RenderingRunnable(new RenderingRunnable.OnPathCalculationListener() {
            @Override
            public void onCalculation() {
                //计算path

//                addFramePath();
            }
        });
        mRenderingFuture = mWorkThread.scheduleAtFixedRate(mRenderingThread, 0, FallDownConfig.RENDERING_DELAY, TimeUnit.MILLISECONDS);
    }

    /**
     * 将计算完的路径放入队列
     *
     * @param path
     */
    private void addFramePath(Path path) {
        try {
            mFrameQueue.put(path);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从队列中取出路径
     *
     * @return
     */
    public Path obtainFramePath() {
        try {
            return (Path) mFrameQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 停止绘制线程
     * 重新onStartDrawTask()方法开启绘制线程
     */
    public void onDrawThreadCancel() {
        if (null != mDrawFuture) {
            mDrawFuture.cancel(true);
        }
    }

    /**
     * 资源销毁
     * 保留线程池
     */
    public void onDestroy() {
        try {
            mDrawFuture.cancel(true);
            mRenderingFuture.cancel(true);
            mDrawThread = null;
            mRenderingThread = null;
            mDrawFuture = null;
            mRenderingFuture = null;
            mFrameQueue.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
