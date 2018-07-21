package com.redhat.demo.dm.ccfraud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.demo.dm.ccfraud.domain.CountryCode;
import com.redhat.demo.dm.ccfraud.domain.CreditCardTransaction;
import com.redhat.demo.dm.ccfraud.domain.Terminal;

/**
 * Loads {@link CreditCardTransaction} objects from the given (CSV) file.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class FactsLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(FactsLoader.class);

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd:HHmmssSSS");

	
	/**
	 * Loads {@link CreditCardTransaction CreditCardTransactions} from the given {@link File}.
	 * 
	 * @param eventsInputStream
	 * @return
	 */
	public static Collection<CreditCardTransaction> loadTransactions(File factsFile) {

		List<CreditCardTransaction> ccts = Collections.EMPTY_LIST;
		try (BufferedReader br =  new BufferedReader(new FileReader(factsFile))) {
			ccts = loadFacts(br);
		} catch (FileNotFoundException fnfe) {
			String message = "File not found.";
			LOGGER.error(message, fnfe);
			throw new IllegalArgumentException(message, fnfe);
		} catch (IOException ioe) {
			String message = "Error processing reader";
			LOGGER.error(message, ioe);
			throw new RuntimeException(message, ioe);
		}
		return ccts;
	}
	
	/**
	 * Loads {@link CreditCardTransaction CreditCardTransactions} from the given {@link InputStream}
	 * 
	 * @param eventsInputStream
	 * @return
	 */
	public static List<CreditCardTransaction> loadFacts(InputStream eventsInputStream) {
		List<CreditCardTransaction> ccts = Collections.EMPTY_LIST;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(eventsInputStream))) {
			ccts = loadFacts(br);
		} catch (IOException ioe) {
			String message = "Error processing reader";
			LOGGER.error(message, ioe);
			throw new RuntimeException(message, ioe);
		}
		return ccts;

	}

	/**
	 * Loads all {@link CreditCardTransaction CreditCardTransactions} from the given {@link Reader} and closes the {@link Reader}.
	 * 
	 * @param reader
	 * @return
	 */
	private static List<CreditCardTransaction> loadFacts(BufferedReader reader) {
		List<CreditCardTransaction> eventList = new ArrayList<CreditCardTransaction>();
			
		try {
			String nextLine;
			while ((nextLine = reader.readLine()) != null) {
				if (!nextLine.startsWith("#")) {
					CreditCardTransaction ccTransaction = readFact(nextLine);
					if (ccTransaction != null) {
						eventList.add(ccTransaction);
					}
				}
			}
		} catch (IOException ioe) {
			throw new RuntimeException("Got an IO exception while reading events.", ioe);
		}
		return eventList;
	}

	/**
	 * Layout of a CreditCardTransaction line has to be {transactionID}, {creditCardNumber}, {amount}, {timestamp}, {terminalID}, {terminalCountryCode}.
	 * 
	 * @param line
	 *            the line to parse.
	 * @return the {@link CreditCardTransaction}
	 */
	private static CreditCardTransaction readFact(String line) {
		String[] transactionData = line.split(",");
		if (transactionData.length != 6) {
			String message = "Unable to parse string: " + line;
			LOGGER.error(message);
			throw new RuntimeException(message);
		}
		CreditCardTransaction ccTransaction = null;
		try {
			Terminal terminal = new Terminal(Long.parseLong(transactionData[4].trim()), CountryCode.valueOf(transactionData[5].trim()));
			BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(transactionData[2].trim()));
			ccTransaction = new CreditCardTransaction(Long.parseLong(transactionData[0].trim()), Long.parseLong(transactionData[1].trim()),
					amount, DATE_FORMAT.parse(transactionData[3].trim()).getTime(), terminal);

		} catch (NumberFormatException nfe) {
			LOGGER.error("Error parsing line: " + line, nfe);
		} catch (ParseException pe) {
			LOGGER.error("Error parsing line: " + line, pe);
		} 
		return ccTransaction;

	}

}
