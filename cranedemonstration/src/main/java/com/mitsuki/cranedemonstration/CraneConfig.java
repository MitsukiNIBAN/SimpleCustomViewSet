package com.mitsuki.cranedemonstration;

public interface CraneConfig {
    int ANEMOMETER_RES = R.mipmap.img_anemometer_01;//顶部风速仪

    //风速仪动画组
    int[] ANEMOMETERS_RES = {R.mipmap.img_anemometer_01,
            R.mipmap.img_anemometer_02,
            R.mipmap.img_anemometer_03};

    int BALANCE_ARM_RES = R.mipmap.img_balance_arm; //平衡臂

    int LIFTING_ARM_RES = R.mipmap.img_lifting_arm; // 吊臂

    int TOWER_BODY_RES = R.mipmap.img_tower_body; //塔身

    int CRANE_CARRIAGE_RES = R.mipmap.img_crane_carriage; //变幅小车

    int LIFTING_ROPE_RES = R.mipmap.img_lifting_rope; //吊索

    int CRANE_HOOK_RES = R.mipmap.img_crane_hook; //吊钩

    int MATERIAL_RES = R.mipmap.img_material; //被吊物体

    /**********************************************************************************************/
    int BASE_RES = R.mipmap.img_base;// 10*10基准图

    float BASE_SIZE = 10;// 基准像素

    float BASE_HEIGHT = 1878;  //整体基准高度

    float BASE_WIDTH = 1542;  //整体基准宽度

    float RELATIVE_SIZE = 0.9f; //相对大小，相对于surfaceView的塔机大小

    int DRAW_DELAY = 15; // 每帧动画延迟，15ms保证60帧流畅

    //移动速度
    int TRANS_X_SPEED = 2; //单位px

    int TRANS_Y_SPEED = 4; //单位px
}
