package com.transactions.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class represents transaction data.
 */
@XmlRootElement
public class Transaction {
	
	/**
	 * Instantiates a new transaction.
	 */
	public Transaction() {
		
	}
	
	/**
	 * Instantiates a new transaction and initializes it with the attributes
	 *
	 * @param timestamp the transaction timestamp
	 * @param amount the transaction amount
	 */
	public Transaction(long timestamp, double amount) {
		this.timestamp = timestamp;
		this.amount = amount;
	}

	/**  Transaction timestamp. */
	private Long timestamp;

	/**  Transaction amount. */
	private Double amount;

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Validates entity attributes. All attributes are considered to be mandatory
	 * and should be positive values
	 *
	 * @throws ValidationException if validation fails
	 */
	public void validate() throws ValidationException {
		if (timestamp == null || timestamp < 0 || timestamp > (new Date()).getTime()) {
			throw new ValidationException("Invalid transaction timestamp");
		} else if (amount == null || amount < 0) {
			throw new ValidationException("Invalid transaction amount");
		}
	}

}
