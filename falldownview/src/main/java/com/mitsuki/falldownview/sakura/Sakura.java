package com.mitsuki.falldownview.sakura;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.mitsuki.falldownview.rain.Rain;

import java.util.Random;

public class Sakura {

    //随着帧改变大小，在线程优化的时候再处理
    private float speed;
    private float wind;
    private float transparent;
    private float rotate;

    private float positionX;
    private float positionY;
    private int parentWidth;
    private int parentHeight;

    private Path mPath;

    public Sakura(Builder builder) {
        this.parentHeight = builder.parentHeight;
        this.parentWidth = builder.parentWidth;

        this.positionX = builder.random.nextInt(parentWidth);
        this.positionY = builder.random.nextInt(parentHeight);

        this.speed = builder.speed;
        this.wind = builder.wind;
        this.transparent = builder.transparent;

        this.mPath = builder.sakuraPath.getSakuraPath(builder.size);
        this.mPath.offset(positionX, positionY);
    }

    public void drawSakura(Canvas canvas, Paint p) {
        canvas.drawPath(mPath, p);
        moveSakura();
    }

    private void moveSakura() {
        positionY = positionY + speed;
        positionX = positionX + wind;
        float tempOffsetX;
        float tempOffsetY;
        if (positionY > parentHeight) {
            tempOffsetY = -positionY + speed;
            positionY = 0;
        } else {
            tempOffsetY = speed;
        }
        if (positionX > parentWidth) {
            tempOffsetX = -positionX + wind;
            positionX = 0;
        } else if (positionX < 0) {
            tempOffsetX = positionX - wind;
            positionX = parentWidth;
        } else {
            tempOffsetX = wind;
        }
        this.mPath.offset(tempOffsetX, tempOffsetY);
    }


    public static class Builder {
        private Random random;

        private float speed;
        private float wind;
        private float transparent;
        private float rotate;
        private int size;

        private int parentWidth;
        private int parentHeight;

        private SakuraPath sakuraPath;

        public Builder(SakuraPath sakuraPath, int parentWidth, int parentHeight) {
            this.random = new Random();
            this.parentWidth = parentWidth;
            this.parentHeight = parentHeight;

            this.sakuraPath = sakuraPath;

            this.speed = 0;
            this.wind = 0;
            this.transparent = 0;
            this.rotate = 0;
            this.size = 0;
        }

        public Builder setSpeed(int speed) {
            this.speed = speed;
            return this;
        }

        public Builder setSpeed(int min, int max) {
            if (max < min) {
                throw new RuntimeException("");
            }
            this.speed = random.nextInt(max - min) + min;
            return this;
        }

        public Builder setWind(int wind) {
            this.wind = wind;
            return this;
        }

        public Builder setWind(int min, int max) {
            if (max < min) {
                throw new RuntimeException("");
            }
            this.wind = random.nextInt(max - min) + min;
            return this;
        }

        public Builder setTransparent(int tran) {
            if (tran < 0
                    || tran > 100) {
                throw new RuntimeException("");
            }
            this.transparent = tran;
            return this;
        }

        public Builder setTransparent(int min, int max) {
            if (max < min
                    || min < 0
                    || max > 100) {
                throw new RuntimeException("");
            }
            this.transparent = random.nextInt(max - min) + min;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setSize(int min, int max) {
            if (max < min) {
                throw new RuntimeException("");
            }
            this.size = random.nextInt(max - min) + min;
            return this;
        }

        public Builder setRotate(int speed) {
            this.rotate = speed;
            return this;
        }

        public Builder setRotate(int min, int max) {
            if (max < min) {
                throw new RuntimeException("");
            }
            this.rotate = random.nextInt(max - min) + min;
            return this;
        }

        public Sakura build() {
            return new Sakura(this);
        }
    }

}
