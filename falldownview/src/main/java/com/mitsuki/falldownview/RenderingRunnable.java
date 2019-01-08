package com.mitsuki.falldownview;

public class RenderingRunnable implements Runnable {

    private OnPathCalculationListener onPathCalculationListener;

    public RenderingRunnable(OnPathCalculationListener onPathCalculationListener) {
        this.onPathCalculationListener = onPathCalculationListener;
    }

    @Override
    public void run() {
        if (null != onPathCalculationListener) {
            this.onPathCalculationListener.onCalculation();
        }
    }

    public interface OnPathCalculationListener {
        void onCalculation();
    }
}
