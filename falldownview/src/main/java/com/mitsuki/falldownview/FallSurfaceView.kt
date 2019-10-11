package com.mitsuki.falldownview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class FallSurfaceView(context: Context, attrs: AttributeSet? = null) : SurfaceView(context, attrs),
    SurfaceHolder.Callback {

    private lateinit var particle: ParticleSet

    init {
        setZOrderOnTop(true)
        holder?.setFormat(PixelFormat.TRANSLUCENT)
        holder?.addCallback(this)

    }

    private var readyTag = 0

    fun rendering() {
        if (readyTag != 3) return
        val ca: Canvas? = holder.lockCanvas()
        if (ca != null) {
            ca.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            particle.draw(canvas = ca)
            holder.unlockCanvasAndPost(ca)
        }
    }

    fun setParticleSet(p: ParticleSet) {
        this.particle = p
        readyTag = readyTag or 2
    }

    /**********************************************************************************************/
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        readyTag = readyTag and 2
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        readyTag = readyTag or 1
    }


}