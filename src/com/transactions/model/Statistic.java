package com.transactions.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class represents transaction statistic data.
 */
@XmlRootElement
public class Statistic {

	/** The sum of all transactions amounts. */
	private double sum;
	
	/** The number of all transactions aggregated by the statistic. */
	private int count;
	
	/** The minimal transaction amount for the statistic period. */
	private double min;
	
	/** The maximal transaction amount for the statistic period. */
	private double max;
	
	/** Statistic period start time stamp */
	private long timestamp;
	
	/**
	 * Instantiates a new statistic.
	 */
	public Statistic() {
		
	}
	
	/**
	 * Instantiates a new statistic with the starting timestamp
	 *
	 * @param timestamp the timestamp value
	 */
	public Statistic(long timestamp) {
		setTimestamp(timestamp);
	}
	
	/**
	 * Appends another statistic resulting in aggregated statistic.
	 *
	 * @param other the other statistic
	 */
	public void append(Statistic other) {
		sum += other.getSum();
		count += other.getCount();
		if (min != 0 && other.getMin() != 0) {
			min = Math.min(min, other.getMin());
		} else if (other.getMin() != 0) {
			min = other.getMin();
		}
		if (max != 0 && other.getMax() != 0) {
			max = Math.max(max, other.getMax());
		} else if (other.getMax() != 0) {
			max = other.getMax();
		}
	}
	
	/**
	 * Appends another transaction to the statistic
	 *
	 * @param transaction the transaction to be appended
	 */
	public void append(Transaction transaction) {
		sum += transaction.getAmount();
		count += 1;
		if (min != 0) {
			min = Math.min(min, transaction.getAmount());
		} else {
			min = transaction.getAmount();
		}
		if (max != 0) {
			max = Math.max(max, transaction.getAmount());
		} else {
			max = transaction.getAmount();
		}
	}

	/**
	 * Gets the sum.
	 *
	 * @return the sum
	 */
	public double getSum() {
		return sum;
	}

	/**
	 * Sets the sum.
	 *
	 * @param sum the new sum
	 */
	public void setSum(double sum) {
		this.sum = sum;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Gets the average.
	 *
	 * @return the average
	 */
	public double getAvg() {
		return count > 0 ? sum / count : 0.0;
	}

	/**
	 * Gets the min.
	 *
	 * @return the min
	 */
	public double getMin() {
		return min;
	}

	/**
	 * Sets the min.
	 *
	 * @param min the new min
	 */
	public void setMin(double min) {
		this.min = min;
	}

	/**
	 * Gets the max.
	 *
	 * @return the max
	 */
	public double getMax() {
		return max;
	}

	/**
	 * Sets the max.
	 *
	 * @param max the new max
	 */
	public void setMax(double max) {
		this.max = max;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	@JsonProperty
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	@JsonIgnore
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
