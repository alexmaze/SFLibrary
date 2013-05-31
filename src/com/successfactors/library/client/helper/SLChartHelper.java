package com.successfactors.library.client.helper;

import java.util.ArrayList;

import com.rednels.ofcgwt.client.model.ChartData;
import com.rednels.ofcgwt.client.model.Text;
import com.rednels.ofcgwt.client.model.axis.XAxis;
import com.rednels.ofcgwt.client.model.axis.YAxis;
import com.rednels.ofcgwt.client.model.elements.BarChart;
import com.rednels.ofcgwt.client.model.elements.BarChart.BarStyle;
import com.rednels.ofcgwt.client.model.elements.LineChart;
import com.rednels.ofcgwt.client.model.elements.PieChart;
import com.rednels.ofcgwt.client.model.elements.SketchBarChart;
import com.successfactors.library.shared.model.SLChartData;

public class SLChartHelper {
	
	private static String[] CHART_COLOURS = { "#ff0000", "#00ff00", "#0000ff", "#ff9900", "#ff00ff", "#FFFF00", "#6699FF", "#339933", "#1199aa" };
	
	/**
	 *  饼图
	 * */
	public static ChartData getAniPieChartData(String strName, String strToolTip, ArrayList<SLChartData> dataList) {
		
		ChartData cd = new ChartData(strName, "font-size: 14px; font-family: Microsoft YaHei; text-align: center;");
		cd.setBackgroundColour("#ffffff");
		
		PieChart pie = new PieChart();
		pie.setTooltip("#label#  #val#"+strToolTip+"<br>#percent#<br>");
		pie.setAnimateOnShow(true);
		pie.setAnimation(new PieChart.PieBounceAnimation(10));
		pie.setGradientFill(false);
		pie.setColours(CHART_COLOURS);

		for (SLChartData smsChartData : dataList) {
			pie.addSlices(smsChartData.toSlice());
		}
		
		cd.addElements(pie);
		return cd;
	}

	/**
	 *  柱状图
	 * */
	public static ChartData getBarChartTransparentData(String strName, String strToolTip, ArrayList<SLChartData> dataList) {
		ChartData cd = new ChartData(strName, "font-size: 14px; font-family: Microsoft YaHei; text-align: center;");
		cd.setBackgroundColour("-1");
		cd.setDecimalSeparatorComma(true);

		int iLargestNum = 0;
		
		XAxis xa = new XAxis();
		ArrayList<String> labelList = new ArrayList<String>();
		for (SLChartData smsChartData : dataList) {
			labelList.add(smsChartData.getName());
			iLargestNum = smsChartData.getValue() > iLargestNum ? smsChartData.getValue() : iLargestNum;
		}
		xa.setLabels(labelList);

		xa.getLabels().setColour("#000000");
		xa.setGridColour("#aaaaff");
		xa.setColour("#000000");
		cd.setXAxis(xa);
		
		YAxis ya = new YAxis();
		ya.setRange(0, iLargestNum);
		ya.setGridColour("#aaaaff");
		ya.setColour("#000000");
		cd.setYAxisLabelStyle(10, "#000000");
		cd.setYAxis(ya);
		
		BarChart bchart = new BarChart(BarStyle.NORMAL);
		bchart.setColour("#000088");
		bchart.setTooltip("#val#"+strToolTip);
		
		for (SLChartData smsChartData : dataList) {
			bchart.addValues(smsChartData.getValue());
		}
		
		cd.addElements(bchart);
		return cd;
	}

	/**
	 *  折线图
	 * */
	public static ChartData getLineChartData(String strName, String strUnderName, String strToolTip, ArrayList<ArrayList<SLChartData>> dataList) {
		
		ChartData cd = new ChartData(strName, "font-size: 14px; font-family: Microsoft YaHei; text-align: center;");
		cd.setBackgroundColour("#ffffff");

		
		ArrayList<String> xLabels = new ArrayList<String>();
		int i = 0;
		int iLargestNum = 0;
		boolean isFirst = true;
		for (ArrayList<SLChartData> arrayList : dataList) {

			LineChart lineChart = new LineChart();
			lineChart.setLineStyle(new LineChart.LineStyle(2, 4));
			lineChart.setDotStyle(null);
			lineChart.setText(isFirst?"借阅趋势":"预订趋势");
			lineChart.setColour(CHART_COLOURS[i++]);
			
			for (SLChartData smsChartData : arrayList) {
				lineChart.addValues(smsChartData.getValue());
				
				if (isFirst) {
					xLabels.add(smsChartData.getName());
				}
				
				iLargestNum = smsChartData.getValue() > iLargestNum ? smsChartData.getValue() : iLargestNum;
			}
			lineChart.setTooltip("#val#" + strToolTip);
			cd.addElements(lineChart);
			isFirst = false;
		}
		
		XAxis xa = new XAxis();
		xa.setLabels(xLabels);
		cd.setXAxis(xa);

		YAxis ya = new YAxis();
		 ya.setMax(iLargestNum);
		 ya.setMin(0);
		cd.setYAxis(ya);

		cd.setXLegend(new Text(strUnderName, "{font-size: 10px; color: #000000}"));

		return cd;
	}

