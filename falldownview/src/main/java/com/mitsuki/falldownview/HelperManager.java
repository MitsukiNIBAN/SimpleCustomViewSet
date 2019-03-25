package com.mitsuki.falldownview;

import android.os.Handler;
import android.os.HandlerThread;

import com.mitsuki.falldownview.config.ComponentType;

public class HelperManager {

    private final String RENDERING = "rendering";
    private final String DRAW = "draw";
    //线程管理
    //渲染path线程
    private HandlerThread mRenderingHandlerThread;
    private Handler mRenderingHandler;
    //绘制path线程
    private HandlerThread mDrawHandlerThread;
    private Handler mDrawHandler;

    //组件帮助类管理
    private ComponentHelper componentHelper;

    public HelperManager initHandlerThread() {
        mRenderingHandlerThread = new HandlerThread(RENDERING);
        mRenderingHandlerThread.start();
        mRenderingHandler = new Handler(mRenderingHandlerThread.getLooper());

        mDrawHandlerThread = new HandlerThread(DRAW);
        mDrawHandlerThread.start();
        mDrawHandler = new Handler(mDrawHandlerThread.getLooper());
        return this;
    }

    public HelperManager initComponentHelper(int type) {
        componentHelper = new ComponentHelper(type);
        componentHelper.setComponentSizeSet(24);
        return this;
    }


}
