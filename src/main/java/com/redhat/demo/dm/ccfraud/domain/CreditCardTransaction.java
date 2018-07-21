package com.redhat.demo.dm.ccfraud.domain;

import java.math.BigDecimal;

/**
 * Represents a credit-card transaction.
 * <p/>
 * This class is immutable.
 * 
 * @author <a href="mailto:ddoyle@redhat.com">Duncan Doyle</a>
 */
public class CreditCardTransaction {

	private long transactionNumber;
	private long creditCardNumber;
	private BigDecimal amount;
	private long timestamp;
	private Terminal terminal;
	
	public CreditCardTransaction(long transactionNumber, long creditCardNumber, BigDecimal amount, long timestamp, Terminal terminal) {
		this.transactionNumber = transactionNumber;
		this.creditCardNumber = creditCardNumber;
		this.amount = amount;
		this.timestamp = timestamp;
		this.terminal = terminal;
	}

	public long getTransactionNumber() {
		return transactionNumber;
	}
	
	public long getCreditCardNumber() {
		return creditCardNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Terminal getTerminal() {
		return terminal;
	}
	
}