	/**
	 *  单折线图
	 * */
	public static ChartData getOneLineChartData(String strName,
			String strUnderName, String strToolTip, ArrayList<SLChartData> dataList) {

		ChartData cd = new ChartData(strName,
				"font-size: 14px; font-family: Microsoft YaHei; text-align: center;");
		cd.setBackgroundColour("#ffffff");

		LineChart lineChart = new LineChart();
		lineChart.setLineStyle(new LineChart.LineStyle(2, 4));
		lineChart.setDotStyle(null);
		lineChart.setColour("#000000");
		lineChart.setTooltip("#val#" + strToolTip);

		for (SLChartData smsChartData : dataList) {
			lineChart.addValues(smsChartData.getValue());
		}

		cd.addElements(lineChart);

		int iLargestNum = 0;
		
		XAxis xa = new XAxis();
		ArrayList<String> labelList = new ArrayList<String>();
		for (SLChartData smsChartData : dataList) {
			labelList.add(smsChartData.getName());
			iLargestNum = smsChartData.getValue() > iLargestNum ? smsChartData.getValue() : iLargestNum;
		}
		xa.setLabels(labelList);
		cd.setXAxis(xa);

		YAxis ya = new YAxis();
		 ya.setMax(iLargestNum);
		 ya.setMin(0);
		cd.setYAxis(ya);

		cd.setXLegend(new Text(strUnderName,
				"{font-size: 10px; color: #000000}"));

		return cd;
	}

	/**
	 *  素描柱状图
	 * */
	public static ChartData getSketchChartData(String strName, String strToolTip, ArrayList<SLChartData> dataList1, ArrayList<SLChartData> dataList2) {
		
		ChartData cd2 = new ChartData(strName, "font-size: 14px; font-family: Microsoft YaHei; text-align: center;");
		cd2.setBackgroundColour("#ffffff");
		
		XAxis xa = new XAxis();
		
		int iLargestNum = 0;
		ArrayList<String> labelList = new ArrayList<String>();
		ArrayList<SLChartData> allArrayList = new ArrayList<SLChartData>();
		allArrayList.addAll(dataList1);
		allArrayList.addAll(dataList2);
		for (SLChartData smsChartData : allArrayList) {
			labelList.add(smsChartData.getName());
			iLargestNum = smsChartData.getValue() > iLargestNum ? smsChartData.getValue() : iLargestNum;
		}
		xa.setLabels(labelList);

		xa.getLabels().setColour("#000000");
		xa.setGridColour("#aaaaff");
		xa.setColour("#000000");
		cd2.setXAxis(xa);
		
		YAxis ya = new YAxis();
		ya.setRange(0, iLargestNum);
//		ya.setSteps(iLargestNum);
		ya.setGridColour("#aaaaff");
		ya.setColour("#000000");
		cd2.setYAxisLabelStyle(10, "#000000");
		cd2.setYAxis(ya);
		
		SketchBarChart sketch = new SketchBarChart("#00AA00", "#009900", 6);
		sketch.setTooltip("#val#"+strToolTip);
		for (SLChartData smsChartData : dataList1) {
			sketch.addValues(smsChartData.getValue());
		}
		for (SLChartData smsChartData : dataList2) {
			SketchBarChart.SketchBar skb = new SketchBarChart.SketchBar(smsChartData.getValue());
			skb.setColour("#6666ff");
			skb.setTooltip("#val#"+strToolTip);
			sketch.addBars(skb);
		}
		
		cd2.addElements(sketch);
		return cd2;
	}
	
}
