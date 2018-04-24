package com.transactions.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.transactions.dao.TransactionsDao;
import com.transactions.dao.TransactionsDaoMemory;
import com.transactions.model.Statistic;
import com.transactions.model.Transaction;

public class StatisticsResourceTest {
	
	TransactionsDao dao = TransactionsDaoMemory.getInstance();
	StatisticsResource tested = new StatisticsResource();
	
	@Before
	public void reset() {
		TransactionsDaoMemory.getInstance().reset();
	}

	@Test
	public void testNoData() {
		try {
			assertFalse(dao.create(new Transaction((new Date()).getTime() - 10 * 60 * 1000, 1.0)));
			Statistic result = (Statistic) tested.getPastMinute().getEntity();
			assertEquals(new Double(0.0), new Double(result.getSum()));
			assertEquals(new Double(0.0), new Double(result.getAvg()));
			assertEquals(new Double(0.0), new Double(result.getMin()));
			assertEquals(new Double(0.0), new Double(result.getMax()));
			assertEquals(0, result.getCount());
		} catch (CloneNotSupportedException e) {
			fail("Didn't expect to fail");
		}		
	}
	
	@Test
	public void testValidData() {
		long now = (new Date()).getTime();
		try {
			dao.create(new Transaction(now, 1.0));
			dao.create(new Transaction(now, 2.0));
			Statistic result = (Statistic) tested.getPastMinute().getEntity();
			assertEquals(new Double(3.0), new Double(result.getSum()));
			assertEquals(new Double(1.5), new Double(result.getAvg()));
			assertEquals(new Double(1.0), new Double(result.getMin()));
			assertEquals(new Double(2.0), new Double(result.getMax()));
			assertEquals(2, result.getCount());
		} catch (CloneNotSupportedException e) {
			fail("Didn't expect to fail");
		}
		
	}
}
