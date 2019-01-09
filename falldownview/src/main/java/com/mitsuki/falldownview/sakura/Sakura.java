package com.mitsuki.falldownview.sakura;

import com.mitsuki.falldownview.base.BaseBuilder;
import com.mitsuki.falldownview.base.FallObjectPath;
import com.mitsuki.falldownview.base.FallObject;

public class Sakura extends FallObject<Sakura.Builder> {

    private float transparent; //透明度
    private float rotate; //转速

    public Sakura(Builder builder) {
        super(builder);
        this.transparent = builder.transparent;
        this.rotate = builder.rotate;
    }

    public static class Builder extends BaseBuilder {

        private float transparent; //透明度
        private float rotate; //转速

        public Builder(int parentWidth, int parentHeight, FallObjectPath pathImpl) {
            super(parentWidth, parentHeight, pathImpl);
            this.rotate = 0;
            this.transparent = 0;
        }

        public Sakura build() {
            return new Sakura(this);
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
    }

}
