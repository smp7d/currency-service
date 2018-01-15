package com.smp7d.currency.client.fixer;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.smp7d.currency.client.RatesClient;
import com.smp7d.currency.domain.CurrencyCode;
import com.smp7d.currency.domain.DateFormattedExchangeRates;
import com.smp7d.currency.domain.ExchangeRates;

public class FixerRatesClient implements RatesClient {
	static final String ENDPOINT_NO_DATE = "http://api.fixer.io/latest?base={code}";
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ExchangeRates retieveRates(CurrencyCode code) {
		// only using http to avoid dealing with certificates for this exercise
		ResponseEntity<DateFormattedExchangeRates> response = restTemplate.exchange(
				ENDPOINT_NO_DATE, HttpMethod.GET, null,
				DateFormattedExchangeRates.class, code.toString());

		return convert(response.getBody());
	}

	private ExchangeRates convert(DateFormattedExchangeRates response) {
		ExchangeRates rates = new ExchangeRates();
		rates.setBase(response.getBase());
		rates.setRates(response.getRates());
		// Fixer returns in CET which is GMT+1 (would need to spend more time to
		// ensure we are handling this properly - DST and the such)
		ZonedDateTime date = ZonedDateTime.parse(response.getDate()
				+ "T00:00:00+01:00");
		rates.setDate(date);

		return rates;
	}

	@Override
	public ExchangeRates retieveRates(CurrencyCode code, ZonedDateTime time) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
