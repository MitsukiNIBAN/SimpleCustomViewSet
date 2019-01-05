package com.mitsuki.falldownview.snow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Random;

public class Snow {
    //    private int size; //大小
    private int speed;  //下落速度
    private int positionX;
    private int positionY;
    private int parentWidth;
    private int parentHeight;

    private Path mPath; // 图片

    public Snow(Builder builder) {
        this.parentWidth = builder.parentWidth;
        this.parentHeight = builder.parentHeight;

        this.positionX = builder.random.nextInt(builder.parentWidth);
        this.positionY = builder.random.nextInt(builder.parentHeight);
        this.speed = builder.random.nextInt(builder.fastest) + 1;

        this.mPath = builder.snowPath.getSnowPath(builder.size);
        this.mPath.offset(positionX, positionY);
    }

    public void drawSnow(Canvas canvas, Paint p) {
        canvas.drawPath(mPath, p);
        moveSnow();
    }

    private void moveSnow() {
        positionY = positionY + speed;
        if (positionY > parentHeight) {
            this.mPath.offset(0, -positionY);
            positionY = 0;
        } else {
            this.mPath.offset(0, speed);
        }

    }

    public static class Builder {
        private Random random;

        private final int parentWidth;
        private final int parentHeight;
        private int size;
        private int fastest; //最快速度
        private SnowPath snowPath;

        public Builder(SnowPath snowPath, int parentWidth, int parentHeight) {
            this.parentWidth = parentWidth;
            this.parentHeight = parentHeight;
            this.random = new Random();
            this.size = random.nextInt(32) + 1;
            this.fastest = 24;
            this.snowPath = snowPath;
        }

        public Builder setBiggest(int size) {
            this.size = random.nextInt(size) + 1;
            return this;
        }

        public Builder setFastest(int speed) {
            this.fastest = speed;
            return this;
        }

        public Snow build() {
            return new Snow(this);
        }
    }


}
