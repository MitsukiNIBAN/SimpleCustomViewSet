package com.mitsuki.falldownview;

import com.mitsuki.falldownview.circle.CircleRenderingRunnable;
import com.mitsuki.falldownview.config.FallType;
import com.mitsuki.falldownview.rain.RainRenderingRunnable;
import com.mitsuki.falldownview.sakura.SakuraRenderingRunnable;
import com.mitsuki.falldownview.snow.SnowRenderingRunnable;

public class RenderingRunnableFactory {

    public static RenderingRunnable newRenderingRunnable(int type) {
        switch (type) {
            case FallType.SNOW:
                return new SnowRenderingRunnable();
            case FallType.RAIN:
                return new RainRenderingRunnable();
            case FallType.SAKURA:
                return new SakuraRenderingRunnable();
            case FallType.CIRCLE:
                return new CircleRenderingRunnable();
            default:
                return null;
        }
    }
}
