package com.mitsuki.falldownview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.SparseArray;

import com.mitsuki.falldownview.config.ComponentType;
import com.mitsuki.falldownview.path.ComponentPath;
import com.mitsuki.falldownview.path.PathFactory;

public class ComponentHelper {
    private SparseArray<Bitmap> bitmapGroup;
    private ComponentPath componentPath;

    public ComponentHelper(int type) {
        this.componentPath = PathFactory.newComponentPath(type);
        this.bitmapGroup = new SparseArray<>();
    }

    public void setComponentSizeSet(int count, float min, float max) {
        bitmapGroup.clear();
        insertComponentSize(count, min, max);
    }

    public void setComponentSizeSet(float... size) {
        bitmapGroup.clear();
        insertComponentSize(size);
    }

    public void insertComponentSize(int count, float min, float max) {
        float step = (max - min) / count;
        for (int i = 0; i < count; i++) {
            float size = min + step * i;
            Bitmap bitmap = Bitmap.createBitmap(Math.round(size), Math.round(size), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawPath(componentPath.getObjPath(size), new Paint());
            bitmapGroup.put(bitmapGroup.size() + i, bitmap);
        }
    }

    public void insertComponentSize(float... size) {
        for (int i = 0; i < size.length; i++) {
            Bitmap bitmap = Bitmap.createBitmap(Math.round(size[i]), Math.round(size[i]), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawPath(componentPath.getObjPath(size[i]), new Paint());
            bitmapGroup.put(bitmapGroup.size() + i, bitmap);
        }
    }

    public Bitmap getBitmapBySizeID(int id) {
        return bitmapGroup.get(id);
    }

    public void onDestroy() {
        bitmapGroup.clear();
        bitmapGroup = null;
        componentPath = null;
    }
}
