package com.mitsuki.chartview;

import java.util.LinkedHashMap;

public class FalseDataProvider {
    public static ChartBean getChartBean() {
        ChartBean mChart = new ChartBean();
        mChart.setVeriMax(1000);
        mChart.setUnit("㎡");
        mChart.setVeriCount(5);
        LinkedHashMap<String, Double> temp = new LinkedHashMap<>();
        temp.put("1月", 706d);
        temp.put("2月", 404d);
        temp.put("3月", 300d);
        temp.put("4月", 700d);
        temp.put("5月", 660d);
        temp.put("6月", 830d);
        temp.put("7月", 780d);

        mChart.setPointMap(temp);
        return mChart;
    }
}
