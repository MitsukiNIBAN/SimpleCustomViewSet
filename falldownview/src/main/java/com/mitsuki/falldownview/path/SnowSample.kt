package com.mitsuki.falldownview.path

import android.graphics.Matrix
import android.graphics.Path

class SnowSample : ComponentSample() {

    override val sizeBenchmark: Float = 360f

    override val defaultScale = 0.06f

    init {
        val center = sizeBenchmark / 2 //中心点
        val piece = 90f//雪花臂的宽度
        val diff = Math.round(piece / 2 * Math.sqrt(3.0)).toFloat()

        mPath = Path()
        mPath.addCircle(center, center, piece / 2, Path.Direction.CCW)

        //单条雪花臂的路径
        val tempPath = Path()
        tempPath.moveTo(center - piece / 2, piece / 2)
        tempPath.arcTo(
            center - piece / 2, 0f, center + piece / 2, piece,
            180f, 180f, false
        )
        tempPath.lineTo(center + piece / 2, sizeBenchmark - piece / 2)
        tempPath.arcTo(
            center - piece / 2, sizeBenchmark - piece, center + piece / 2, sizeBenchmark,
            0f, 180f, false
        )
        tempPath.close()
        val matrix = Matrix()
        //绘制整朵雪花
        for (i in 0..2) {
            matrix.reset()
            matrix.setRotate((60 * i).toFloat(), center, center)
            val path = Path(tempPath)
            path.transform(matrix)
            mPath.op(path, Path.Op.UNION)
        }
    }

}
