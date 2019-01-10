package com.mitsuki.falldownview.snow;

import android.graphics.Matrix;
import android.graphics.Path;

import com.mitsuki.falldownview.FallObjectPath;

public class SnowSample implements FallObjectPath {

    private Path mPath;

    private final float baseline = 360; //基准大小
    private final float center = baseline / 2; //中心点
    private final float piece = 90; //雪花臂的宽度
    private final float diff = Math.round(piece / 2 * Math.sqrt(3));

    public SnowSample() {
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

    @Override
    public int getBaseLine() {
        return (int) baseline;
    }

    @Override
    public Path getObjPath(float size) {
        //根据大小缩放处理
        Path path = new Path(mPath);
        Matrix matrix = new Matrix();
        matrix.setScale(size / baseline, size / baseline);
        path.transform(matrix);
        return path;
    }
}
