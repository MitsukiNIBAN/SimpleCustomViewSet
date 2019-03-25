package com.mitsuki.falldownview.path;

import com.mitsuki.falldownview.config.ComponentType;

public class PathFactory {
    public static ComponentPath newComponentPath(int type) {
        switch (type) {
            case ComponentType.SNOW:
                return new SnowSample();
            case ComponentType.RAIN:
                return new RainSample();
            case ComponentType.SAKURA:
                return new SakuraSample();
            case ComponentType.CIRCLE:
                return new CircleSample();
            default:
                return null;
        }
    }
}
