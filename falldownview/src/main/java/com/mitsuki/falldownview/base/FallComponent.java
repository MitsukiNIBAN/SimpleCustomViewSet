package com.mitsuki.falldownview.base;

/**
 * 基础组件
 * 针对抽象组件的实现
 * 包含下落物体最基本的元素
 * 以及最基础的计算方式
 * (下落，左右风力)
 * <p>
 * 通过装饰类对基础组件增加例如透明度，景深，飘动等多种附加效果
 */
public class FallComponent implements BaseComponent {

    //所在view的大小
    private int parentWidth;
    private int parentHeight;
    //在view中的相对0，0的位置
    private float positionX;
    private float positionY;
    //相对上个位置需要移动的距离
    private float offsetX = 0;
    private float offsetY = 0;

    private float fallSpeed; //下落速度
    private float wind; //风力

    @Override
    public void move() {
        positionY = positionY + fallSpeed;
        positionX = positionX + wind;

        if (positionY > parentHeight) {
            offsetY = -positionY + fallSpeed;
            positionY = 0;
        } else if (positionY < 0) {
            offsetY = positionY - fallSpeed;
            positionY = parentHeight;
        } else {
            offsetY = fallSpeed;
        }

        if (positionX > parentWidth) {
            offsetX = -positionX + wind;
            positionX = 0;
        } else if (positionX < 0) {
            offsetX = positionX - wind;
            positionX = parentWidth;
        } else {
            offsetX = wind;
        }
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }
}
