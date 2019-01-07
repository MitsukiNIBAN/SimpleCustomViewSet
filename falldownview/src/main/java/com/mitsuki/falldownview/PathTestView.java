package com.mitsuki.falldownview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mitsuki.falldownview.sakura.SakuraPath;
import com.mitsuki.falldownview.sakura.SakuraSample;

public class PathTestView extends View {

    private Paint mPaint;
    private SakuraPath sakuraPath;

    public PathTestView(Context context) {
        this(context, null);
    }

    public PathTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xffffffff);
        mPaint.setStyle(Paint.Style.FILL);
        sakuraPath = new SakuraSample();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(sakuraPath.getSakuraPath(300), mPaint);
    }
}
