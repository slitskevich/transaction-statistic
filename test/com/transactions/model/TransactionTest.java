package com.transactions.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;

public class TransactionTest {
	
	@Test
	public void testMissingAmount() {
		try {
			Transaction tested = new Transaction();
			tested.setTimestamp((new Date()).getTime());
			tested.validate();
			fail("Expected to fail");
		} catch (Exception ex) {
			assertTrue(ex instanceof ValidationException);
		}
	}
	
	@Test
	public void testInvalidAmount() {
		try {
			Transaction tested = new Transaction();
			tested.setTimestamp((new Date()).getTime());
			tested.setAmount(new Double(-1.0));
			tested.validate();
			fail("Expected to fail");
		} catch (Exception ex) {
			assertTrue(ex instanceof ValidationException);
		}
	}
	
	@Test
	public void testMissingTimestamp() {
		try {
			Transaction tested = new Transaction();
			tested.setAmount(1.0);
			tested.validate();
			fail("Expected to fail");
		} catch (Exception ex) {
			assertTrue(ex instanceof ValidationException);
		}
	}
	
	@Test
	public void testInvalidTimestamp() {
		try {
			Transaction tested = new Transaction();
			tested.setAmount(1.0);
			tested.setTimestamp(-1L);
			tested.validate();
			fail("Expected to fail");
		} catch (Exception ex) {
			assertTrue(ex instanceof ValidationException);
		}
	}
}
