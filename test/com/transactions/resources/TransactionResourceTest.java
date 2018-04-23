package com.transactions.resources;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import com.transactions.dao.TransactionsDaoMemory;
import com.transactions.model.Transaction;

public class TransactionResourceTest {
	
	@Before
	public void reset() {
		TransactionsDaoMemory.getInstance().reset();
	}

	@Test
	public void testInsertInvalid() {
		TransactionResource tested = new TransactionResource();
		Transaction t = new Transaction();
		Response response = tested.insert(t);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void testInsertOld() {
		TransactionResource tested = new TransactionResource();
		long oldestTimestamp = (new Date()).getTime() - 60 * 1000;
		Transaction t = new Transaction();
		t.setAmount(1.0);
		t.setTimestamp(oldestTimestamp - 10);
		Response response = tested.insert(t);
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void testInsertNew() {
		TransactionResource tested = new TransactionResource();
		Transaction t = new Transaction();
		t.setAmount(1.0);
		t.setTimestamp((new Date()).getTime());
		Response response = tested.insert(t);
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testInsertValid() {
		TransactionResource tested = new TransactionResource();
		Transaction t = new Transaction();
		t.setAmount(1.0);
		t.setTimestamp((new Date()).getTime());
		tested.insert(t);
		Transaction[] list = (Transaction[]) tested.getAll().getEntity();
		assertEquals(1, list.length);
		assertEquals(t.getAmount(), list[0].getAmount());
		assertEquals(t.getTimestamp(), list[0].getTimestamp());
	}
}
