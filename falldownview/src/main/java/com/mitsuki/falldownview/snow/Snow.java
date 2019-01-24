//package com.mitsuki.falldownview.snow;
//
//import com.mitsuki.falldownview.FallObject;
//import com.mitsuki.falldownview.path.ComponentPath;
//
//import java.util.Random;
//
//public class Snow extends FallObject {
//
//    private int swayAmplitude; //摇晃幅度,与曲线振幅相乘
//    private int swayFrequency; //摇晃频度, 决定函数角度的步进
//    private int angle;
//
//    public Snow(Builder builder) {
//        this.parentWidth = builder.parentWidth;
//        this.parentHeight = builder.parentHeight;
//
//        this.positionX = builder.random.nextInt(builder.parentWidth);
//        this.positionY = builder.random.nextInt(builder.parentHeight);
//
//        this.size = builder.size;
//        this.fallSpeed = builder.fallSpeed;
//        this.wind = builder.wind;
//
//        this.mPath = builder.pathImpl.getObjPath(builder.size);
//        this.mPath.offset(positionX, positionY);
//
//        this.swayAmplitude = builder.swayAmplitude;
//        this.swayFrequency = builder.swayFrequency;
//
//        this.angle = 0;
//    }
//
//    @Override
//    protected void move() {
//        angle = angle + swayFrequency;
//        positionY = positionY + fallSpeed;
//
//        float tempOffsetX;
//        float tempOffsetY;
//
//        if (positionY > parentHeight) {
//            tempOffsetY = -positionY + fallSpeed;
//            positionY = 0;
//        } else {
//            tempOffsetY = fallSpeed;
//        }
//
//        if (angle > 360) {
//            tempOffsetX = (float) ((Math.sin(Math.toRadians(angle - swayFrequency)) - Math.sin(Math.toRadians(0))) * swayAmplitude);
//            angle = 0;
//        } else {
//            tempOffsetX = (float) ((Math.sin(Math.toRadians(angle)) - Math.sin(Math.toRadians(angle - swayFrequency))) * swayAmplitude);
//        }
//        this.mPath.offset(tempOffsetX, tempOffsetY);
//    }
//
//    public static class Builder {
//        private Random random;
//
//        private final int parentWidth;
//        private final int parentHeight;
//
//        private int size;
//        private int fallSpeed;
//        protected float wind;
//
//        protected ComponentPath pathImpl;
//
//        private int swayAmplitude;
//        private int swayFrequency;
//
//        public Builder(ComponentPath pathImpl, int parentWidth, int parentHeight) {
//            this.random = new Random();
//
//            this.parentWidth = parentWidth;
//            this.parentHeight = parentHeight;
//
//            this.size = 0;
//            this.fallSpeed = 0;
//            this.wind = 0;
//
//            this.swayAmplitude = 0;
//            this.swayFrequency = 0;
//
//            this.pathImpl = pathImpl;
//        }
//
//        public Snow build() {
//            return new Snow(this);
//        }
//
//        /******************************************************************************************/
//        public Builder setSize(int size) {
//            this.size = size;
//            return this;
//        }
//
//        public Builder setSize(int min, int max) {
//            if (max < min) {
//                throw new RuntimeException("");
//            }
//            this.size = random.nextInt(max - min) + min;
//            return this;
//        }
//
//        public Builder setSpeed(int speed) {
//            this.fallSpeed = speed;
//            return this;
//        }
//
//        public Builder setSpeed(int min, int max) {
//            if (max < min) {
//                throw new RuntimeException("");
//            }
//            this.fallSpeed = random.nextInt(max - min) + min;
//            return this;
//        }
//
//        public Builder setWind(int wind) {
//            this.wind = wind;
//            return this;
//        }
//
//        public Builder setWind(int min, int max) {
//            if (max < min) {
//                throw new RuntimeException("");
//            }
//            this.wind = random.nextInt(max - min) + min;
//            return this;
//        }
//
//        /******************************************************************************************/
//        public Builder setSway(int amplitude, int frequency) {
//            this.swayAmplitude = amplitude;
//            this.swayFrequency = frequency;
//            return this;
//        }
//
//        public Builder setSwayAmplitude(int min, int max) {
//            if (max < min) {
//                throw new RuntimeException("");
//            }
//            this.swayAmplitude = random.nextInt(max - min) + min;
//            return this;
//        }
//
//        public Builder setSwayFrequency(int min, int max) {
//            if (max < min
//                    || min < 0
//                    || max > 360) {
//                throw new RuntimeException("");
//            }
//            this.swayFrequency = random.nextInt(max - min) + min;
//            return this;
//        }
//
//    }
//
//}
