package com.mitsuki.falldownview.path

import android.graphics.Path

interface ComponentPath {
    val sizeBenchmark: Float

    fun particlePath(size: Float): Path
}
