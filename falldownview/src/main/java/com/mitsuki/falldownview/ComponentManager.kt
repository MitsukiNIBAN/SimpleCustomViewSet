package com.mitsuki.falldownview

import android.os.Handler
import android.os.HandlerThread
import java.lang.ref.WeakReference
import java.util.concurrent.LinkedBlockingQueue

class ComponentManager(view: FallSurfaceView) {
    private val renderingView: WeakReference<FallSurfaceView> = WeakReference(view)

    private val RENDERING = "rendering"
    private val DRAW = "draw"
    //线程管理
    //渲染path线程
    private var mRenderingHandlerThread: HandlerThread
    private var mRenderingHandler: Handler
    //绘制path线程
    private var mDrawHandlerThread: HandlerThread
    private var mDrawHandler: Handler

    private var playing = false
    private var particleSet: ParticleSet
    private val mFrameQueue: LinkedBlockingQueue<Int> = LinkedBlockingQueue(2)

    init {
        mRenderingHandlerThread = HandlerThread(RENDERING)
        mRenderingHandlerThread.start()
        mRenderingHandler = Handler(mRenderingHandlerThread.looper)

        mDrawHandlerThread = HandlerThread(DRAW)
        mDrawHandlerThread.start()
        mDrawHandler = Handler(mDrawHandlerThread.looper)

        particleSet = ParticleSet()
        renderingView.get()?.setParticleSet(particleSet)
    }

    fun initParticle(number: Int, sizeInterval: Int, type: ParticleType) {
        particleSet.rangeWidth = renderingView.get()?.width ?: 0
        particleSet.rangeHeight = renderingView.get()?.height ?: 0

        particleSet.sizeSet = ComponentFactory.fetchComponentPathGroup(type, sizeInterval)

        particleSet.particleGroup = ComponentFactory.fetchParticleGroup(
            type,
            sizeInterval,
            number,
            particleSet.rangeWidth,
            particleSet.rangeHeight
        )
    }


    fun start() {
        if (playing) return
        playing = true
        mDrawHandler.post {
            while (playing) {
                mFrameQueue.take()
                renderingView.get()?.rendering()
                Thread.sleep(16)
            }
        }

        mRenderingHandler.post {
            while (playing) {
                particleSet.move()
                mFrameQueue.put(0)
            }
        }

    }

    fun stop() {
        playing = false
    }

    fun destory() {
        mRenderingHandlerThread.quit()
        mDrawHandlerThread.quit()
    }
}