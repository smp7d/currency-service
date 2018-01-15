package com.smp7d.currency.client.fixer;

import java.time.ZonedDateTime;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smp7d.currency.client.fixer.FixerRatesClient;
import com.smp7d.currency.domain.CurrencyCode;
import com.smp7d.currency.domain.ExchangeRates;
import com.smp7d.currency.test.DebugTests;

/**
 * Used just to debug actual calls to Fixer.io. Not unit tests! Should not be
 * run in regression suite.
 *
 */

public class FixerRatesClientDebugTest {

	@Test
	@Category(DebugTests.class)
	public void testRetieveRates() {
		FixerRatesClient client = new FixerRatesClient();
		RestTemplate restTemplate = new RestTemplate();
		client.setRestTemplate(restTemplate);

		ExchangeRates rates = client.retieveRates(CurrencyCode.USD);

		prettyPrint(rates);
	}

	@Test
	@Category(DebugTests.class)
	public void testRetieveRatesForTime() {
		FixerRatesClient client = new FixerRatesClient();
		RestTemplate restTemplate = new RestTemplate();
		client.setRestTemplate(restTemplate);

		ZonedDateTime time = ZonedDateTime.parse("2004-01-02T23:00:00Z");
		ExchangeRates rates = client.retieveRates(CurrencyCode.USD, time);

		prettyPrint(rates);
	}

	private void prettyPrint(ExchangeRates rates) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(rates);
		// TODO use logger instead
		System.out.println(json);
	}
}
