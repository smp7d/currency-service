package com.smp7d.currency.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Optional;

import org.junit.Test;

import com.smp7d.currency.client.RatesClient;
import com.smp7d.currency.domain.CurrencyCode;
import com.smp7d.currency.domain.DateFormattedExchangeRates;
import com.smp7d.currency.domain.ExchangeRates;

public class RatesServiceTest {

	@Test
	public void testRetrieveLatestRates() {
		RatesService service = new RatesService();
		RatesClient stubbedClient = mock(RatesClient.class);
		CurrencyCode code = CurrencyCode.AUD;
		ExchangeRates fakeRates = fakeRates();
		when(stubbedClient.retrieveRates(code)).thenReturn(fakeRates);
		service.setClient(stubbedClient);

		DateFormattedExchangeRates resultingRates = service
				.retrieveLatestRates(code.toString(), Optional.empty());

		performStandardAssertions(fakeRates, resultingRates);
	}

	@Test
	public void testRetrieveLatestRatesWhenTarget() {
		RatesService service = new RatesService();
		RatesClient stubbedClient = mock(RatesClient.class);
		CurrencyCode code = CurrencyCode.AUD;
		ExchangeRates fakeRates = fakeRates();
		when(stubbedClient.retrieveRates(code)).thenReturn(fakeRates);
		service.setClient(stubbedClient);

		CurrencyCode targetCode = CurrencyCode.USD;
		DateFormattedExchangeRates resultingRates = service
				.retrieveLatestRates(code.toString(),
						Optional.of(targetCode.toString()));

		assertThat(resultingRates.getBase(), is(fakeRates.getBase()));
		assertThat(resultingRates.getRates().size(), is(1));
		assertThat(resultingRates.getRates().get(targetCode), is(fakeRates
				.getRates().get(targetCode)));
		assertThat(resultingRates.getDate(), is(fakeRates.getDate().toString()));
	}

	@Test
	public void testRetrieveLatestRatesForDay() {
		RatesService service = new RatesService();
		RatesClient stubbedClient = mock(RatesClient.class);
		CurrencyCode code = CurrencyCode.AUD;
		ExchangeRates fakeRates = fakeRates();
		when(stubbedClient.retrieveRates(code, "2004-01-01")).thenReturn(
				fakeRates);
		service.setClient(stubbedClient);

		DateFormattedExchangeRates resultingRates = service.retrieveRates(
				code.toString(), "2004-01-01T12:00:00Z", Optional.empty());

		performStandardAssertions(fakeRates, resultingRates);
	}
	
	@Test
	public void testRetrieveLatestRatesForDayWhenTarget() {
		RatesService service = new RatesService();
		RatesClient stubbedClient = mock(RatesClient.class);
		CurrencyCode code = CurrencyCode.AUD;
		ExchangeRates fakeRates = fakeRates();
		when(stubbedClient.retrieveRates(code, "2004-01-01")).thenReturn(
				fakeRates);
		service.setClient(stubbedClient);
		CurrencyCode targetCode = CurrencyCode.USD;

		DateFormattedExchangeRates resultingRates = service.retrieveRates(
				code.toString(), "2004-01-01T12:00:00Z", Optional.of(targetCode.toString()));

		assertThat(resultingRates.getBase(), is(fakeRates.getBase()));
		assertThat(resultingRates.getRates().size(), is(1));
		assertThat(resultingRates.getRates().get(targetCode), is(fakeRates
				.getRates().get(targetCode)));
		assertThat(resultingRates.getDate(), is(fakeRates.getDate().toString()));
	}

	@Test
	public void testRetrieveLatestRatesForDayWhenConversionChangesDay() {
		RatesService service = new RatesService();
		RatesClient stubbedClient = mock(RatesClient.class);
		CurrencyCode code = CurrencyCode.AUD;
		ExchangeRates fakeRates = fakeRates();
		when(stubbedClient.retrieveRates(code, "2004-01-02")).thenReturn(
				fakeRates);
		service.setClient(stubbedClient);

		DateFormattedExchangeRates resultingRates = service.retrieveRates(
				code.toString(), "2004-01-01T23:00:00Z", Optional.empty());

		performStandardAssertions(fakeRates, resultingRates);
	}

	private void performStandardAssertions(ExchangeRates fakeRates,
			DateFormattedExchangeRates resultingRates) {
		assertThat(resultingRates.getBase(), is(fakeRates.getBase()));
		assertThat(resultingRates.getRates(),
				is(sameInstance(fakeRates.getRates())));
		assertThat(resultingRates.getDate(), is(fakeRates.getDate().toString()));
	}

	private ExchangeRates fakeRates() {
		ExchangeRates exchangeRates = new ExchangeRates();
		exchangeRates.setBase(CurrencyCode.BGN);
		exchangeRates.setDate(ZonedDateTime.parse("2004-01-01T23:00:00Z"));
		HashMap<CurrencyCode, Float> rates = new HashMap<CurrencyCode, Float>();
		rates.put(CurrencyCode.USD, 1.0f);
		rates.put(CurrencyCode.EUR, 2.0f);
		exchangeRates.setRates(rates);

		return exchangeRates;
	}
}
