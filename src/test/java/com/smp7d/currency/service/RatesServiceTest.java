package com.smp7d.currency.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.HashMap;

import org.junit.Test;

import com.smp7d.currency.client.RatesClient;
import com.smp7d.currency.domain.CurrencyCode;
import com.smp7d.currency.domain.DateFormattedExchangeRates;
import com.smp7d.currency.domain.ExchangeRates;

public class RatesServiceTest {

	@Test
	public void testRetieveLatestRates() {
		RatesService service = new RatesService();
		RatesClient stubbedClient = mock(RatesClient.class);
		CurrencyCode code = CurrencyCode.AUD;
		ExchangeRates fakeRates = fakeRates();
		when(stubbedClient.retrieveRates(code)).thenReturn(fakeRates);
		service.setClient(stubbedClient);

		DateFormattedExchangeRates resultingRates = service
				.retrieveLatestRates(code.toString());

		assertThat(resultingRates.getBase(), is(fakeRates.getBase()));
		assertThat(resultingRates.getRates(),
				is(sameInstance(fakeRates.getRates())));
		assertThat(resultingRates.getDate(), is(fakeRates.getDate().toString()));
	}

	private ExchangeRates fakeRates() {
		ExchangeRates exchangeRates = new ExchangeRates();
		exchangeRates.setBase(CurrencyCode.BGN);
		exchangeRates.setDate(ZonedDateTime.parse("2004-01-01T23:00:00Z"));
		exchangeRates.setRates(new HashMap<CurrencyCode, Float>());

		return exchangeRates;
	}
}
