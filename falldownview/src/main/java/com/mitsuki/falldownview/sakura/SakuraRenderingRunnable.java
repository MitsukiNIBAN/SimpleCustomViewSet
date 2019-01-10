package com.mitsuki.falldownview.sakura;

import android.graphics.Path;

import com.mitsuki.falldownview.RenderingRunnable;
import com.mitsuki.falldownview.config.FallDownConfig;

public class SakuraRenderingRunnable extends RenderingRunnable<Sakura> {

    private Path mPath;

    public SakuraRenderingRunnable() {
        super();
        this.fallObjectPath = new SakuraSample();
        this.mPath = new Path();
    }

    @Override
    protected Path onPathCalculation() {
        //实现path计算
        mPath.reset();
        for (int i = 0; i < FallDownConfig.ELEMENT_COUNT; i++) {
            if (i == 0) {
                mPath.set(mFallList.get(i).getPath());
            } else {
                mPath.op(mFallList.get(i).getPath(), Path.Op.UNION);
            }
        }
        return mPath;
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
