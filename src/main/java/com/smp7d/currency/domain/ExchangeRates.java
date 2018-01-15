package com.smp7d.currency.domain;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * A "set" of exchange rates for a particular currency
 *
 */
public class ExchangeRates {
	private String base;
	private ZonedDateTime date;
	private Map<CurrencyCode, Float> rates;

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}

	public Map<CurrencyCode, Float> getRates() {
		return rates;
	}

	public void setRates(Map<CurrencyCode, Float> rates) {
		this.rates = rates;
	}
}
