package com.mitsuki.falldownview.path;

import android.graphics.Matrix;
import android.graphics.Path;

public class RainSample extends ComponentSample {

    private final int w = 2;

    public RainSample() {
        this.baseline = 100;
        this.mPath = new Path();

        mPath.addRect(0, 0, 2, baseline, Path.Direction.CW);
    }

    @Override
    public Path getObjPath(float size) {
        Path path = new Path(mPath);
        Matrix matrix = new Matrix();
        matrix.setScale(1, size / baseline);
        path.transform(matrix);
        return path;
    }
}
