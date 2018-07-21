package com.redhat.demo.dm.ccfraud.domain;

/**
 * Represent a payment terminal.
 * <p/>
 * This class is immutable
 * 
 * @author <a href="mailto:ddoyle@redhat.com">Duncan Doyle</a>
 */
public class Terminal {

	private long id;
	private CountryCode countryCode;
	
	public Terminal(long id, CountryCode countryCode) {
		this.id = id;
		this.countryCode = countryCode;
	}

	public long getId() {
		return id;
	}

	public CountryCode getCountryCode() {
		return countryCode;
	}
	
}
