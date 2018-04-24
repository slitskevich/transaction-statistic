package com.transactions.dao;

import com.transactions.data.History;
import com.transactions.model.Statistic;
import com.transactions.model.Transaction;

/**
 * Transactions DAO storing all data in memory. Singleton with synchronized methods
 * to ensure thread safety.
 */
public class TransactionsDaoMemory implements TransactionsDao {

	private static Object mutex = new Object();
	
	/** The instance. */
	private static TransactionsDaoMemory instance = null;
	
	private History history = new History(60);

	/**
	 * Instantiates a new transactions dao memory.
	 */
	protected TransactionsDaoMemory() {
    }

	/**
	 * Gets the single instance of TransactionsDaoMemory.
	 *
	 * @return single instance of TransactionsDaoMemory
	 */
	public static TransactionsDaoMemory getInstance() {
		TransactionsDaoMemory result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new TransactionsDaoMemory();
			}
		}
		return result;
	}
	
	/**
	 * Resets all the stored data
	 */
	public void reset() {
		history.reset();
	}

	/* (non-Javadoc)
	 * @see com.transactions.dao.TransactionsDao#create(com.transactions.model.Transaction)
	 */
	@Override
	public boolean create(Transaction transaction) throws CloneNotSupportedException {
		return history.update(transaction);
	}
	
	/* (non-Javadoc)
	 * @see com.transactions.dao.TransactionsDao#getMinuteStatistic()
	 */
	@Override
	public Statistic getMinuteStatistic() {
		return history.getMinuteStatistic();
	}
	
	History getHistory() {
		return history;
	}
}
