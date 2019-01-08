package com.mitsuki.falldownview;

import android.graphics.Path;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 用于管理帧队列、path计算线程、绘制线程
 */
class FrameQueueManager {
    private static volatile FrameQueueManager singleton = null;

    private FrameQueueManager() {
        mFrameQueue = new LinkedBlockingQueue(FallDownConfig.FRAME_CACHE_SIZE);
        mWorkThread = Executors.newScheduledThreadPool(2);//核心线程数为2
    }

    public static FrameQueueManager getInstance() {
        if (singleton == null) {
            synchronized (FrameQueueManager.class) {
                if (singleton == null) {
                    singleton = new FrameQueueManager();
                }
            }
        }
        return singleton;
    }

    private LinkedBlockingQueue mFrameQueue; //帧队列，用于存放计算和合成完毕的path
    private ScheduledExecutorService mWorkThread; //工作线程池，其中包含path计算线程和path绘制线程
    private Runnable mDrawThread;
    private Runnable mRenderingThread;

    private void addFrame(Path path) {
        try {
            mFrameQueue.put(path);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addTest() {
        try {
            mFrameQueue.put(i);
            i += 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getTest() {
        try {
            return (int) mFrameQueue.take();
        } catch (InterruptedException e) {
            return 0;
        }
    }

    public Path getFrame() {
        try {
            return (Path) mFrameQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    public void bindDrawTask(Runnable task) {
        if (null != mDrawThread) {
            mDrawThread = null;
        }
        this.mDrawThread = task;

    }

    public void startDrawWork() {
        //加入线程池
        mWorkThread.scheduleAtFixedRate(mDrawThread, 0, FallDownConfig.DRAW_DELAY, TimeUnit.MILLISECONDS);
    }

    int i = 0;

    public void startRenderingWork() {
        if (null != mRenderingThread) {
            mRenderingThread = null;
        }
        mRenderingThread = new Runnable() {
            @Override
            public void run() {
                //这里生成path
//                addFrame(i);
                addTest();
                Log.d("rendering", mFrameQueue.size() + "");
            }
        };
        mWorkThread.scheduleAtFixedRate(mRenderingThread, 0, FallDownConfig.RENDERING_DELAY, TimeUnit.MILLISECONDS);
    }

}
