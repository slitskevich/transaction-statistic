package com.transactions.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class represents transaction statistic data
 */
@XmlRootElement
public class Statistic implements Serializable {

	private static final long serialVersionUID = 7982557247315585010L;

	private double sum;
	private int count;
	private double average;
	private double min;
	private double max;

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}
}
