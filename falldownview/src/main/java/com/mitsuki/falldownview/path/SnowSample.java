package com.mitsuki.falldownview.path;

import android.graphics.Matrix;
import android.graphics.Path;

public class SnowSample extends ComponentSample {
    public SnowSample() {
        this.baseline = 360;
        float center = baseline / 2; //中心点
        float piece = 90;//雪花臂的宽度
        float diff = Math.round(piece / 2 * Math.sqrt(3));

        mPath = new Path();
        mPath.addCircle(center, center, piece / 2, Path.Direction.CCW);

        //单条雪花臂的路径
        Path tempPath = new Path();
        tempPath.moveTo(center - piece / 2, piece / 2);
        tempPath.arcTo(center - piece / 2, 0, center + piece / 2, piece,
                180, 180, false);
        tempPath.lineTo(center + piece / 2, baseline - piece / 2);
        tempPath.arcTo(center - piece / 2, baseline - piece, center + piece / 2, baseline,
                0, 180, false);
        tempPath.close();
        Matrix matrix = new Matrix();
        //绘制整朵雪花
        for (int i = 0; i < 3; i++) {
            matrix.reset();
            matrix.setRotate(60 * i, center, center);
            Path path = new Path(tempPath);
            path.transform(matrix);
            mPath.op(path, Path.Op.UNION);
        }
    }

}
