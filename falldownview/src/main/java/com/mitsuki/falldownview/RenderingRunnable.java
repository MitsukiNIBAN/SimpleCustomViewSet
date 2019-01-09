package com.mitsuki.falldownview;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderingRunnable<T extends FallObject> implements Runnable {

    protected FallObjectPath fallObjectPath;
    protected List<T> mFallList;

    public RenderingRunnable() {
        this.mFallList = new ArrayList<>();
    }

    @Override
    public void run() {
        //回调
        FrameThreadQueueManager.getInstance().addFramePath(onPathCalculation());
    }

    protected abstract Path onPathCalculation();

    protected abstract void onCreateFallObject(int width, int height);
}
