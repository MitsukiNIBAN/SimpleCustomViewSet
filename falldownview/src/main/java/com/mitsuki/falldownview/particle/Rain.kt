package com.mitsuki.falldownview.particle

class Rain(x: Float, y: Float, size: Int) : Particle(x, y, size) {

    override fun moveForX(): Float {
        return 4f
    }

    override fun moveForY(): Float {
        return 40f
    }

    override fun extraOffsetForX(): Float {
        return 0f
    }

    override fun extraOffsetForY(): Float {
        return 0f
    }

}