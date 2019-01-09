package com.mitsuki.falldownview.sakura;

import android.graphics.Path;

import com.mitsuki.falldownview.base.OnPathCalculationCallback;
import com.mitsuki.falldownview.base.RenderingRunnable;

public class SakuraRenderingRunnable extends RenderingRunnable<Sakura> {

    public SakuraRenderingRunnable(OnPathCalculationCallback onPathCalculationCallback) {
        super(onPathCalculationCallback);
    }

    @Override
    protected Path onPathCalculation() {
        //实现path计算
        return new Path();
    }

}
