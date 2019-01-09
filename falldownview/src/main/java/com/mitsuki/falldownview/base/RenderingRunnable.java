package com.mitsuki.falldownview.base;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderingRunnable<T extends FallObject> implements Runnable {

    protected List<T> mFallList;
    private OnPathCalculationCallback onPathCalculationCallback;

    public RenderingRunnable(OnPathCalculationCallback onPathCalculationCallback) {
        this.onPathCalculationCallback = onPathCalculationCallback;
        this.mFallList = new ArrayList<>();
    }

    @Override
    public void run() {
        //回调
        if (null != onPathCalculationCallback) {
            onPathCalculationCallback.onCalculation(onPathCalculation());
        }
    }

    protected abstract Path onPathCalculation();

    //可能还要增加抽象方法
}
