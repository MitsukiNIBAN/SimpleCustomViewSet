package com.mitsuki.falldownview.config;

import android.support.annotation.IntDef;

@IntDef({
        ComponentType.SNOW,
        ComponentType.RAIN,
        ComponentType.SAKURA,
        ComponentType.CIRCLE,
        ComponentType.CONFETTI,
})

public @interface ComponentType {
    int SNOW = 1;
    int RAIN = 2;
    int SAKURA = 3;
    int CIRCLE = 4;
    int CONFETTI = 5;
}


