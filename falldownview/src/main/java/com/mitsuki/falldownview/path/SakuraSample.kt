package com.mitsuki.falldownview.path

import android.graphics.Path
import android.graphics.RectF

class SakuraSample : ComponentSample() {

    override val sizeBenchmark: Float = 80f

    init {
        this.mPath = Path()

        val ovalProportion = 0.7f
        val width = sizeBenchmark
        val ovalWidth = sizeBenchmark * 3f
        val ovalHeight = ovalProportion * ovalWidth

        val tempPath = Path()
        val tempDelta = Path()
        tempPath.addOval(RectF(width - ovalWidth, 0f, width, ovalHeight), Path.Direction.CCW)
        tempDelta.moveTo(width / 2, 0f)
        tempDelta.lineTo(width / 2, ovalHeight * 2 / 5)
        tempDelta.lineTo(0f, 0f)
        tempDelta.close()
        tempPath.op(tempDelta, Path.Op.DIFFERENCE)

        mPath.set(tempPath)

        tempPath.reset()
        tempDelta.reset()
        tempPath.addOval(RectF(0f, 0f, ovalWidth, ovalHeight), Path.Direction.CCW)

        tempDelta.moveTo(width / 2, 0f)
        tempDelta.lineTo(width / 2, ovalHeight * 2 / 5)
        tempDelta.lineTo(width, 0f)
        tempDelta.close()
        tempPath.op(tempDelta, Path.Op.DIFFERENCE)

        mPath.op(tempPath, Path.Op.INTERSECT)
    }
}
