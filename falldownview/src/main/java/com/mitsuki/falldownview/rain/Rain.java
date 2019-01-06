package com.mitsuki.falldownview.rain;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.mitsuki.falldownview.snow.Snow;

import java.util.Random;

public class Rain {


    private float length; //雨的长度
    private float width;  //雨的宽度
    private float speed;  //下落速度
    private float transparent;  //雨的透明度
    private float wind;  //风力 注：当有风力时，为方便计算实际雨的长度会比设置的长度长，并且风力越大，偏差越大

    private float positionX;
    private float positionY;
    private int parentWidth;
    private int parentHeight;

    public Rain(Builder builder) {
        this.parentHeight = builder.parentHeight;
        this.parentWidth = builder.parentWidth;

        this.positionX = builder.random.nextInt(parentWidth) + 1;
        this.positionY = builder.random.nextInt(parentHeight) + 1;

        this.length = builder.length;
        this.width = builder.width;
        this.speed = builder.speed;
        this.transparent = builder.transparent;
        this.wind = builder.wind;
    }

    public void drawRain(Canvas canvas, Paint p) {
        p.setStrokeWidth(width);
        p.setAlpha((int) (255 * transparent / 100));
        canvas.drawLine(positionX, positionY, positionX + wind, positionY + length, p);
        moveRain();
    }

    private void moveRain() {
        positionY = positionY + speed;
        positionX = positionX + speed / length * wind;
        if (positionY > parentHeight) {
            positionY = 0;
        }
        if (positionX < 0) {
            positionX = parentWidth;
        } else if (positionX > parentWidth) {
            positionX = 0;
        }
    }

    public static class Builder {
        private Random random;
        private final int parentWidth;
        private final int parentHeight;

        private int length;
        private int width;
        private int speed;
        private int transparent;
        private int wind;

        public Builder(int parentWidth, int parentHeight) {
            this.random = new Random();
            this.parentWidth = parentWidth;
            this.parentHeight = parentHeight;

            this.length = 0;
            this.width = 0;
            this.speed = 0;
            this.transparent = 0;
            this.wind = 0;
        }

        public Builder setLength(int length) {
            this.length = length;
            return this;
        }

        public Builder setLength(int min, int max) {
            if (max < min) {
                throw new RuntimeException("");
            }
            this.length = random.nextInt(max - min) + min;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setWidth(int min, int max) {
            if (max < min) {
                throw new RuntimeException("");
            }
            this.width = random.nextInt(max - min) + min;
            return this;
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

        public Builder setWind(int wind) {
            this.wind = wind;
            return this;
        }

        public Rain build() {
            return new Rain(this);
        }
    }
}
