package com.mitsuki.falldownview.circle;

import android.graphics.Path;

import com.mitsuki.falldownview.RenderingRunnable;
import com.mitsuki.falldownview.config.FallDownConfig;

public class CircleRenderingRunnable extends RenderingRunnable<Circle> {
    private Path mPath;

    public CircleRenderingRunnable() {
        super();
        mPath = new Path();
    }

    @Override
    protected Path onPathCalculation() {
        mPath.rewind();
        for (int i = 0; i < mFallList.size(); i++) {
            Circle circle = mFallList.get(i);
            if (i == 0) {
                mPath.set(circle.getPath());
            } else {
                mPath.op(circle.getPath(), Path.Op.UNION);
            }
            circle.move();
        }
        return mPath;
    }

    @Override
    protected void onCreateFallObject(int width, int height) {
        for (int i = 0; i < FallDownConfig.ELEMENT_COUNT; i++) {
            mFallList.add(new Circle.Builder(width, height)
                    .setSpeed(1, 8)
                    .setSize(10, 30)
                    .build());
        }
    }
}
