package com.mitsuki.falldownview.snow;

import android.graphics.Path;

public interface SnowPath {
    int getBaseLine();

    Path getSnowPath(int size);

}
