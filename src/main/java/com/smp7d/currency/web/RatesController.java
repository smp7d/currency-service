package com.smp7d.currency.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smp7d.currency.domain.DateFormattedExchangeRates;
import com.smp7d.currency.service.RatesService;

@RestController
public class RatesController {
	@Autowired
	private RatesService service;

	// TODO add documentation for this API endpoint
	@RequestMapping("/rates")
	public DateFormattedExchangeRates loadLatest(
			@RequestParam("base") String base) {
		return service.retrieveLatestRates(base);
	}

}
