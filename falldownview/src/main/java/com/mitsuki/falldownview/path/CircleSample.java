package com.mitsuki.falldownview.path;

import android.graphics.Path;

public class CircleSample extends ComponentSample {
    public CircleSample() {
        this.baseline = 100f;
        this.mPath = new Path();
        mPath.addCircle(baseline / 2, baseline / 2, baseline / 2, Path.Direction.CW);
    }
}
