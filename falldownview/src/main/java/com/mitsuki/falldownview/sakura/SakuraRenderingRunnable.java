package com.mitsuki.falldownview.sakura;

import com.mitsuki.falldownview.RenderingRunnable;
import com.mitsuki.falldownview.config.FallDownConfig;

public class SakuraRenderingRunnable extends RenderingRunnable<Sakura> {

    public SakuraRenderingRunnable() {
        super();
        this.fallObjectPath = new SakuraSample();
    }

    @Override
    protected void onCreateFallObject(int w, int h) {
        for (int i = 0; i < FallDownConfig.ELEMENT_COUNT; i++) {
            mFallList.add(new Sakura.Builder(fallObjectPath, w, h)
                    .setWind(4)
                    .setSpeed(4)
                    .setSize(120, 240)
                    .build());
        }
    }

}
