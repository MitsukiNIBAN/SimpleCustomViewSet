package com.mitsuki.falldownview;

import android.graphics.Path;

public abstract class FallObject {
    //所在view的大小
    protected int parentWidth;
    protected int parentHeight;

    //在view中的位置
    protected float positionX;
    protected float positionY;

    protected int size; //物体大小
    protected float fallSpeed; //下落速度
    protected float wind; //风力

    protected Path mPath; //物体的路径

    public Path getPath() {
        return mPath;
    }

    protected abstract void move(); //移动路径
}
