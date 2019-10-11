package com.mitsuki.falldownview.particle

abstract class Particle(protected var x: Float, protected var y: Float, var size: Int) {

    fun move(rangeX: Int, rangeY: Int) {
        x += moveForX()
        if (x > rangeX) x = 0f
        if (x < 0) x = rangeX.toFloat()
        y += moveForY()
        if (y > rangeY) y = 0f
        if (y < 0) y = rangeY.toFloat()
    }

    fun sourceX() = x

    fun sourceY() = y

    fun offsetX() = x + extraOffsetForX()

    fun offsetY() = y + extraOffsetForY()

    abstract fun moveForX(): Float

    abstract fun moveForY(): Float

    abstract fun extraOffsetForX(): Float

    abstract fun extraOffsetForY(): Float
}