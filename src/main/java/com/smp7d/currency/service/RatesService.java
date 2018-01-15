package com.smp7d.currency.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
	 * @param targetFilter
	 *            the code for which we want the target rate, if not supplied
	 *            will provide all rates
	 * 
	 * @return the rates
	 */
	public DateFormattedExchangeRates retrieveLatestRates(String code,
			Optional<String> targetFilter) {
		ExchangeRates retrievedRates = client.retrieveRates(CurrencyCode
				.valueOf(code));
		DateFormattedExchangeRates formattedRates = convertToDateFormatted(retrievedRates);

		if (targetFilter.isPresent()) {
			filter(formattedRates, targetFilter.get());
		}

		return formattedRates;
	}

	/**
	 * Retrieve rates for a specific time
	 * 
	 * @param code
	 *            the currency code for which to retrieve rates
	 * @param time
	 *            a formatted time using
	 *            {@link java.time.format.DateTimeFormatter#ISO_ZONED_DATE_TIME}
	 * @param targetFilter
	 *            the code for which we want the target rate, if not supplied
	 *            will provide all rates
	 * 
	 * @return the rates for that day
	 */
	public DateFormattedExchangeRates retrieveRates(String code, String time,
			Optional<String> targetFilter) {
		ZonedDateTime zonedForCentralEuropeanTime = convertTime(ZonedDateTime
				.parse(time));
		String formattedDay = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(
				zonedForCentralEuropeanTime);
		ExchangeRates retrievedRates = client.retrieveRates(
				CurrencyCode.valueOf(code), formattedDay);
		DateFormattedExchangeRates formattedRates = convertToDateFormatted(retrievedRates);

		if (targetFilter.isPresent()) {
			filter(formattedRates, targetFilter.get());
		}
		
		return formattedRates;
	}

	private DateFormattedExchangeRates convertToDateFormatted(
			ExchangeRates retrievedRates) {
		DateFormattedExchangeRates formattedRates = new DateFormattedExchangeRates();
		formattedRates.setBase(retrievedRates.getBase());
		formattedRates.setRates(retrievedRates.getRates());
		formattedRates.setDate(retrievedRates.getDate().toString());
		return formattedRates;
	}

	private void filter(DateFormattedExchangeRates formattedRates,
			String targetFilter) {
		Map<CurrencyCode, Float> filtered = new HashMap<CurrencyCode, Float>();
		CurrencyCode targetCode = CurrencyCode.valueOf(targetFilter);
		Float targetRate = formattedRates.getRates().get(targetCode);
		if (targetCode == null) {
			// TODO create a custom exception for this sort of logic
			throw new RuntimeException("Could not find rate for target: "
					+ targetFilter);
		}
		filtered.put(targetCode, targetRate);
		formattedRates.setRates(filtered);
	}

	/**
	 * Convert to CET
	 */
	private ZonedDateTime convertTime(ZonedDateTime time) {
		return time.withZoneSameInstant(ZoneId.of("GMT+1"));
	}

	public void setClient(RatesClient client) {
		this.client = client;
	}
}
