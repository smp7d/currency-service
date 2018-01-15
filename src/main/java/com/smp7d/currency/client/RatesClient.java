package com.smp7d.currency.client;

import java.time.ZonedDateTime;

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
	ExchangeRates retieveRates(CurrencyCode code);

	/**
	 * Retrieve rates for a currency at a specified time.
	 * 
	 * @param code
	 *            the currency code
	 * @param time
	 *            the time for which to fetch rates
	 * @return the current rates for the currency at that time
	 */
	ExchangeRates retieveRates(CurrencyCode code, ZonedDateTime time);
}
