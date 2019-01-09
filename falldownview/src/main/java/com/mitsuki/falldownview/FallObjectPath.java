package com.mitsuki.falldownview;

import android.graphics.Path;

public interface FallObjectPath {
    int getBaseLine();

    Path getObjPath(float size);
}
