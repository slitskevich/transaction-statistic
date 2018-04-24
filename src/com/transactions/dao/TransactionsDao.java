package com.transactions.dao;

import com.transactions.model.Statistic;
import com.transactions.model.Transaction;

/**
 * General transaction DAO interface specifying necessary operations with transactions
 */
public interface TransactionsDao {
	
	/**
	 * Logs new transaction
	 * @param transaction a new transaction to log
	 * @throws CloneNotSupportedException 
	 */
	public boolean create(Transaction transaction) throws CloneNotSupportedException;
	
	/**
	 * @return builds and returns transaction statistic for past minute
	 */
	public Statistic getMinuteStatistic();

}
