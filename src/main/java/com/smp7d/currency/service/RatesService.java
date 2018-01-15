package com.smp7d.currency.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smp7d.currency.client.RatesClient;
import com.smp7d.currency.domain.CurrencyCode;
import com.smp7d.currency.domain.DateFormattedExchangeRates;
import com.smp7d.currency.domain.ExchangeRates;

@Component
public class RatesService {
	@Autowired
	private RatesClient client;

	/**
	 * Retrieve latest available rates
	 * 
	 * @param code
	 *            the currency code for which to retrieve rates
	 * @return the rates
	 */
	public DateFormattedExchangeRates retrieveLatestRates(String code) {
		ExchangeRates retrievedRates = client.retrieveRates(CurrencyCode
				.valueOf(code));
		DateFormattedExchangeRates formattedRates = new DateFormattedExchangeRates();
		formattedRates.setBase(retrievedRates.getBase());
		formattedRates.setRates(retrievedRates.getRates());
		formattedRates.setDate(retrievedRates.getDate().toString());

		return formattedRates;
	}

	public void setClient(RatesClient client) {
		this.client = client;
	}
}