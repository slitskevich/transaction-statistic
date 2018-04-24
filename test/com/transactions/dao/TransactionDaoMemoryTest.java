package com.transactions.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.transactions.data.History;
import com.transactions.data.HistoryItem;
import com.transactions.model.Transaction;

public class TransactionDaoMemoryTest {
	
	@Before
	public void reset() {
		TransactionsDaoMemory.getInstance().reset();
	}

	@Test
	public void testSingleton() {
		TransactionsDaoMemory instanceA = TransactionsDaoMemory.getInstance();
		TransactionsDaoMemory instanceB = TransactionsDaoMemory.getInstance();
		assertEquals(instanceA, instanceB);
	}
	
	@Test
	public void testCreate() {
		long stamp = (new Date()).getTime();
		long normalized = History.normalizedTimestamp(stamp);
		Transaction t1 = new Transaction(stamp, 1.0);
		TransactionsDaoMemory tested = TransactionsDaoMemory.getInstance();
		try {
			tested.create(t1);
			boolean found = false;
			for (HistoryItem next : tested.getHistory().getList()) {
				if (next.getTimestamp() == normalized) {
					assertEquals(new Double(1.0), new Double(next.getStatistic().getSum()));
					assertEquals(1, next.getStatistic().getCount());
					found = true;
				}
			}
			assertTrue(found);
		} catch (CloneNotSupportedException e) {
			fail("Didn't expect to fail");
		}
	}
	
	@Test
	public void testCreate2() {
		long stamp = (new Date()).getTime();
		long normalized = History.normalizedTimestamp(stamp);
		Transaction t1 = new Transaction(stamp, 1.0);
		TransactionsDaoMemory tested = TransactionsDaoMemory.getInstance();
		try {
			tested.create(t1);
			tested.create(t1);
			boolean found = false;
			for (HistoryItem next : tested.getHistory().getList()) {
				if (next.getTimestamp() == normalized) {
					assertEquals(new Double(2.0), new Double(next.getStatistic().getSum()));
					assertEquals(2, next.getStatistic().getCount());
					found = true;
				}
			}
			assertTrue(found);
		} catch (CloneNotSupportedException e) {
			fail("Didn't expect to fail");
		}
	}
}
