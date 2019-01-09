package com.mitsuki.falldownview.sakura;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

import com.mitsuki.falldownview.base.FallObjectPath;

public class SakuraSample implements FallObjectPath {

    private Path mPath;

    private float width;

    private float ovalWidth;
    private float ovalHeight;
    private float ovalProportion;

    private float baseline = 80;

    public SakuraSample() {
        this.mPath = new Path();

        this.ovalProportion = 0.7f;
        this.width = baseline;
        this.ovalWidth = baseline * 3f;
        this.ovalHeight = ovalProportion * ovalWidth;

        Path tempPath = new Path();
        Path tempDelta = new Path();
        tempPath.addOval(new RectF(width - ovalWidth, 0, width, ovalHeight), Path.Direction.CCW);
        tempDelta.moveTo(width / 2, 0);
        tempDelta.lineTo(width / 2, ovalHeight * 2 / 5);
        tempDelta.lineTo(0, 0);
        tempDelta.close();
        tempPath.op(tempDelta, Path.Op.DIFFERENCE);

        mPath.set(tempPath);

        tempPath.reset();
        tempDelta.reset();
        tempPath.addOval(new RectF(0, 0, ovalWidth, ovalHeight), Path.Direction.CCW);

        tempDelta.moveTo(width / 2, 0);
        tempDelta.lineTo(width / 2, ovalHeight * 2 / 5);
        tempDelta.lineTo(width, 0);
        tempDelta.close();
        tempPath.op(tempDelta, Path.Op.DIFFERENCE);

        mPath.op(tempPath, Path.Op.INTERSECT);
    }

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
