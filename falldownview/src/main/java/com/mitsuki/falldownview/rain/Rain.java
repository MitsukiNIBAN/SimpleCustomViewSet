package com.mitsuki.falldownview.rain;

import android.graphics.Path;

import com.mitsuki.falldownview.FallObject;

import java.util.Random;

public class Rain extends FallObject {

    private float length; //雨的长度
    private float width;  //雨的宽度

    private float transparent;  //雨的透明度

    public Rain(Builder builder) {
        this.parentHeight = builder.parentHeight;
        this.parentWidth = builder.parentWidth;

        this.positionX = builder.random.nextInt(parentWidth);
        this.positionY = builder.random.nextInt(parentHeight);

        this.fallSpeed = builder.fallSpeed;
        this.wind = builder.wind;

        this.width = builder.width;
        this.length = builder.length;
        this.transparent = builder.transparent;

        this.mPath = new Path();
        this.mPath.addRect(positionX, positionY, positionX + width, positionY + length, Path.Direction.CW);
    }

    @Override
    protected void move() {
        positionY = positionY + fallSpeed;
        positionX = positionX + wind;
        float tempOffsetX;
        float tempOffsetY;
        if (positionY > parentHeight) {
            tempOffsetY = -positionY + fallSpeed;
            positionY = 0;
        } else {
            tempOffsetY = fallSpeed;
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

        private final int parentWidth;
        private final int parentHeight;

        private int size;
        private int fallSpeed;
        protected float wind;

        private int length;
        private int width;

        private int transparent;

        public Builder(int parentWidth, int parentHeight) {
            this.random = new Random();
            this.parentWidth = parentWidth;
            this.parentHeight = parentHeight;

            this.size = 0;
            this.fallSpeed = 0;
            this.wind = 0;

            this.length = 0;
            this.width = 0;

            this.transparent = 0;
        }

        public Rain build() {
            return new Rain(this);
        }

        /******************************************************************************************/
        public Builder setSpeed(int speed) {
            this.fallSpeed = speed;
            return this;
        }

        public Builder setSpeed(int min, int max) {
            if (max < min) {
                throw new RuntimeException("");
            }
            this.fallSpeed = random.nextInt(max - min) + min;
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

        /******************************************************************************************/

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

    }
}
