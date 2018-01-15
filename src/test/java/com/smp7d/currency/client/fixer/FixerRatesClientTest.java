package com.smp7d.currency.client.fixer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.HashMap;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.smp7d.currency.domain.CurrencyCode;
import com.smp7d.currency.domain.DateFormattedExchangeRates;
import com.smp7d.currency.domain.ExchangeRates;

public class FixerRatesClientTest {

	@Test
	public void testRetrieveRates() {
		FixerRatesClient client = new FixerRatesClient();

		CurrencyCode code = CurrencyCode.USD;
		DateFormattedExchangeRates ratesFromService = fakeFormattedDateRates();

		RestTemplate stubbedRestTemplate = mock(RestTemplate.class);
		ResponseEntity<DateFormattedExchangeRates> fakeResponseEntity = new ResponseEntity<DateFormattedExchangeRates>(
				ratesFromService, HttpStatus.OK);
		when(
				stubbedRestTemplate.exchange(FixerRatesClient.ENDPOINT_NO_DATE,
						HttpMethod.GET, null, DateFormattedExchangeRates.class,
						code.toString())).thenReturn(fakeResponseEntity);
		client.setRestTemplate(stubbedRestTemplate);

		ExchangeRates datedRates = client.retrieveRates(code);

		performAssertions(ratesFromService, datedRates);
	}

	@Test
	public void testRetrieveRatesForSameDayTimezone() {
		FixerRatesClient client = new FixerRatesClient();

		CurrencyCode code = CurrencyCode.USD;
		DateFormattedExchangeRates ratesFromService = fakeFormattedDateRates();

		RestTemplate stubbedRestTemplate = mock(RestTemplate.class);
		ResponseEntity<DateFormattedExchangeRates> fakeResponseEntity = new ResponseEntity<DateFormattedExchangeRates>(
				ratesFromService, HttpStatus.OK);
		ZonedDateTime inputDate = ZonedDateTime.parse("2004-01-02T12:00:00Z");
		String expectedFormattedDate = "2004-01-02";
		when(
				stubbedRestTemplate.exchange(
						FixerRatesClient.ENDPOINT_WITH_DATE, HttpMethod.GET,
						null, DateFormattedExchangeRates.class,
						expectedFormattedDate, code.toString())).thenReturn(
				fakeResponseEntity);
		client.setRestTemplate(stubbedRestTemplate);

		ExchangeRates datedRates = client.retieveRates(code, inputDate);

		performAssertions(ratesFromService, datedRates);
	}

	/**
	 * Example would be if we check for 11PM UTC which is actually next day in
	 * Central European time so needs to call Fixer.io for that day instead
	 */
	@Test
	public void testRetrieveRatesForDifferentDayTimezone() {
		FixerRatesClient client = new FixerRatesClient();

		CurrencyCode code = CurrencyCode.USD;
		DateFormattedExchangeRates ratesFromService = fakeFormattedDateRates();

		RestTemplate stubbedRestTemplate = mock(RestTemplate.class);
		ResponseEntity<DateFormattedExchangeRates> fakeResponseEntity = new ResponseEntity<DateFormattedExchangeRates>(
				ratesFromService, HttpStatus.OK);
		ZonedDateTime inputDate = ZonedDateTime.parse("2004-01-01T23:00:00Z");
		String expectedFormattedDate = "2004-01-02";
		when(
				stubbedRestTemplate.exchange(
						FixerRatesClient.ENDPOINT_WITH_DATE, HttpMethod.GET,
						null, DateFormattedExchangeRates.class,
						expectedFormattedDate, code.toString())).thenReturn(
				fakeResponseEntity);
		client.setRestTemplate(stubbedRestTemplate);

		ExchangeRates datedRates = client.retieveRates(code, inputDate);

		performAssertions(ratesFromService, datedRates);
	}

	private void performAssertions(DateFormattedExchangeRates ratesFromService,
			ExchangeRates foundRates) {
		assertThat(foundRates.getBase(), is(ratesFromService.getBase()));
		assertThat(foundRates.getRates(),
				is(sameInstance(ratesFromService.getRates())));
		assertThat(foundRates.getDate().getHour(), is(0));
		assertThat(foundRates.getDate().getMinute(), is(0));
		assertThat(foundRates.getDate().getSecond(), is(0));
		assertThat(foundRates.getDate().getNano(), is(0));
		assertThat(foundRates.getDate().getDayOfMonth(),
				is(extractDay(ratesFromService.getDate())));
		assertThat(foundRates.getDate().getYear(),
				is(extractYear(ratesFromService.getDate())));
		assertThat(foundRates.getDate().getMonth().getValue(),
				is(extractMonth(ratesFromService.getDate())));
	}

	private int extractMonth(String date) {
		// TODO should find a better way of working with dates when we have
		// more...time
		return Integer.parseInt(date.substring(5, 7));
	}

	private int extractYear(String date) {
		// TODO should find a better way of working with dates when we have
		// more...time
		return Integer.parseInt(date.substring(0, 4));
	}

	private int extractDay(String date) {
		// TODO should find a better way of working with dates when we have
		// more...time
		return Integer.parseInt(date.substring(8, 10));
	}

	private DateFormattedExchangeRates fakeFormattedDateRates() {
		DateFormattedExchangeRates rates = new DateFormattedExchangeRates();
		rates.setBase(CurrencyCode.USD);
		HashMap<CurrencyCode, Float> exchangeRates = new HashMap<CurrencyCode, Float>();
		exchangeRates.put(CurrencyCode.EUR, .81234f);
		rates.setRates(exchangeRates);
		rates.setDate("2017-01-02");

		return rates;
	}
}
