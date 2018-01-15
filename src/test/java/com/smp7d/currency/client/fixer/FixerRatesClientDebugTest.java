package com.smp7d.currency.client.fixer;

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
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(rates);
		System.out.println(json);
	}
}
