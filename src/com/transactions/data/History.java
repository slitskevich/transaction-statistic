package com.transactions.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.transactions.model.Statistic;
import com.transactions.model.Transaction;

/**
 * The data structure to store incoming transaction data and prepare statistic.
 */
public class History {
	
	/** The list of statistics for required statistic period */
	private List<HistoryItem> list = Collections.synchronizedList(new ArrayList<HistoryItem>());
	
	/** The size of the statistic - the number of time units required for analysis. */
	private int size;
	
	/**
	 * Instantiates a new history and initailizes with the provided history size and current moment timestamp
	 *
	 * @param size the size of the history data
	 */
	public History(int size) {
		init(size, currentSecond());
	}
	
	/**
	 * Instantiates a new history and initailizes with the provided history size and provided timestamp
	 *
	 * @param size the size of the history data
	 * @param startTimestamp the starting moment for the history (the most latest or actual moment)
	 */
	History(int size, long startTimestamp) {
		init(size, normalizedTimestamp(startTimestamp));
	}
	
	/**
	 * Initializes the history data with the provided history size and provided timestamp
	 *
	 * @param size the size of the history data
	 * @param startTimestamp the starting moment for the history (the most latest or actual moment)
	 */
	void init(int size, long startTimestamp) {
		this.size = size;
		for (int i = 0; i < size; i += 1) {
			list.add(new HistoryItem(startTimestamp - i * 1000));
		}
	}
	
	/**
	 * Re-initializes the data structure to start with the specified timestamp and removing 
	 * outdated items (older than required history size)
	 *
	 * @param startTimestamp the new history start timestamp
	 */
	void cleanOutdated(long startTimestamp) {
		long start = list.get(0).getTimestamp();
		for (long stamp = start + 1000; stamp <= startTimestamp; stamp += 1000) {
			list.add(0, new HistoryItem(stamp));
			list.remove(list.size() - 1);
		}
	}
	
	/**
	 * Resets the data and re-initializes with the current timestamp
	 */
	public void reset() {
		list.clear();
		init(size, currentSecond());
	}
	
	/**
	 * Updates history with the new transaction
	 *
	 * @param transaction the new transaction
	 * @return true, if transaction updated the history; false, if it is out of scope of it.
	 */
	public boolean update(Transaction transaction) {
		synchronized (list) {
			long periodStart = historyStart();
			if (transaction.getTimestamp() >= periodStart) {
				cleanOutdated(currentSecond());
				long transactionSecond = (transaction.getTimestamp() / 1000) * 1000;
				for (int i = 0; i < list.size(); i += 1) {
					if (list.get(i).getTimestamp() == transactionSecond) {
						list.get(i).append(transaction);
						return true;
					}
				}				
			}
			return false;		
		}
	}
	
	/**
	 * Bulds and returns aggregated statistic for the period starting with current timestamp
	 * and ending a minute ago.
	 *
	 * @return the aggregated history statistic
	 */
	public Statistic getMinuteStatistic() {
		synchronized (list) {
			long periodStart = historyStart();
			Statistic minute = new Statistic();
			for (HistoryItem second : list) {
				if (second.getTimestamp() >= periodStart) {
					minute.append(second.getStatistic());
				} else {
					break;
				}
			}			
			return minute;
		}
	}
	
	/**
	 * Returns a timestamp representing a beginning of the second for the provided timestamp
	 *
	 * @param timestamp the timestamp
	 * @return the beginning of the timestamp second.
	 */
	public static long normalizedTimestamp(long timestamp) {
		return (timestamp / 1000) * 1000;
	}
	
	/**
	 * Timestamp for the beginning of the current moment second.
	 *
	 * @return the current moment second beginning
	 */
	private static long currentSecond() {
		return normalizedTimestamp((new Date()).getTime());
	}
	
	/**
	 * The furthest moment in the past which is still the part of the history
	 *
	 * @return the moment timestamp
	 */
	private long historyStart() {
		return currentSecond() - size * 1000;
	}
	
	/**
	 * @return the history list
	 */
	public List<HistoryItem> getList() {
		return list;
	}
}
