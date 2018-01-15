package com.smp7d.currency.client;

import com.smp7d.currency.domain.CurrencyCode;
import com.smp7d.currency.domain.ExchangeRates;

public interface RatesClient {

	/**
	 * Retrieve current rates for a currency.
	 * 
	 * @param code
	 *            the currency code
	 * @return the current rates for the currency
	 */
	ExchangeRates retrieveRates(CurrencyCode code);

	/**
	 * Retrieve current rates for a currency for a specific day at midnight CET.
	 * 
	 * @param code
	 *            the currency for which to find rates
	 * @param dateInCentralEuropeanTime
	 *            the date in format YYYY-MM-DD
	 * 
	 * @return the current rates for the currency on that day
	 */
	ExchangeRates retieveRates(CurrencyCode code,
			String dateInCentralEuropeanTime);
}
