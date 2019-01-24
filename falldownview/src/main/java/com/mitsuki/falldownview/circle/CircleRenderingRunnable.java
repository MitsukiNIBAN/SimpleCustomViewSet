//package com.mitsuki.falldownview.circle;
//
//import com.mitsuki.falldownview.RenderingRunnable;
//import com.mitsuki.falldownview.config.FallDownConfig;
//
//public class CircleRenderingRunnable extends RenderingRunnable<Circle> {
//
//    public CircleRenderingRunnable() {
//        super();
//    }
//
//    @Override
//    protected void onCreateFallObject(int width, int height) {
//        for (int i = 0; i < FallDownConfig.ELEMENT_COUNT; i++) {
//            mFallList.add(new Circle.Builder(width, height)
//                    .setSpeed(1, 8)
//                    .setSize(10, 30)
//                    .build());
//        }
//    }
//}
