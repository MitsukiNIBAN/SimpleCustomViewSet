package com.mitsuki.falldownview.path;

import android.graphics.Matrix;
import android.graphics.Path;

public class ComponentSample implements ComponentPath {
    protected float baseline;

    protected Path mPath;

    @Override
    public int getBaseLine() {
        return (int) baseline;
    }

    @Override
    public Path getObjPath(float size) {
        Path path = new Path(mPath);
        Matrix matrix = new Matrix();
        matrix.setScale(size / baseline, size / baseline);
        path.transform(matrix);
        return path;
    }
}
