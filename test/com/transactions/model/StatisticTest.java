package com.transactions.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.transactions.model.Statistic;
import com.transactions.model.Transaction;

public class StatisticTest {
	@Test
	public void testAppendTransaction1() {
		Statistic tested = new Statistic();
		
		long now = (new Date()).getTime();
		tested.append(new Transaction(now - 100, 1.0));
		tested.append(new Transaction(now - 200, 2.0));
		
		assertEquals(new Double(3.0), new Double(tested.getSum()));
		assertEquals(2, tested.getCount());
		assertEquals(new Double(1.0), new Double(tested.getMin()));
		assertEquals(new Double(2.0), new Double(tested.getMax()));
		assertEquals(new Double(1.5), new Double(tested.getAvg()));
	}
	
	@Test
	public void testAppendStatistic() {
		Statistic tested = new Statistic();
		long now = (new Date()).getTime();
		tested.append(new Transaction(now - 100, 1.0));
		tested.append(new Transaction(now - 200, 2.0));

		Statistic other = new Statistic();
		other.append(new Transaction(now - 1200, 3.0));
		
		tested.append(other);
		assertEquals(new Double(6.0), new Double(tested.getSum()));
		assertEquals(3, tested.getCount());
		assertEquals(new Double(1.0), new Double(tested.getMin()));
		assertEquals(new Double(3.0), new Double(tested.getMax()));
		assertEquals(new Double(2.0), new Double(tested.getAvg()));
	}
}
