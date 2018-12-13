package com.mitsuki.chartview;

import java.util.LinkedHashMap;

/**
 * 图标数据bean
 * 其中需要包含
 * 点数据 map key为横轴名字，value为纵轴数值
 * 纵轴数据单位
 * 纵轴坐标点个数
 * 纵轴坐标最大值
 */
public class ChartBean {

    private LinkedHashMap<String, Double> mPointMap;
    private String unit;
    private int mVeriCount;
    private double mVeriMax;

    public ChartBean() {
        this.mPointMap = new LinkedHashMap<>();
        this.unit = "";
        this.mVeriCount = 0;
        this.mVeriMax = 0;
    }

    public void setPointMap(LinkedHashMap<String, Double> mPointMap) {
        this.mPointMap.clear();
        this.mPointMap.putAll(mPointMap);
    }

    public LinkedHashMap<String, Double> getPointMap() {
        return mPointMap;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getVeriCount() {
        return mVeriCount;
    }

    public void setVeriCount(int mVeriCount) {
        this.mVeriCount = mVeriCount;
    }


    public double getVeriMax() {
        return mVeriMax;
    }

    public void setVeriMax(double mVeriMax) {
        this.mVeriMax = mVeriMax;
    }

    /**********************************************************************************************/

    //获得坐标点个数
    public int getPointCount() {
        if (null == mPointMap)
            return 0;
        return mPointMap.size();
    }

    //获取纵坐标单位数值【即纵轴刻度文字】
    public double getVerticalUnitValue() {
        return mVeriMax / (mVeriCount - 1);
    }

    public double getVeriPosition(double value, double interval) {
        return value / getVerticalUnitValue() * interval;
    }
}
