//package com.mitsuki.falldownview.rain;
//
//import com.mitsuki.falldownview.RenderingRunnable;
//import com.mitsuki.falldownview.config.FallDownConfig;
//import com.mitsuki.falldownview.snow.Snow;
//
//public class RainRenderingRunnable extends RenderingRunnable<Rain> {
//    public RainRenderingRunnable() {
//        super();
//    }
//
//    @Override
//    protected void onCreateFallObject(int width, int height) {
//        for (int i = 0; i < FallDownConfig.ELEMENT_COUNT; i++) {
//            mFallList.add(new Rain.Builder(width, height)
//                    .setSpeed(4)
//                    .setWidth(4)
//                    .setLength(24)
//                    .build());
//        }
//    }
//}
