////用法示例
//package com.mitsuki.cranedemonstration;
//
//import android.app.Activity;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.PixelFormat;
//import android.graphics.PorterDuff;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.util.Log;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Switch;
//
//import com.mitsuki.cranedemonstration.R;
//
//public class DemoActivity extends Activity implements Runnable {
//
//    private SurfaceView surfaceView;
//    private Canvas canvas;
//    private CraneHelper towerHelper;
//    private SurfaceHolder surfaceHolder;
//
//    HandlerThread handlerThread;
//    Handler handler;
//
//    private float x;
//    private float y;
//
//    private boolean oneStep = true;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        surfaceView = findViewById(R.id.surface);
//
//        handlerThread = new HandlerThread("anemometer");
//        handlerThread.start();
//
//        surfaceHolder = surfaceView.getHolder();
//
//        surfaceView.setZOrderOnTop(true);
//        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
//
//        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                Log.e("SurfaceView", "surfaceCreated");
//                if (towerHelper == null) {
//                    towerHelper = new TowerHelper(MainActivity.this, surfaceView.getWidth(), surfaceView.getHeight());
//                }
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//                canvas = holder.lockCanvas();
//                if (null != canvas) {
//                    towerHelper.drawTower(canvas, false);
//                    towerHelper.drawCraneComponent(canvas);
//                    holder.unlockCanvasAndPost(canvas);
//                }
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//
//            }
//        });
//
//        handler = new Handler(handlerThread.getLooper());
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        towerHelper.release();
//    }
//
//    @Override
//    public void run() {
//        canvas = surfaceHolder.lockCanvas();
//
//        if (null != canvas) {
//            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//            towerHelper.drawTower(canvas, true);
//            towerHelper.onRotateAnemometer(canvas);
//            towerHelper.moveCraneComponent(canvas, oneStep);
//            surfaceHolder.unlockCanvasAndPost(canvas);
//        }
//        if (towerHelper.isRefresh()) {
//            handler.postDelayed(this, TowerConfig.DRAW_DELAY);
//        }
//    }
//
//    public void onAnemBtnClick(View view) {
//        boolean isCheck = ((Switch) view).isChecked();
//        towerHelper.setBlow(isCheck);
//        if (towerHelper.isRefresh()) {
//            handler.postDelayed(this, TowerConfig.DRAW_DELAY);
//        }
//    }
//
//    public void onLoadBtnClick(View view) {
//        boolean isCheck = ((Switch) view).isChecked();
//        towerHelper.setLoad(isCheck);
//        if (towerHelper.isRefresh()) {
//            handler.postDelayed(this, TowerConfig.DRAW_DELAY);
//        }
//    }
//
//    public void up(View view) {
//        oneStep = true;
//        towerHelper.setDirection(0, -0.1f);
//        handler.postDelayed(this, TowerConfig.DRAW_DELAY);
//    }
//
//    public void down(View view) {
//        oneStep = true;
//        towerHelper.setDirection(0, 0.1f);
//        handler.postDelayed(this, TowerConfig.DRAW_DELAY);
//    }
//
//    public void left(View view) {
//        oneStep = true;
//        towerHelper.setDirection(-0.1f, 0);
//        handler.postDelayed(this, TowerConfig.DRAW_DELAY);
//    }
//
//    public void right(View view) {
//        oneStep = true;
//        towerHelper.setDirection(0.1f, 0);
//        handler.postDelayed(this, TowerConfig.DRAW_DELAY);
//    }
//
//    public void leftUp(View view) {
//        oneStep = true;
//        towerHelper.setDirection(-0.1f, -0.1f);
//        handler.postDelayed(this, TowerConfig.DRAW_DELAY);
//    }
//
//    public void rightUp(View view) {
//        oneStep = true;
//        towerHelper.setDirection(0.1f, -0.1f);
//        handler.postDelayed(this, TowerConfig.DRAW_DELAY);
//    }
//
//    public void rightDown(View view) {
//        oneStep = true;
//        towerHelper.setDirection(0.1f, 0.1f);
//        handler.postDelayed(this, TowerConfig.DRAW_DELAY);
//    }
//
//    public void leftDown(View view) {
//        oneStep = true;
//        towerHelper.setDirection(-0.1f, 0.1f);
//        handler.postDelayed(this, TowerConfig.DRAW_DELAY);
//    }
//
//    public void positionGo(View view) {
//        oneStep = false;
//        float x = Float.parseFloat(((EditText) findViewById(R.id.x)).getText().toString());
//        float y = Float.parseFloat(((EditText) findViewById(R.id.y)).getText().toString());
//        towerHelper.setTransfer(x, y);
//        if (towerHelper.isRefresh()) {
//            handler.postDelayed(this, TowerConfig.DRAW_DELAY);
//        }
//    }
//}
