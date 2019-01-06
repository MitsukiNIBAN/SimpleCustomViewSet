package com.mitsuki.falldownview.snow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Random;

public class Snow {
    private int speed;  //下落速度
    private int swayAmplitude; //摇晃幅度,与曲线振幅相乘
    private int swayFrequency; //摇晃频度, 决定函数角度的步进

    private int positionX;
    private int positionY;
    private int parentWidth;
    private int parentHeight;
    private int angle;

    private Path mPath; // 图片

    public Snow(Builder builder) {
        this.parentWidth = builder.parentWidth;
        this.parentHeight = builder.parentHeight;

        this.positionX = builder.random.nextInt(builder.parentWidth);
        this.positionY = builder.random.nextInt(builder.parentHeight);
        this.speed = builder.random.nextInt(builder.fastest) + 1;

        this.mPath = builder.snowPath.getSnowPath(builder.size);
        this.mPath.offset(positionX, positionY);

        this.swayAmplitude = builder.swayAmplitude;
        this.swayFrequency = builder.swayFrequency;
        this.angle = 0;
    }

    public void drawSnow(Canvas canvas, Paint p) {
        canvas.drawPath(mPath, p);
        moveSnow();
    }

    private void moveSnow() {
        angle = angle + swayFrequency;
        positionY = positionY + speed;

        float tempOffsetX;
        float tempOffsetY;

        if (positionY > parentHeight) {
            tempOffsetY = -positionY + speed;
            positionY = 0;
        } else {
            tempOffsetY = speed;
        }

        if (angle > 360) {
            tempOffsetX = (float) ((Math.sin(Math.toRadians(angle - swayFrequency)) - Math.sin(Math.toRadians(0))) * swayAmplitude);
            angle = 0;
        } else {
            tempOffsetX = (float) ((Math.sin(Math.toRadians(angle)) - Math.sin(Math.toRadians(angle - swayFrequency))) * swayAmplitude);
        }
        this.mPath.offset(tempOffsetX, tempOffsetY);
    }

    public static class Builder {
        private Random random;
        private final int parentWidth;
        private final int parentHeight;
        private int size;
        private int fastest; //最快速度
        private SnowPath snowPath;

        private int swayAmplitude;
        private int swayFrequency;

        public Builder(SnowPath snowPath, int parentWidth, int parentHeight) {
            this.random = new Random();

            this.parentWidth = parentWidth;
            this.parentHeight = parentHeight;
            this.size = random.nextInt(32) + 1;
            this.fastest = 24;
            this.snowPath = snowPath;

            this.swayAmplitude = 0;
            this.swayFrequency = 0;
        }

        public Builder setBiggest(int size) {
            this.size = random.nextInt(size) + 1;
            return this;
        }

        public Builder setFastest(int speed) {
            this.fastest = speed;
            return this;
        }

        public Builder setSway(int amplitude, int frequency) {
            this.swayAmplitude = amplitude;
            this.swayFrequency = frequency;
            return this;
        }

        public Snow build() {
            return new Snow(this);
        }
    }


}
