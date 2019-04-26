package com.mitsuki.chowder;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class ScanEffect extends View {

    //view 的大小
    private int width;
    private int height;

    private final ScanEffectDelegate mScanEffectDelegate;

    private float relaMove = 0;

    public ScanEffect(Context context) {
        this(context, null);
    }

    public ScanEffect(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScanEffectDelegate = new ScanEffectDelegate(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < width / mScanEffectDelegate.getGridSize(); i++) {
            canvas.drawLine(i * mScanEffectDelegate.getGridSize(), 0,
                    i * mScanEffectDelegate.getGridSize(), height,
                    mScanEffectDelegate.getGridLinePaint());
        }

        for (int i = 0; i < height / mScanEffectDelegate.getGridSize(); i++) {
            canvas.drawLine(0, i * mScanEffectDelegate.getGridSize(),
                    width, i * mScanEffectDelegate.getGridSize(),
                    mScanEffectDelegate.getGridLinePaint());
        }

        switch (mScanEffectDelegate.getDirection()) {
            case 0://向上
                float ts = height - relaMove;
                float te = height - relaMove + mScanEffectDelegate.getScanWidth();
                canvas.drawRect(0, ts, width, te,
                        mScanEffectDelegate.getScanPaint(ts, te));
                relaMove += mScanEffectDelegate.getSpeed();
                if (height - relaMove + mScanEffectDelegate.getScanWidth() < 0) {
                    relaMove = 0;
                }
                break;
            case 1://向下
                float bs = relaMove;
                float be = relaMove - mScanEffectDelegate.getScanWidth();
                canvas.drawRect(0, bs, width, be,
                        mScanEffectDelegate.getScanPaint(bs, be));
                relaMove += mScanEffectDelegate.getSpeed();
                if (relaMove - mScanEffectDelegate.getScanWidth() - height > 0) {
                    relaMove = 0;
                }
                break;
            case 2://向左
                float ls = width - relaMove;
                float le = width - relaMove + mScanEffectDelegate.getScanWidth();
                canvas.drawRect(ls, 0, le, height,
                        mScanEffectDelegate.getScanPaint(ls, le));
                relaMove += mScanEffectDelegate.getSpeed();
                if (width - relaMove + mScanEffectDelegate.getScanWidth() < 0) {
                    relaMove = 0;
                }
                break;
            case 3://向右
                float rs = relaMove;
                float re = relaMove - mScanEffectDelegate.getScanWidth();
                canvas.drawRect(rs, 0, re, height, mScanEffectDelegate.getScanPaint(rs, re));
                relaMove += mScanEffectDelegate.getSpeed();
                if (relaMove - mScanEffectDelegate.getScanWidth() - width > 0) {
                    relaMove = 0;
                }
                break;
        }

        postInvalidateDelayed(mScanEffectDelegate.getDelay());
    }
}
