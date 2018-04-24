package com.transactions.data;

import com.transactions.model.Statistic;
import com.transactions.model.Transaction;

public class HistoryItem {
	private long timestamp;
	private Statistic statistic;
	
	public HistoryItem(long timestamp) {
		this.timestamp = timestamp;
		this.statistic = new Statistic();
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long periodTimestamp) {
		this.timestamp = periodTimestamp;
	}
	public Statistic getStatistic() {
		return statistic;
	}
	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}
	
	/**
	 * Appends another transaction to the statistic
	 *
	 * @param transaction the transaction to be appended
	 */
	public void append(Transaction transaction) {
		statistic.setSum(statistic.getSum() + transaction.getAmount());
		statistic.setCount(statistic.getCount() + 1);
		if (statistic.getMin() != 0) {
			statistic.setMin(Math.min(statistic.getMin(), transaction.getAmount()));
		} else {
			statistic.setMin(transaction.getAmount());
		}
		if (statistic.getMax() != 0) {
			statistic.setMax(Math.max(statistic.getMax(), transaction.getAmount()));
		} else {
			statistic.setMax(transaction.getAmount());
		}
	}


}
