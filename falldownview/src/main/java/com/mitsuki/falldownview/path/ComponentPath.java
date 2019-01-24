package com.mitsuki.falldownview.path;

import android.graphics.Path;

public interface ComponentPath {
    int getBaseLine();

    Path getObjPath(float size);
}
