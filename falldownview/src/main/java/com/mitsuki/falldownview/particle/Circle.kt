package com.mitsuki.falldownview.particle

import kotlin.math.sin

class Circle(x: Float, y: Float, size: Int) : Particle(x, y, size) {
    private var angle: Int = 0
    private var swayAmplitude = 400
    private var swayFrequency = 2

    override fun moveForX(): Float {
        return 0f
    }

    override fun moveForY(): Float {
        return (8f * (1 - 0.5 * size / 10)).toFloat()
    }

    override fun extraOffsetForX(): Float {
        angle += swayFrequency
        if (angle > 360) {
            angle -= 360
        }
        return ((sin(Math.toRadians(angle.toDouble())) -
                sin(Math.toRadians((angle - swayFrequency).toDouble()))) * swayAmplitude * (0.5 + 0.5 * size / 10)).toFloat()
    }

    override fun extraOffsetForY(): Float {
        return 0f
    }

}