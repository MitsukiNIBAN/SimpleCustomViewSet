package com.mitsuki.falldownview.path

import android.graphics.Matrix
import android.graphics.Path

class RainSample : ComponentSample() {
    override val sizeBenchmark: Float = 100f

    init {
        this.mPath = Path()
        mPath.addRect(0f, 0f, 2f, sizeBenchmark, Path.Direction.CW)
    }

    override fun particlePath(size: Float): Path {
        val path = Path(mPath)
        val matrix = Matrix()
        matrix.setScale(1f, size / sizeBenchmark)
        path.transform(matrix)
        return path
    }
}
