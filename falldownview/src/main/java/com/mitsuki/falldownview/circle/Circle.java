package com.mitsuki.falldownview.circle;

import android.graphics.Path;

import com.mitsuki.falldownview.FallObject;

import java.util.Random;

public class Circle extends FallObject {

    public Circle(Builder builder) {
        this.parentWidth = builder.parentWidth;
        this.parentHeight = builder.parentHeight;

        this.positionX = builder.random.nextInt(builder.parentWidth);
        this.positionY = builder.random.nextInt(builder.parentHeight);

        this.size = builder.size;
        this.fallSpeed = builder.fallSpeed;
        this.wind = builder.wind;

        this.mPath = new Path();
        this.mPath.addCircle(positionX - builder.size, positionY - builder.size, builder.size, Path.Direction.CW);
    }

    @Override
    protected void move() {
        positionY = positionY + fallSpeed;
        float tempOffsetY;
        if (positionY > parentHeight) {
            tempOffsetY = -positionY + fallSpeed;
            positionY = 0;
        } else {
            tempOffsetY = fallSpeed;
        }
        this.mPath.offset(0, tempOffsetY);
    }


    public static class Builder {
        private Random random;

        private final int parentWidth;
        private final int parentHeight;

        private int size;
        private int fallSpeed; //下落速度
        protected float wind;


        private int swayAmplitude;
        private int swayFrequency;

        public Builder(int parentWidth, int parentHeight) {
            this.random = new Random();

            this.parentWidth = parentWidth;
            this.parentHeight = parentHeight;

            this.size = 0;
            this.fallSpeed = 0;
            this.wind = 0;

            this.swayAmplitude = 0;
            this.swayFrequency = 0;
        }

        public Circle build() {
            return new Circle(this);
        }

        /******************************************************************************************/
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
    }

}
