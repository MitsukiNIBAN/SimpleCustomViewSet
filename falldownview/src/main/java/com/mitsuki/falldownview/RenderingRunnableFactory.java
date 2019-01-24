//package com.mitsuki.falldownview;
//
//import com.mitsuki.falldownview.circle.CircleRenderingRunnable;
//import com.mitsuki.falldownview.config.ComponentType;
//import com.mitsuki.falldownview.rain.RainRenderingRunnable;
//import com.mitsuki.falldownview.sakura.SakuraRenderingRunnable;
//import com.mitsuki.falldownview.snow.SnowRenderingRunnable;
//
//public class RenderingRunnableFactory {
//
//    public static RenderingRunnable newRenderingRunnable(int type) {
//        switch (type) {
//            case ComponentType.SNOW:
//                return new SnowRenderingRunnable();
//            case ComponentType.RAIN:
//                return new RainRenderingRunnable();
//            case ComponentType.SAKURA:
//                return new SakuraRenderingRunnable();
//            case ComponentType.CIRCLE:
//                return new CircleRenderingRunnable();
//            default:
//                return null;
//        }
//    }
//}
