package com.transactions.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
		Transaction t1 = new Transaction();
		t1.setAmount(1.0);
		t1.setTimestamp((new Date()).getTime());
		
		TransactionsDaoMemory tested = TransactionsDaoMemory.getInstance();
		try {
			tested.create(t1);
			assertTrue(findTransactions(tested, t1).size() == 1);
		} catch (CloneNotSupportedException e) {
			fail("Didn't expect to fail");
		}
	}
	
	@Test
	public void testCreate2() {
		Transaction t1 = new Transaction();
		t1.setAmount(1.0);
		t1.setTimestamp((new Date()).getTime());
		
		TransactionsDaoMemory tested = TransactionsDaoMemory.getInstance();
		try {
			tested.create(t1);
			tested.create(t1);
			assertTrue(findTransactions(tested, t1).size() == 2);
		} catch (CloneNotSupportedException e) {
			fail("Didn't expect to fail");
		}
	}
	
	@Test
	public void testLoadPeriod() {
		TransactionsDaoMemory tested = TransactionsDaoMemory.getInstance();

		long periodStart = (new Date()).getTime();
		
		Transaction t1 = new Transaction();
		t1.setAmount(1.0);
		t1.setTimestamp(periodStart - 10);
		Transaction t2 = new Transaction();
		t2.setAmount(2.0);
		t2.setTimestamp(periodStart + 10 * 1000);
		
		try {
			tested.create(t1);
			tested.create(t2);
			List<Transaction> periodTransactions = tested.loadForPeriod(periodStart);
			assertEquals(1, periodTransactions.size());
			assertEquals(t2.getAmount(), periodTransactions.get(0).getAmount());
			assertEquals(t2.getTimestamp(), periodTransactions.get(0).getTimestamp());
		} catch (CloneNotSupportedException e) {
			fail("Didn't expect to fail");
		}
	}
	
	private List<Transaction> findTransactions(TransactionsDao dao, Transaction t) {
		List<Transaction> all = dao.loadAll();
		List<Transaction> result = new ArrayList<Transaction>(all.size());
		for (Transaction next : all) {
			if (next.getTimestamp() == t.getTimestamp() && next.getAmount() == t.getAmount()) {
				result.add(t); 
			}
		}
		return result;
	}
}
