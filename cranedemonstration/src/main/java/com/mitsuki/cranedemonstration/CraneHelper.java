package com.mitsuki.cranedemonstration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.Map;

public class CraneHelper {
    private Bitmap mMainBitmap;
    private Bitmap bitmap;
    private Context context;
    private Map<Integer, CraneComponent> component;
    private Map<Integer, Bitmap> bitmapMap;

    //所在surface view的尺寸
    private float parentWidth;
    private float parentHeight;

    //换算出的塔机的大小
    private float towerWidth;
    private float towerHeight;

    //撑满surfaceView的比例
    private float fullProp;

    //将全部图像绘制在中心时相对于0，0坐标的原点
    private float originalX;
    private float originalY;

    private float craneRailLength = 0; //塔吊轨道长度
    private float craneRopeLength = 0; // 吊索的长度

    private Paint paint;
    private Matrix matrix; //用于转换
    private int windVelocityMark = 0; //风速计标记
    private boolean isLoad = false;//是否有负载

    private float currentRelativeComponentX = 0;//当前位置
    private float currentRelativeComponentY = 0;//当前位置

    private float directionX; //目标位置
    private float directionY; //目标位置

    private boolean isBlow; //是否存在风力
    private boolean isXTransfer; //横向是否需要移动
    private boolean isYTransfer; //纵向是否需要移动

    private boolean powerSwitch;

    public CraneHelper(Context context, int w, int h) {
        this.powerSwitch = true;
        this.parentWidth = w;
        this.parentHeight = h;
        this.context = context;
        this.component = new HashMap<>();
        this.bitmapMap = new HashMap<>();
        this.matrix = new Matrix();
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        calibration();

        this.mMainBitmap = Bitmap.createBitmap((int) towerWidth, (int) towerHeight, Bitmap.Config.ARGB_8888);
    }

