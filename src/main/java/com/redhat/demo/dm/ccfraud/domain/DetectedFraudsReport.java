package com.redhat.demo.dm.ccfraud.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Collects detected fraud.
 * 
 * @author <a href="mailto:ddoyle@redhat.com">Duncan Doyle</a>
 */
public class DetectedFraudsReport {
	
	private final long creditCardNumber;
	
	private final Map<String, Collection<CreditCardTransaction>> detectedFrauds = new HashMap<>();
	
	
	public DetectedFraudsReport(long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	
	public long getCreditCardNumber() {
		return creditCardNumber;
	}

	public Map<String, Collection<CreditCardTransaction>> getDetectedFrauds() {
		return detectedFrauds;
	}

	public void addDetectedFraud(String message, Collection<CreditCardTransaction> ccTransactions) {
		detectedFrauds.put(message, ccTransactions);
	}
	
	
}
