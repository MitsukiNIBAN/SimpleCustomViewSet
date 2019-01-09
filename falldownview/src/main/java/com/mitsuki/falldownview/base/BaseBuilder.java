package com.mitsuki.falldownview.base;

import java.util.Random;

public class BaseBuilder {
    protected Random random;

    protected final int parentWidth;
    protected final int parentHeight;

    protected int size;
    protected float fallSpeed;
    protected float wind;

    protected FallObjectPath pathImpl;

    public BaseBuilder(int parentWidth, int parentHeight, FallObjectPath pathImpl) {
        this.random = new Random();

        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;

        this.size = 0;
        this.fallSpeed = 0;
        this.wind = 0;

        this.pathImpl = pathImpl;
    }


    public BaseBuilder setSpeed(int speed) {
        this.fallSpeed = speed;
        return this;
    }

    public BaseBuilder setSpeed(int min, int max) {
        if (max < min) {
            throw new RuntimeException("");
        }
        this.fallSpeed = random.nextInt(max - min) + min;
        return this;
    }

    public BaseBuilder setWind(int wind) {
        this.wind = wind;
        return this;
    }

    public BaseBuilder setWind(int min, int max) {
        if (max < min) {
            throw new RuntimeException("");
        }
        this.wind = random.nextInt(max - min) + min;
        return this;
    }

    public BaseBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public BaseBuilder setSize(int min, int max) {
        if (max < min) {
            throw new RuntimeException("");
        }
        this.size = random.nextInt(max - min) + min;
        return this;
    }
}
