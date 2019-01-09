package com.mitsuki.falldownview.base;

import android.graphics.Path;

public interface FallObjectPath {
    int getBaseLine();

    Path getObjPath(float size);
}
