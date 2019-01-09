package com.mitsuki.falldownview;

import com.mitsuki.falldownview.base.OnPathCalculationCallback;
import com.mitsuki.falldownview.base.RenderingRunnable;
import com.mitsuki.falldownview.config.FallType;
import com.mitsuki.falldownview.sakura.SakuraRenderingRunnable;

public class RenderingRunnableFactory {

    public static RenderingRunnable newRenderingRunnable(int type, OnPathCalculationCallback onPathCalculationCallback) {
        switch (type) {
            case FallType.SNOW:
                return null;
            case FallType.RAIN:
                return null;
            case FallType.SUKURA:
                return new SakuraRenderingRunnable(onPathCalculationCallback);
            default:
                return null;
        }
    }
}
