package com.mitsuki.falldownview;

import android.os.Handler;
import android.os.HandlerThread;

public class Manager {

    //线程管理
    //渲染path线程
    private HandlerThread mRenderingHandlerThread;
    private Handler mRenderingHandler;
    //绘制path线程
    private HandlerThread mPathDrawHandlerThread;
    private Handler mDrawHandler;

    //组件帮助类管理
    private ComponentHelper componentHelper;

    public Manager initHandlerThread() {
        return this;
    }

    public Manager initComponentHelper() {
        return this;
    }


}
