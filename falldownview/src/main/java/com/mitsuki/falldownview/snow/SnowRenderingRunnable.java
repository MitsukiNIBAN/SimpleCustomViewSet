//package com.mitsuki.falldownview.snow;
//
//import com.mitsuki.falldownview.RenderingRunnable;
//import com.mitsuki.falldownview.config.FallDownConfig;
//
//public class SnowRenderingRunnable extends RenderingRunnable<Snow> {
//
//    public SnowRenderingRunnable() {
//        super();
//        this.fallObjectPath = new SnowSample();
//    }
//
//    @Override
//    protected void onCreateFallObject(int width, int height) {
//        for (int i = 0; i < FallDownConfig.ELEMENT_COUNT; i++) {
//            mFallList.add(new Snow.Builder(fallObjectPath, width, height)
//                    .setSpeed(1, 9)
//                    .setSize(4, 30)
//                    .setSway(8, 5)
//                    .build());
//        }
//    }
//}
