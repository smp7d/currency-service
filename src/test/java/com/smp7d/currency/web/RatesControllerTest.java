package com.smp7d.currency.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import com.smp7d.currency.domain.DateFormattedExchangeRates;
import com.smp7d.currency.service.RatesService;

public class RatesControllerTest {

	@Test
	public void testLoadWhenNoTimeSpecified() {
		RatesController controller = new RatesController();
		RatesService stubbedService = mock(RatesService.class);
		controller.setService(stubbedService);
		String base = RandomStringUtils.randomAlphabetic(3);
		DateFormattedExchangeRates fakeRates = new DateFormattedExchangeRates();
		when(stubbedService.retrieveLatestRates(base, Optional.empty()))
				.thenReturn(fakeRates);

		DateFormattedExchangeRates result = controller.load(base, null, null);

		assertThat(result, is(sameInstance(fakeRates)));
	}

	@Test
	public void testLoadWhenTimeSpecified() {
		RatesController controller = new RatesController();
		RatesService stubbedService = mock(RatesService.class);
		controller.setService(stubbedService);
		String base = RandomStringUtils.randomAlphabetic(3);
		String time = RandomStringUtils.randomAlphabetic(10);
		DateFormattedExchangeRates fakeRates = new DateFormattedExchangeRates();
		when(stubbedService.retrieveRates(base, time, Optional.empty()))
				.thenReturn(fakeRates);

		DateFormattedExchangeRates result = controller.load(base, time, null);

		assertThat(result, is(sameInstance(fakeRates)));
	}
}
