package com.mitsuki.falldownview

import android.graphics.*
import android.util.SparseArray
import com.mitsuki.falldownview.particle.Circle
import com.mitsuki.falldownview.particle.Particle
import com.mitsuki.falldownview.particle.Rain
import com.mitsuki.falldownview.particle.Snow
import com.mitsuki.falldownview.path.*
import java.util.*
import kotlin.math.roundToInt

object ComponentFactory {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        mPaint.color = 0xeeffffff.toInt()
    }


    fun fetchComponentPathGroup(type: ParticleType, sizeInterval: Int): SizeSet {
        val pathGetter = fetchComponentPath(type)
        val sizeSet = SizeSet()
        val sizeStep = pathGetter.sizeBenchmark / sizeInterval
        for (i in 1..sizeInterval) {
            sizeSet.bitmapGroup.put(i, fetchBitmap(pathGetter.particlePath(sizeStep * i), sizeStep * i))
        }
        return sizeSet
    }

    private val random = Random()

    fun fetchParticleGroup(
            type: ParticleType, sizeInterval: Int, number: Int, maxX: Int, maxY: Int
    ): SparseArray<Particle> {
        val particleGroup: SparseArray<Particle> = SparseArray()
        for (i in 0 until number) {
            particleGroup.put(i, fetchParticle(
                    type,
                    random.nextInt(sizeInterval) + 1,
                    random.nextInt(maxX),
                    random.nextInt(maxY)
            ))
        }
        return particleGroup
    }

    /**********************************************************************************************/
    private fun fetchComponentPath(type: ParticleType): ComponentPath {
        return when (type) {
            ParticleType.SNOW -> SnowSample()
            ParticleType.RAIN -> RainSample()
            ParticleType.SAKURA -> SakuraSample()
            ParticleType.CIRCLE -> CircleSample()
        }
    }

    private fun fetchParticle(type: ParticleType, size: Int, x: Int, y: Int): Particle {
        return when (type) {
            ParticleType.SNOW -> Snow(x.toFloat(), y.toFloat(), size)
            ParticleType.RAIN -> Rain(x.toFloat(), y.toFloat(), size)
            ParticleType.SAKURA -> Circle(x.toFloat(), y.toFloat(), size)
            ParticleType.CIRCLE -> Circle(x.toFloat(), y.toFloat(), size)
        }
    }

    private fun fetchBitmap(path: Path, size: Float): Bitmap {
        val bitmap =
                Bitmap.createBitmap(size.roundToInt(), size.roundToInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawPath(path, mPaint)
        return bitmap
    }
}

enum class ParticleType { SNOW, RAIN, SAKURA, CIRCLE }
