package com.mitsuki.falldownview;

import android.graphics.Path;

public abstract class FallObject<T extends BaseBuilder> {
    //所在view的大小
    private int parentWidth;
    private int parentHeight;

    //在view中的位置
    protected float positionX;
    protected float positionY;

    protected int size; //物体大小
    protected float fallSpeed; //下落速度
    protected float wind; //风力

    protected Path mPath; //物体的路径

    public FallObject(T builder) {
        this.parentHeight = builder.parentHeight;
        this.parentWidth = builder.parentWidth;

        this.positionX = builder.random.nextInt(parentWidth - 1) + 1;
        this.positionY = builder.random.nextInt(parentHeight - 1) + 1;

        this.size = builder.size;
        this.fallSpeed = builder.fallSpeed;
        this.wind = builder.wind;

        this.mPath = builder.pathImpl.getObjPath(this.size);
        this.mPath.offset(positionX, positionY);
    }

    public Path getPath() {
        return mPath;
    }
}
