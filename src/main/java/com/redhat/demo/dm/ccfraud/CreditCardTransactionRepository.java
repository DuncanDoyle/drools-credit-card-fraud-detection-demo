package com.redhat.demo.dm.ccfraud;

import java.util.Collection;

import com.redhat.demo.dm.ccfraud.domain.CreditCardTransaction;

/**
 * Repository of credit-card transactions. Provides functionality to retrieve collections of transactions based on the credit-card number
 * and/or timestamp of the transaction.
 * 
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public interface CreditCardTransactionRepository {

	/**
	 * Retrieves all credit-card transactions of the card with the given number.
	 * 
	 * @param creditCardNumber
	 * @return
	 */
	Collection<CreditCardTransaction> getCreditCardTransactionsForCC(long creditCardNumber);

	/**
	 * Retrieves the transactions of this credit-card since the given timestamp.
	 * 
	 * @param creditCardNumber
	 * @param timestamp
	 * @return
	 */
	Collection<CreditCardTransaction> getCreditCardTransactionsForCC(long creditCardNumber, long timestamp);

}
