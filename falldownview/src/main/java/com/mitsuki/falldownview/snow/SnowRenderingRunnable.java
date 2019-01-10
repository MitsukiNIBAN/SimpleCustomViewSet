package com.mitsuki.falldownview.snow;

import android.graphics.Path;

import com.mitsuki.falldownview.RenderingRunnable;
import com.mitsuki.falldownview.config.FallDownConfig;

public class SnowRenderingRunnable extends RenderingRunnable<Snow> {
//    private Path mPath;

    public SnowRenderingRunnable() {
        super();
        this.fallObjectPath = new SnowSample();
//        this.mPath = new Path();
    }

    @Override
    protected Path onPathCalculation() {
        Path path = new Path();
        for (int i = 0; i < mFallList.size(); i++) {
            Snow snow = mFallList.get(i);
            if (i == 0) {
                path.set(snow.getPath());
            } else {
                path.op(snow.getPath(), Path.Op.UNION);
            }
            snow.move();
        }
        return path;
    }

    @Override
    protected void onCreateFallObject(int width, int height) {
        for (int i = 0; i < FallDownConfig.ELEMENT_COUNT; i++) {
            mFallList.add(new Snow.Builder(fallObjectPath, width, height)
                    .setSpeed(4, 16)
                    .setSize(4, 30)
                    .setSway(8, 5)
                    .build());
        }
    }
}
