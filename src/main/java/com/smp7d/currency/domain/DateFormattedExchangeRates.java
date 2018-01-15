package com.smp7d.currency.domain;

import java.util.Map;

/**
 * Exchange rates with DateFormatted. Used for simpler
 * serialization/deserialization. Can probably add custom serialization where
 * needed as an alternative.
 *
 */
public class DateFormattedExchangeRates {

	private String base;
	private String date;
	private Map<CurrencyCode, Float> rates;

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Map<CurrencyCode, Float> getRates() {
		return rates;
	}

	public void setRates(Map<CurrencyCode, Float> rates) {
		this.rates = rates;
	}
}
