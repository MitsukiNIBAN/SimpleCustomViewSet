package com.mitsuki.falldownview.path

import android.graphics.Matrix
import android.graphics.Path

abstract class ComponentSample : ComponentPath {

    protected lateinit var mPath: Path

    open val defaultScale: Float = 1f

    override fun particlePath(size: Float): Path {
        val path = Path(mPath)
        val matrix = Matrix()
        matrix.setScale(size / sizeBenchmark * defaultScale, size / sizeBenchmark * defaultScale)
        path.transform(matrix)
        return path
    }

}
