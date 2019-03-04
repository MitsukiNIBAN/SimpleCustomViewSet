package com.mitsuki.falldownview;

import com.mitsuki.falldownview.base.FallComponent;
import com.mitsuki.falldownview.path.ComponentPath;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderingRunnable<T extends FallComponent> implements Runnable {

    protected ComponentPath fallObjectPath;
    protected List<T> mFallList;

    public RenderingRunnable() {
        this.mFallList = new ArrayList<>();
    }

    @Override
    public void run() {
        //回调
        for (int i = 0; i < mFallList.size(); i++) {
            mFallList.get(i).move();
        }
        FrameThreadQueueManager.getInstance().addFramePath(mFallList);
        FrameThreadQueueManager.getInstance().onPostRenderingTask();
    }

    protected abstract void onCreateFallObject(int width, int height);
}
