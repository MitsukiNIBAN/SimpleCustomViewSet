//package com.mitsuki.falldownview.sakura;
//
//import com.mitsuki.falldownview.FallObject;
//import com.mitsuki.falldownview.path.ComponentPath;
//
//import java.util.Random;
//
//public class Sakura extends FallObject {
//
//    private float transparent; //透明度
//    private float rotate; //转速
//
//    public Sakura(Builder builder) {
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
//        this.transparent = builder.transparent;
//        this.rotate = builder.rotate;
//    }
//
//    @Override
//    protected void move() {
//        positionY = positionY + fallSpeed;
//        positionX = positionX + wind;
//        float tempOffsetX;
//        float tempOffsetY;
//        if (positionY > parentHeight) {
//            tempOffsetY = -positionY + fallSpeed;
//            positionY = 0;
//        } else {
//            tempOffsetY = fallSpeed;
//        }
//        if (positionX > parentWidth) {
//            tempOffsetX = -positionX + wind;
//            positionX = 0;
//        } else if (positionX < 0) {
//            tempOffsetX = positionX - wind;
//            positionX = parentWidth;
//        } else {
//            tempOffsetX = wind;
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
//        private int fallSpeed; //下落速度
//        protected float wind;
//
//        protected ComponentPath pathImpl;
//
//        private float transparent; //透明度
//        private float rotate; //转速
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
//            this.transparent = 0;
//            this.rotate = 0;
//
//            this.pathImpl = pathImpl;
//        }
//
//        public Sakura build() {
//            return new Sakura(this);
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
//        public Builder setTransparent(int tran) {
//            if (tran < 0
//                    || tran > 100) {
//                throw new RuntimeException("");
//            }
//            this.transparent = tran;
//            return this;
//        }
//
//        public Builder setTransparent(int min, int max) {
//            if (max < min
//                    || min < 0
//                    || max > 100) {
//                throw new RuntimeException("");
//            }
//            this.transparent = random.nextInt(max - min) + min;
//            return this;
//        }
//
//        public Builder setRotate(int speed) {
//            this.rotate = speed;
//            return this;
//        }
//
//        public Builder setRotate(int min, int max) {
//            if (max < min) {
//                throw new RuntimeException("");
//            }
//            this.rotate = random.nextInt(max - min) + min;
//            return this;
//        }
//    }
//
//}
