package com.mitsuki.falldownview;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.util.Log;

import com.mitsuki.falldownview.config.FallDownConfig;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 用于管理帧队列、path计算线程、绘制线程
 */
class FrameThreadQueueManager {
    private static volatile FrameThreadQueueManager singleton = null;

    private FrameThreadQueueManager() {
        mFrameQueue = new LinkedBlockingQueue(FallDownConfig.FRAME_CACHE_SIZE);
        mRenderingHandlerThread = new HandlerThread(RENDERING, Process.getThreadPriority(208));
        mRenderingHandlerThread.start();
        mRenderingHandler = new Handler(mRenderingHandlerThread.getLooper());
        mPathDrawHandlerThread = new HandlerThread(DRAW, Process.getThreadPriority(209));
        mPathDrawHandlerThread.start();
        mDrawHandler = new Handler(mPathDrawHandlerThread.getLooper());
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

    private final String RENDERING = "rendering";
    private final String DRAW = "draw";

    private LinkedBlockingQueue mFrameQueue;
    //渲染path线程
    private HandlerThread mRenderingHandlerThread;
    private Handler mRenderingHandler;
    //绘制path线程
    private HandlerThread mPathDrawHandlerThread;
    private Handler mDrawHandler;

    //存下来存下来
    private Runnable mRenderingThread;
    private Runnable mDrawThread;

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
    public void onPostDrawTask() {
        //开启线程任务
        mDrawHandler.postDelayed(mDrawThread, FallDownConfig.DRAW_DELAY);
    }

    /**
     * 提前保存Runnable
     *
     * @param task
     */
    public void onBindRenderingTask(Runnable task) {
        if (null != mRenderingThread) {
            mRenderingThread = null;
        }
        this.mRenderingThread = task;
    }

    /**
     * 开启path计算线程
     * 该线程会因队列塞满而阻塞
     */
    public void onPostRenderingTask() {
        mRenderingHandler.postDelayed(mRenderingThread, FallDownConfig.RENDERING_DELAY);
    }

    /**
     * 将计算完的路径放入队列
     *
     * @param path
     */
    public void addFramePath(Object path) {
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
    public Object obtainFramePath() {
        try {
            Log.d("count", mFrameQueue.size() + "");
            return mFrameQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * 停止绘制线程
     * 重新onStartDrawTask()方法开启绘制线程
     */
    public void onDrawThreadCancel() {
        if (null != mPathDrawHandlerThread) {
            mPathDrawHandlerThread.quit();
        }
    }

    /**
     * 资源销毁
     * 保留线程池
     */
    public void onDestroy() {
        try {
            mRenderingHandler.removeCallbacks(mRenderingThread);
            mDrawHandler.removeCallbacks(mDrawThread);
            mRenderingHandlerThread.quit();
            mPathDrawHandlerThread.quit();
            mRenderingThread = null;
            mDrawThread = null;
            mRenderingHandlerThread = null;
            mDrawThread = null;
            mFrameQueue.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