    //图像大小校准
    private void calibration() {
        bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.BASE_RES);
        //确定实际读取的塔机图片的大小
        towerWidth = bitmap.getWidth() / CraneConfig.BASE_SIZE * CraneConfig.BASE_WIDTH;
        towerHeight = bitmap.getHeight() / CraneConfig.BASE_SIZE * CraneConfig.BASE_HEIGHT;
        //换算塔机在surfaceView中的比例
        if (parentWidth / parentHeight < towerWidth / towerHeight) {
            //按照宽度的比例
            fullProp = parentWidth / towerWidth;
        } else {
            //按照高度的比例
            fullProp = parentHeight / towerHeight;
        }
        //确定position zero
        originalX = parentWidth / 2 - towerWidth * fullProp * CraneConfig.RELATIVE_SIZE / 2;
        originalY = parentHeight / 2 - towerHeight * fullProp * CraneConfig.RELATIVE_SIZE / 2;
    }
    /**********************************************************************************************/
    //绘制塔机
    public void drawTower(Canvas canvas, boolean reDraw) {
        if (!powerSwitch) return;
        if (!reDraw) buildCrane();
        canvas.drawBitmap(mMainBitmap, originalX, originalY, paint);
    }

    //绘制能够移动的组件
    public void drawCraneComponent(Canvas canvas) {
        if (!powerSwitch) return;
        drawCraneCarriage(canvas, paint);
        drawLiftingRope(canvas, paint);
        drawCraneHook(canvas, paint);
        rotateAnemometer(canvas, paint);
    }

    public void moveCraneComponent(Canvas canvas, boolean oneStep) {
        if (!powerSwitch) return;
        moveComponent(canvas, paint, oneStep);
    }

    public void onRotateAnemometer(Canvas canvas) {
        if (!powerSwitch) return;
        rotateAnemometer(canvas, paint);
    }

    //设置移动值
    public void setTransfer(float x, float y) {
        //x，y需要百分比小数进来
        if (x > 1
                || x < 0
                || y > 1
                || y < 0) {
            return;
        }
        if (directionX != x * craneRailLength) {
            directionX = x * craneRailLength;
            isXTransfer = true;
        }
        if (directionY != y * craneRopeLength) {
            directionY = y * craneRopeLength;
            isYTransfer = true;
        }
    }

    public void setDirection(float x, float y) {
        if (x != 0) {
            directionX += x * craneRailLength;
            if (directionX > craneRailLength) directionX = craneRailLength;
            if (directionX < 0) directionX = 0;
            isXTransfer = true;
        }
        if (y != 0) {
            directionY += y * craneRopeLength;
            if (directionY > craneRopeLength) directionY = craneRopeLength;
            if (directionY < 0) directionY = 0;
            isYTransfer = true;
        }
    }

    //设置风速仪转动
    public void setBlow(boolean blow) {
        isBlow = blow;
    }

    //设置是否有负载
    public void setLoad(boolean load) {
        isLoad = load;
    }

    public boolean isActionContinue() {
        return isXTransfer || isYTransfer || isBlow;
    }

    public boolean isRefresh() {
        return isActionContinue();
    }

    //释放资源
    public void release() {
        powerSwitch = false;
        if (null != mMainBitmap) {
            mMainBitmap = null;
            mMainBitmap.recycle();
        }
        if (null != bitmap) {
            bitmap = null;
            bitmap.recycle();
        }
        if (null != context) context = null;
        if (null != component) {
            component.clear();
            component = null;
        }
        if (null != bitmapMap) {
            bitmapMap.clear();
            bitmapMap = null;
        }
        paint = null;
        matrix = null;
    }

    /**********************************************************************************************/
    //组装塔机
    private void buildCrane() {
        Canvas canvas = new Canvas(mMainBitmap);

        float tempPositionX = 0;
        float tempPositionY = 0;

        //加载风速仪资源，不需要绘制
        for (int i = 0; i < CraneConfig.ANEMOMETERS_RES.length; i++) {
            bitmap = bitmapMap.get(CraneConfig.ANEMOMETERS_RES[i]);
            if (null == bitmap) {
                bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.ANEMOMETERS_RES[i]);
                bitmapMap.put(CraneConfig.ANEMOMETERS_RES[i], bitmap);
            }
        }

        CraneComponent anemometer = new CraneComponent();
        anemometer.setY(originalY);

        tempPositionX = 0;
        tempPositionY = bitmap.getHeight() * fullProp * CraneConfig.RELATIVE_SIZE;
        //装上平衡臂
        bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.BALANCE_ARM_RES);
        matrix.reset();
        matrix.setTranslate(tempPositionX, tempPositionY);
        matrix.preScale(fullProp * CraneConfig.RELATIVE_SIZE,
                fullProp * CraneConfig.RELATIVE_SIZE);
        canvas.drawBitmap(bitmap, matrix, paint);

        //装完后一些数据更新
        anemometer.setX(originalX + bitmap.getWidth() * fullProp * CraneConfig.RELATIVE_SIZE);
        component.put(CraneConfig.ANEMOMETER_RES, anemometer);

        craneRailLength = bitmap.getWidth() * fullProp * CraneConfig.RELATIVE_SIZE;

        tempPositionX = bitmap.getWidth() * fullProp * CraneConfig.RELATIVE_SIZE;

        //装上吊臂
        bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.LIFTING_ARM_RES);
        matrix.reset();
        matrix.setTranslate(tempPositionX, tempPositionY);
        matrix.preScale(fullProp * CraneConfig.RELATIVE_SIZE,
                fullProp * CraneConfig.RELATIVE_SIZE);
        canvas.drawBitmap(bitmap, matrix, paint);

        //装完后一些数据更新
        craneRailLength += bitmap.getWidth() * fullProp * CraneConfig.RELATIVE_SIZE;

        tempPositionX = 0;
        tempPositionY += bitmap.getHeight() * fullProp * CraneConfig.RELATIVE_SIZE;

        //装上塔身
        bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.TOWER_BODY_RES);
        matrix.reset();
        matrix.setTranslate(tempPositionX, tempPositionY);
        matrix.preScale(fullProp * CraneConfig.RELATIVE_SIZE,
                fullProp * CraneConfig.RELATIVE_SIZE);
        canvas.drawBitmap(bitmap, matrix, paint);

        //并设定好初始变幅小车的位置
        CraneComponent craneCarriage = new CraneComponent();
        craneCarriage.setX(originalX + bitmap.getWidth() * fullProp * CraneConfig.RELATIVE_SIZE);
        craneCarriage.setY(originalY + tempPositionY);
        component.put(CraneConfig.CRANE_CARRIAGE_RES, craneCarriage);

        craneRailLength -= bitmap.getWidth() * fullProp * CraneConfig.RELATIVE_SIZE;
    }

    //#1、绘制变幅小车
    private void drawCraneCarriage(Canvas canvas, Paint p) {
        bitmap = bitmapMap.get(CraneConfig.CRANE_CARRIAGE_RES);
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.CRANE_CARRIAGE_RES);
            bitmapMap.put(CraneConfig.CRANE_CARRIAGE_RES, bitmap);
        }

        CraneComponent craneCarriage = component.get(CraneConfig.CRANE_CARRIAGE_RES);
        if (null == craneCarriage) return;

        matrix.reset();
        matrix.setTranslate(craneCarriage.getX(),
                craneCarriage.getY());
        matrix.preScale(fullProp * CraneConfig.RELATIVE_SIZE,
                fullProp * CraneConfig.RELATIVE_SIZE);
        canvas.drawBitmap(bitmap, matrix, p);

        CraneComponent liftingRope = new CraneComponent();
        liftingRope.setX(craneCarriage.getX());
        liftingRope.setY(craneCarriage.getY() + bitmap.getHeight() * fullProp * CraneConfig.RELATIVE_SIZE);
        component.put(CraneConfig.LIFTING_ROPE_RES, liftingRope);

        craneRailLength -= bitmap.getWidth() * fullProp * CraneConfig.RELATIVE_SIZE;
    }

    //#2、绘制吊索
    private void drawLiftingRope(Canvas canvas, Paint p) {
        bitmap = bitmapMap.get(CraneConfig.LIFTING_ROPE_RES);
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.LIFTING_ROPE_RES);
            bitmapMap.put(CraneConfig.LIFTING_ROPE_RES, bitmap);
        }

        CraneComponent liftingRope = component.get(CraneConfig.LIFTING_ROPE_RES);
        if (null == liftingRope) return;

        matrix.reset();
        matrix.setTranslate(liftingRope.getX(),
                liftingRope.getY());
        matrix.preScale(fullProp * CraneConfig.RELATIVE_SIZE, 0);
        canvas.drawBitmap(bitmap, matrix, p);

        CraneComponent craneHook = new CraneComponent();
        craneHook.setX(liftingRope.getX());
        craneHook.setY(liftingRope.getY());
        component.put(CraneConfig.CRANE_HOOK_RES, craneHook);

        craneRopeLength = bitmap.getHeight() * fullProp * CraneConfig.RELATIVE_SIZE;
    }

    //#3、绘制吊钩
    private void drawCraneHook(Canvas canvas, Paint p) {
        bitmap = bitmapMap.get(CraneConfig.CRANE_HOOK_RES);
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.CRANE_HOOK_RES);
            bitmapMap.put(CraneConfig.CRANE_HOOK_RES, bitmap);
        }

        CraneComponent craneHook = component.get(CraneConfig.CRANE_HOOK_RES);
        if (null == craneHook) return;

        matrix.reset();
        matrix.setTranslate(craneHook.getX(),
                craneHook.getY());
        matrix.preScale(fullProp * CraneConfig.RELATIVE_SIZE,
                fullProp * CraneConfig.RELATIVE_SIZE);
        canvas.drawBitmap(bitmap, matrix, p);

        CraneComponent material = new CraneComponent();
        material.setX(craneHook.getX());
        material.setY(craneHook.getY() + bitmap.getHeight() * fullProp * CraneConfig.RELATIVE_SIZE);
        component.put(CraneConfig.MATERIAL_RES, material);

        //加载重物
        bitmap = bitmapMap.get(CraneConfig.MATERIAL_RES);
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.MATERIAL_RES);
            bitmapMap.put(CraneConfig.MATERIAL_RES, bitmap);
        }
        if (isLoad) {
            matrix.reset();
            matrix.setTranslate(material.getX(),
                    material.getY());
            matrix.preScale(fullProp * CraneConfig.RELATIVE_SIZE,
                    fullProp * CraneConfig.RELATIVE_SIZE);
            canvas.drawBitmap(bitmap, matrix, p);
        }

    }

    //--------------------------------------------------------------------------------------------//
    //移动各个组件
    private void moveComponent(Canvas canvas, Paint p, boolean oneStep) {
        //先进行一波计算
        if (oneStep) {
            currentRelativeComponentX = directionX;
            currentRelativeComponentY = directionY;
            isXTransfer = false;
            isYTransfer = false;
        } else {
            if (isXTransfer) {
                if (directionX > currentRelativeComponentX) {
                    currentRelativeComponentX += CraneConfig.TRANS_X_SPEED;
                    if (currentRelativeComponentX > directionX) {
                        currentRelativeComponentX = directionX;
                        isXTransfer = false;
                    }
                } else {
                    currentRelativeComponentX -= CraneConfig.TRANS_X_SPEED;
                    if (currentRelativeComponentX < directionX) {
                        currentRelativeComponentX = directionX;
                        isXTransfer = false;
                    }
                }
            }
            if (isYTransfer) {
                if (directionY > currentRelativeComponentY) {
                    currentRelativeComponentY += CraneConfig.TRANS_Y_SPEED;
                    if (currentRelativeComponentY > directionY) {
                        currentRelativeComponentY = directionY;
                        isYTransfer = false;
                    }
                } else {
                    currentRelativeComponentY -= CraneConfig.TRANS_Y_SPEED;
                    if (currentRelativeComponentY < directionY) {
                        currentRelativeComponentY = directionY;
                        isYTransfer = false;
                    }
                }
            }
        }
        /******************************************************************************************/
        //移动变幅小车
        bitmap = bitmapMap.get(CraneConfig.CRANE_CARRIAGE_RES);
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.CRANE_CARRIAGE_RES);
            bitmapMap.put(CraneConfig.CRANE_CARRIAGE_RES, bitmap);
        }

        CraneComponent tempComponent = component.get(CraneConfig.CRANE_CARRIAGE_RES);
        if (null == tempComponent) return;
        matrix.reset();
        matrix.setTranslate(tempComponent.getX() + currentRelativeComponentX,
                tempComponent.getY());
        matrix.preScale(fullProp * CraneConfig.RELATIVE_SIZE,
                fullProp * CraneConfig.RELATIVE_SIZE);
        canvas.drawBitmap(bitmap, matrix, p);
        /******************************************************************************************/
        //移动吊索
        bitmap = bitmapMap.get(CraneConfig.LIFTING_ROPE_RES);
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.LIFTING_ROPE_RES);
            bitmapMap.put(CraneConfig.LIFTING_ROPE_RES, bitmap);
        }
        tempComponent = component.get(CraneConfig.LIFTING_ROPE_RES);
        if (null == tempComponent) return;
        matrix.reset();
        matrix.setTranslate(tempComponent.getX() + currentRelativeComponentX,
                tempComponent.getY());
        matrix.preScale(fullProp * CraneConfig.RELATIVE_SIZE,
                currentRelativeComponentY / craneRopeLength * fullProp * CraneConfig.RELATIVE_SIZE);
        canvas.drawBitmap(bitmap, matrix, p);
        /******************************************************************************************/
        //移动吊钩
        bitmap = bitmapMap.get(CraneConfig.CRANE_HOOK_RES);
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.CRANE_HOOK_RES);
            bitmapMap.put(CraneConfig.CRANE_HOOK_RES, bitmap);
        }

        tempComponent = component.get(CraneConfig.CRANE_HOOK_RES);
        if (null == tempComponent) return;

        matrix.reset();
        matrix.setTranslate(tempComponent.getX() + currentRelativeComponentX,
                tempComponent.getY() + currentRelativeComponentY);
        matrix.preScale(fullProp * CraneConfig.RELATIVE_SIZE,
                fullProp * CraneConfig.RELATIVE_SIZE);
        canvas.drawBitmap(bitmap, matrix, p);

        //加载重物
        if (isLoad) {
            bitmap = bitmapMap.get(CraneConfig.MATERIAL_RES);
            if (null == bitmap) {
                bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.MATERIAL_RES);
                bitmapMap.put(CraneConfig.MATERIAL_RES, bitmap);
            }

            tempComponent = component.get(CraneConfig.MATERIAL_RES);
            if (null == tempComponent) return;

            matrix.reset();
            matrix.setTranslate(tempComponent.getX() + currentRelativeComponentX,
                    tempComponent.getY() + currentRelativeComponentY);
            matrix.preScale(fullProp * CraneConfig.RELATIVE_SIZE,
                    fullProp * CraneConfig.RELATIVE_SIZE);
            canvas.drawBitmap(bitmap, matrix, p);
        }
    }

    //旋转风速仪
    private void rotateAnemometer(Canvas canvas, Paint p) {
        bitmap = bitmapMap.get(CraneConfig.ANEMOMETERS_RES[windVelocityMark]);
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), CraneConfig.ANEMOMETERS_RES[windVelocityMark]);
            bitmapMap.put(CraneConfig.ANEMOMETERS_RES[windVelocityMark], bitmap);
        }

        CraneComponent anemometer = component.get(CraneConfig.ANEMOMETER_RES);
        if (null == anemometer) return;

        matrix.reset();
        matrix.setTranslate(anemometer.getX(), anemometer.getY());
        matrix.preScale(fullProp * CraneConfig.RELATIVE_SIZE,
                fullProp * CraneConfig.RELATIVE_SIZE);
        canvas.drawBitmap(bitmap, matrix, p);

        if (isBlow) {
            windVelocityMark++;
            if (windVelocityMark > CraneConfig.ANEMOMETERS_RES.length - 1) {
                windVelocityMark = 0;
            }
        }
    }

}
