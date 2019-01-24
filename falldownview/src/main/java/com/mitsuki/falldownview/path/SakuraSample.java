package com.mitsuki.falldownview.path;

import android.graphics.Path;
import android.graphics.RectF;

public class SakuraSample extends ComponentSample {

    public SakuraSample() {
        this.baseline = 80;
        this.mPath = new Path();

        float ovalProportion = 0.7f;
        float width = baseline;
        float ovalWidth = baseline * 3f;
        float ovalHeight = ovalProportion * ovalWidth;

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
}
