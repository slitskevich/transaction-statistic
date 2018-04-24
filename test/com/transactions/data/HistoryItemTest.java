package com.transactions.data;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.transactions.model.Transaction;

public class HistoryItemTest {
	@Test
	public void testAppendTransaction1() {
		long now = (new Date()).getTime();
		HistoryItem tested = new HistoryItem(now);
		
		tested.append(new Transaction(now - 100, 1.0));
		tested.append(new Transaction(now - 200, 2.0));
		
		assertEquals(new Double(3.0), new Double(tested.getStatistic().getSum()));
		assertEquals(2, tested.getStatistic().getCount());
		assertEquals(new Double(1.0), new Double(tested.getStatistic().getMin()));
		assertEquals(new Double(2.0), new Double(tested.getStatistic().getMax()));
		assertEquals(new Double(1.5), new Double(tested.getStatistic().getAvg()));
	}

	@Test
	public void testAppendStatistic() {
		long now = (new Date()).getTime();
		HistoryItem tested = new HistoryItem(now);

		tested.append(new Transaction(now - 100, 1.0));
		tested.append(new Transaction(now - 200, 2.0));

		HistoryItem other = new HistoryItem(now);
		other.append(new Transaction(now - 1200, 3.0));
		
		tested.getStatistic().append(other.getStatistic());
		assertEquals(new Double(6.0), new Double(tested.getStatistic().getSum()));
		assertEquals(3, tested.getStatistic().getCount());
		assertEquals(new Double(1.0), new Double(tested.getStatistic().getMin()));
		assertEquals(new Double(3.0), new Double(tested.getStatistic().getMax()));
		assertEquals(new Double(2.0), new Double(tested.getStatistic().getAvg()));
	}

}
