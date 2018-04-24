package com.transactions.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.transactions.data.History;
import com.transactions.model.Statistic;
import com.transactions.model.Transaction;

public class HistoryTest {
	
	private static int DEFAULT_SIZE = 60;
	
	@Test
	public void testValidTransaction() {
		History tested = new History(DEFAULT_SIZE);
		long now = (new Date()).getTime();
		assertTrue(tested.update(new Transaction(now - 100, 1.0)));
	}
	
	@Test
	public void testOutdatedTransaction() {
		History tested = new History(DEFAULT_SIZE);
		long now = (new Date()).getTime();
		assertFalse(tested.update(new Transaction(now - 100 * 1000, 1.0)));
	}
	
	@Test
	public void testFutureTransaction() {
		History tested = new History(DEFAULT_SIZE);
		long now = (new Date()).getTime();
		assertFalse(tested.update(new Transaction(now + 100 * 1000, 1.0)));
	}
	
	@Test
	public void testValid1() {
		History tested = new History(DEFAULT_SIZE);
		long now = (new Date()).getTime();
		tested.update(new Transaction(now - 100, 1.0));
		tested.update(new Transaction(now - 200, 2.0));
		tested.update(new Transaction(now - 70 * 1000, 3.0));
		Statistic result = tested.getMinuteStatistic();
		assertEquals(new Double(3.0), new Double(result.getSum()));
		assertEquals(2, result.getCount());
		assertEquals(new Double(1.0), new Double(result.getMin()));
		assertEquals(new Double(2.0), new Double(result.getMax()));		
		assertEquals(new Double(1.5), new Double(result.getAvg()));
	}

	@Test
	public void testValid2() {
		History tested = new History(DEFAULT_SIZE);
		long now = (new Date()).getTime();
		tested.update(new Transaction(now - 100, 1.0));
		tested.update(new Transaction(now - 200, 2.0));
		tested.update(new Transaction(now - 7 * 1000, 3.0));
		Statistic result = tested.getMinuteStatistic();
		assertEquals(new Double(6.0), new Double(result.getSum()));
		assertEquals(3, result.getCount());
		assertEquals(new Double(1.0), new Double(result.getMin()));
		assertEquals(new Double(3.0), new Double(result.getMax()));
		assertEquals(new Double(2.0), new Double(result.getAvg()));
	}
	
	@Test
	public void testCleanOutdated() {
		long testStamp = (new Date()).getTime();
		long initStamp = testStamp - 30 * 1000;
		History tested = new History(DEFAULT_SIZE, initStamp);
		checkHistoryList(initStamp, tested);
		
		tested.cleanOutdated(testStamp);
		checkHistoryList(testStamp, tested);
	}
	
	private void checkHistoryList(long historyEnd, History tested) {
		long normalizedStamp = History.normalizedTimestamp(historyEnd);
		assertEquals(normalizedStamp, tested.getList().get(0).getTimestamp());
		long lastStamp = normalizedStamp - (DEFAULT_SIZE - 1) * 1000; 
		assertEquals(lastStamp, tested.getList().get(DEFAULT_SIZE - 1).getTimestamp());
	}
}
