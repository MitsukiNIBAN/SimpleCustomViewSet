package com.mitsuki.falldownview.path

import android.graphics.Path

class CircleSample : ComponentSample() {

    override val sizeBenchmark: Float = 20f

    override val defaultScale: Float = 0.8f

    init {
        this.mPath = Path()
        mPath.addCircle(
            sizeBenchmark / 2,
            sizeBenchmark / 2,
            sizeBenchmark / 2,
            Path.Direction.CW
        )
    }
}
