package com.mitsuki.falldownview

import android.graphics.Canvas
import android.graphics.Paint
import android.util.SparseArray
import com.mitsuki.falldownview.particle.Particle

/**
 * 粒子活动、初始化均通过该类实现
 */
class ParticleSet {
    //包含所有粒子信息
    lateinit var particleGroup: SparseArray<Particle>
    lateinit var sizeSet: SizeSet

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    var rangeWidth = 0
    var rangeHeight = 0

    fun draw(canvas: Canvas) {
//        for (p in particleGroup.valueIterator()) {
//            canvas.drawBitmap(sizeSet.getBitmapForSize(p.size), p.offsetX(), p.offsetY(), mPaint)
//        }
        for (i in 0 until particleGroup.size())
            canvas.drawBitmap(sizeSet.getBitmapForSize(particleGroup[i].size), particleGroup[i].offsetX(), particleGroup[i].offsetY(), mPaint)
    }

    fun move() {
        for (i in 0 until particleGroup.size()) particleGroup[i].move(rangeWidth, rangeHeight)
//        for (p in particleGroup.valueIterator()) p.move(rangeWidth, rangeHeight)
    }
}