package com.transactions.resources;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import com.transactions.dao.TransactionsDaoMemory;
import com.transactions.model.Statistic;
import com.transactions.model.Transaction;

public class TransactionResourceTest {
	
	@Before
	public void reset() {
		TransactionsDaoMemory.getInstance().reset();
	}

	@Test
	public void testInsertInvalid() {
		TransactionResource tested = new TransactionResource();
		StatisticsResource stat = new StatisticsResource();
		Transaction t = new Transaction();
		Response response = tested.insert(t);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		Statistic result = (Statistic) stat.getPastMinute().getEntity();
		assertEquals(0, result.getCount());
	}

	@Test
	public void testInsertOld() {
		TransactionResource tested = new TransactionResource();
		StatisticsResource stat = new StatisticsResource();
		long oldestTimestamp = (new Date()).getTime() - 60 * 1000;
		Response response = tested.insert(new Transaction(oldestTimestamp - 10, 1.0));
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
		Statistic result = (Statistic) stat.getPastMinute().getEntity();
		assertEquals(0, result.getCount());
	}

	@Test
	public void testInsertFuture() {
		TransactionResource tested = new TransactionResource();
		StatisticsResource stat = new StatisticsResource();
		Response response = tested.insert(new Transaction((new Date()).getTime() + 100 * 1000, 1.0));
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		Statistic result = (Statistic) stat.getPastMinute().getEntity();
		assertEquals(0, result.getCount());
	}
	
	@Test
	public void testInsertValid() {
		TransactionResource tested = new TransactionResource();
		StatisticsResource stat = new StatisticsResource();
		Response response = tested.insert(new Transaction((new Date()).getTime(), 1.0));
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		Statistic result = (Statistic) stat.getPastMinute().getEntity();
		assertEquals(1, result.getCount());
		assertEquals(new Double(1.0), new Double(result.getSum()));
	}
	
	@Test
	public void testMany() {
		TransactionResource tested = new TransactionResource();
		StatisticsResource stat = new StatisticsResource();
		
		long start = (new Date()).getTime();
		for (int i = 0; i < 10; i += 1) {
			for (int j = 0; j < 10; j += 1) {
				tested.insert(new Transaction(start - (i * 13) * 1000 - j, 1.0));
			}
		}
		Statistic result = (Statistic) stat.getPastMinute().getEntity();
		assertEquals(50, result.getCount());
	}
}
