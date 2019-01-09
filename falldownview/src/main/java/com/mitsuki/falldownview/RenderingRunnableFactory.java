package com.mitsuki.falldownview;

import com.mitsuki.falldownview.config.FallType;
import com.mitsuki.falldownview.sakura.SakuraRenderingRunnable;

public class RenderingRunnableFactory {

    public static RenderingRunnable newRenderingRunnable(int type) {
        switch (type) {
            case FallType.SNOW:
                return null;
            case FallType.RAIN:
                return null;
            case FallType.SUKURA:
                return new SakuraRenderingRunnable();
            default:
                return null;
        }
    }
}
