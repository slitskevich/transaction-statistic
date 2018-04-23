package com.transactions.dao;

import java.util.List;

import com.transactions.model.Transaction;

/**
 * General transaction DAO interface specifying necessary operations with transactions
 */
public interface TransactionsDao {
	
	/**
	 * @return the list of all logged transactions
	 */
	public List<Transaction> loadAll();
	
	/**
	 * Loads all transactions that are not older than specified timestamp
	 * @param periodStartTimestamp the start of the transactions period
	 * @return the list of transactions for the period
	 */
	public List<Transaction> loadForPeriod(long periodStartTimestamp);
	
	/**
	 * Logs new transaction
	 * @param transaction a new transaction to log
	 * @throws CloneNotSupportedException 
	 */
	public void create(Transaction transaction) throws CloneNotSupportedException;

}
