package com.transactions.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class represents transaction data
 */
@XmlRootElement
public class Transaction implements Serializable, Cloneable {

	private static final long serialVersionUID = 721756743894563L;

	/** Transaction unique ID */
	private int id;

	/** Transaction timestamp */
	private Long timestamp;

	/** Transaction amount */
	private Double amount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public Transaction clone() throws CloneNotSupportedException {
		Transaction result = (Transaction) super.clone();
		result.setAmount(amount);
		result.setTimestamp(timestamp);
		return result;
	}

	/**
	 * Validates entity attributes. All attributes are considered to be mandatory
	 * and should be positive values
	 *
	 * @throws ValidationException if validation fails
	 */
	public void validate() throws ValidationException {
		if (this.getTimestamp() == null || this.getTimestamp() < 0 || this.getTimestamp() > (new Date()).getTime()) {
			throw new ValidationException("Invalid transaction timestamp");
		} else if (this.getAmount() == null || this.getAmount() < 0) {
			throw new ValidationException("Invalid transaction amount");
		}
	}

}
