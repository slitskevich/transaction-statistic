package com.transactions.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.transactions.model.Transaction;

/**
 * Transactions DAO storing all data in memory. Singleton with synchronized methods
 * to ensure thread safety.
 */
public class TransactionsDaoMemory implements TransactionsDao {

	/** The list. */
	private List<Transaction> list = Collections.synchronizedList(new ArrayList<Transaction>());
	
	private static Object mutex = new Object();
	
	/** The instance. */
	private static TransactionsDaoMemory instance = null;

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
		synchronized(list) {
			list.clear();
		}
	}

	/* (non-Javadoc)
	 * @see com.transactions.dao.TransactionsDao#loadAll()
	 */
	@Override
	public synchronized List<Transaction> loadAll() {
		synchronized (list) {
			return list;
		}
	}

	/* (non-Javadoc)
	 * @see com.transactions.dao.TransactionsDao#loadForPeriod(long)
	 */
	@Override
	public synchronized List<Transaction> loadForPeriod(long periodStartTimestamp) {
		synchronized (list) {
			List<Transaction> result = new ArrayList<Transaction>(list.size());
			for (Transaction next : list) {
				if (next.getTimestamp() >= periodStartTimestamp) {
					result.add(next);
				}
			}
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see com.transactions.dao.TransactionsDao#create(com.transactions.model.Transaction)
	 */
	@Override
	public synchronized void create(Transaction transaction) throws CloneNotSupportedException {
		synchronized (list) {
			Transaction clone = transaction.clone();
			clone.setId(list.size());
			list.add(clone);
		}
	}

}
