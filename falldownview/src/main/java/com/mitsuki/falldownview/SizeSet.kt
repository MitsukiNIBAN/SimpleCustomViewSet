package com.mitsuki.falldownview

import android.graphics.Bitmap
import android.util.SparseArray

/**
 * 包含基本的几种Size的粒子
 */
class SizeSet {
    //包含各种大小的基本bitmap集合
    val bitmapGroup: SparseArray<Bitmap> = SparseArray()

    fun getBitmapForSize(size: Int): Bitmap {
        return bitmapGroup[size]
    }
}