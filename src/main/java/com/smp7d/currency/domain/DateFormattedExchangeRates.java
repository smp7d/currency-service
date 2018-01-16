package com.smp7d.currency.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * Exchange rates with DateFormatted. Used for simpler
 * serialization/deserialization. Can probably add custom serialization where
 * needed as an alternative.
 *
 */
public class DateFormattedExchangeRates {

	@ApiModelProperty(notes = "The base for the rates")
	private CurrencyCode base;
	@ApiModelProperty(notes = "The date for the rates")
	private String date;
	@ApiModelProperty(notes = "A Map of the rates for each currency relative to the base")
	private Map<CurrencyCode, Float> rates;

	public CurrencyCode getBase() {
		return base;
	}

	public void setBase(CurrencyCode base) {
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
