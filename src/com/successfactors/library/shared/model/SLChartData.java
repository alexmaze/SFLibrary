package com.successfactors.library.shared.model;

import java.io.Serializable;

import com.rednels.ofcgwt.client.model.elements.PieChart.Slice;

@SuppressWarnings("serial")
public class SLChartData implements Serializable {
	
	private String name;
	private int value;
	
	private String date;
	
	public SLChartData() {
		
	}
	
	public SLChartData(String strName, int intValue) {
		name = strName;
		value = intValue;
	}
	public SLChartData(String strName, int intValue, String strDate) {
		name = strName;
		value = intValue;
		date = strDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public Slice toSlice() {
		return new Slice(value, name);
	}

	public void increaseOne() {
		value++;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
